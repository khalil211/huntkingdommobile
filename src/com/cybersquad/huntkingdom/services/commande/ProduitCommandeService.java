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
import com.cybersquad.huntkingdom.entities.commande.ProduitCommande;
import com.cybersquad.huntkingdom.utils.CnxRequest;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author khalil
 */
public class ProduitCommandeService {
    
    private ConnectionRequest req;
    private ArrayList<ProduitCommande> liste;
    
    public ProduitCommandeService() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public void ajouter(int idProduit, int idUser) {
        String url=Statics.BASE_URL+"/mobile/commande/add/"+idProduit+"/"+idUser;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public ArrayList<ProduitCommande> getProduitCommande(Commande c) {
        String url=Statics.BASE_URL+"/mobile/commande/"+c.getId()+"/produits";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                liste=parseProduitCommande(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        c.setDetails(liste);
        return liste;
    }
    
    private ArrayList<ProduitCommande> parseProduitCommande(String json) {
        try {
            liste=new ArrayList<>();
            JSONParser parser=new JSONParser();
            Map<String, Object> produitsJSON=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> produits=(List<Map<String, Object>>)produitsJSON.get("root");
            for (Map<String, Object> produit : produits) {
                ProduitCommande pc=new ProduitCommande();
                pc.setId((int)Float.parseFloat(produit.get("id").toString()));
                pc.setQuantite((int)Float.parseFloat(produit.get("quantite").toString()));
                pc.setCommandeId((int)Float.parseFloat(((Map<String, Object>)produit.get("commande")).get("id").toString()));
                pc.setImage(((Map<String, Object>)produit.get("produit")).get("imageProd").toString());
                pc.setNom(((Map<String, Object>)produit.get("produit")).get("nomProd").toString());
                pc.setPrixUnitaire((double)Float.parseFloat(((Map<String, Object>)produit.get("produit")).get("prixProd").toString()));
                pc.setProduitId((int)Float.parseFloat(((Map<String, Object>)produit.get("produit")).get("id").toString()));
                liste.add(pc);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return liste;
    }
    
    public void modifierProduit(ProduitCommande pc) {
        String url=Statics.BASE_URL+"/mobile/commande/produit/edit/"+pc.getId()+"/"+pc.getQuantite();
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
    
    public void supprimerProduit(ProduitCommande pc) {
        String url=Statics.BASE_URL+"/mobile/commande/produit/delete/"+pc.getId();
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
    }
}
