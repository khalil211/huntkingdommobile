/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.animal;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.List;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.animal.CategorieAnimal;
import com.cybersquad.huntkingdom.utils.CnxRequest;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author G I E
 */
public class CategorieAnimalService {
    
    ConnectionRequest req;
    ArrayList<CategorieAnimal> categories;
    
    public CategorieAnimalService() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public ArrayList<CategorieAnimal> getCategories() {
        String url=Statics.BASE_URL+"/api/animaux/categories";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                categories=parseCategories(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return categories;
    }
    
    private ArrayList<CategorieAnimal> parseCategories(String json) {
        try {
            categories=new ArrayList<>();
            JSONParser parser=new JSONParser();
            Map<String, Object> catJson=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            ArrayList<Map<String, Object>> liste=(ArrayList<Map<String, Object>>)catJson.get("root");
            for (Map<String, Object> cat : liste) {
                CategorieAnimal categorie = new CategorieAnimal();
                categorie.setId((int)Float.parseFloat(cat.get("id").toString()));
                categorie.setNom(cat.get("nom").toString());
                categorie.setDescription(cat.get("description").toString());
                categories.add(categorie);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return categories;
    }
}
