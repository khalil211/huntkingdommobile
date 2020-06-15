/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.publication;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.publication.Publication;
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
public class PublicationService 
{
    public boolean resultOK;
    
    public ArrayList<Publication> publications;
    
    public static PublicationService instance=null;
    private ConnectionRequest req;

    private PublicationService() 
    {
         req = new ConnectionRequest();
    }

    public static PublicationService getInstance() {
        if (instance == null) 
        {
            instance = new PublicationService();
        }
        return instance;
    }   

    
    public boolean addPublication(String p) {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"/api/user/createpub?id="+cu.id+"&text="+p;
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
    
    public boolean addGroupePublication(String p) {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"/api/user/grppub/"+cu.id+"/"+cu.targetGroupId+"/"+p;
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
    
    public String replace(String str) 
    {
        //return str;
          //str.replaceAll("","%20");
    return str;
    }
    
    public ArrayList<Publication> parsePubs(String jsonText){
        try {
            
            publications=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> pubs = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> publication : pubs){
                Publication p = new Publication();
                p.setId((int)Float.parseFloat(publication.get("id").toString()));
                p.setUserId((int)Float.parseFloat(((Map<String, Object>)publication.get("user")).get("id").toString()));
                p.setText(publication.get("text").toString());
                
                publications.add(p);
            }
            
            
            } catch (IOException ex) {

            }
            return publications;
        }
    
    public ArrayList<Publication> getAllPubs() {
        
        String url = Statics.BASE_URL+"/api/user/pubs";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                publications = parsePubs(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return publications;
    }
    
    public ArrayList<Publication> getMyPubs(int id) {
        
        String url = Statics.BASE_URL+"/api/user/mypubs/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                publications = parsePubs(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return publications;
    }
    
    public ArrayList<Publication> getGroupePubs(int id) {
        
        String url = Statics.BASE_URL+"/api/user/findgroupepub/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                publications = parsePubs(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return publications;
    }
    
}
