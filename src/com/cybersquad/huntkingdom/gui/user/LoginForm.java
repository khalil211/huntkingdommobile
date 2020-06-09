/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.user;

import com.codename1.components.SpanLabel;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.ui.Button;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.entities.user.User;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.services.user.UserService;
import java.io.IOException;

/**
 *
 * @author moez
 */
public class LoginForm extends Form
{
    Form current;
Database db;
    public LoginForm(Form previous) 
    {
        
        setTitle("Se connecter");
        setLayout(BoxLayout.y());
        
        SpanLabel sp = new SpanLabel();
        CurrentUser cu = CurrentUser.CurrentUser();
      
        sp.setText(UserService.getInstance().getAllUsers().toString());
      
        TextField tfUsername = new TextField("","UserName");
        TextField tfPassword = new TextField("","Mot de passe", 20, TextArea.PASSWORD);
        sp.setText("mot de pass ou username incorrect");
        sp.setHidden(true);
        Button btnValider = new Button("Se connecter");
        
        
        
        
        
        btnValider.addActionListener(new ActionListener() 
        {
            
            User x = new User();
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
                
                if ((tfUsername.getText().length()>0)||(tfPassword.getText().length()>0))
                    {
                     User u = new User(tfUsername.getText(),tfPassword.getText());
                     x=UserService.getInstance().connectUser(u);
                     if (cu.username.equals(u.getUsername()) && cu.password.equals(u.getPassword()) )
                    {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        cu.id=x.getId();
                        //System.out.println("connected : "+x.getId());
                        new Home().show();
                        try {
                                db = Database.openOrCreate("hintkingdom");
                                
                                db.execute("create table if not exists user (id INTEGER, username TEXT);");
                                db.execute("insert into user (id,username) values ('"+cu.id+"','"+tfUsername.getText()+"'); ");
                                Cursor cur = db.executeQuery("select * from user");
                                while (cur.next()) {
                                    Row row = cur.getRow();
                                    int id = row.getInteger(0);
                                    String prenom = row.getString(1);
                                    System.out.println("id :" + id + "prenom :" + prenom);
                                }
                            } catch (IOException e) 
                            {
                                System.out.println(""+e.getMessage());
                            }
                    }
                    else
                    {
                        CurrentUser cu = CurrentUser.CurrentUser();  
                        System.out.println("mot de pass ou username incorrect"); 
                        sp.setHidden(false);
                           sp.getParent().animateLayout(200);

                    }
                    }
                CurrentUser cu = CurrentUser.CurrentUser();
                
            }
            
        });
        
        //addAll(sp);
        addAll(tfUsername,tfPassword,btnValider,sp);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack()); 
    }
    
    public LoginForm() 
    {
        
        current=this;
        setTitle("Se connecter");
        setLayout(BoxLayout.y());
        
        SpanLabel sp = new SpanLabel();
        CurrentUser cu = CurrentUser.CurrentUser();
        cu.id=0;
        sp.setText(UserService.getInstance().getAllUsers().toString());
      
        TextField tfUsername = new TextField("","UserName");
        TextField tfPassword = new TextField("","Mot de passe", 20, TextArea.PASSWORD);
        sp.setText("mot de pass ou username incorrect");
        sp.setHidden(true);
        Button btnValider = new Button("Se connecter");
try {
                                db = Database.openOrCreate("huntkingdom");
                                
                                db.execute("drop table user;");
                                
                            } catch (IOException e) 
                            {
                                System.out.println(""+e.getMessage());
                            }
        btnValider.addActionListener(new ActionListener() 
        {
            
            User x = new User();
            
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
                
                if ((tfUsername.getText().length()>0)||(tfPassword.getText().length()>0))
                    {
                     User u = new User(tfUsername.getText(),tfPassword.getText());
                     x=UserService.getInstance().connectUser(u);
                     if (cu.username.equals(u.getUsername()) && cu.password.equals(u.getPassword()) )
                    {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        cu.id=x.getId();
                        //System.out.println("connected : "+x.getId());
                        new Home().show();
                    }
                    else
                    {
                        CurrentUser cu = CurrentUser.CurrentUser();  
                        System.out.println("mot de pass ou username incorrect"); 
                        sp.setHidden(false);
                           sp.getParent().animateLayout(200);

                    }
                    }
                CurrentUser cu = CurrentUser.CurrentUser();
                
            }
            
        });
        try {
                                db = Database.openOrCreate("huntkingdom");                        
                
                                db.execute("drop table user;");
                            } catch (IOException e) 
                            {
                                System.out.println(""+e.getMessage());
                            }
        //addAll(sp);
        addAll(tfUsername,tfPassword,btnValider,sp);
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack()); 
    }
}
