package com.cybersquad.huntkingdom.entities.commande;

public class ProduitCommande {
    private int Id;
    private int commandeId;
    private int produitId;
    private int quantite;
    
    private String nom;
    private double prixUnitaire;
    private String image;
    
    public ProduitCommande(){
        
    }

    public ProduitCommande(int Id, int commandeId, int produitId, int quantite) {
        this.Id = Id;
        this.commandeId = commandeId;
        this.produitId = produitId;
        this.quantite = quantite;
    }

    public ProduitCommande(int commandeId, int produitId, int quantite) {
        this.commandeId = commandeId;
        this.produitId = produitId;
        this.quantite = quantite;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
    }

    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    public double getPrixTotal() {
        return prixUnitaire*quantite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "ProduitCommande{" + "Id=" + Id + ", commandeId=" + commandeId + ", produitId=" + produitId + ", quantite=" + quantite + ", nom=" + nom + ", prixUnitaire=" + prixUnitaire + ", image=" + image + '}';
    }
}
