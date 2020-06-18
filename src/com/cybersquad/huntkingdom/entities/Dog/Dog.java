/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.Dog;
import java.util.Date;

/**
 *
 * @author Louay
 */
public class Dog {
   int id ; 
    int userId;
    int coachId;
    String nom;
    Date dateDebut;
    int age;
    String Maladie;
    String typeChase;
    int note;
    String etat;
    String username;
    String nomCoach;
    String race;


  
    

    public Dog() {
        
    }
    
    public Dog(int userId, String nom, int age, String Maladie, String typeChase,int coachId,  String nomCoach,String race) {
        this.userId = userId;
        this.coachId= coachId;
        this.nom = nom;
        this.age = age;
        this.Maladie = Maladie;
        this.nomCoach =  nomCoach;
        this.typeChase = typeChase;
         this.race = race;

    }
    public Dog(int id,String nom, int age, String Maladie,String etat, String typeChase,String race/*,Date dateDebut*/) {
        this.id=id;
        this.etat=etat;
        this.nom = nom;
        this.age = age;
        this.Maladie = Maladie; 
        this.typeChase = typeChase;
         this.race = race;
         this.dateDebut=dateDebut;

    }
     public String getNomCoach() {
        return nomCoach;
    }

    public void setNomCoach(String nomCoach) {
        this.nomCoach = nomCoach;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public int getCoachId() {
        return coachId;
    }

    public void setCoachId(int coachId) {
        this.coachId = coachId;
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

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

   

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getMaladie() {
        return Maladie;
    }

    public void setMaladie(String Maladie) {
        this.Maladie = Maladie;
    }

    public String getTypeChase() {
        return typeChase;
    }

    public void setTypeChase(String typeChase) {
        this.typeChase = typeChase;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
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
        return "Dog{" + "id=" + id + ", userId=" + userId + ", coachId=" + coachId + ", nom=" + nom + ", dateDebut=" + dateDebut + ", age=" + age + ", Maladie=" + Maladie + ", typeChase=" + typeChase + ", note=" + note + ", etat=" + etat + ", username=" + username + ", nomCoach=" + nomCoach + ", race=" + race + '}';
    }
    
    
}
