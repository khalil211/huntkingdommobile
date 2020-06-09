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
import com.cybersquad.huntkingdom.entities.produit.CategorieProduit;
import com.cybersquad.huntkingdom.utils.CnxRequest;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author user
 */
public class ServiceCategorieProduit {
    
    private ConnectionRequest req;
    private ArrayList<CategorieProduit> categories;
    
    public ServiceCategorieProduit() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public ArrayList<CategorieProduit> getCategories() {
        String url=Statics.BASE_URL+"/api/produit/categories";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }
    
    private ArrayList<CategorieProduit> parseCategories(String json) {
        categories=new ArrayList<>();
        try {
            JSONParser parser=new JSONParser();
            Map<String, Object> jsonCat=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            ArrayList<Map<String, Object>> liste=(ArrayList<Map<String, Object>>)jsonCat.get("root");
            for (Map<String, Object> c : liste) {
                CategorieProduit cat=new CategorieProduit();
                cat.setId((int)Float.parseFloat(c.get("id").toString()));
                cat.setDescription(c.get("descriptionCat").toString());
                cat.setNom(c.get("nomCat").toString());
                categories.add(cat);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return categories;
    }
}
