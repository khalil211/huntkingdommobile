/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.groupe;

/**
 *
 * @author moez
 */
public class Membership {
    public int id;
    public int userId;
    public int groupId;
    public int role;
    public String stringRole;
    public String stringGroup;
    public String stringUsername;

    public void setStringUsername(String stringUsername) {
        this.stringUsername = stringUsername;
    }

    public String getStringUsername() {
        return stringUsername;
    }

    public void setStringRole(String stringRole) {
        this.stringRole = stringRole;
    }

    public void setStringGroup(String stringGroup) {
        this.stringGroup = stringGroup;
    }

    public String getStringRole() {
        return stringRole;
    }

    public String getStringGroup() {
        return stringGroup;
    }
    
    public Membership()
    {
        
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public int getGroupId() {
        return groupId;
    }

    public int getRole() {
        return role;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
