/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.groupe;

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
import com.cybersquad.huntkingdom.entities.groupe.Membership;
import com.cybersquad.huntkingdom.entities.publication.Publication;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.gui.publication.PublicationGroupe;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.gui.user.SearchForm;
import com.cybersquad.huntkingdom.services.groupe.GroupeService;
import com.cybersquad.huntkingdom.services.publication.PublicationService;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyle;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class InsideGroupeForm extends Form
{
    Form current;
    public ArrayList<Publication> publications = new ArrayList<Publication>();
    public ArrayList<Membership> members = new ArrayList<Membership>();
    public ArrayList<Membership> memembers = new ArrayList<Membership>();
    
    public InsideGroupeForm()
    {
     current=this;
        CurrentUser cu = CurrentUser.CurrentUser();
        setTitle("Groupe : "+ GroupeService.getInstance().findGroupeName(cu.targetGroupId));
        setLayout(BoxLayout.y());
        addMenu(this);
         Home.addMenu(this);
        memembers=GroupeService.getInstance().isAMember(cu.id, cu.targetGroupId);
        if (memembers.size()==0)
        {
          Button join = new Button("rejoindre le groupe");
          Label txt = new Label("vous devez rejoindre le groupe pour publier !");
          
          join.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) 
            {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                         GroupeService.getInstance().joinGroupe(cu.id, cu.targetGroupId,0);
                         join.setText("");
                         Dialog.show("vous etes desormais un member","",new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "", new Command("OK"));
                    }
            }
        });

          
          addAll(join,txt);
        }
        else
        {
            TextField tfpub = new TextField("","Publier");
            Button btnpub = new Button("publier"); 
            
            btnpub.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfpub.getText().length()>0)
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        //Publication p = new Publication(cu.id,tfpub.getText());
                        if( PublicationService.getInstance().addGroupePublication(tfpub.getText()))
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
                
                
            }
        });
            addAll(tfpub,btnpub);
        }
        
        addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
        
    }
    
    public void addItem(Publication pub) {
        //ImageViewer img = null;
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label username = new Label(UserService.getInstance().findUserId(pub.getUserId()));
        //Label username = new Label(pub.getText());
        Label publi = new Label(pub.getText());

        username.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetPubId=pub.getId();
                cu.targetText=pub.getText();
                cu.targetId=pub.getUserId();
                //Dialog.show("Contact", "Nom : " + l.getText() + " \n Tel : " + tel.getText(), "Ok", null);
                new PublicationGroupe().show();
            }
        });
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyle(publi);
        setLabelStyleGreen(username);
        C2.add(username);
        C2.add(publi);
        //C1.add(img);
        C1.add(C2);
        C1.setLeadComponent(username);
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
    
    private void init() {
      CurrentUser cu=CurrentUser.CurrentUser();
        publications=PublicationService.getInstance().getGroupePubs(cu.targetGroupId);
        for (int i = 0; i < publications.size(); i++) 
        {
            
            addItem(publications.get(i));
        }
    }
    
    public static void addMenu(Form f) {
        f.getToolbar().addMaterialCommandToRightSideMenu("Groupe Info", FontImage.MATERIAL_ADJUST, e-> new GroupeInfoForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Amis  ", FontImage.MATERIAL_ADJUST, e-> new MyFriendsForm().show());
  }
}
