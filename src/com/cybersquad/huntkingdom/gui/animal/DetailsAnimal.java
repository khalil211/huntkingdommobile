/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.animal;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.animal.Animal;
import com.cybersquad.huntkingdom.entities.animal.CategorieAnimal;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import com.cybersquad.huntkingdom.gui.Home;

/**
 *
 * @author G I E
 */
public class DetailsAnimal extends Form {
    public DetailsAnimal(Form previous, Animal a, CategorieAnimal c) {
        Home.addMenu(this);
        setTitle("Détails animal");
        setLayout(BoxLayout.y());
        EncodedImage enc=null;
        try {
            enc=EncodedImage.create("/loading.png");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        URLImage imageUrl=URLImage.createToStorage(enc, Statics.IMAGE_URL+a.getMedias(), Statics.IMAGE_URL+a.getMedias(),URLImage.RESIZE_SCALE);
        ImageViewer image=new ImageViewer(imageUrl);
        addAll(image, new Label("Nom: "+a.getNom()),new Label("Catégorie: "+c.getNom()), new Label("Saison: "+a.getSaison()), new Label("Zone: "+a.getZone()), new SpanLabel("Description: "+a.getDescription()));
        getToolbar().addMaterialCommandToRightBar("Retour", FontImage.MATERIAL_ARROW_BACK, e->previous.showBack());
    }
}
