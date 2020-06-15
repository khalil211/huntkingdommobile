/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.interest;

/**
 *
 * @author moez
 */
public class Interests 
{
private int id;
 private String interest;
 
 public Interests ()
 {
     
 }

    public int getId() {
        return id;
    }

    public String getInterest() {
        return interest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInterest(String interst) {
        this.interest = interst;
    }    
}
