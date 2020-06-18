/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.entities.user;

/**
 *
 * @author moez
 */
public class CurrentUser 
{
    private static CurrentUser single_instance=null; 

	// variable of type String 
	public int id;// 
        public int role;
        public String search;
        public int targetId;
        public int targetPubId;
        public int targetGroupId;
        public int error;
        public String code;
        public String targetInterest;
        public int idmail;//
        public String username;//
        public String password;
        public String targetText;
        

	// private constructor restricted to this class itself 
	private CurrentUser() 
	{ 
		id =0; 
                role = 0;
                targetId = 0;
                error = 0;
                targetPubId=0;
                targetGroupId=0;
                targetInterest="";
                code="";
                search = "";
                username ="";
                password = "";
                targetText = "";
                
	} 

	// static method to create instance of CurrentUser class 
	public static CurrentUser CurrentUser() 
	{ 
		// To ensure only one instance is created 
		if (single_instance == null) 
		{ 
			single_instance = new CurrentUser(); 
		} 
		return single_instance; 
	} 
}
