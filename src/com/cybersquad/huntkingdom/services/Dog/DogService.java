/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.Dog;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.messaging.Message;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.cybersquad.huntkingdom.entities.Dog.Dog;
import com.cybersquad.huntkingdom.entities.coach.Coach;
import com.cybersquad.huntkingdom.gui.Dog.ADog;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.utils.CnxRequest;
import com.cybersquad.huntkingdom.utils.Statics;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Amal
 */
public class DogService {
      private ConnectionRequest req;
          private ArrayList<Dog> dogs;
        private Dog d;
        private Coach c;
      public DogService() {
        req=CnxRequest.getInstance().getConnectionRequest();
    }
       
    private ConnectionRequest connectionRequest;
    public static Form listOfBooks;
    public void addBook(Dog book){
        connectionRequest=new ConnectionRequest(){
            @Override
            protected void postResponse() {
                            System.out.println("nom:"+book.getNom()+" Age:"+book.getAge()+" mal"+book.getMaladie()+"race"+book.getRace()+"type"+book.getTypeChase());
                            Dialog d=new Dialog();
                    if(d.show("Demande d'entrainement","Demande envoye","Ok",null))
            { 
             
            }
            }
        };
        connectionRequest.setUrl("http://localhost/shelfie/insert.php?nom=" + book.getNom() + "&age=" + book.getAge()+"&mal="+book.getMaladie()+"&race="+book.getRace()+"&type="+book.getTypeChase());
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    
    public void removeBook(Dog c){   
        
        Dialog d = new Dialog();
            if(d.show("Delete Dog","Do you really want to remove this dog","Yes","No"))
            {             
                connectionRequest = new ConnectionRequest();
                connectionRequest.setUrl("http://localhost/shelfie/remove.php?id=" + c.getId());
                NetworkManager.getInstance().addToQueue(connectionRequest);
                new Home().show();
                d.dispose();
            }
    }

    public void findAllBooks(){
        connectionRequest = new ConnectionRequest() {
        List<Dog> chiens = new ArrayList<>();
            @Override
            protected void readResponse(InputStream in) throws IOException {

                JSONParser json = new JSONParser();
                try {
                    Reader reader = new InputStreamReader(in, "UTF-8");

                    Map<String, Object> data = json.parseJSON(reader);
                    List<Map<String, Object>> content = (List<Map<String, Object>>) data.get("root");
                    chiens.clear();
                  
                    for (Map<String, Object> obj : content) {
                         chiens.add(new Dog(Integer.parseInt((String) obj.get("id")) ,(String)obj.get("nom"),Integer.parseInt((String) obj.get("age")) ,(String)obj.get("maladie"),(String)obj.get("etat"),(String)obj.get("typeChasse"),(String)obj.get("race")/*,convertStringToDate((String) obj.get("dates"))*/)
                        );
                    }
                } catch (IOException err) {
                    Log.e(err);
                }
            }

            @Override
            protected void postResponse() {
                //System.out.println(libs.size());
                listOfBooks = new Form();
                com.codename1.ui.List uiLibsList = new com.codename1.ui.List();
                ArrayList<String> libsNoms = new ArrayList<>();
                for(Dog l :chiens){
                    libsNoms.add(l.getId()+"("+l.getEtat()+")");
                }
                com.codename1.ui.list.DefaultListModel<String> listModel = new com.codename1.ui.list.DefaultListModel<>(libsNoms);
                uiLibsList.setModel(listModel);
                uiLibsList.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent evt) {
                        Dog currentchien = chiens.get(uiLibsList.getCurrentSelected());
                        new ADog(currentchien.getId(),currentchien.getNom(),currentchien.getRace(),currentchien.getTypeChase(),currentchien.getMaladie(),currentchien.getAge(),currentchien.getEtat()).show();
                    }
                });
                listOfBooks.setLayout(new BorderLayout());
                listOfBooks.add(BorderLayout.NORTH,uiLibsList);
                listOfBooks.add(BorderLayout.SOUTH,Statics.createBackBtn());
                listOfBooks.show();             
            }
        };
        connectionRequest.setUrl("http://localhost/shelfie/getbooks.php");
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }
      public Dog getChien(int userId) {
        String url=Statics.BASE_URL+"/api/chiens/monchien/"+userId;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                d=parseChien(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return d;
    }
      private Dog parseChien(String json) {

        try {
           
            JSONParser parser=new JSONParser();
            Map<String, Object> chiensJSON=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> chiensRoot=(List<Map<String, Object>>)chiensJSON.get("root");
            for (Map<String, Object> chien : chiensRoot) {
                d=new Dog();
                d.setId((int)Float.parseFloat(chien.get("id").toString()));
                d.setAge((int)Float.parseFloat(chien.get("age").toString()));
                d.setNote((int)Float.parseFloat(chien.get("note").toString()));
                d.setNom((String)chien.get("nom").toString());

                d.setEtat((String)chien.get("etat").toString());
                d.setRace((String)chien.get("race").toString());
                d.setMaladie((String)chien.get("maladie").toString());
                d.setTypeChase((String)chien.get("typeChasse").toString());
                d.setCoachId((int)Float.parseFloat(((Map<String, Object>)chien.get("coach")).get("id").toString()));
                d.setNomCoach(((Map<String, Object>)(((Map<String, Object>)chien.get("coach")).get("user"))).get("username").toString());
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return d;
    }
      public ArrayList<Dog> getChienCoach(int coachId) {
        String url=Statics.BASE_URL+"/api/chiens/meschien/"+coachId;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                dogs=parseChienCoach(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
         
        return dogs;
    }
    
    private ArrayList<Dog> parseChienCoach(String json) {
        try {
            dogs=new ArrayList<>();
            JSONParser parser=new JSONParser();
            Map<String, Object> dogJSON=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            List<Map<String, Object>> dogRoot=(List<Map<String, Object>>)dogJSON.get("root");
            for (Map<String, Object> chien : dogRoot) {
                d=new Dog();
                d.setId((int)Float.parseFloat(chien.get("id").toString()));
                d.setAge((int)Float.parseFloat(chien.get("age").toString()));
                d.setNote((int)Float.parseFloat(chien.get("note").toString()));
                d.setNom((String)chien.get("nom").toString());

                d.setEtat((String)chien.get("etat").toString());
                d.setRace((String)chien.get("race").toString());
                d.setMaladie((String)chien.get("maladie").toString());
                d.setTypeChase((String)chien.get("typeChasse").toString());
                d.setUsername((String)((Map<String, Object>)chien.get("user")).get("username").toString());
                               dogs.add(d);

            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return dogs;
    }
    /*
    public void updateBook(Chien b){
        connectionRequest = new ConnectionRequest() {
            
            @Override
            protected void postResponse() { 
                Dialog d = new Dialog("Popup Title");
                TextArea popupBody = new TextArea("Book updated");
                popupBody.setUIID("PopupBody");
                popupBody.setEditable(false);
                d.setLayout(new BorderLayout());
                d.add(BorderLayout.CENTER, popupBody);
                d.show();
            }
        };
        connectionRequest.setUrl("http://localhost/shelfie/update.php?title="+b.getTitle()+"&author="+b.getAuthor()+
                                "&category="+b.getCategory()+"&isbn="+b.getIsbn()+"&id=3");
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }
    */

    public void updateDog(int id,int note) {
       Dialog d=new Dialog();
         if(d.show("Noter chien","vous voulez vraiment attribuer cette note?","Oui","Non"))
            {   
                String url=Statics.BASE_URL+"/api/chiens/notechien/"+id+"/"+note;
        req.setUrl(url);
        NetworkManager.getInstance().addToQueueAndWait(req);
             
         
        
        Message m = new Message("Hello \nWe want to inform you that your dog just got a new rating open Mobile app and check out");
          
        Display.getInstance().sendMessage(new String[] {"louay.gourrida@esprit.tn"}, "New Rating", m);
                
                d.dispose();
            }
    }
    
    
     public Coach getHigher(int userId) {
        String url=Statics.BASE_URL+"/api/chiens/higher/"+userId;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                c=parseChienNbr(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return c;
    }
      public Coach getLower(int userId) {
        String url=Statics.BASE_URL+"/api/chiens/lower/"+userId;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                c=parseChienNbr(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return c;
    }
       public Coach getBetween(int userId) {
        String url=Statics.BASE_URL+"/api/chiens/between/"+userId;
        req.setUrl(url);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent a) {
                c=parseChienNbr(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return c;
    }

      
      private Coach parseChienNbr(String json) {

        try {
           
            JSONParser parser=new JSONParser();
            Map<String, Object> coach=parser.parseJSON(new CharArrayReader(json.toCharArray()));
            
                c=new Coach();
                 c.setNbr((int)Float.parseFloat(coach.get("nbr").toString()));
                 System.out.println(c.getNbr()+"ttest");
               
                
                
                
            
        } catch (IOException ex) {
            System.out.println(ex);
        }
        return c;
    }
      
      
}
