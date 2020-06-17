/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.appointments;

/**
 *
 * @author marwe
 */
public class Participant {
   public int id ;
    public int user_id;
    public String email; 

    public Participant() {
    }


    @Override
    public String toString() {
        return "Participant{" + "id=" + id + ", user_id=" + user_id + ", email=" + email + '}';
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Participant(int id, int user_id, String email) {
        this.id = id;
        this.user_id = user_id;
        this.email = email;
    }
}
