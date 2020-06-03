/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.produit;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.produit.Produit;
import com.cybersquad.huntkingdom.utils.CnxRequest;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author user
 */
public class ServiceProduit {
    
    private ConnectionRequest req;
    private ArrayList<Produit> produits;
    
    public ServiceProduit() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public ArrayList<Produit> getProduits() {
        String url=Statics.BASE_URL+"/api/produits";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                parseProduits(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return produits;
    }
    
    private void parseProduits(String json) {
        produits=new ArrayList<>();
        try {
            JSONParser parser=new JSONParser();
            Map<String, Object> jsonProduits=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            ArrayList<Map<String, Object>> liste=(ArrayList<Map<String, Object>>)jsonProduits.get("root");
            for (Map<String, Object> p : liste) {
                Produit produit=new Produit();
                produit.setId((int)Float.parseFloat(p.get("id").toString()));
                produit.setPrix((double)Float.parseFloat(p.get("prixProd").toString()));
                produit.setQuantite((int)Float.parseFloat(p.get("quantiteProd").toString()));
                produit.setCategorie((int)Float.parseFloat(((Map<String, Object>)p.get("categorie")).get("id").toString()));
                produit.setDescription(p.get("descriptionProd").toString());
                produit.setImage(p.get("imageProd").toString());
                produit.setNom(p.get("nomProd").toString());
                produits.add(produit);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
}
