/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.commande;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.commande.Commande;
import com.cybersquad.huntkingdom.utils.CnxRequest;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author khalil
 */
public class CommandeService {
    private ConnectionRequest req;
    private Commande c;
    private ArrayList<Commande> commandes;
    
    public CommandeService() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public Commande getPanier(int userId) {
        String url=Statics.BASE_URL+"/mobile/commande/panier/"+userId;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                c=parseCommande(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return c;
    }
    
    private Commande parseCommande(String json) {
        try {
            c=new Commande();
            JSONParser parser=new JSONParser();
            Map<String, Object> commande=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            c.setId((int)Float.parseFloat(commande.get("id").toString()));
            c.setEtat((int)Float.parseFloat(commande.get("etat").toString()));
            c.setUserId((int)Float.parseFloat(((Map<String, Object>)commande.get("user")).get("id").toString()));
            if (commande.get("date")==null)
                c.setDate("");
            else
                c.setDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)Float.parseFloat(((Map<String, Object>)commande.get("date")).get("timestamp").toString())*1000)));
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return c;
    }
    
    public void modifierCommande(Commande c) {
        String url=Statics.BASE_URL+"/mobile/commande/edit/"+c.getId()+"/"+c.getEtat();
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public ArrayList<Commande> getCommandes(int userId) {
        String url=Statics.BASE_URL+"/mobile/commande/user/"+userId;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                commandes=parseCommandes(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return commandes;
    }
    
    private ArrayList<Commande> parseCommandes(String json) {
        try {
            commandes=new ArrayList<>();
            JSONParser parser=new JSONParser();
            Map<String, Object> commandesJSON=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> commandesRoot=(List<Map<String, Object>>)commandesJSON.get("root");
            for (Map<String, Object> commande : commandesRoot) {
                c=new Commande();
                c.setId((int)Float.parseFloat(commande.get("id").toString()));
                c.setEtat((int)Float.parseFloat(commande.get("etat").toString()));
                c.setUserId((int)Float.parseFloat(((Map<String, Object>)commande.get("user")).get("id").toString()));
                c.setDate(new SimpleDateFormat("dd/MM/yyyy").format(new Date((long)Float.parseFloat(((Map<String, Object>)commande.get("date")).get("timestamp").toString())*1000)));
                commandes.add(c);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return commandes;
    }
    
    public void genererFacture(int idCommande) {
        String url=Statics.BASE_URL+"/mobile/commande/pdf/"+idCommande;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void ajouterPanier(int idUser) {
        String url=Statics.BASE_URL+"/mobile/panier/verifier/"+idUser;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
}
