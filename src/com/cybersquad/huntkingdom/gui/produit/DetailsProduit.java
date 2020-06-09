/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.produit;

import com.codename1.components.ImageViewer;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.produit.CategorieProduit;
import com.cybersquad.huntkingdom.entities.produit.Produit;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.services.commande.ProduitCommandeService;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;

/**
 *
 * @author user
 */
public class DetailsProduit extends Form {
    private ProduitCommandeService pcs;
    
    public DetailsProduit(Form previous, Produit produit, CategorieProduit categorie) {
        setTitle("Détails produit");
        setLayout(BoxLayout.y());
        Home.addMenu(this);
        pcs=new ProduitCommandeService();
        EncodedImage enc=null;
        try {
            enc = EncodedImage.create("/loading.png");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        URLImage imageURL=URLImage.createToStorage(enc,Statics.IMAGE_URL+produit.getImage(),Statics.IMAGE_URL+produit.getImage(), URLImage.RESIZE_SCALE);
        ImageViewer imageProduit=new ImageViewer(imageURL);
        Button ajouterPanier=new Button("Ajouter au panier");
        ajouterPanier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent l) {
                Dialog.show("Panier", "Produit ajouté au panier", new Command("Ok"));
                pcs.ajouter(produit.getId(),1);//id user
            }
        });
        addAll(new Label(produit.getNom()), imageProduit, new Label("Prix: "+produit.getPrix()), new Label("Description:"), new SpanLabel(produit.getDescription()), ajouterPanier, new Label("Catégorie: "+categorie.getNom()), new SpanLabel(categorie.getDescription()));
        getToolbar().addMaterialCommandToRightBar("Retour", FontImage.MATERIAL_ARROW_BACK, ev->previous.showBack());
    }
}
