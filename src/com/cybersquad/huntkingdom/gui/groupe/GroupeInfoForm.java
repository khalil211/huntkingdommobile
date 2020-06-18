/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.groupe;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.groupe.Membership;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.gui.user.ProfileForm;
import com.cybersquad.huntkingdom.services.groupe.GroupeService;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class GroupeInfoForm extends Form 
{
Form current;
    //public ArrayList<Publication> publications = new ArrayList<Publication>();
    public ArrayList<Membership> members = new ArrayList<Membership>();  
    
    public GroupeInfoForm()
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        current=this;
        setTitle("Groupe Info");
        setLayout(BoxLayout.y());
        addMenu(this);
         Home.addMenu(this);
        Label name = new Label("Nom du Groupe :" +GroupeService.getInstance().findGroupeName(cu.targetGroupId));
        Label desc = new Label("Description du groupe : "+GroupeService.getInstance().findGroupeDescription(cu.targetGroupId));
        Label num = new Label("Les members ("+GroupeService.getInstance().getNbrMembers(cu.targetGroupId)+") :");
        
        addAll(name, desc, num);
        addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
    }
    
    
    
    private void init() {
      CurrentUser cu=CurrentUser.CurrentUser();
        members=GroupeService.getInstance().getMembers(cu.targetGroupId);
        for (int i = 0; i < members.size(); i++) 
        {
            
            addItem(members.get(i));
        }
    }
    
    public void addItem(Membership m) 
    {
        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label Name = new Label(UserService.getInstance().findUserId(m.getUserId()));
        //Label groupeName = new Label(""+m.getGroupId());
        Label role = new Label("Member");
        if (m.getRole()==0)
        {
          role.setText("(Member)");  
        }
        else
        {
            role.setText("(Administrateur)");  
        }
        //Label desc = new Label(GroupeService.getInstance().findGroupeDescription(m.getGroupId()));
        

        Name.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int x= m.getUserId();
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetId=x;
                new ProfileForm().show();
                
            }
        });
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(Name);
        C2.add(Name);
        C2.add(role);
        C1.add(C2);
        //C1.add(desc);
        C1.setLeadComponent(Name);
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
    
    public static void addMenu(Form f) 
  {
        f.getToolbar().addMaterialCommandToRightSideMenu("Groupe  ", FontImage.MATERIAL_ADJUST, e-> new InsideGroupeForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Amis  ", FontImage.MATERIAL_ADJUST, e-> new MyFriendsForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Mes invitations", FontImage.MATERIAL_ADJUST, e-> new invitationsForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("My interests", FontImage.MATERIAL_ADJUST, e-> new MyInterestsForm().show());
  }
}
