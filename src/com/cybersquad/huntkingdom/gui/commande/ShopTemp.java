/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.commande;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.services.commande.ProduitCommandeService;
import java.io.IOException;

/**
 *
 * @author khalil
 */
public class ShopTemp extends Form {
    
    public ShopTemp() {
        ProduitCommandeService pcs=new ProduitCommandeService();
        Home.addMenu(this);
        setTitle("Boutique");
        setLayout(BoxLayout.yCenter());
        Container c1=new Container(BoxLayout.yCenter());
        EncodedImage enc1 = null;
        try {
            enc1 = EncodedImage.create("/loading.png");
        } catch (IOException ex) {
        }
        URLImage image1=URLImage.createToStorage(enc1,"http://localhost/HuntKingdomjava/uploads/6HQVukqJ.jpg","http://localhost/HuntKingdomjava/uploads/6HQVukqJ.jpg", URLImage.RESIZE_SCALE);
        ImageViewer imagev1=new ImageViewer(image1);
        Container cc1=new Container(BoxLayout.xCenter());
        Label l1=new Label("produit1");
        Button b1=new Button("Ajouter au panier");
        b1.addActionListener(e-> {
            Dialog.show("Panier", "Produit ajouté au panier", new Command("Ok"));
            pcs.ajouter(3,1);//id produit, id user
        });
        //imagev1.setWidth(100);
        //imagev1.setHeight(100);
        cc1.addAll(l1, b1);
        c1.addAll(imagev1, cc1);
        add(c1);
        Container c2=new Container(BoxLayout.yCenter());
        URLImage image2=URLImage.createToStorage(enc1,"http://localhost/HuntKingdomjava/uploads/3uw7GpyL.jpg","http://localhost/HuntKingdomjava/uploads/3uw7GpyL.jpg", URLImage.RESIZE_SCALE);
        ImageViewer imagev2=new ImageViewer(image2);
        Container cc2=new Container(BoxLayout.xCenter());
        Label l2=new Label("produit2");
        Button b2=new Button("Ajouter au panier");
        b2.addActionListener(e-> {
            Dialog.show("Panier", "Produit ajouté au panier", new Command("Ok"));
            pcs.ajouter(4,1);//id produit, id user
        });
        cc2.addAll(l2, b2);
        c2.addAll(imagev2, cc2);
        add(c2);
    }
    
}
