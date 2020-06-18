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
import com.cybersquad.huntkingdom.entities.publication.Publication;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.gui.friends.MyFriendsForm;
import com.cybersquad.huntkingdom.gui.friends.invitationsForm;
import com.cybersquad.huntkingdom.gui.interests.MyInterestsForm;
import com.cybersquad.huntkingdom.gui.publication.PublicationForm;
import com.cybersquad.huntkingdom.services.publication.PublicationService;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyle;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class MyProfileForm extends Form
{
    Form current;
    public ArrayList<Publication> publications = new ArrayList<Publication>();
    
  public MyProfileForm(Form previous) 
    {
        
        setTitle("Mon Profile");
        setLayout(BoxLayout.y());
        publications=PublicationService.getInstance().getAllPubs();
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfpub = new TextField("","Publier");
        Button btnpub = new Button("publier");
        Label space = new Label("");
        C1.add(btnpub);
        C1.add(tfpub);
        C1.add(space);
        
        btnpub.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfpub.getText().length()>0)
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        Publication p = new Publication(cu.id,tfpub.getText());
                        if( PublicationService.getInstance().addPublication(tfpub.getText()))
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
        
        addAll(C1);  

    } 
  public MyProfileForm() 
    {
        CurrentUser cu = CurrentUser.CurrentUser();
        current=this;
        publications=PublicationService.getInstance().getMyPubs(cu.id);
        setTitle("Mon Profile");
        setLayout(BoxLayout.y());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        
        TextField tfpub = new TextField("","Publier");
        Button btnpub = new Button("publier");
        C1.add(btnpub);
        C1.add(tfpub);
        
        btnpub.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfpub.getText().length()>0)
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        Publication p = new Publication(cu.id,tfpub.getText());
                        if( PublicationService.getInstance().addPublication(tfpub.getText()))
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
        
        Home.addMenu(this);
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
  
  public static void addMenu(Form f) 
  {

        f.getToolbar().addMaterialCommandToRightSideMenu("Modifier mon Profile  ", FontImage.MATERIAL_ADJUST, e-> new UpdateForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("Amis  ", FontImage.MATERIAL_ADJUST, e-> new MyFriendsForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("Mes invitations", FontImage.MATERIAL_ADJUST, e-> new invitationsForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("My interests", FontImage.MATERIAL_ADJUST, e-> new MyInterestsForm().show());
  }
  
  public void addItem(Publication pub) {
        //UserService us;
        //ImageViewer img = null;
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));

        /*try {
            img = new ImageViewer(Image.createImage(contact.getImg()));
        } catch (IOException ex) {

        }*/
        Label username = new Label(UserService.getInstance().findUserId(pub.getUserId()));
        //Label username = new Label(pub.getText());
        Label publi = new Label(pub.getText());
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
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
                new PublicationForm().show();
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
        publications=PublicationService.getInstance().getMyPubs(cu.id);
        for (int i = 0; i < publications.size(); i++) 
        {
            
            addItem(publications.get(i));
        }
    }
  
}
