/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.javasnakegame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 *
 * @author hmac1
 */
public class GamePanel extends JPanel implements ActionListener {
    // počáteční nastavení a deklarace

    JButton playAgainButton; // tlačítko pro opětovné hraní
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
    static final int ZPOZDENI = 100;
    final int x[] = new int [HERNI_JEDNOTKY];
    final int y[] = new int [HERNI_JEDNOTKY];
    
    
    GamePanel(){
        nahoda = new Random();
        this.setPreferredSize(new Dimension(OBRAZOVKA_VYSKA,OBRAZOVKA_SIRKA)); // nastavuje  preferovanou velikost 
        this.setBackground(Color.darkGray); // nastavuje pozadí herního panelu na darkGray
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());


        initPlayAgainButton();
        resetHernihoStavu(); // resetuje herní stav
        startHry(); // spustí hru
    }    
    public void startHry(){
        //novyJablko();
        beh = true;
        casovac = new Timer(ZPOZDENI,this); // vytvoří nový objekt třídy Timer a nastaví jeho zpoždění
        casovac.start();  // zacne generovat události v pravidelných intervalech
        playAgainButton.setVisible(false); // skryje tlačítko pro opětovné hraní
        repaint(); // překreslí herní panel
    }

    // metoda  k resetování herního stavu
    private void resetHernihoStavu(){
        castiTela = 6;
        snezenaJablka = 0;
        smer = 'R';
        x[0] = OBRAZOVKA_VYSKA / 2; // resetuje pozici hada na střed
        y[0] = OBRAZOVKA_SIRKA / 2; // resetuje pozici hada na střed
        for (int i = 1; i < HERNI_JEDNOTKY; i++) {
            x[i] = -100; // resetuje pozice těla hada
            y[i] = -100; // resetuje pozice těla hada
        }
        novyJablko();
    }

    // metoda pro inicializaci tlačítka pro opětovné hraní
    public void initPlayAgainButton() {
        playAgainButton = new JButton("Hraj znovu");  // vytvoří tlačítko pro opětovné hraní
        playAgainButton.setForeground(Color.white);
        playAgainButton.setBackground(new Color(0xD2691E));
        playAgainButton.setFont(new Font("Monospaced", Font.BOLD, 30));
        playAgainButton.setVisible(false); // tlačítko bude viditelné až po skončení hry
        playAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetHernihoStavu();
                startHry(); // spustí hru znovu
            }
        });
        this.setLayout(null);
        this.add(playAgainButton);

    }


    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    // metoda pro vykreslení herního panelu
    public void draw(Graphics g){
        
        if(beh) {
        g.setColor(Color.red);  // nastaví barvu "jablka" na červenou
        g.fillOval(jablkoX, jablkoY, VELIKOST_JEDNOTKY, VELIKOST_JEDNOTKY);
        
        for(int i = 0; i < castiTela;i++){
            if(i == 0){
                g.setColor(Color.ORANGE);
                g.fillRect(x[i], y[i], VELIKOST_JEDNOTKY, VELIKOST_JEDNOTKY);
            }
            else {
                g.setColor(new Color(45,180,0));
                g.fillRect(x[i], y[i], VELIKOST_JEDNOTKY, VELIKOST_JEDNOTKY);
            }
        }
        // zobrazí skore
        g.setColor(Color.red);
        g.setFont(new Font("Serif", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Snězeno jablek: " + snezenaJablka, (OBRAZOVKA_VYSKA - metrics.stringWidth("Snězeno jablek: " + snezenaJablka))/2, g.getFont().getSize());
        }
        else{
            konecHry(g);
        }
    }

    // metoda pro pohyb hada
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
            casovac.stop(); // zastaví herní smyčku
            playAgainButton.setBounds(OBRAZOVKA_VYSKA / 2 - 100, OBRAZOVKA_SIRKA / 2 + 70, 250, 100);
            playAgainButton.setVisible(true); // zobrazí tlačítko pro opětovné hraní
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
        g.drawString("Snězeno jablek: " + snezenaJablka, (OBRAZOVKA_VYSKA - metrics.stringWidth("Snězeno jablek: " + snezenaJablka))/2, g.getFont().getSize());
        // text pro konec hry
        g.setColor(new Color(0xD2691E));
        g.setFont(new Font("Monospaced", Font.BOLD, 50));
        FontMetrics metricsKonec = getFontMetrics(g.getFont());
        g.drawString("Konec hry ", (OBRAZOVKA_VYSKA/2 - metricsKonec.stringWidth("Konec hry")), OBRAZOVKA_SIRKA/2);
       // g.drawString("  Mačkáš mi hada kámo!", (OBRAZOVKA_VYSKA/1 - metricsKonec.stringWidth("Mačkáš mi hada kámo!"))/3, OBRAZOVKA_SIRKA/2 + g.getFont().getSize());
        // tlačítko pro opětovné hraní
        if(!playAgainButton.isVisible()) {
            playAgainButton.setVisible(true); // zobrazí tlačítko pro opětovné hraní
            playAgainButton.setBounds(OBRAZOVKA_VYSKA / 2 - 100, OBRAZOVKA_SIRKA / 2 + 75, 200, 50); // nastaví pozici tlačítka
        }
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

    // vnitřní třída pro zpracování stisků kláves
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
