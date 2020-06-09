/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.user;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.entities.user.User;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author moez
 */
public class UserService 
{
public boolean resultOK;
    
    public ArrayList<User> users;
    
    public static UserService instance=null;
    private ConnectionRequest req;

    private UserService() 
    {
         req = new ConnectionRequest();
    }

    public static UserService getInstance() {
        if (instance == null) 
        {
            instance = new UserService();
        }
        return instance;
    }
    
    
    public boolean addUser(User u) {
        String url = Statics.BASE_URL+"api/user/create?username="+u.getUsername()+"&email="+u.getEmail()+"&password="+u.getPassword();
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
    
    public boolean updatePassword( String password) {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"api/user/updatepassword/"+cu.id+"/"+password;
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


        public ArrayList<User> parseUsers(String jsonText){
        try {
            users=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> list = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> obj : list){
                User u = new User();
                float id = Float.parseFloat(obj.get("id").toString());
                u.setId((int)id);
                u.setUsername(obj.get("username").toString());
                u.setPassword(obj.get("password").toString());
                u.setEmail(obj.get("email").toString());
                users.add(u);
            }
            
            
            } catch (IOException ex) {

            }
            return users;
        }
   
    public User parseUser(String json)
    {
        User u = new User();
         try {
            
            JSONParser j = new JSONParser();
            Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                float id = Float.parseFloat(user.get("id").toString());
                u.setId((int)id);
                u.setUsername(user.get("username").toString());
                u.setPassword(user.get("password").toString());
    
            } catch (IOException ex) {

            }
            return u;
        }
    
    
    public ArrayList<User> getAllUsers() {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        String url = Statics.BASE_URL+"api/user/all";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                users = parseUsers(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return users;
    }
    
    public ArrayList<User> searchUsers() {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"api/user/searchuser/"+cu.search;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                users = parseUsers(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return users;
    }
    
    public User connectUser(User u) 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"api/user/connect/"+u.getUsername();
        User s = new User();
        req.setUrl(url);
        req.setPost(false);

        NetworkManager.getInstance().addToQueueAndWait(req);
         s = parseUser(new String(req.getResponseData()));
                 cu.id=s.getId();
                 cu.username = s.getUsername();
                 cu.password = s.getPassword();
                System.out.println("my username is"+cu.id);
                System.out.println(s.getId());
        //User s=getUser(users);
        System.out.println("a"+s.getId());
        return s;
    }
    
    /*public User connectUser(User u) 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"api/user/connect/"+u.getUsername();
        User s = new User();
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                User s ;
                CurrentUser cu = CurrentUser.CurrentUser();
                s = parseUser(new String(req.getResponseData()));
                req.removeResponseListener(this);
                 cu.id=s.getId();
                 cu.username = s.getUsername();
                 cu.password = s.getPassword();
                System.out.println("my username is"+cu.id);
                System.out.println(s.getId());
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        //User s=getUser(users);
        System.out.println("a"+s.getId());
        return s;
    }*/
    
    
 
    
    public User getUser(ArrayList<User> users) 
    { 
        User u = new User();
        CurrentUser cu = CurrentUser.CurrentUser();
        if (users != null && !users.isEmpty()) { 
        u=users.get(users.size() - 1); 
        }
        System.out.println("aaaxx"+u.getUsername());
        return u;
    }
    
    public String findUserId(int id) 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        
        String url = Statics.BASE_URL+"api/user/find/"+id;
        User s = new User();
        req.setUrl(url);
        req.setPost(false);

        NetworkManager.getInstance().addToQueueAndWait(req);
         s = parseUser(new String(req.getResponseData()));
        String r=s.getUsername();
        //System.out.println("a"+s.getId());
        return r;
    }    
}
