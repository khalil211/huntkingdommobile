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
import com.cybersquad.huntkingdom.entities.groupe.Groupe;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.gui.user.SearchForm;
import com.cybersquad.huntkingdom.services.groupe.GroupeService;
import com.cybersquad.huntkingdom.services.user.UserService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyle;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class SearchGroupeForm extends Form
{
   Form current;
    public ArrayList<Groupe> groupes = new ArrayList<Groupe>();
    
    public SearchGroupeForm()
    {
        current=this;
        CurrentUser cu = CurrentUser.CurrentUser();
        setTitle("Recherche :'"+cu.search+"'");
        setLayout(BoxLayout.y());
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfsearch = new TextField("","rechercher");
        tfsearch.setText(cu.search);
        Button btnsearch = new Button("Rechercher");
        //Button btnlogout = new Button("déconexion");
        C1.add(btnsearch);
        C1.add(tfsearch);
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
         Home.addMenu(this);
        addAll(C1); 
        
        addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
        
    }
    
    public static void addMenu(Form f) {

        f.getToolbar().addMaterialCommandToRightSideMenu("Utilisateurs", FontImage.MATERIAL_ADJUST, e-> new SearchForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Amis  ", FontImage.MATERIAL_ADJUST, e-> new MyFriendsForm().show());
  }
    
    public void addItem(Groupe g)
    {
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Container C2 = new Container(new BoxLayout(BoxLayout.Y_AXIS));
        Label username = new Label(g.getName());
        username.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {;
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetGroupId=g.getId();
                //Dialog.show("Contact", "Nom : " + l.getText() + " \n Tel : " + tel.getText(), "Ok", null);
                new InsideGroupeForm().show();
            }
        });
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(username);
        C2.add(username);
        C1.add(C2);
        C1.setLeadComponent(username);
        current.add(C1);
                Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
    
    private void init() {
        groupes=GroupeService.getInstance().searchGroups();
        for (int i = 0; i < groupes.size(); i++) 
        {
            
            addItem(groupes.get(i));
        }
    } 
}