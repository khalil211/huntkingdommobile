/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.friends;

/**
 *
 * @author moez
 */
public class Friends 
{
public int id;
    public int firstuser;
    public int seconduser;
    public int etat;
    public int actionUser;
    public String secondusername;

    
     public Friends ()
    {
        
    }

    public int getActionUser() {
        return actionUser;
    }

    public void setActionUser(int actionUser) {
        this.actionUser = actionUser;
    }
     
    public void setSecondusername(String secondusername) {
        this.secondusername = secondusername;
    }

    public String getSecondusername() {
        return secondusername;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstuser(int firstuser) {
        this.firstuser = firstuser;
    }

    public void setSeconduser(int seconduser) {
        this.seconduser = seconduser;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getId() {
        return id;
    }

    public int getFirstuser() {
        return firstuser;
    }

    public int getSeconduser() {
        return seconduser;
    }

    public int getEtat() {
        return etat;
    }    
}
