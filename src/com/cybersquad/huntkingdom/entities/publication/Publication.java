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
public class Publication 
{
private int id;
private String text;
private int userId;

public Publication()
{
    
}
public Publication(int userId, String Text)
{
    this.text=text;
    this.userId=userId;  
}

public int getId()
{
    return id;
}

public int getUserId()
{
    return userId;
}

public String getText()
{
    return text;
}

public void setId(int id)
{
    this.id=id;
}

public void setUserId( int userId)
{
    this.userId=userId;
}

public void setText(String text)
{
    this.text=text;
}

@Override
    public String toString() 
    {
        return "Username : "+userId+" publication : "+text;
    }
        
}
