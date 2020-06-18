/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.groupe;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.groupe.Groupe;
import com.cybersquad.huntkingdom.entities.groupe.Membership;
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
public class GroupeService 
{
public boolean resultOK;
    
    public ArrayList<Groupe> groupes;
    public ArrayList<Membership> members;
    public ArrayList<Membership> memembers;
    public int x=0;
    
    public static GroupeService instance=null;
    private ConnectionRequest req;

    private GroupeService() 
    {
         req = new ConnectionRequest();
    }

    public static GroupeService getInstance() {
        if (instance == null) 
        {
            instance = new GroupeService();
        }
        return instance;
    }
     
    public boolean createGroupe(String name, String desc) {
        String url = Statics.BASE_URL+"/api/user/creategroupe/"+name+"/"+desc;
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
    
    public ArrayList<Groupe> searchGroups() {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"/api/user/searchgroup/"+cu.search;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                groupes = parseGroupes(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return groupes;
    }
    
    public boolean joinGroupe(int id, int gid, int role) {
        String url = Statics.BASE_URL+"/api/user/addmember/"+id+"/"+gid+"/"+role;
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
    
    public ArrayList<Groupe> parseGroupes(String jsonText){
        try {
            groupes=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                Groupe g = new Groupe();
                float id = Float.parseFloat(obj.get("id").toString());
                g.setId((int)id);
                g.setName(obj.get("name").toString());
                g.setDescription(obj.get("description").toString());
                groupes.add(g);
            }
            
            
            } catch (IOException ex) {

            }
            return groupes;
        }
    
    public ArrayList<Membership> parseMemberships(String jsonText){
        try {
            members=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> membership : list){
                Membership m = new Membership();
                float id = Float.parseFloat(membership.get("id").toString());
                m.setId((int)id);
                m.setRole((int)Float.parseFloat(membership.get("role").toString()));
                m.setUserId((int)Float.parseFloat(membership.get("userId").toString()));
                m.setGroupId((int)Float.parseFloat(membership.get("groupId").toString()));
                members.add(m);
            }
            
            } catch (IOException ex) {

            }
            return members;
        }
    
    public ArrayList<Membership> parseMememberships(String jsonText){
        try {
            memembers=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> membership : list){
                Membership m = new Membership();
                float id = Float.parseFloat(membership.get("id").toString());
                m.setId((int)id);
                m.setRole((int)Float.parseFloat(membership.get("role").toString()));
                m.setUserId((int)Float.parseFloat(membership.get("userId").toString()));
                m.setGroupId((int)Float.parseFloat(membership.get("groupId").toString()));
                memembers.add(m);
            }
            
            } catch (IOException ex) {

            }
            return memembers;
        }
    
    public ArrayList<Groupe> getAllGroupes() {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        String url = Statics.BASE_URL+"/api/user/allgroupes";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                groupes = parseGroupes(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return groupes;
    }
    
    public ArrayList<Membership> getMyGroupes(int id) {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        String url = Statics.BASE_URL+"/api/user/mygroupeslist/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                members = parseMemberships(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return members;
    }
    
    public int groupeId()
    {
     getAllGroupes();
     int x=0;
     for (int i=0; i<groupes.size(); i++)
     {
      x=groupes.get(i).getId();
     }
     return x;
    }
    
    public Groupe parseGroupe(String json)
    {
        Groupe g = new Groupe();
         try {
            
            JSONParser j = new JSONParser();
            Map<String,Object> gr = j.parseJSON(new CharArrayReader(json.toCharArray()));

                float id = Float.parseFloat(gr.get("id").toString());
                g.setId((int)id);
                g.setName(gr.get("name").toString());
                g.setDescription(gr.get("description").toString());
    
            } catch (IOException ex) {

            }
            return g;
        }
    
    public String findGroupeName(int id) 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        
        String url = Statics.BASE_URL+"/api/user/findgroupe/"+id;
        Groupe s = new Groupe();
        req.setUrl(url);
        req.setPost(false);

        NetworkManager.getInstance().addToQueueAndWait(req);
         s = parseGroupe(new String(req.getResponseData()));
        String r=s.getName();
        //System.out.println("a"+s.getId());
        return r;
    }
    
    public String findGroupeDescription(int id) 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        
        String url = Statics.BASE_URL+"/api/user/findgroupe/"+id;
        Groupe s = new Groupe();
        req.setUrl(url);
        req.setPost(false);

        NetworkManager.getInstance().addToQueueAndWait(req);
         s = parseGroupe(new String(req.getResponseData()));
        String r=s.getDescription();
        //System.out.println("a"+s.getId());
        return r;
    }
    
    public ArrayList<Membership> getMembers(int id) {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        String url = Statics.BASE_URL+"/api/user/getmembers/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                members = parseMemberships(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return members;
    }
    
    public int getNbrMembers(int id) {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        String url = Statics.BASE_URL+"/api/user/getmembers/"+id;
        //int x=0;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                members = parseMemberships(new String(req.getResponseData()));
                req.removeResponseListener(this);
                x=members.size();
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return x;
    }
    
    public ArrayList<Membership> isAMember(int id, int gid) {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        String url = Statics.BASE_URL+"/api/user/member/"+id+"/"+gid;
        //int x=0;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                memembers = parseMememberships(new String(req.getResponseData()));
                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req); 
        System.out.println("aaaccc"+memembers.size());
        return memembers;
    }    
}
