/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.appointments;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.appointments.Participant;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marwe
 */
public class ServiceParticipant {
    public ArrayList<Participant> tasks;
    
    public static ServiceParticipant instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    public ServiceParticipant() {
         req = new ConnectionRequest();
    }

    public static ServiceParticipant getInstance() {
        if (instance == null) {
            instance = new ServiceParticipant();
        }
        return instance;
    }
    public ArrayList<Participant> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Participant t = new Participant();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                float user_id = Float.parseFloat(obj.get("user_id").toString());
                t.setUser_id((int)user_id);
                t.setEmail(obj.get("email").toString());
                
                //t.setEnd_date(obj.get("end_date").toString());
                

                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    public boolean addP(Participant task,int eventid) {
        String url = Statics.BASE_URL + "/appointment/" + eventid
                + "?user_id=" + task.getUser_id()
                ;

        req.setUrl(url); 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK 
                System.out.println(req.getResponseData().toString());
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;
    }
}
