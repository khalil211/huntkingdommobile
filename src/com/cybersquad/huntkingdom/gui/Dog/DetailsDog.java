/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.Dog;

import com.codename1.components.ImageViewer;
import static com.codename1.push.PushContent.setTitle;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.GridLayout;
import com.cybersquad.huntkingdom.entities.Dog.Dog;
import com.cybersquad.huntkingdom.entities.commande.Commande;
import com.cybersquad.huntkingdom.entities.commande.ProduitCommande;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.services.Dog.DogService;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class DetailsDog extends Form {
      private final Label l1,l2,l3,l4,l5,l6,l7,l8,l9;
    private final Label noteInf,nomInf,raceInf,typeInf,maladieInf,ageInf,etatInf;
    private final Container mainContainer;
      private final TextField noteTf;
    private final Button editBtn;
    private Dog currentBook;
    
    public DetailsDog(Form previous, Dog d) {
        setTitle(d.getNom());
        Home.addMenu(this);
        setLayout(BoxLayout.y());
       
        getToolbar().addMaterialCommandToRightBar("Retour", FontImage.MATERIAL_ARROW_BACK, a->previous.showBack());
        this.setLayout(new BorderLayout());
        mainContainer = new Container();
        mainContainer.setLayout(new GridLayout(8,2));
        
            
        l1 = new Label("Chien num° "+d.getNom());
        //l1.getUnselectedStyle().setAlignment(Component.CENTER);
        l1.getUnselectedStyle().setFgColor(-16777216);
        Font l1_font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        l1.getUnselectedStyle().setFont(l1_font);
         
        l2 = new Label("Nom");
        nomInf = new Label(d.getNom()); 
        l3 = new Label("Race");
        raceInf= new Label(d.getRace());
        l4 = new Label("Type de Chasse");
        typeInf= new Label(d.getTypeChase());
        l5 = new Label("Maladie");
        maladieInf= new Label(d.getMaladie());
        l6 = new Label("Age");
        ageInf= new Label(d.getAge()+" Ans");
        l7 = new Label("Etat");
        etatInf= new Label(d.getEtat());
        l8 = new Label("note");
        noteInf= new Label(d.getNote()+"");
        l9 = new Label("noter Dog");
        noteTf = new TextField(""); 
        editBtn= new Button("Noter Chien ");
        editBtn.getUnselectedStyle().setFgColor(5542241);
        
        mainContainer.add(l1);
        mainContainer.add(new Label());
        Statics.setLabelStyle(l2);
        mainContainer.add(l2);
        Statics.setLabelStyle(l3);
        mainContainer.add(l3);
        mainContainer.add(nomInf);
        mainContainer.add(raceInf);
        Statics.setLabelStyle(l4);
        mainContainer.add(l4);
        Statics.setLabelStyle(l5);
        mainContainer.add(l5);
        mainContainer.add(typeInf);
        mainContainer.add(maladieInf);
        Statics.setLabelStyle(l6);
        mainContainer.add(l6);
        Statics.setLabelStyle(l7);
        mainContainer.add(l7);
        mainContainer.add(ageInf);
        mainContainer.add(etatInf);
        Statics.setLabelStyle(l8);
        mainContainer.add(l8);
        mainContainer.add(noteInf);
         Statics.setLabelStyle(l9);
        mainContainer.add(l9);
        mainContainer.add(noteTf);
        mainContainer.add(editBtn);
        
        editBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            new DogService().updateDog(d.getId(),Integer.parseInt(noteTf.getText()));
            });

        
        currentBook = new Dog(d.getId(),d.getNom(),d.getAge(),d.getMaladie(),d.getEtat(),d.getTypeChase(),d.getRace()/*,Date dateDebut*/);
                this.add(BorderLayout.NORTH, mainContainer);

    }
    
   
}
