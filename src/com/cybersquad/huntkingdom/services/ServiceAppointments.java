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
import com.cybersquad.huntkingdom.entities.appointments.appointments;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 *
 * @author marwe
 */
public class ServiceAppointments {
    public ArrayList<appointments> tasks;
    
    public static ServiceAppointments instance=null;
    public boolean resultOK;
    private ConnectionRequest req;
    
    public ServiceAppointments() {
         req = new ConnectionRequest();
    }

    public static ServiceAppointments getInstance() {
        if (instance == null) {
            instance = new ServiceAppointments();
        }
        return instance;
    }
    
    public ArrayList<appointments> getAllTasks(){
        String url = Statics.BASE_URL+"/appointments/Affich";
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
    public ArrayList<appointments> parseTasks(String jsonText){
        try {
            tasks=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                appointments t = new appointments();
                float id = Float.parseFloat(obj.get("id").toString());
                t.setId((int)id);
                t.setTitle(obj.get("title").toString());
                t.setDescription(obj.get("description").toString());
                
                //t.setEnd_date(obj.get("end_date").toString());
                

                tasks.add(t);
            }
            
            
        } catch (IOException ex) {
            
        }
        return tasks;
    }
    
    public boolean getTest(int id , int idv){
        System.out.println("open");
    resultOK = false;
          String url = Statics.BASE_URL+"/scheduler/appointment/test?user_id=" + id + "&id_event=" +idv;
        req.setUrl(url);
        req.setPost(false);
        //System.out.println(id);
        //System.out.println(idv);
        
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                try {
                   // System.out.println(req.getResponseCode());
                    // System.out.println(new String(req.getResponseData()));*
                    String rep = req.getResponseData().toString();
                    JSONParser j = new JSONParser();
                    Map<String,Object> result = new JSONParser().parseJSON(new InputStreamReader(new ByteArrayInputStream(req.getResponseData()), "UTF-8"));
                    String response = (String)result.get("content");
                    System.out.println(response);
                    System.err.println(result);
                    if (response.equals("OK"))
                    {
                        resultOK = true;
                    }else {
                        resultOK = false;
                    }
                    req.removeResponseListener(this);
                } catch (UnsupportedEncodingException ex) {
                    System.out.println(ex);    
                } catch (IOException ex) {
                    System.err.println(ex);    
                }
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        //System.out.println(resultOK);
        return resultOK;
    
    
    }
}
