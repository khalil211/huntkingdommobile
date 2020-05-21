/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.commande;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Container;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.commande.Commande;
import com.cybersquad.huntkingdom.entities.commande.ProduitCommande;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author khalil
 */
public class DetailsCommande extends Form {
    private EncodedImage encodedLoadingImage;
    
    public DetailsCommande(Form previous, Commande commande, ArrayList<ProduitCommande> produits) {
        setTitle("Liste produits");
        Home.addMenu(this);
        setLayout(BoxLayout.y());
        try {
            encodedLoadingImage = EncodedImage.create("/loading.png");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        for (ProduitCommande pc : produits)
            addProduitCommande(pc);
        getToolbar().addMaterialCommandToRightBar("Retour", FontImage.MATERIAL_ARROW_BACK, a->previous.showBack());
    }
    
    private void addProduitCommande(ProduitCommande pc) {
        Container produitContainer=new Container(BoxLayout.x());
        Container detailsContainer=new Container(BoxLayout.y());
        URLImage imageURL=URLImage.createToStorage(encodedLoadingImage,Statics.IMAGE_URL+pc.getImage(),Statics.IMAGE_URL+pc.getImage(), URLImage.RESIZE_SCALE);
        ImageViewer imageProduit=new ImageViewer(imageURL);
        Label prixUnitaire=new Label(Double.toString(pc.getPrixUnitaire()));
        Label quantity=new Label(Integer.toString(pc.getQuantite()));
        Label totalProduit=new Label(Double.toString(pc.getPrixTotal()));
        detailsContainer.addAll(prixUnitaire, quantity, totalProduit);
        produitContainer.addAll(imageProduit, detailsContainer);
        add(produitContainer);
    }
}
