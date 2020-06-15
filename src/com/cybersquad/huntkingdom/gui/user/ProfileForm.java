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
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.friends.Friends;
import com.cybersquad.huntkingdom.entities.publication.Publication;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.friends.MyFriendsForm;
import com.cybersquad.huntkingdom.gui.interests.InterestsForm;
import com.cybersquad.huntkingdom.gui.publication.PublicationForm;
import com.cybersquad.huntkingdom.services.friends.FriendsService;
import com.cybersquad.huntkingdom.services.publication.PublicationService;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyle;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class ProfileForm extends Form
{
 Form current;
 public ArrayList<Publication> publications = new ArrayList<Publication>();
 public ArrayList<Friends> friendships = new ArrayList<Friends>();
    public ProfileForm()
    {
     CurrentUser cu=CurrentUser.CurrentUser();
     current=this;
     setTitle("Profile de "+UserService.getInstance().findUserId(cu.targetId));
     setLayout(BoxLayout.y());
     addMenu(this);
     friendships=FriendsService.getInstance().areWeFriends(cu.targetId);
     int x= FriendsService.getInstance().arewefriends();
     
     System.out.println("x="+x);
     if (x==0)
     {
        Button btnajout = new Button("Anuller l'invitation"); 
        btnajout.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                    try {
                        CurrentUser cu;
                        /*if( FriendsService.getInstance().addFriend(cu.targetId))
                        {
                        Dialog.show("invitation envoyé","",new Command("OK"));
                        } 
                        else
                        {
                         Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                         */   
                        cu = CurrentUser.CurrentUser();
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
             
            }
        });
        add(btnajout);
     }
     else if (x==2)
     {
         Button btnajout = new Button("supprimer de ma liste d'amis");
         btnajout.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                    try {
                        /*CurrentUser cu = CurrentUser.CurrentUser();
                        if( FriendsService.getInstance().addFriend(cu.targetId))
                        {
                        Dialog.show("invitation envoyé","",new Command("OK"));
                        } 
                        else
                        {
                         Dialog.show("ERROR", "Server error", new Command("OK"));
                        }*/
                            
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
             
            }
        });
         add(btnajout);
     }
     else if (x==3 || x==1)
     {
        Button btnajout = new Button("Ajouter à ma liste d'amis");
        
        btnajout.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        if( FriendsService.getInstance().addFriend(cu.targetId))
                        {
                        Dialog.show("invitation envoyé","",new Command("OK"));
                        } 
                        else
                        {
                         Dialog.show("ERROR", "Server error", new Command("OK"));
                        }
                           
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
             
            }
        });
        add(btnajout);
     }
    
     
     addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
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
    
    public void addItem(Publication pub) {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label username = new Label(UserService.getInstance().findUserId(pub.getUserId()));
        Label publi = new Label(pub.getText());
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyle(publi);
        setLabelStyleGreen(username);
        username.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int x= pub.getId();
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetPubId=x;
                //Dialog.show("Contact", "Nom : " + l.getText() + " \n Tel : " + tel.getText(), "Ok", null);
                new PublicationForm(current).show();
            }
        });
        
        C2.add(username);
        C2.add(publi);
        C1.add(C2);
        C1.setLeadComponent(username);
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
  
  private void init() {
      CurrentUser cu=CurrentUser.CurrentUser();
        publications=PublicationService.getInstance().getMyPubs(cu.targetId);
        for (int i = 0; i < publications.size(); i++) 
        {
            
            addItem(publications.get(i));
        }
    }
}
