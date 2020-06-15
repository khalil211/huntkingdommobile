/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.commentaire;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.entities.publication.Commentaire;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.services.user.UserService;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author moez
 */
public class CommentaireService 
{
public boolean resultOK;
    
    public ArrayList<Commentaire> commentaires;
    
    public static CommentaireService instance=null;
    private ConnectionRequest req;

    private CommentaireService() 
    {
         req = new ConnectionRequest();
    }

    public static CommentaireService getInstance() {
        if (instance == null) 
        {
            instance = new CommentaireService();
        }
        return instance;
    } 
    
    public boolean addCommentaire(String text) {
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"/api/user/createcomment/"+cu.id+"/"+cu.targetPubId+"?text="+text;
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
    
    public ArrayList<Commentaire> parseCommentaires(String jsonText){
        try {
            
            commentaires=new ArrayList<>();
            JSONParser j = new JSONParser();
            Map<String,Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));
            
            List<Map<String,Object>> coms = (List<Map<String,Object>>)tasksListJson.get("root");
            for(Map<String,Object> commentaire : coms){
                Commentaire c = new Commentaire();
                float id = Float.parseFloat(commentaire.get("id").toString());
                c.setId((int)id);
                c.setPublicationId((int)Float.parseFloat(((Map<String, Object>)commentaire.get("publication")).get("id").toString()));
                c.setUserId((int)Float.parseFloat(((Map<String, Object>)commentaire.get("user")).get("id").toString()));
                c.setText(commentaire.get("text").toString());
                String r = UserService.getInstance().findUserId(c.userId);
                c.setUsername(r);
                commentaires.add(c);
            }
            
            
            } catch (IOException ex) {

            }
            return commentaires;
        }
    
    public ArrayList<Commentaire> getPubComments() {
        //String url = Statics.BASE_URL+"api/user/connect/moez";
        CurrentUser cu = CurrentUser.CurrentUser();
        String url = Statics.BASE_URL+"/api/user/pubcomments/"+cu.targetPubId;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
         
                commentaires = parseCommentaires(new String(req.getResponseData()));

                req.removeResponseListener(this);
                
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);    
        return commentaires;
    }    
}
