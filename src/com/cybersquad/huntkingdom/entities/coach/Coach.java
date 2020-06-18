/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.coach;

import java.util.Date;


/**
 *
 * @author Louay
 */
public class Coach {
    int id;
    int userId;
    String nom;
    int experienceYears;
    String email;
    String etat;
    Date hireDate;
    int nbr;
    

    String race;

  
    public Coach() {
       
    }
    public Coach(int nbr) {
        this.nbr=nbr;
       
    }

    public Coach(int user_id, int experienceYears, String email, String etat, Date hireDate, String race) {
        this.userId = user_id;
        this.experienceYears = experienceYears;
        this.email = email;
        this.etat = etat;
        this.hireDate = hireDate;
        this.race = race;
    }
    

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
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

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

   
     public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }
    
    
 
    
    @Override
    public String toString() {
        return nom+"("+ race +")";
    }
}