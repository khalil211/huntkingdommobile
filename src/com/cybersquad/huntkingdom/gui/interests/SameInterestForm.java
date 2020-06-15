/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.interests;

import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.interest.MyInterests;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.groupe.GroupeForm;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.gui.user.ProfileForm;
import com.cybersquad.huntkingdom.services.interests.InterestsService;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class SameInterestForm extends Form
{
 Form current;
 public ArrayList<MyInterests> myinterests = new ArrayList<MyInterests>();
 
 public SameInterestForm()
 {
    current=this;
    setTitle("My interests");
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
 
 private void init() 
   {
      CurrentUser cu=CurrentUser.CurrentUser();
        myinterests=InterestsService.getInstance().sameInterest(cu.targetInterest);
        for (int i = 0; i < myinterests.size(); i++) 
        {
            
            addItem(myinterests.get(i));
        }
    }
 
 public void addItem(MyInterests m) {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label inter = new Label(UserService.getInstance().findUserId(m.getUserId()));
        
                inter.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetId=m.getUserId();
                new ProfileForm().show();
            }
        });
                C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(inter);
        C2.add(inter);
        C1.add(C2);
        C1.setLeadComponent(inter);
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
 
 public static void addMenu(Form f) {
        f.getToolbar().addMaterialCommandToLeftSideMenu("Acceuil", FontImage.MATERIAL_LIST, e-> new HomePageForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Mon Profil", FontImage.MATERIAL_LIST, e-> new MyProfileForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Groupes", FontImage.MATERIAL_LIST, e-> new GroupeForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Logout", FontImage.MATERIAL_LIST, e-> new LoginForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("Profile", FontImage.MATERIAL_LIST, e-> new ProfileForm().show());  
    }
 
}
