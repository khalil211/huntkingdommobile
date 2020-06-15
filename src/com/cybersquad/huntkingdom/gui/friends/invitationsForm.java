/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.friends;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.friends.Friends;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.services.friends.FriendsService;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class invitationsForm extends Form
{
    Form current;
    public ArrayList<Friends> friendships = new ArrayList<Friends>();
    
    public invitationsForm()
    {
      CurrentUser cu=CurrentUser.CurrentUser();
     current=this;
     setTitle("Mes invitations");
     setLayout(BoxLayout.y());
     addMenu(this); 
     
     addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
     
    }
    
    public void addItem(Friends fRequest) {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label username = new Label(UserService.getInstance().findUserId(fRequest.getActionUser()));
        Button btnaccept = new Button("Accepter");
        Button btnrefuser = new Button("Refuser");
        /*username.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetPubId=fRequest.getActionUser();
                new ProfileForm().show();
            }
        });*/
        
        btnaccept.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
                
                
                
                try {
                       
                        if( FriendsService.getInstance().acceptOrDeny(fRequest.getSeconduser(),1))
                        {
                        Dialog.show("Publication ajouté","Connection accepted",new Command("OK"));
                        current.refreshTheme();  
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
        });
        
        btnrefuser.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
                FriendsService.getInstance().acceptOrDeny(fRequest.getSeconduser(),2);
                current.refreshTheme();
            }
        });
        setLabelStyleGreen(username);

        C2.add(username);
        C2.add(btnaccept);
        C2.add(btnrefuser);
        C1.add(C2);
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        
        
        //C1.add(username);
        //C1.setLeadComponent(username);
        current.add(C1);
                Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
    
     public static void addMenu(Form f)
     {
        f.getToolbar().addMaterialCommandToLeftSideMenu("Acceuil", FontImage.MATERIAL_LIST, e-> new HomePageForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Mon Profil", FontImage.MATERIAL_LIST, e-> new MyProfileForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Logout", FontImage.MATERIAL_LIST, e-> new LoginForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Modifier mon Profile  ", FontImage.MATERIAL_ADJUST, e-> new UpdateForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Mes invitations", FontImage.MATERIAL_ADJUST, e-> new invitationsForm().show());
     }
     
     private void init() 
     {
      CurrentUser cu=CurrentUser.CurrentUser();
        friendships=FriendsService.getInstance().getMyInvitations(cu.id);
        for (int i = 0; i < friendships.size(); i++) 
        {
            addItem(friendships.get(i));
            i++;
        }
    }
}
