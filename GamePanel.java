package tetris;

import java.awt.Dimension;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel {
	
	//The Tetris Grid we want to draw
	private Block[][] tetrisGrid;
	
	//Constructor
	public GamePanel(Block[][] tetrisGrid) {
		this.tetrisGrid = tetrisGrid;
		repaint();
	}
	
	//Set a new tetrisGrid to generate
	public void setTetrisGrid(Block[][] tetrisGrid) {
		this.tetrisGrid = tetrisGrid;
	}
	
	//Defining the paintComponent method that will generate the visuals of the grid
	@Override
	public void paintComponent(Graphics g1) {
		super.paintComponent(g1);
		Graphics2D g = (Graphics2D) g1;
		
		//Checks the whole array tetrisGrid. If a field is Null, then it isnt occupied and thus is a black field. Otherwise get the type of the Block occupying the Field
		//And paint it in the according color
		for(int r = 0; r < 22; r++) {
			for(int c = 0; c < 10; c++) {
				
				//If null is returned then the field is empty
				if(tetrisGrid[r][c] == null) {
					g.setColor(Color.BLACK);
					g.fillRect(c*32, r*32, 32, 32);
				}
				
				//If not then there is a Block element occupying the field and thus has a type and a color will be painted
				else {
					
					//Get the type of the Element that the Block is a part of
					int temp = tetrisGrid[r][c].getType();
					
					//Switch statement
					switch(temp) {
						case 0: g.setColor(Color.CYAN);
								g.fillRect(c*32,r*32,32,32);
								g.setColor(Color.BLACK);
								g.drawRect(c*32, r*32, 32, 32);
								break;
						
						case 1: g.setColor(Color.BLUE);
								g.fillRect(c*32,r*32,32,32);
								g.setColor(Color.BLACK);
								g.drawRect(c*32, r*32, 32, 32);
								break;
								
						case 2: g.setColor(Color.ORANGE);
								g.fillRect(c*32,r*32,32,32);
								g.setColor(Color.BLACK);
								g.drawRect(c*32, r*32, 32, 32);
								break;
								
						case 3: g.setColor(Color.YELLOW);
								g.fillRect(c*32,r*32,32,32);
								g.setColor(Color.BLACK);
								g.drawRect(c*32, r*32, 32, 32);
								break;
								
						case 4: g.setColor(Color.GREEN);
								g.fillRect(c*32,r*32,32,32);
								g.setColor(Color.BLACK);
								g.drawRect(c*32, r*32, 32, 32);
								break;
								
						case 5: g.setColor(new Color(128,0,128)); //Purple
								g.fillRect(c*32,r*32,32,32);
								g.setColor(Color.BLACK);
								g.drawRect(c*32, r*32, 32, 32);
								break;
								
						case 6: g.setColor(Color.RED);
								g.fillRect(c*32,r*32,32,32);
								g.setColor(Color.BLACK);
								g.drawRect(c*32, r*32, 32, 32);
								break;
					}			
					
				}
			}
		}
		
	}
	
	//Repaint the Graphics with the new Grid
	public void repaintGraphics(Block[][] tetrisGrid) {
		this.tetrisGrid = tetrisGrid;
		repaint();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(10*32,22*32);
	}
}
