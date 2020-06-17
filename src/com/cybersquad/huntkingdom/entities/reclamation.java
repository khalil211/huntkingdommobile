/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.reclamation;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author marwe
 */
public class reclamation {
    public int id ;
   public int user_id; 
   public Date Date_creation;
   public String type;
   public String description;
   public String response;
   public int status;
   public String fichier;
    private String username;

    public reclamation(int id, int user_id, Date Date_creation, String type, String description, String response, int status, String fichier) {
        this.id = id;
        this.user_id = user_id;
        this.Date_creation = Date_creation;
        this.type = type;
        this.description = description;
        this.response = response;
        this.status = status;
        this.fichier = fichier;
    }

    public reclamation() {
    }

    public reclamation(int id, String description, String type) {
                this.id = id;
        this.description = description;
        this.type = type;

    }

    public reclamation(String description, String type) {
         this.description = description;
        this.type = type;
    }

    public reclamation(String description) {
                 this.description = description;

    }

    @Override
    public String toString() {
        return "reclamation{" + "type=" + type + ", description=" + description + '}';
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getDate_creation() {
        return Date_creation;
    }

    public void setDate_creation(Date Date_creation) {
        this.Date_creation = Date_creation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getFichier() {
        return fichier;
    }

    public void setFichier(String fichier) {
        this.fichier = fichier;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 41 * hash + this.id;
        hash = 41 * hash + this.user_id;
        hash = 41 * hash + Objects.hashCode(this.Date_creation);
        hash = 41 * hash + Objects.hashCode(this.type);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.response);
        hash = 41 * hash + this.status;
        hash = 41 * hash + Objects.hashCode(this.fichier);
        hash = 41 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final reclamation other = (reclamation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.user_id != other.user_id) {
            return false;
        }
        if (this.status != other.status) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.response, other.response)) {
            return false;
        }
        if (!Objects.equals(this.fichier, other.fichier)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.Date_creation, other.Date_creation)) {
            return false;
        }
        return true;
    }
}
