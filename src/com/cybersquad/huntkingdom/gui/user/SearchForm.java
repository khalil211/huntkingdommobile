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
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.entities.user.User;
import com.cybersquad.huntkingdom.gui.groupe.SearchGroupeForm;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyle;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class SearchForm extends Form
{
    Form current;
    public ArrayList<User> users = new ArrayList<User>();
    
    public SearchForm()
    {
        current=this;
        CurrentUser cu = CurrentUser.CurrentUser();
        setTitle("Recherche '"+cu.search+"'");
        setLayout(BoxLayout.y());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfsearch = new TextField("","rechercher");
        tfsearch.setText(cu.search);
        Button btnsearch = new Button("Rechercher");
        //Button btnlogout = new Button("déconexion");
        C1.add(btnsearch);
        C1.add(tfsearch);
        btnsearch.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfsearch.getText().length()>0)
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        cu.search=tfsearch.getText();
                        UserService.getInstance().searchUsers();
                          new SearchForm().show();    
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "", new Command("OK"));
                    }
                    
                } 
            }
        });
        addMenu(this);
        addAll(C1); 
        
        addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
        
    }
    
    public static void addMenu(Form f) {
        f.getToolbar().addMaterialCommandToLeftSideMenu("Acceuil", FontImage.MATERIAL_LIST, e-> new HomePageForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Mon Profil", FontImage.MATERIAL_LIST, e-> new MyProfileForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Logout", FontImage.MATERIAL_LIST, e-> new LoginForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("chercher un Groupe", FontImage.MATERIAL_ADJUST, e-> new SearchGroupeForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Amis  ", FontImage.MATERIAL_ADJUST, e-> new MyFriendsForm().show());
  }
    
    public void addItem(User u)
    {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label username = new Label(u.getUsername());
        username.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int x= u.getId();
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetId=x;
                //Dialog.show("Contact", "Nom : " + l.getText() + " \n Tel : " + tel.getText(), "Ok", null);
                new ProfileForm().show();
            }
        });
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(username);
        C2.add(username);
        C1.add(C2);
        C1.setLeadComponent(username);
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
    
    private void init() {
        users=UserService.getInstance().searchUsers();
        for (int i = 0; i < users.size(); i++) 
        {
            
            addItem(users.get(i));
        }
    }
}

