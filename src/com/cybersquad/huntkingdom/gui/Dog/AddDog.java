/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.Dog;
import com.codename1.components.ToastBar;
import com.codename1.ui.Button;
import com.codename1.ui.ButtonGroup;
import com.codename1.ui.Component;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.RadioButton;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;
import com.cybersquad.huntkingdom.entities.Dog.Dog;
import com.cybersquad.huntkingdom.services.Dog.DogService;
import com.cybersquad.huntkingdom.utils.Statics;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Amal
 */
public class AddDog extends Form {
    
    private final Label l1,l2,l3,l4,l5,l6,l7;
    private final TextField nameTf,ageTf,typeTf;
    private final Container mainContainer;
    private final Button addBtn,backBtn;
    
    
   public AddDog(){
       String maladie;
       Picker p =new Picker();
        this.setLayout(new BorderLayout());
        mainContainer = new Container();
        mainContainer.setLayout(new GridLayout(8,2));
        l1 = new Label("Envoyer demande");
        l1.setAlignment(CENTER);
        l1.getUnselectedStyle().setAlignment(Component.CENTER);
        l1.getUnselectedStyle().setFgColor(-16777216);
        Font l1_font = Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE);
        l1.getUnselectedStyle().setFont(l1_font);
        l7 = new Label("");
        l2 = new Label("Name:");
        nameTf = new TextField(""); 
        l3 = new Label("Age:");
        ageTf = new TextField("");
        l4 = new Label("Race");
        String[] characters = { "Berger", "Labrador", "Slougui" ,"Boxeur"/* cropped */};

       p.setStrings(characters);
       p.setSelectedString(characters[0]);
       p.addActionListener(e -> ToastBar.showMessage("You picked " + p.getSelectedString(), FontImage.MATERIAL_INFO));
       
        l5 = new Label("Maladie");
        RadioButton rb1 = new RadioButton("Oui");
        RadioButton rb2 = new RadioButton("Non");
        
        ButtonGroup bg=new ButtonGroup();
        bg.add(rb1);
        bg.add(rb2);
        l6 = new Label("Type Chasse:");
        typeTf = new TextField("");
      
        addBtn= new Button("Add");
        addBtn.getUnselectedStyle().setFgColor(5542241);
        mainContainer.add(l1);
        mainContainer.add(new Label());
        Statics.setLabelStyle(l2);
        mainContainer.add(l2);
        mainContainer.add(nameTf);
        Statics.setLabelStyle(l3);
        mainContainer.add(l3);
        mainContainer.add(ageTf);
        Statics.setLabelStyle(l4);
        mainContainer.add(l4);
        mainContainer.add(p);
        Statics.setLabelStyle(l5);
        mainContainer.add(l5);
        mainContainer.add(rb1);
        mainContainer.add(rb2);
        mainContainer.add(l7);
        Statics.setLabelStyle(l6);
        mainContainer.add(l6);
         
        mainContainer.add(typeTf);
        mainContainer.add(addBtn);
        backBtn = Statics.createBackBtn(); 
        mainContainer.add(backBtn);
        if(bg.getSelectedIndex()+1==1)
        {
            maladie="oui";
        }
        else
        {
            maladie="non";
        }
        addBtn.addActionListener((ActionListener) (ActionEvent evt) -> {
            // add a book
            Dog typedChien = new Dog(1,nameTf.getText(),Integer.parseInt(ageTf.getText()),maladie,"en attente",typeTf.getText(),p.getSelectedString());
            new  DogService().addBook(typedChien);
            });
        this.add(BorderLayout.NORTH, mainContainer);
   }

   
}
