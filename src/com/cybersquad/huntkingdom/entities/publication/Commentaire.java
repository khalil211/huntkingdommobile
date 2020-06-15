/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.publication;

/**
 *
 * @author moez
 */
public class Commentaire 
{
public int id;
    public int userId;
    public int publicationId;
    public String username;
    public String text;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    public Commentaire ()
    {
        
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getPublicationId() {
        return publicationId;
    }

    public String getText() {
        return text;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPublicationId(int publicationId) {
        this.publicationId = publicationId;
    }

    public void setText(String text) {
        this.text = text;
    }    
}
