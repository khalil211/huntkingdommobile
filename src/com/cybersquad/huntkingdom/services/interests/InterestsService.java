/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.interests;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.interest.Interests;
import com.cybersquad.huntkingdom.entities.interest.MyInterests;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author moez
 */
public class InterestsService 
{
public boolean resultOK;
    
    public ArrayList<MyInterests> myinterests;
    public ArrayList<Interests> interests;
    
    public static InterestsService instance=null;
    private ConnectionRequest req;

    private InterestsService() 
    {
         req = new ConnectionRequest();
    }

    public static InterestsService getInstance() {
        if (instance == null) 
        {
            instance = new InterestsService();
        }
        return instance;
    }   
    
    public boolean addInterest(String s) {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"/api/user/addmyinterest/"+cu.id+"/"+s;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    }
    
    public ArrayList<MyInterests> parseInterests(String jsonText){
        try {
            
            myinterests=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> pubs = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> interest : pubs){
                MyInterests p = new MyInterests();
                p.setId((int)Float.parseFloat(interest.get("id").toString()));
                p.setUserId((int)Float.parseFloat((interest.get("userId")).toString()));
                p.setMyinterest(interest.get("interest").toString());
                myinterests.add(p);
            }
            
            
            } catch (IOException ex) {

            }
            return myinterests;
        }
    
    public ArrayList<Interests> parseAllInterests(String jsonText){
        try {
            
            interests=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> pubs = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> interest : pubs){
                Interests p = new Interests();
                p.setId((int)Float.parseFloat(interest.get("id").toString()));
                p.setInterest(interest.get("interest").toString());
                interests.add(p);
            }
            
            
            } catch (IOException ex) {

            }
            return interests;
        }
    
    public ArrayList<MyInterests> getMyInterests(int x) {
        
        String url = Statics.BASE_URL+"/api/user/myinterestslist/"+x;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                myinterests = parseInterests(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return myinterests;
    }
    
    public ArrayList<MyInterests> sameInterest(String x) {
        
        String url = Statics.BASE_URL+"/api/user/sameinterest/"+x;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                myinterests = parseInterests(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return myinterests;
    }
    
    
    public ArrayList<Interests> getAllInterests() {
        
        String url = Statics.BASE_URL+"/api/user/allinterests";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                interests = parseAllInterests(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return interests;
    }    
}
