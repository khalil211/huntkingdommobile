/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.services.Dog;

import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.Log;
import com.codename1.io.NetworkManager;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.cybersquad.huntkingdom.entities.Dog.Dog;
import com.cybersquad.huntkingdom.gui.Dog.ADog;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.utils.Statics;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Amal
 */
public class DogService {
        
    private ConnectionRequest connectionRequest;
    public static Form listOfBooks;
    public void addBook(Dog book){
        connectionRequest=new ConnectionRequest(){
            @Override
            protected void postResponse() {
                            System.out.println("nom:"+book.getNom()+" Age:"+book.getAge()+" mal"+book.getMaladie()+"race"+book.getRace()+"type"+book.getTypeChase());

            Dialog d = new Dialog("Add Dog");
            TextArea popupBody = new TextArea("envoyé avec succès");
            popupBody.setUIID("PopupBody");
            popupBody.setEditable(false);
            d.setLayout(new BorderLayout());
            d.add(BorderLayout.CENTER, popupBody);
            d.showDialog();
            }
        };
        connectionRequest.setUrl("http://localhost/shelfie/insert.php?nom=" + book.getNom() + "&age=" + book.getAge()+"&mal="+book.getMaladie()+"&race="+book.getRace()+"&type="+book.getTypeChase());
        NetworkManager.getInstance().addToQueue(connectionRequest);
    }

    
    public void removeBook(Dog c){   
        connectionRequest = new ConnectionRequest() {
            @Override
            protected void postResponse() {
            Dialog d = new Dialog();
            if(d.show("Delete Dog","Do you really want to remove this dog","Yes","No"))
            {
                                 d.dispose();

                connectionRequest.setUrl("http://localhost/shelfie/remove.php?id=" + c.getId());
                NetworkManager.getInstance().addToQueue(connectionRequest);
             
            new Home().show();
             
               
            }
             else
            {
                d.dispose();

            }
            }           
        };
        connectionRequest.setUrl("http://localhost/shelfie/remove.php?id=");
        NetworkManager.getInstance().addToQueue(connectionRequest);
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
}
