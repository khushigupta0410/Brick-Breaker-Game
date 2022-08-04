/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package brickbreaker;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
/**
 *
 * @author gkhus
 */
public class MapGenerator {
    
    public int map[][];
    public int brickwidth;
    public int brickheight;
    
    public MapGenerator(int row,int col,int k){
        map=new int[row][col];
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if((i-j)%k==0)
                    map[i][j]=2; 
                else
                    map[i][j]=1; 
            }
        }
        brickwidth=600/col;
        brickheight=170/row;    
    }
    
    public void setBrick(int r,int c,int value){
        map[r][c]=value;
    }
    
    public void draw(Graphics2D g){
        for(int i=0;i<map.length;i++){
            for(int j=0;j<map[0].length;j++){
                if(!(map[i][j]==0)){
                    if(map[i][j]==2)
                    g.setColor(Color.RED); 
                    else
                    g.setColor(Color.darkGray);
                    g.fillRect(150+j*brickwidth, 100+i*brickheight, brickwidth, brickheight);
                    g.setColor(Color.black);
                    g.setStroke(new BasicStroke(3));
                    g.drawRect(150+j*brickwidth, 100+i*brickheight, brickwidth, brickheight);  
                }
            }
        }   
    }
}
