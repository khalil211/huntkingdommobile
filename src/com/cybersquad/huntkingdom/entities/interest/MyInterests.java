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
public class MyInterests 
{
 private int id;
    private int userId;
    private String myinterest;
    private String userString;
    
    public MyInterests()
    {
        
    }

    public int getId() {
        return id;
    }

    public void setUserString(String userString) {
        this.userString = userString;
    }

    public String getUserString() {
        return userString;
    }

    public int getUserId() {
        return userId;
    }

    public String getMyinterest() {
        return myinterest;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setMyinterest(String myinterest) {
        this.myinterest = myinterest;
    }   
}
