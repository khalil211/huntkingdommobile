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
import com.cybersquad.huntkingdom.gui.friends.invitationsForm;
import com.cybersquad.huntkingdom.gui.groupe.GroupeForm;
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
public class HomePageForm extends Form
{
    Form current;
    public ArrayList<Publication> publications = new ArrayList<Publication>();
    
    
    public HomePageForm(Form previous) 
    {
        Home.addMenu(this);
        Form g;
        publications=PublicationService.getInstance().getAllPubs();
        setTitle("Acceuil");
        setLayout(BoxLayout.y());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfsearch = new TextField("","rechercher");
        Button btnsearch = new Button("Rechercher");
        //Button btnlogout = new Button("déconexion");
        TextField tfpub = new TextField("","Publier");
        Button btnpub = new Button("publier");
        C1.add(btnsearch);
        C1.add(tfsearch);
        
        g = new Form();
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
        
        btnsearch.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfsearch.getText().length()>0)
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        cu.search=tfsearch.getText();
                        UserService.getInstance().searchUsers();
                          new SearchForm().show();    
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "", new Command("OK"));
                    }
                    
                } 
            }
        });
        
       // getToolbar().addMaterialCommandToLeftBar("Se déconnecter", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack()); 
        addMenu(this);
        addAll(C1,tfpub,btnpub);  

    }
    
    public HomePageForm() 
    {
        Home.addMenu(this);
        current=this;
        publications=PublicationService.getInstance().getAllPubs();
        Form g;
        setTitle("Acceuil");
        setLayout(BoxLayout.y());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfsearch = new TextField("","rechercher");
        Button btnsearch = new Button("Rechercher");
        //Button btnlogout = new Button("déconexion");
        TextField tfpub = new TextField("","Publier");
        Button btnpub = new Button("publier");
        C1.add(btnsearch);
        C1.add(tfsearch);
        System.out.println("size"+publications.size());
        g = new Form();
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
       
        btnsearch.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfsearch.getText().length()>0)
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        cu.search=tfsearch.getText();
                        UserService.getInstance().searchUsers();
                          new SearchForm().show();    
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "", new Command("OK"));
                    }
                    
                } 
            }
        });
        
        addMenu(this);
        addAll(C1,tfpub,btnpub); 
         addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
        /*for (int i = 0; i < publications.size(); i++) 
        {
            
            System.out.println(publications.get(i).getUserId());
            addItem(publications.get(i));
        }*/

    }
    public static void addMenu(Form f) {
        f.getToolbar().addMaterialCommandToRightSideMenu("Mes invitations", FontImage.MATERIAL_ADJUST, e-> new invitationsForm().show());
    }
    
    private void init() {
        publications=PublicationService.getInstance().getAllPubs();
        for (int i = 0; i < publications.size(); i++) 
        {
            
            addItem(publications.get(i));
        }
    }
    
    public void addItem(Publication pub) {
        //UserService us;
        //ImageViewer img = null;
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));

        /*try {
            img = new ImageViewer(Image.createImage(contact.getImg()));
        } catch (IOException ex) {

        }*/
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        
        //Container container = new Container(new BorderLayout());
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);

        Label username = new Label(UserService.getInstance().findUserId(pub.getUserId()));
        Label publi = new Label(pub.getText());
        setLabelStyle(publi);
        setLabelStyleGreen(username);
        Label space = new Label("");
        username.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int x= pub.getId();
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetId=pub.getUserId();
                cu.targetPubId=pub.getId();
                cu.targetText=pub.getText();
                //Dialog.show("Contact", "Nom : " + l.getText() + " \n Tel : " + tel.getText(), "Ok", null);
                new PublicationForm().show();
            }
        });

        C2.add(username);
        C2.add(publi);
        //C1.add(img);
        C1.add(C2);
        C1.setLeadComponent(username);
        current.add(C1);
        current.add(space);
        current.refreshTheme();

    }
}
