/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.user;

import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.entities.user.User;
import com.cybersquad.huntkingdom.services.commande.CommandeService;
import com.cybersquad.huntkingdom.services.user.UserService;
import java.io.IOException;

/**
 *
 * @author moez
 */
public class RegisterForm extends Form 
{
 Form current;
   Database db;
   
    public RegisterForm()
    {
        
        current=this;
        //Form m = new Home(current);
        CurrentUser cu = CurrentUser.CurrentUser();
        setTitle("créer un compte");
        setLayout(BoxLayout.y());
        //CurrentUser cu = CurrentUser.CurrentUser();
       try {
            
                                db = Database.openOrCreate("huntkingdom");
                                
                                db.execute("create table if not exists user (id INTEGER, username TEXT);");
                                Cursor cur = db.executeQuery("select * from user");
                                while (cur.next()) 
                                {
                                    Row row = cur.getRow();
                                    int id = row.getInteger(0);
                                    String username = row.getString(1);
                                    
                                    System.out.println("id :" + id + "prenom :" + username);
                                    cu.id=id;
                                    cu.username=username;
                                    System.out.println("before"+cu.id);
                                    
                                }
                            } catch (IOException e) 
                            {
                                System.out.println(""+e.getMessage());
                            }
        TextField tfUsername = new TextField("","UserName");
        TextField tfEmail = new TextField("", "Email");
        TextField tfPassword = new TextField("","Mot de passe", 20, TextArea.PASSWORD);
        Button btnValider = new Button("créer un compte");
        Button btnLogin = new Button("se connecter");
        
        
        
        btnValider.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfUsername.getText().length()==0)||(tfPassword.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        User u = new User(tfUsername.getText(), tfEmail.getText(),tfPassword.getText());
                        if( UserService.getInstance().addUser(u)) {
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        }
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        
           if (cu.id>0)
                                    {
                                    
                                    }
            else
            {
                btnLogin.addActionListener(e-> new LoginForm(current).show());
                addAll(tfUsername,tfEmail,tfPassword,btnValider,btnLogin);   
                System.out.println(cu.id);
            } 
        
        //getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
                
    }
}
