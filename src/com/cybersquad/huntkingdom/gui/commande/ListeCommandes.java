/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui.commande;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.cybersquad.huntkingdom.entities.commande.Commande;
import com.cybersquad.huntkingdom.entities.commande.ProduitCommande;
import com.cybersquad.huntkingdom.gui.Home;
import com.cybersquad.huntkingdom.services.commande.CommandeService;
import com.cybersquad.huntkingdom.services.commande.ProduitCommandeService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author khalil
 */
public class ListeCommandes extends Form {
    private ListeCommandes current;
    
    private CommandeService commandeS;
    private ProduitCommandeService produitCommandeS;
    private ArrayList<Commande> commandes;
    private Map<Integer, ArrayList<ProduitCommande>> produits;
    
    public ListeCommandes() {
        current=this;
        Home.addMenu(this);
        setTitle("Liste des commandes");
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
        produits=new HashMap<>();
        commandeS=new CommandeService();
        produitCommandeS=new ProduitCommandeService();
        commandes=commandeS.getCommandes(1);//id user
        if (commandes.size()==0) {
            setLayout(BoxLayout.xCenter());
            add(new Label("Vous n'avez aucune commande"));
            refreshTheme();
        } else {
            for (Commande c : commandes) {
                produits.put(c.getId(), produitCommandeS.getProduitCommande(c));
                addCommande(c);
                refreshTheme();
            }
        }
    }
    
    private void addCommande(Commande c) {
        Container commandeContainer=new Container(BoxLayout.x());
        Container infosContainer=new Container(BoxLayout.y());
        Label etatLabel=new Label("Etat: "+c.getEtatToString());
        infosContainer.addAll(new Label("Date: "+c.getDate()), new Label("Nb produits: "+c.getNbProduits()), new Label("Total: "+c.getTotal()), etatLabel);
        Container actionsContainer=new Container(BoxLayout.y());
        Button consulterButton=new Button("Consulter");
        consulterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent a) {
                new DetailsCommande(current, c, produits.get(c.getId())).show();
            }
        });
        actionsContainer.add(consulterButton);
        if (c.getEtat()==1) {
            Button annulerButton=new Button("Annuler");
            annulerButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent a) {
                    c.setEtat(3);
                    etatLabel.setText("Etat: "+c.getEtatToString());
                    annulerButton.remove();
                    refreshTheme();
                    Dialog.show("Commande", "Votre commande a été annulée", new Command("Ok"));
                    commandeS.modifierCommande(c);
                }
            });
            actionsContainer.add(annulerButton);
        }
        commandeContainer.addAll(infosContainer, actionsContainer);
        add(commandeContainer);
    }
}
