package com.cybersquad.huntkingdom.entities.commande;

import java.util.ArrayList;

public class Commande {
    private int id;
    private int userId;
    private String date;
    private int etat;
    
    private double total;
    private int nbProduits;
    
    public Commande(){
        
    }

    public Commande(int userId, String date, int etat) {
        this.userId = userId;
        this.date = date;
        this.etat = etat;
    }

    public Commande(int id, int userId, String date, int etat) {
        this.id = id;
        this.userId = userId;
        this.date = date;
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getNbProduits() {
        return nbProduits;
    }

    public void setNbProduits(int nbProduits) {
        this.nbProduits = nbProduits;
    }
    
    public String getEtatToString(){
        if (etat==1)
            return "En attente";
        if (etat==2)
            return "Confirmée";
        return "Annulée";
    }
    
    public void setDetails(ArrayList<ProduitCommande> produits) {
        nbProduits=0;
        total=0;
        for (ProduitCommande pc : produits) {
            nbProduits+=pc.getQuantite();
            total+=pc.getPrixTotal();
        }
    }

    @Override
    public String toString() {
        return "Commande{" + "id=" + id + ", userId=" + userId + ", date=" + date + ", etat=" + etat + ", total=" + total + ", nbProduits=" + nbProduits + '}';
    }
}
