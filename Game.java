/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;
/**
 *
 * @author gkhus
 */
public class Game extends JPanel implements KeyListener,ActionListener{
    
    private boolean play=false;
    private int score=0;
    private int playerx=350;
    private int ballposx=100;
    private int ballposy=400;
    private int balldirx=3;
    private int balldiry=3;
    private int delay=1;
    private Timer timer;
    private MapGenerator mp;
    private int totalbricks=45;
    private File ting;
    private File music;
    private Clip clip;
    private int k=4;
    private int temp=k;
    private int maxscore=0;
    
    public Game(){
        ting=new File("C:\\Users\\gkhus\\Documents\\NetBeansProjects\\brickbreaker\\src\\brickbreaker\\ting.wav");
        music=new File("C:\\Users\\gkhus\\Documents\\NetBeansProjects\\brickbreaker\\src\\brickbreaker\\music.wav");
        playsound(music);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
        timer.start();
        mp=new MapGenerator(5,9,k);
    }

    public void playsound(File sound){
        try {
            clip=AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(sound));
            clip.start();
        } 
        catch (Exception ex) {}
    }
    
    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(0, 0, 900, 800);
        //borders
        g.setColor(Color.yellow);
        g.fillRect(0, 0, 900, 5);
        g.fillRect(0, 0, 5, 800);
        g.fillRect(889, 0, 5, 800);
        //paddle
        g.setColor(Color.BLUE);
        g.fillRect(playerx,750,110,11);
        //ball
        g.setColor(Color.yellow);
        g.fillOval(ballposx, ballposy, 20, 20);
        //bricks
        mp.draw((Graphics2D) g); 
        //score
        g.setColor(Color.GREEN);
        g.setFont(new Font("serif",Font.BOLD,15));
        g.drawString("SCORE: "+score, 780, 30);
        //high score
        g.setColor(Color.GREEN);
        g.setFont(new Font("serif",Font.BOLD,15));
        g.drawString("HISCORE: "+maxscore, 770, 50);
        //pause
        g.setColor(Color.GREEN);
        g.setFont(new Font("serif",Font.BOLD,15));
        g.drawString("START OR PAUSE : PRESS ENTER", 20, 30);
        //REStart
        g.setColor(Color.RED);
        g.setFont(new Font("serif",Font.BOLD,15));
        g.drawString("RESET: PRESS R", 350, 30);
        //gameover   
        if(ballposy>=800){
                play=false;
                g.setColor(Color.red);
                g.drawString("GAME OVER :(", 350, 400);
                g.drawString("Score: "+score, 350, 450);
                if(maxscore<score)
                    maxscore=score;
                g.drawString("PRESS R TO RESTART", 350, 500);    
        }
        //won
        if(totalbricks==0){
            if(temp==1){
                play=false;
                if(maxscore<score)
                    maxscore=score;
                g.setColor(Color.GREEN);
                g.drawString("YOU WON :)", 350, 400);
                g.drawString("Score: "+score, 350, 450);
                g.drawString("PRESS R FOR RESTART", 350, 500);
            }
            else{
                play=false;
                g.setColor(Color.GREEN);
                g.drawString("LEVEL COMPLETE :)", 350, 400);
                g.drawString("Score: "+score, 350, 450);
                g.drawString("PRESS N FOR NEXT LEVEL", 350, 500);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_LEFT){
            if(playerx<=5)
                playerx=5;
            else{
                play=true;
                playerx-=30;  
            }         
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            if(playerx>=780)
                playerx=780;
            else{
                play=true;
                playerx+=30;
            }        
        }
        if(e.getKeyCode()==KeyEvent.VK_ENTER){
            if(play==false)
                play=true;
            else
                play=false;        
        }
        if(e.getKeyCode()==KeyEvent.VK_R){
            play=false;
            score=0;
            temp=k;
            playerx=350;
            ballposx=100;
            ballposy=400;
            balldirx=3;
            balldiry=3;
            totalbricks=45;
            mp=new MapGenerator(5,9,k);
        }
        if(temp>1){
            if(e.getKeyCode()==KeyEvent.VK_N){
                play=false;
                playerx=350;
                ballposx=100;
                ballposy=400;
                balldirx=3;
                balldiry=3;
                balldirx+=k-temp;
                balldiry+=k-temp;
                totalbricks=45;
                mp=new MapGenerator(5,9,--temp);
            }
        } 
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void actionPerformed(ActionEvent e) {
        if(play){
            //ball hits the left and right boundary
            if(ballposx<=5 || ballposx>=872){
                balldirx=-balldirx;
                playsound(ting);
            }   
            //ball hits the top boundary
            if(ballposy<=5){
                 balldiry=-balldiry;
                  playsound(ting);
            }  
            //ball hits the paddle
            Rectangle ball=new Rectangle(ballposx,ballposy,20,20);
            Rectangle paddle=new Rectangle(playerx,750,110,11);
            if(ball.intersects(paddle)){
                balldiry=-balldiry;
                 playsound(ting);
            }
            //ball hits the bricks
            A:for(int i=0;i<mp.map.length;i++){
                for(int j=0;j<mp.map[0].length;j++){
                    if(mp.map[i][j]==1){
                        Rectangle brick=new Rectangle(150+j*mp.brickwidth, 100+i*mp.brickheight, mp.brickwidth, mp.brickheight);
                        if(ball.intersects(brick)){
                            playsound(ting);
                            mp.setBrick(i,j,0);
                            totalbricks--;
                            score+=5;
                            if(ballposx<=150+j*mp.brickwidth || ballposx>= mp.brickwidth+150+j*mp.brickwidth)
                                balldirx=-balldirx;
                            else
                                balldiry=-balldiry;
                            break A;
                        }
                    }
                    if(mp.map[i][j]==2){
                        Rectangle brick=new Rectangle(150+j*mp.brickwidth, 100+i*mp.brickheight, mp.brickwidth, mp.brickheight);
                        if(ball.intersects(brick)){
                            playsound(ting);
                            mp.setBrick(i,j,1);
                            score+=2;
                            if(ballposx<=150+j*mp.brickwidth || ballposx>= mp.brickwidth+150+j*mp.brickwidth)
                                balldirx=-balldirx;
                            else
                                balldiry=-balldiry;
                            break A;
                        }
                    }
                }
            }
            ballposx+=balldirx;
            ballposy+=balldiry;
        }
        repaint();
    } 
}
