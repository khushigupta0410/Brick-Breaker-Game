/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;
import javax.swing.JFrame;
/**
 *
 * @author gkhus
 */
public class Brickbreaker {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        JFrame obj=new JFrame();
        obj.setBounds(500, 100, 900, 800);
        obj.setResizable(false);
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setTitle("BRICK BREAKER");
        Game game=new Game();
        obj.setVisible(true);
        obj.add(game);
    }  
}
