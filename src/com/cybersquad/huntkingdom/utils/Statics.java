/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cybersquad.huntkingdom.utils;

import com.codename1.ui.Button;
import com.codename1.ui.Font;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.cybersquad.huntkingdom.gui.Home;

/**
 *
 * @author khalil
 */
public class Statics {
    //public static final String BASE_URL="http://localhost/huntkingdom/web/app_dev.php";
    public static final String BASE_URL="http://localhost:8888/huntkingdomy/web/app_dev.php/";
    public static final String IMAGE_URL="http://localhost/HuntKingdomjava/uploads/";
    
     public static void setLabelStyle(Label l){
        l.getUnselectedStyle().setFgColor(-16777216);
        l.getUnselectedStyle().setFont(Font.createSystemFont(Font.FACE_SYSTEM, Font.STYLE_ITALIC, Font.SIZE_MEDIUM));
    }
     
     public static Button createBackBtn(){
         Button b=new Button("Back");
         b.getUnselectedStyle().setFgColor(5542241);
         b.addActionListener((ActionListener) (ActionEvent evt) -> {
            new Home().show();
         });
         return b;
     }
}
