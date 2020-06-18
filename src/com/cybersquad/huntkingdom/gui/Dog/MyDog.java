/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.Dog;

import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.util.ImageIO;
import com.cybersquad.huntkingdom.entities.Dog.Dog;
import com.cybersquad.huntkingdom.entities.coach.Coach;
import com.cybersquad.huntkingdom.services.Dog.DogService;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 *
 * @author Louay
 */
public class MyDog extends Form {
    private DogService dogS ;
    private Dog d;
    private Coach c;
     private final Label l1,l2,l3,l4,l5,l6,l7,l8,l9;
    private final Label coachnameInf,noteInf,nomInf,raceInf,typeInf,maladieInf,ageInf,etatInf;
    private final Container mainContainer;
    private final Button editBtn,removeBtn,backBtn;
    private Dog currentBook;
    
    
    
    public MyDog(){
        dogS= new DogService();
       
        int useri=8;
        d=dogS.getChien(useri);
        
        System.out.println(d);
        this.setLayout(new BorderLayout());
        mainContainer = new Container();
        mainContainer.setLayout(new GridLayout(8,2));
        
                

        l1 = new Label("Chien num° "+d.getId());
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
        l9 = new Label("Coach");
       coachnameInf= new Label(d.getNomCoach());
        editBtn= new Button("Edit");
        editBtn.getUnselectedStyle().setFgColor(5542241);
        removeBtn= new Button("Remove");
        removeBtn.getUnselectedStyle().setFgColor(5542241);
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
        Statics.setLabelStyle(l9);
        mainContainer.add(l9);
        mainContainer.add(noteInf);
        mainContainer.add(coachnameInf);
        mainContainer.add(editBtn);
        mainContainer.add(removeBtn);
        backBtn = Statics.createBackBtn(); 
        mainContainer.add(backBtn);
        currentBook = new Dog(d.getId(),d.getNom(),d.getAge(),d.getMaladie(),d.getEtat(),d.getTypeChase(),d.getRace()/*,Date dateDebut*/);
       
        /*editBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            new BookDAO().updateBook(currentBook);
            });
*/ 
    
        
        removeBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent evt) {
                new DogService().removeBook(currentBook);
            }

        });
        this.add(BorderLayout.NORTH, mainContainer);
    }
   

    
}
