/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.animal;

/**
 *
 * @author G I E
 */
public class Animal {
    
    private int id;
    private int categorie_id;
    private String nom;
    private String description;
    private String medias;
    private String zone;
    private String saison;

    public Animal() {
    }

    public Animal(int id, int categorie_id, String nom, String description, String medias, String zone, String saison) {
        this.id = id;
        this.categorie_id = categorie_id;
        this.nom = nom;
        this.description = description;
        this.medias = medias;
        this.zone = zone;
        this.saison = saison;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategorie_id() {
        return categorie_id;
    }

    public void setCategorie_id(int categorie_id) {
        this.categorie_id = categorie_id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMedias() {
        return medias;
    }

    public void setMedias(String medias) {
        this.medias = medias;
    }

    public String getZone() {
        return zone;
    }

    public void setZone(String zone) {
        this.zone = zone;
    }

    public String getSaison() {
        return saison;
    }

    public void setSaison(String saison) {
        this.saison = saison;
    }

    @Override
    public String toString() {
        return "Animal{" + "id=" + id + ", categorie_id=" + categorie_id + ", nom=" + nom + ", description=" + description + ", medias=" + medias + ", zone=" + zone + ", saison=" + saison + '}';
    }
    
}
