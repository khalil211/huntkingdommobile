/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.Dog;

/**
 *
 * @author Louay
 */
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.Dog.Dog;
import com.cybersquad.huntkingdom.entities.commande.Commande;
import com.cybersquad.huntkingdom.entities.commande.ProduitCommande;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.gui.commande.DetailsCommande;
import com.cybersquad.huntkingdom.services.Dog.DogService;
import com.cybersquad.huntkingdom.services.commande.CommandeService;
import com.cybersquad.huntkingdom.services.commande.ProduitCommandeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author khalil
 */
public class CoachMyDogs extends Form {
    private CoachMyDogs current;
    
    private DogService dogS;
    private ArrayList<Dog> dogs;
    
    public CoachMyDogs() {
        current=this;
        Home.addMenu(this);
        setTitle("Liste des chiens");
        setLayout(BoxLayout.y());
        addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
    }
    
    private void init() {
       
       dogS=new DogService();
        dogs=dogS.getChienCoach(18);//id user
        
        if (dogs.isEmpty()) {
            setLayout(BoxLayout.xCenter());
            add(new Label("Vous n'avez aucun chien"));
            refreshTheme();
        } else {
            for (Dog d : dogs) {
                System.out.println(d);
                        
                addDog(d);
                refreshTheme();
            }
            getToolbar().addMaterialCommandToRightBar("Stats", FontImage.MATERIAL_RATE_REVIEW, ev-> new PieChart(current).show());
            refreshTheme();
        }
    }
    
    private void addDog(Dog d) {
        Container dogContainer=new Container(BoxLayout.x());
        Container infosContainer=new Container(BoxLayout.y());
        Label etatLabel=new Label("Owner: "+d.getUsername());
        infosContainer.addAll(new Label(d.getNom()), etatLabel);
        Container actionsContainer=new Container(BoxLayout.y());
        Button consulterButton=new Button("Consulter");
        consulterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                new DetailsDog(current, d).show();
            }
        });
        actionsContainer.add(consulterButton);
        
        dogContainer.addAll(infosContainer, actionsContainer);
        add(dogContainer);
    }
}