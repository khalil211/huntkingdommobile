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
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.URLImage;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.commande.Commande;
import com.cybersquad.huntkingdom.entities.commande.ProduitCommande;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.services.commande.CommandeService;
import com.cybersquad.huntkingdom.services.commande.ProduitCommandeService;
import com.cybersquad.huntkingdom.utils.Statics;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author khalil
 */
public class Panier extends Form {
    
    private Panier current;
    private Container listeProduitsCommande;
    private Map<Integer, Container> produitsContainers;
    private Map<Integer, Label> listeTotalProduit;
    private Map<Integer, Label> listeQuantiteProduit;
    private Label totalCommande;
    private Label nbProduitTotal;
    private EncodedImage encodedLoadingImage;
    private CommandeService commandeS;
    private ProduitCommandeService produitCommandeS;
    private Commande commande;
    private ArrayList<ProduitCommande> produits;
    private Button passerCommande;
    
    public Panier() {
        current=this;
        Home.addMenu(this);
        setTitle("Panier");
        setLayout(BoxLayout.y());
        produitsContainers=new HashMap<>();
        listeTotalProduit=new HashMap<>();
        listeQuantiteProduit=new HashMap();
        totalCommande=new Label("0");
        nbProduitTotal=new Label("0");
        listeProduitsCommande=new Container(BoxLayout.y());
        add(listeProduitsCommande);
        Container detailsCommande=new Container(BoxLayout.y());
        Container nbProduitContainer=new Container(BoxLayout.x());
        Container totalContainer=new Container(BoxLayout.x());
        nbProduitContainer.addAll(new Label("Nb produits: "), nbProduitTotal);
        totalContainer.addAll(new Label("Total: "), totalCommande);
        detailsCommande.addAll(nbProduitContainer, totalContainer);
        Container commandeFooter=new Container(BoxLayout.x());
        passerCommande=new Button("Passer commande");
        commandeFooter.addAll(detailsCommande, passerCommande);
        add(commandeFooter);
        refreshTheme();
        addShowListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                init();
                current.removeShowListener(this);
            }
        });
    }
    
    private void init() {
        commandeS=new CommandeService();
        produitCommandeS=new ProduitCommandeService();
        commande=commandeS.getPanier(1);//id utilisateur connecte
        produits=produitCommandeS.getProduitCommande(commande);
        if (produits.size()==0)
            clearPanier();
        else {
            try {
                encodedLoadingImage = EncodedImage.create("/loading.png");
            } catch (IOException ex) {
                System.out.println(ex);
            }
            for (ProduitCommande pc : produits)
                addProduitCommande(pc);
            nbProduitTotal.setText(Integer.toString(commande.getNbProduits()));
            totalCommande.setText(Double.toString(commande.getTotal()));
        }
        passerCommande.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                if (produits.size()!=0) {
                    commande.setEtat(1);
                    clearPanier();
                    Dialog.show("Commande", "Votre commande a été passe avec succès", new Command("Ok"));
                    commandeS.modifierCommande(commande);
                } else
                    Dialog.show("Commande", "Votre panier est vide", new Command("Ok"));
            }
        });
        refreshTheme();
    }
    
    private void addProduitCommande(ProduitCommande pc) {
        URLImage imageURL=URLImage.createToStorage(encodedLoadingImage,Statics.IMAGE_URL+pc.getImage(),Statics.IMAGE_URL+pc.getImage(), URLImage.RESIZE_SCALE);
        ImageViewer imageProduit=new ImageViewer(imageURL);
        Label prixUnitaire=new Label(Double.toString(pc.getPrixUnitaire()));
        Button decrementButton=new Button();
        FontImage.setMaterialIcon(decrementButton, FontImage.MATERIAL_REMOVE);
        decrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                if (pc.getQuantite()>1) {
                    pc.setQuantite(pc.getQuantite()-1);
                    commande.setTotal(commande.getTotal()-pc.getPrixUnitaire());
                    commande.setNbProduits(commande.getNbProduits()-1);
                    listeTotalProduit.get(pc.getId()).setText(Double.toString(pc.getPrixTotal()));
                    listeQuantiteProduit.get(pc.getId()).setText(Integer.toString(pc.getQuantite()));
                    totalCommande.setText(Double.toString(commande.getTotal()));
                    nbProduitTotal.setText(Integer.toString(commande.getNbProduits()));
                    produitCommandeS.modifierProduit(pc);
                }
            }
        });
        Label quantity=new Label(Integer.toString(pc.getQuantite()));
        Button incrementButton=new Button();
        FontImage.setMaterialIcon(incrementButton, FontImage.MATERIAL_ADD);
        incrementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                if (pc.getQuantite()<9) {
                    pc.setQuantite(pc.getQuantite()+1);
                    commande.setTotal(commande.getTotal()+pc.getPrixUnitaire());
                    commande.setNbProduits(commande.getNbProduits()+1);
                    listeTotalProduit.get(pc.getId()).setText(Double.toString(pc.getPrixTotal()));
                    listeQuantiteProduit.get(pc.getId()).setText(Integer.toString(pc.getQuantite()));
                    totalCommande.setText(Double.toString(commande.getTotal()));
                    nbProduitTotal.setText(Integer.toString(commande.getNbProduits()));
                    produitCommandeS.modifierProduit(pc);
                }
            }
        });
        Label totalProduit=new Label(Double.toString(pc.getPrixTotal()));
        Button supprimerProduit=new Button();
        FontImage.setMaterialIcon(supprimerProduit, FontImage.MATERIAL_DELETE);
        supprimerProduit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                commande.setNbProduits(commande.getNbProduits()-pc.getQuantite());
                commande.setTotal(commande.getTotal()-pc.getPrixTotal());
                totalCommande.setText(Double.toString(commande.getTotal()));
                nbProduitTotal.setText(Integer.toString(commande.getNbProduits()));
                listeTotalProduit.remove(pc.getId());
                listeQuantiteProduit.remove(pc.getId());
                produitsContainers.get(pc.getId()).remove();
                current.refreshTheme();
                produitsContainers.remove(pc.getId());
                produits.remove(pc);
                if (produits.size()==0)
                    clearPanier();
                produitCommandeS.supprimerProduit(pc);
            }
        });
        Container produitContainer=new Container(BoxLayout.x());
        Container detailsProduit=new Container(BoxLayout.y());
        Container quantityContainer=new Container(BoxLayout.x());
        quantityContainer.addAll(decrementButton, quantity, incrementButton);
        detailsProduit.addAll(prixUnitaire, quantityContainer, totalProduit);
        produitContainer.addAll(imageProduit, detailsProduit, supprimerProduit);
        listeProduitsCommande.add(produitContainer);
        listeQuantiteProduit.put(pc.getId(), quantity);
        listeTotalProduit.put(pc.getId(), totalProduit);
        produitsContainers.put(pc.getId(), produitContainer);
    }
    
    private void clearPanier() {
        produits.clear();
        produitsContainers.clear();
        listeTotalProduit.clear();
        listeQuantiteProduit.clear();
        listeProduitsCommande.removeAll();
        listeProduitsCommande.setLayout(BoxLayout.xCenter());
        listeProduitsCommande.add(new Label("Votre panier est vide"));
        nbProduitTotal.setText("0");
        totalCommande.setText("0");
        refreshTheme();
    }
}
