/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.animal;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.animal.Animal;
import com.cybersquad.huntkingdom.entities.animal.CategorieAnimal;
import com.cybersquad.huntkingdom.services.animal.AnimalService;
import com.cybersquad.huntkingdom.services.animal.CategorieAnimalService;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import com.cybersquad.huntkingdom.gui.Home;

/**
 *
 * @author G I E
 */
public class Animaux extends Form {
    private Animaux current;
    private ArrayList<Animal> animaux;
    private ArrayList<CategorieAnimal> categories;
    private EncodedImage enc;
    private Container animauxC;
    
    public Animaux() {
        current=this;
        Home.addMenu(this);
        try {
            enc=EncodedImage.create("/loading.png");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        AnimalService as=new AnimalService();
        CategorieAnimalService cas=new CategorieAnimalService();
        animaux=as.getAnimaux();
        categories=cas.getCategories();
        setTitle("Animaux");
        setLayout(BoxLayout.y());
        ComboBox listeCat=new ComboBox();
        listeCat.addItem("Tout");
        for (CategorieAnimal c : categories)
            listeCat.addItem(c);
        listeCat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listeCat.getSelectedIndex()==0)
                    trier(-1);
                else
                    trier(((CategorieAnimal)listeCat.getSelectedItem()).getId());
            }
        });
        add(listeCat);
        animauxC=new Container();
        animauxC.setLayout(BoxLayout.y());
        for (Animal a : animaux)
            addAnimal(a);
        add(animauxC);
    }
    
    private void addAnimal(Animal a) {
        Container animalC=new Container(BoxLayout.y());
        URLImage imageUrl=URLImage.createToStorage(enc, Statics.IMAGE_URL+a.getMedias(), Statics.IMAGE_URL+a.getMedias(),URLImage.RESIZE_SCALE);
        ImageViewer image=new ImageViewer(imageUrl);
        Button details=new Button(a.getNom());
        details.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CategorieAnimal ca=null;
                for (CategorieAnimal c : categories) {
                    if (c.getId()==a.getCategorie_id()) {
                        ca=c;
                        break;
                    }
                }
                new DetailsAnimal(current, a, ca).show();
            }
        });
        animalC.addAll(image, details);
        animalC.setLeadComponent(details);
        animauxC.add(animalC);
    }
    
    private void trier(int id) {
        animauxC.removeAll();
        if (id==-1) {
            for (Animal a : animaux)
                addAnimal(a);
        } else {
            for (Animal a : animaux) {
                if (a.getCategorie_id()==id)
                    addAnimal(a);
            }
        }
        refreshTheme();
    }
}
