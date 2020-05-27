/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.Dog;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.cybersquad.huntkingdom.entities.Dog.Dog;
import com.cybersquad.huntkingdom.services.Dog.DogService;
import com.cybersquad.huntkingdom.utils.Statics;


/**
 *
 * @author Amal
 */
public class ADog extends Form {
    
    private final Label l1,l2,l3,l4,l5,l6,l7;
    private final Label nomInf,raceInf,typeInf,maladieInf,ageInf,etatInf;
    private final Container mainContainer;
    private final Button editBtn,removeBtn,backBtn;
    private Dog currentBook;
    
    
    
    public ADog(int id,String nom,String race,String typechasse,String maladie,int age,String etat){
        
        this.setLayout(new BorderLayout());
        mainContainer = new Container();
        mainContainer.setLayout(new GridLayout(8,2));
        l1 = new Label("Chien num° "+id);
        //l1.getUnselectedStyle().setAlignment(Component.CENTER);
        l1.getUnselectedStyle().setFgColor(-16777216);
        Font l1_font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        l1.getUnselectedStyle().setFont(l1_font);
        l2 = new Label("Nom");
        nomInf = new Label(nom); 
        l3 = new Label("Race");
        raceInf= new Label(race);
        l4 = new Label("Type de Chasse");
        typeInf= new Label(typechasse);
        l5 = new Label("Maladie");
        maladieInf= new Label(maladie);
        l6 = new Label("Age");
        ageInf= new Label(age+" Ans");
        l7 = new Label("Etat");
        etatInf= new Label(etat);
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
        mainContainer.add(editBtn);
        mainContainer.add(removeBtn);
        backBtn = Statics.createBackBtn(); 
        mainContainer.add(backBtn);
        currentBook = new Dog(id,nom,age,maladie,etat,typechasse,race/*,Date dateDebut*/);
       
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