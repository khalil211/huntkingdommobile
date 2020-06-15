/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.interests;

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
import com.cybersquad.huntkingdom.entities.interest.MyInterests;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.services.interests.InterestsService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class MyInterestsForm extends Form
{
 Form current;
 public ArrayList<MyInterests> myinterests = new ArrayList<MyInterests>();
 
 public MyInterestsForm()
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
   public static void addMenu(Form f)
    {
        f.getToolbar().addMaterialCommandToLeftSideMenu("Acceuil", FontImage.MATERIAL_LIST, e-> new HomePageForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Mon Profil", FontImage.MATERIAL_LIST, e-> new MyProfileForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Logout", FontImage.MATERIAL_LIST, e-> new LoginForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Modifier mon Profile  ", FontImage.MATERIAL_ADJUST, e-> new UpdateForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("ajouter", FontImage.MATERIAL_ADJUST, e-> new AddInterestForm().show());
    }
   
   private void init() 
   {
      CurrentUser cu=CurrentUser.CurrentUser();
        myinterests=InterestsService.getInstance().getMyInterests(cu.id);
        for (int i = 0; i < myinterests.size(); i++) 
        {
            
            addItem(myinterests.get(i));
        }
    }
   
   public void addItem(MyInterests m) {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label inter = new Label(m.getMyinterest());
        Button del = new Button("supprimer");
        
        del.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                    try {
                        
                            
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
             
            }
        });
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(inter);
        C2.add(inter);
        C2.add(del);
        C1.add(C2);
        //C1.setLeadComponent(username);
        //setTitle("My interests ("+myinterests.size()+")");
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
}
