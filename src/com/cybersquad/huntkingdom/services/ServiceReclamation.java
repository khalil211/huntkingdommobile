/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.reclamation;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.reclamation.reclamation;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author marwe
 */
public class ServiceReclamation {
    public ArrayList<reclamation> tasks;
    
    public static ServiceReclamation instance=null;
    public boolean resultOK;
    private ConnectionRequest req;

    public ServiceReclamation() {
         req = new ConnectionRequest();
    }

    public static ServiceReclamation getInstance() {
        if (instance == null) {
            instance = new ServiceReclamation();
        }
        return instance;
    }
    public ArrayList<reclamation> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                reclamation t = new reclamation();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setDescription(obj.get("description").toString());
                t.setType(obj.get("type").toString());
                

                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    public boolean addTask(reclamation task) {
        String url = Statics.BASE_URL + "/reclamation/add?id=" + task.getId()
                + "&user_id=" + task.getUser_id()
                + "&description=" + task.getDescription()
                + "&type=" + task.getType()
                + "&Date_creation=" + task.getDate_creation()
                + "&status=" + task.getStatus()
                + "&response=" + task.getResponse()
                + "&fichier=" + task.getFichier()

                ;

        req.setUrl(url); 
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; // Code HTTP 200 OK 
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);

        return resultOK;
    }
    public ArrayList<reclamation> getAllTasks(){
        String url = Statics.BASE_URL+"/reclamation/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                tasks = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return tasks;
    }
}
