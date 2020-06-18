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
import com.cybersquad.huntkingdom.entities.interest.Interests;
import com.cybersquad.huntkingdom.entities.interest.MyInterests;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.Home;
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
public class AddInterestForm extends Form
{
Form current;
 public ArrayList<MyInterests> myinterests = new ArrayList<MyInterests>();
 public ArrayList<Interests> interests = new ArrayList<Interests>();
 public ArrayList<Interests> leftinterests = new ArrayList<Interests>();
 
 public AddInterestForm()
 {
     current=this;
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
        f.getToolbar().addMaterialCommandToRightSideMenu("My Interests", FontImage.MATERIAL_ADJUST, e-> new MyInterestsForm().show());
    }
 
 private void init() 
   {
      CurrentUser cu=CurrentUser.CurrentUser();
        myinterests=InterestsService.getInstance().getMyInterests(cu.id);
        interests=InterestsService.getInstance().getAllInterests();
        
            System.out.println(interests.size());
            System.out.println(myinterests.size());
        
        for (int i = 0 ; i< interests.size(); i++)
        {
            String a =interests.get(i).getInterest();
            //System.out.println(a);
            int u=0;
         for (int j = 0 ; j< myinterests.size(); j++)
         {
             String b = myinterests.get(j).getMyinterest();
             //System.out.println(b);
             
             //ab.setInterest(a);
             if (!a.equals(b))
             {
                 u++;                  
             }
         }
         if (u==myinterests.size())
             {
                 Interests ab = new Interests();
                 ab.setInterest(a);
                 int s=0;
                 for (int z = 0 ; z< leftinterests.size(); z++)
                 {
                  if(leftinterests.get(z).getInterest().equals(a))
                  {
                   s++;   
                  }
                 }
               if (s==0)
               {
                leftinterests.add(ab);     
               }
              
             }
        }
        
        for (int i = 0; i < leftinterests.size(); i++) 
        {
            
            addItem(leftinterests.get(i));
        }
    }
 
 public void addItem(Interests m) {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label inter = new Label(m.getInterest());
        Button aj = new Button("ajouter");
        
        aj.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                   try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        
                        if( InterestsService.getInstance().addInterest(m.getInterest()))
                        {
                        Dialog.show("Publication ajouté","Connection accepted",new Command("OK"));
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
        });
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(inter);
        //C2.add(inter);
        C2.add(inter);
        C2.add(aj);
        C1.add(C2);
        //C1.setLeadComponent(username);
        setTitle("My interests ("+myinterests.size()+")");
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
}
