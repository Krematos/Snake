/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javasnakegame;

import javax.swing.JFrame;

/**
 *
 * @author hmac1
 */
public class GameFrame extends JFrame{
    GameFrame(){
        
        this.add(new GamePanel()); // přidá GamePanel jako obsah okna
        this.setTitle("Snake"); // hodí oknu title Snake
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ukončení aplikace
        this.setResizable(false); // zakáže možnost změny velikosti okna uživatelem
        this.pack(); // nastaví velikost okna podle GamePanel()
        this.setVisible(true); // nastaví okno viditelné
        this.setLocationRelativeTo(null); // nastaví polohu okna do středu obrazovky
    }
}
