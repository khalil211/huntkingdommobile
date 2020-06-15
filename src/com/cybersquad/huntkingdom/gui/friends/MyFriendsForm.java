/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.friends;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.friends.Friends;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.gui.user.ProfileForm;
import com.cybersquad.huntkingdom.services.friends.FriendsService;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class MyFriendsForm extends Form
{
   Form current;
   public ArrayList<Friends> friends = new ArrayList<Friends>();
    public MyFriendsForm()
    {
        CurrentUser cu=CurrentUser.CurrentUser();
     current=this;
     friends=FriendsService.getInstance().getMyFriends(cu.id);
     setTitle("Mes Amis ");
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
    
    public static void addMenu(Form f) {
        f.getToolbar().addMaterialCommandToLeftSideMenu("Mon Profil", FontImage.MATERIAL_LIST, e-> new MyProfileForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Logout", FontImage.MATERIAL_LIST, e-> new LoginForm().show());
        
    }
    
    public void addItem(Friends friend) 
    {

        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        //User user = new User();
        String s = UserService.getInstance().findUserId(friend.getSeconduser());
        Label username = new Label(s);

        username.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                CurrentUser cu= CurrentUser.CurrentUser();
                int x= friend.getFirstuser();
                int x2= friend.getSeconduser();
                int x3=friend.getActionUser();
                if (x!=x3)
                {
                  cu.targetId=x;  
                }
                else
                {
                 cu.targetId=x2;   
                }
                
                
                //Dialog.show("Contact", "Nom : " + l.getText() + " \n Tel : " + tel.getText(), "Ok", null);
                new ProfileForm().show();
            }
        });
        
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(username);
        C2.add(username);
        C1.add(C2);
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        
        current.refreshTheme();
    }
    
    private void init() {
      CurrentUser cu=CurrentUser.CurrentUser();
        friends=FriendsService.getInstance().getMyFriends(cu.id);
        for (int i = 0; i < friends.size(); i++) 
        {
            
            addItem(friends.get(i));
        }
    }
}
