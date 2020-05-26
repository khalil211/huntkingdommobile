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
import com.cybersquad.huntkingdom.entities.animal.Animal;
import com.cybersquad.huntkingdom.utils.CnxRequest;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author G I E
 */
public class AnimalService {
    
    private ConnectionRequest req;
    private ArrayList<Animal> animaux;
    
    public AnimalService() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
    
    public ArrayList<Animal> getAnimaux() {
        String url=Statics.BASE_URL+"/api/animaux";
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                animaux=parseAnimaux(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return animaux;
    }
    
    private ArrayList<Animal> parseAnimaux(String json) {
        try {
            animaux=new ArrayList<>();
            JSONParser parser=new JSONParser();
            Map<String, Object> animauxJson=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            ArrayList<Map<String, Object>> liste=(ArrayList<Map<String, Object>>)animauxJson.get("root");
            for (Map<String, Object> animal : liste) {
                Animal a=new Animal();
                a.setId((int)Float.parseFloat(animal.get("id").toString()));
                a.setDescription(animal.get("description").toString());
                a.setMedias(animal.get("medias").toString());
                a.setNom(animal.get("nom").toString());
                a.setSaison(animal.get("saison").toString());
                a.setZone(animal.get("zone").toString());
                a.setCategorie_id((int)Float.parseFloat(((Map<String, Object>)animal.get("categorie")).get("id").toString()));
                animaux.add(a);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return animaux;
    }
}
