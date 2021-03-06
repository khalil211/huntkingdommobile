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
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.gui.user.ProfileForm;
import com.cybersquad.huntkingdom.services.interests.InterestsService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class InterestsForm extends Form
{
  Form current;
 public ArrayList<MyInterests> myinterests = new ArrayList<MyInterests>();
 
 public InterestsForm()
 {
    current=this;
    setTitle("My interests");
    setLayout(BoxLayout.y());
    addMenu(this);
    
     Home.addMenu(this);
     
 
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
        //f.getToolbar().addMaterialCommandToRightSideMenu("Modifier mon Profile  ", FontImage.MATERIAL_ADJUST, e-> new UpdateForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("Profile", FontImage.MATERIAL_ADJUST, e-> new ProfileForm().show());
    }
   
   private void init() 
   {
      CurrentUser cu=CurrentUser.CurrentUser();
        myinterests=InterestsService.getInstance().getMyInterests(cu.targetId);
        for (int i = 0; i < myinterests.size(); i++) 
        {
            
            addItem(myinterests.get(i));
        }
    }
   
   public void addItem(MyInterests m) {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label inter = new Label(m.getMyinterest());
        
        inter.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetInterest=m.getMyinterest();
                //Dialog.show("Contact", "Nom : " + l.getText() + " \n Tel : " + tel.getText(), "Ok", null);
                new SameInterestForm().show();
            }
        });
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(inter);
        //C2.add(inter);
        C2.add(inter);
        C1.add(C2);
        //C1.setLeadComponent(username);
        setTitle("My interests ("+myinterests.size()+")");
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }  
}
