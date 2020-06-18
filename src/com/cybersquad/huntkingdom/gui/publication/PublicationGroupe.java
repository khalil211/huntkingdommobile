/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.publication;

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
import com.cybersquad.huntkingdom.entities.publication.Commentaire;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.gui.groupe.InsideGroupeForm;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.services.commentaire.CommentaireService;
import com.cybersquad.huntkingdom.services.user.UserService;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class PublicationGroupe extends Form
{
    Form current;
    public ArrayList<Commentaire> commentaires = new ArrayList<Commentaire>();
    
    public PublicationGroupe(Form previous)
    {
        //current=this;
        setTitle("Publication");
        setLayout(BoxLayout.y());
        CurrentUser cu = CurrentUser.CurrentUser();
         Home.addMenu(this);
        //Label username = new Label(UserService.getInstance().findUserId(cu.targetId);
        //Label username = new Label(pub.getText());
        TextField publi = new TextField(cu.targetText);
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfcomment = new TextField("","Ajouter un commentaire");
        Button btncomment = new Button("publier");
        getToolbar().addMaterialCommandToRightBar("", FontImage.MATERIAL_ARROW_BACK, e-> previous.showBack());
        btncomment.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfcomment.getText().length()>0)
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        
                        if( CommentaireService.getInstance().addCommentaire(tfcomment.getText()))
                        {
                        Dialog.show("Commentaire ajouté","Connection accepted",new Command("OK"));
                        System.out.println("publié");   
                        new PublicationForm(current).show();
                        new InsideGroupeForm().show();
                        //current.refreshTheme();
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
        
        addAll(publi);
        addMenu(this);
        addAll(C1);

        addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
        C1.add(btncomment);
        C1.add(tfcomment);
    }
    
    public PublicationGroupe()
    {
        current=this;
        setTitle("Publication");
        setLayout(BoxLayout.y());
        CurrentUser cu = CurrentUser.CurrentUser();
        
        Label username = new Label(UserService.getInstance().findUserId(cu.targetId));
        //Label username = new Label(pub.getText());
        Label publi = new Label(cu.targetText);
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfcomment = new TextField("","ajouter un commentaire");
        Button btncomment = new Button("publier");
        btncomment.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if (tfcomment.getText().length()>0)
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        
                        if( CommentaireService.getInstance().addCommentaire(tfcomment.getText()))
                        {
                        Dialog.show("Commentaire ajouté","Connection accepted",new Command("OK"));
                        System.out.println("publié");
                        new PublicationGroupe().show();
                        current.refreshTheme();
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
        
        addAll(username,publi);
        addMenu(this);
        addAll(C1);

        addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
        C1.add(btncomment);
        C1.add(tfcomment);
    }
    
    public static void addMenu(Form f) {
        f.getToolbar().addMaterialCommandToLeftSideMenu("Acceuil", FontImage.MATERIAL_LIST, e-> new HomePageForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Mon Profil", FontImage.MATERIAL_LIST, e-> new MyProfileForm().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Logout", FontImage.MATERIAL_LIST, e-> new LoginForm().show());
        f.getToolbar().addMaterialCommandToRightSideMenu("Groupe", FontImage.MATERIAL_LIST, e-> new InsideGroupeForm().show());
  }
    
    public void addItem(Commentaire com) 
    {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label username = new Label("@"+com.getUsername()+" :");
        Label publi = new Label(com.getText());
        
        C2.add(publi);
        //C1.add(img);
        C1.add(username);
        C1.add(C2);
        C1.setLeadComponent(username);
        current.add(C1);
        current.refreshTheme();
    }
       
    private void init() {
      CurrentUser cu=CurrentUser.CurrentUser();
        commentaires=CommentaireService.getInstance().getPubComments();
        for (int i = 0; i < commentaires.size(); i++) 
        {
            
            addItem(commentaires.get(i));
        }
    }
    
}
