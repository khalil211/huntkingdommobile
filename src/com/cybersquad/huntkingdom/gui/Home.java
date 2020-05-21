/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.gui;

import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.cybersquad.huntkingdom.gui.commande.ListeCommandes;
import com.cybersquad.huntkingdom.gui.commande.Panier;
import com.cybersquad.huntkingdom.gui.commande.ShopTemp;

/**
 *
 * @author khalil
 */
public class Home extends Form {
    public Home() {
        setTitle("Home");
        addMenu(this);
    }
    
    public static void addMenu(Form f) {
        f.getToolbar().addMaterialCommandToLeftSideMenu("Accueil", FontImage.MATERIAL_HOME, e-> new Home().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Boutique", FontImage.MATERIAL_SHOP, e-> new ShopTemp().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Panier", FontImage.MATERIAL_PAYMENT, e-> new Panier().show());
        f.getToolbar().addMaterialCommandToLeftSideMenu("Liste commandes", FontImage.MATERIAL_LIST, e-> new ListeCommandes().show());
    }
}
