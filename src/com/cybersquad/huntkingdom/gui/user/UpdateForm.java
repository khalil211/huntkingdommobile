/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.user;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.friends.MyFriendsForm;
import com.cybersquad.huntkingdom.gui.interests.InterestsForm;
import com.cybersquad.huntkingdom.services.user.UserService;

/**
 *
 * @author moez
 */
public class UpdateForm extends Form
{
    Form current;
    public UpdateForm(Form previous) 
    {
        setTitle("Modifier mon profil");
        setLayout(BoxLayout.y());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfpub = new TextField("","Publier", 20, TextArea.PASSWORD);
        Button btnpassword = new Button("changer");
        C1.add(btnpassword);
        C1.add(tfpub); 
        addAll(C1);
    }
    
    public UpdateForm() 
    {
        current=this;
        setTitle("Modifier mon profil");
        setLayout(BoxLayout.y());
        addMenu(this);
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfpassword = new TextField("","Mot de passe", 20, TextArea.PASSWORD);
        //Button btnpassword = new Button("changer");
        //C1.add(btnpassword);
        C1.add(tfpassword); 
        Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfcomf = new TextField("","Comfirmer le mot de passe", 20, TextArea.PASSWORD);
        Button btncomf = new Button("Changer le mot de passe");
        btncomf.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfpassword.getText().length()>0) &&(tfcomf.getText().length()>0))
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        
                        if( UserService.getInstance().updatePassword(tfpassword.getText()))
                        {
                        Dialog.show("Mot de passe modifié","Connection accepted",new Command("OK"));
                        System.out.println("publié");    
                        } 
                        else
                        {
                         Dialog.show("ERROR", "Server error", new Command("OK"));
                         System.out.println("sike");
                        }
                            
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        C2.add(tfcomf);
        
        addAll(C1,C2,btncomf);
    }
    
    public static void addMenu(Form f)
    {
        f.getToolbar().addMaterialCommandToLeftSideMenu("Acceuil", FontImage.MATERIAL_LIST, e-> new HomePageForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Mon Profil", FontImage.MATERIAL_LIST, e-> new MyProfileForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Logout", FontImage.MATERIAL_LIST, e-> new LoginForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Modifier mon Profile  ", FontImage.MATERIAL_ADJUST, e-> new UpdateForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("Mes Amis", FontImage.MATERIAL_ADJUST, e-> new MyFriendsForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("Interests", FontImage.MATERIAL_ADJUST, e-> new InterestsForm().show());
    }
}
