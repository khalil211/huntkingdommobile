/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.produit;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import com.codename1.ui.ComboBox;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.EncodedImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.produit.CategorieProduit;
import com.cybersquad.huntkingdom.entities.produit.Produit;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.services.commande.ProduitCommandeService;
import com.cybersquad.huntkingdom.services.produit.ServiceCategorieProduit;
import com.cybersquad.huntkingdom.services.produit.ServiceProduit;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author user
 */
public class Shop extends Form {
    
    private Shop current;
    private Container produitsC;
    private ArrayList<Produit> produits;
    private ArrayList<CategorieProduit> categories;
    private ComboBox listeCat;
    private EncodedImage enc;
    private TextField recherche;
    
    private ProduitCommandeService pcs;
    
    public Shop() {
        current=this;
        Home.addMenu(this);
        setTitle("Boutique");
        setLayout(BoxLayout.y());
        pcs=new ProduitCommandeService();
        produitsC=new Container();
        ServiceProduit sp=new ServiceProduit();
        produits=sp.getProduits();
        ServiceCategorieProduit scp=new ServiceCategorieProduit();
        categories=scp.getCategories();
        try {
            enc = EncodedImage.create("/loading.png");
        } catch (IOException ex) {
            System.out.println(ex);
        }
        
        recherche=new TextField("", "Recherche");
        recherche.addActionListener(a->trier());
        add(recherche);
        
        listeCat=new ComboBox();
        listeCat.addItem("Tout");
        for (CategorieProduit cp : categories)
            listeCat.addItem(cp);
        listeCat.addActionListener(l->trier());
        add(listeCat);
        
        for (Produit p : produits)
            addProduit(p);
        
        add(produitsC);
    }
    
    private void addProduit(Produit p) {
        Container produitC=new Container(BoxLayout.x());
        URLImage imageURL=URLImage.createToStorage(enc,Statics.IMAGE_URL+p.getImage(),Statics.IMAGE_URL+p.getImage(), URLImage.RESIZE_SCALE);
        ImageViewer imageProduit=new ImageViewer(imageURL);
        produitC.add(imageProduit);
        Container infos=new Container(BoxLayout.y());
        Button nomButton=new Button(p.getNom());
        nomButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent l) {
                CategorieProduit cp=null;
                for (CategorieProduit catP : categories) {
                    if (catP.getId()==p.getCategorie()) {
                        cp=catP;
                        break;
                    }
                }
                new DetailsProduit(current, p, cp).show();
            }
        });
        Button ajouterPanier=new Button("Ajouter au panier");
        ajouterPanier.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent l) {
                Dialog.show("Panier", "Produit ajouté au panier", new Command("Ok"));
                pcs.ajouter(p.getId(),1);// id user
            }
        });
        infos.addAll(nomButton, new Label(Double.toString(p.getPrix())), ajouterPanier);
        produitC.add(infos);
        produitC.setLeadComponent(nomButton);
        ajouterPanier.setBlockLead(true);
        produitsC.add(produitC);
    }
    
    private void trier() {
        produitsC.removeAll();
        String rechercheTexte=recherche.getText().toLowerCase().trim();
        int catId=-1;
        if (listeCat.getSelectedIndex()!=0)
            catId=((CategorieProduit)listeCat.getSelectedItem()).getId();
        for (Produit p : produits) {
            if (p.getNom().toLowerCase().trim().contains(rechercheTexte) && (p.getCategorie()==catId || catId==-1))
                addProduit(p);
        }
        refreshTheme();
    }
}
