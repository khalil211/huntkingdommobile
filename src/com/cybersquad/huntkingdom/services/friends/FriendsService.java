/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.friends;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.friends.Friends;
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
public class FriendsService 
{
public boolean resultOK;
    
    public ArrayList<Friends> friendships;
    
    public static FriendsService instance=null;
    private ConnectionRequest req;

    private FriendsService() 
    {
         req = new ConnectionRequest();
    }

    public static FriendsService getInstance() {
        if (instance == null) 
        {
            instance = new FriendsService();
        }
        return instance;
    } 
    
    public boolean addFriend(int d) {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"/api/user/friend/"+cu.id+"/"+d;
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
    
    public Friends parseFriend(String json)
    {
        Friends f = new Friends();
         try {
            
            JSONParser j = new JSONParser();
            Map<String,Object> user = j.parseJSON(new CharArrayReader(json.toCharArray()));

                float id = Float.parseFloat(user.get("id").toString());
                f.setId((int)id);
                f.setFirstuser((int)Float.parseFloat(user.get("firstuser").toString()));
                f.setSeconduser((int)Float.parseFloat(user.get("seconduser").toString()));
                f.setActionUser((int)Float.parseFloat(user.get("actionuser").toString()));
                f.setActionUser((int)Float.parseFloat(user.get("etat").toString()));
    
            } catch (IOException ex) {

            }
            return f;
        }
    
    public ArrayList<Friends> parseFriends(String jsonText){
        try {
            
            friendships=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> pubs = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> friendship : pubs){
                Friends p = new Friends();
                p.setId((int)Float.parseFloat(friendship.get("id").toString()));
                p.setFirstuser((int)Float.parseFloat((friendship.get("firstuser")).toString()));
                p.setSeconduser((int)Float.parseFloat((friendship.get("seconduser")).toString()));
                p.setActionUser((int)Float.parseFloat((friendship.get("actionuser")).toString()));
                p.setEtat((int)Float.parseFloat((friendship.get("etat")).toString()));
                
                friendships.add(p);
            }
            
            
            } catch (IOException ex) {

            }
            return friendships;
        }
    
    public ArrayList<Friends> getMyFriends(int x) {
        
        String url = Statics.BASE_URL+"/api/user/myfriends/"+x;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                friendships = parseFriends(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return friendships;
    }
    
    
    public ArrayList<Friends> getMyInvitations(int x) {
        
        String url = Statics.BASE_URL+"/api/user/myinvitations/"+x;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                friendships = parseFriends(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return friendships;
    }
    
    public ArrayList<Friends> areWeFriends(int x) 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        int y=0;
        String url = Statics.BASE_URL+"/api/user/searchfriend/"+cu.id+"/"+x;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                friendships = parseFriends(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        
        
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return friendships;
    }
    
    public int arewefriends()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        int y=0;
        if (!friendships.isEmpty())
        {
         for (int i = 0; i < friendships.size(); i++) 
        {
            
            if (friendships.get(i).getEtat()==0)
            {
                y=1; // invitation sent
            }
            /*else if (friendships.get(i).getEtat()==cu.id)
            {
                y=3;// My profile
            }*/
            else if (friendships.get(i).getEtat()==1)
            {
                y=2; // friends *_*
            }
        } 
        }
        else
            {
                y=3;
            }
        return y;
    }
    
    public boolean acceptOrDeny( int second, int des) {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"/api/user/acceptinvitations/"+cu.id+"/"+second+"/"+des;
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
}
