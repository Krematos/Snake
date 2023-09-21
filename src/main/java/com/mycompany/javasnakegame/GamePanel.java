/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javasnakegame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import javax.swing.*;
import java.util.Random;

/**
 *
 * @author hmac1
 */
public class GamePanel extends JPanel implements ActionListener{
    // počáteční nastavení a deklarace    
    int castiTela = 6;
    int snezenaJablka;
    int jablkoX;
    int jablkoY;
    char smer = 'R';
    boolean beh = false;
    Timer casovac;
    Random nahoda;
    static final int OBRAZOVKA_VYSKA = 900;  // nastaví výšku na 810 pixelů
    static final int OBRAZOVKA_SIRKA = 900;  // nastaví sířku na 810 pixelů
    static final int VELIKOST_JEDNOTKY = 30;  // nastaví velikost jednotky na 30 
    static final int HERNI_JEDNOTKY = (OBRAZOVKA_VYSKA*OBRAZOVKA_SIRKA)/VELIKOST_JEDNOTKY;
    static final int ZPOZDENI = 75;  
    final int x[] = new int [HERNI_JEDNOTKY];
    final int y[] = new int [HERNI_JEDNOTKY];
    
    
    GamePanel(){
        /*for (int i = 0; i < HERNI_JEDNOTKY; i++) {
    x[i] = 0;
    y[i] = 0;
    }*/         
        nahoda = new Random();
        this.setPreferredSize(new Dimension(OBRAZOVKA_VYSKA,OBRAZOVKA_SIRKA)); // nastavuje  preferovanou velikost 
        this.setBackground(Color.darkGray); // nastavuje pozadí herního panelu na darkGray
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startHry();  // start hry
    }    
    public void startHry(){
        novyJablko();
        beh = true;
        casovac = new Timer(ZPOZDENI,this); // vytvoří nový objekt třídy Timer a nastaví jeho zpoždění
        casovac.start();  // zacne generovat události v pravidelných intervalech
    }    
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        
        if(beh) {
            /*
            for(int i=0;i< OBRAZOVKA_VYSKA/VELIKOST_JEDNOTKY;i++){  // použití cyklu na rozkouskování obrazovky
            g.drawLine(i*VELIKOST_JEDNOTKY, 0, i*VELIKOST_JEDNOTKY, OBRAZOVKA_VYSKA);
            g.drawLine(0,  i*VELIKOST_JEDNOTKY, OBRAZOVKA_SIRKA, i*VELIKOST_JEDNOTKY);
        }*/
        g.setColor(Color.red);  // nastaví barvu "jablka" na červenou
        g.fillOval(jablkoX, jablkoY, VELIKOST_JEDNOTKY, VELIKOST_JEDNOTKY);
        
        for(int i = 0; i < castiTela;i++){
            if(i == 0){
                g.setColor(Color.ORANGE);
                g.fillRect(x[i], y[i], VELIKOST_JEDNOTKY, VELIKOST_JEDNOTKY);
            }
            else {
                g.setColor(new Color(45,180,0));
                //g.setColor(new Color(nahoda.nextInt(20),nahoda.nextInt(255), nahoda.nextInt(255)));
                g.fillRect(x[i], y[i], VELIKOST_JEDNOTKY, VELIKOST_JEDNOTKY);
            }
        }
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Mačkání hada: " + snezenaJablka, (OBRAZOVKA_VYSKA - metrics.stringWidth("Mačkání hada: " + snezenaJablka))/2, g.getFont().getSize());
        }
        else{
            konecHry(g);
        }
    }    
    public void pohyb(){
        for(int i = castiTela; i > 0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch(smer){
            case 'U':
                y[0] = y[0] - VELIKOST_JEDNOTKY;
                break;
            case 'D':
                y[0] = y[0] + VELIKOST_JEDNOTKY;
                break;
            case 'L':
                x[0] = x[0] - VELIKOST_JEDNOTKY;
                break;
            case 'R':
                x[0] = x[0] + VELIKOST_JEDNOTKY;
                break;
        }
    } 
    public void novyJablko(){
        // vytvoří "jablko" a umístí ho náhodně do prostoru
        jablkoX = nahoda.nextInt((int)(OBRAZOVKA_VYSKA/VELIKOST_JEDNOTKY))*VELIKOST_JEDNOTKY;
        jablkoY = nahoda.nextInt((int)(OBRAZOVKA_SIRKA/VELIKOST_JEDNOTKY))*VELIKOST_JEDNOTKY;
    }
    public void zkontrolujStrety(){
        for(int i = castiTela;i>0;i--){ // ověřuje jestli se hlava nestřetla s tělem 
            if((x[0] == x[i]) && (y[0] == y[i])){
                beh = false;
            }
        }
        // ověřuje jestli se hlava dotkla horního okraje
        if(y[0] < 0){
            beh = false;
        }
        // ověřuje jestli se hlava dotkla dolniho okraje
        if(y[0] >= OBRAZOVKA_VYSKA){
            beh = false;
        }
        // ověřuje jestli se hlava dotkla levého okraje
        if(x[0] < 0){
            beh = false;
        }
        // ověřuje jestli se hlava dotkla pravého okraje
        if(x[0] >= OBRAZOVKA_SIRKA){
            beh = false;
        }
        if(!beh){
            casovac.stop();
        }
    }
    public void zkontrolujJablko(){
        if((x[0] == jablkoX) && (y[0] == jablkoY)){  // ověřuje jistli had nesnědl jablko
            castiTela++;   // přidá další část těla, když sní jablko            
            snezenaJablka++; // přidá snědené jablko
            novyJablko();   // vytvoří nové jablko
        }
    } 
    public void konecHry(Graphics g){
        // score text
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Mačkal jsi hada: " + snezenaJablka, (OBRAZOVKA_VYSKA - metrics.stringWidth(" Mačkal jsi hada: " + snezenaJablka))/2, g.getFont().getSize());
        // text pro konec hry
        g.setColor(Color.red);
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        FontMetrics metricsKonec = getFontMetrics(g.getFont());
        g.drawString("  Game Over", (OBRAZOVKA_VYSKA/1 - metricsKonec.stringWidth("Game Over"))/3, OBRAZOVKA_SIRKA/2); // když nevíš jak to dát na střed tak tam prostě dej spoustu mezer xD
        g.drawString("  Mačkáš mi hada kámo!", (OBRAZOVKA_VYSKA/1 - metricsKonec.stringWidth("Mačkáš mi hada kámo!"))/3, OBRAZOVKA_SIRKA/2 + g.getFont().getSize());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(beh){
            pohyb();
            zkontrolujJablko();
            zkontrolujStrety();
        }
        repaint(); // překleslí herní panel na základě nového stavu
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                    if(smer != 'R'){
                        smer = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(smer != 'L'){
                        smer = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(smer != 'D'){
                        smer = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(smer != 'U'){
                        smer = 'D';
                    }
                    break;    
            }
        }
    }
    
}
