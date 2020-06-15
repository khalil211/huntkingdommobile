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
import com.cybersquad.huntkingdom.entities.groupe.Membership;
import com.cybersquad.huntkingdom.entities.user.CurrentUser;
import com.cybersquad.huntkingdom.gui.user.HomePageForm;
import com.cybersquad.huntkingdom.gui.user.LoginForm;
import com.cybersquad.huntkingdom.gui.user.MyProfileForm;
import com.cybersquad.huntkingdom.services.groupe.GroupeService;
import static com.cybersquad.huntkingdom.utils.Statics.setLabelStyleGreen;
import java.util.ArrayList;

/**
 *
 * @author moez
 */
public class GroupeForm extends Form
{
    Form current;
    public ArrayList<Groupe> groupes = new ArrayList<Groupe>();
    public ArrayList<Membership> members = new ArrayList<Membership>();
    
    public GroupeForm()
    {
        current=this;
        CurrentUser cu = CurrentUser.CurrentUser();
        setTitle("Groupes");
        setLayout(BoxLayout.y());
        addMenu(this);
        //groupes=GroupeService.getInstance().getAllPubs();
        //members=GroupeService.getInstance().getMyGroupes(cu.id);
        Container C1 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        TextField tfname = new TextField("","Nom du Groupe");
        TextField tfdesc = new TextField("","Description");
        Button btncreate = new Button("créer");
        
        btncreate.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tfname.getText().length()>0) || (tfdesc.getText().length()>0))
                {
                    try {
                        CurrentUser cu = CurrentUser.CurrentUser();
                        
                        if( GroupeService.getInstance().createGroupe(tfname.getText(),tfdesc.getText()))
                        {
                            int x= GroupeService.getInstance().groupeId();
                        Dialog.show("Groupe créé","Connection accepted",new Command("OK"));
                        cu.targetGroupId=x;
                        GroupeService.getInstance().joinGroupe(cu.id,cu.targetGroupId,1);
                           
                        } 
                        else
                        {
                         Dialog.show("ERROR", "Server error", new Command("OK"));
                         
                        }
                            
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        //C1.add(btncreate);
        C1.add(tfname);
        //C1.add(tfdesc);
        addAll(C1,tfdesc, btncreate);
        
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
        f.getToolbar().addMaterialCommandToRightSideMenu("Groupe Info", FontImage.MATERIAL_ADJUST, e-> new GroupeInfoForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("Mes invitations", FontImage.MATERIAL_ADJUST, e-> new invitationsForm().show());
        //f.getToolbar().addMaterialCommandToRightSideMenu("My interests", FontImage.MATERIAL_ADJUST, e-> new MyInterestsForm().show());
  }
    
    public void addItem(Membership m) {
        //UserService us;
        //ImageViewer img = null;
        Container C1 = new Container(new BoxLayout(BoxLayout.Y_AXIS));

        /*try {
            img = new ImageViewer(Image.createImage(contact.getImg()));
        } catch (IOException ex) {

        }*/
        Container C2 = new Container(new BoxLayout(BoxLayout.X_AXIS));
        Label groupeName = new Label(GroupeService.getInstance().findGroupeName(m.getGroupId()));
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
        Label desc = new Label(GroupeService.getInstance().findGroupeDescription(m.getGroupId()));
        

        groupeName.addPointerPressedListener((ActionListener) new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                int x= m.getGroupId();
                CurrentUser cu= CurrentUser.CurrentUser();
                cu.targetGroupId=x;
                new InsideGroupeForm().show();
                
            }
        });
        C1.getStyle().setBgColor(0x99CCCC);
        C1.getStyle().setBgTransparency(40);
        setLabelStyleGreen(groupeName);
        C2.add(groupeName);
        C2.add(role);
        C1.add(C2);
        C1.add(desc);
        C1.setLeadComponent(groupeName);
        current.add(C1);
        Label space = new Label("");
        current.add(space);
        current.refreshTheme();

    }
    
    private void init() {
      CurrentUser cu=CurrentUser.CurrentUser();
        members=GroupeService.getInstance().getMyGroupes(cu.id);
        for (int i = 0; i < members.size(); i++) 
        {
            
            addItem(members.get(i));
        }
    }
}
