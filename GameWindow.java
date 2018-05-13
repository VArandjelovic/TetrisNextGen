package tetris;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener, KeyListener {
	
	//Random Number Generator for new Elements
	Random r = new Random();
	
	//Array for keeping the game state in
	private Block[][] panelField;
	
	//The Tetris Blocks were currently controlling
	private ArrayList<Block> currentElement = new ArrayList<>();
	
	//The JPanel containing the Blocks
	private GamePanel panelGame;
	
	//The Block we rotate the current Element around
	private Block rotator;
	
	//Constructor
	public GameWindow() {
		
		//Method to instantiate visuals
		createView();
		
		//Timer for our ActionListener and refresh rate of once per second
		Timer t = new Timer(300,this);
		t.start();
		
		
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_LEFT) {
			
			
			Boolean leftMovable = true;
			
			for(Block block: currentElement) {
				
				int col = block.getCol();
				int row = block.getRow();
				
				if(col == 0) {
					leftMovable = false;
					break;
				}
				
				try {
					if(panelField[row][col-1] != null && !(panelField[row][col-1].isMoving())) {
						leftMovable = false;
						break;
					}
				} catch(NullPointerException ex){
					continue;
				}
			}
			
			if(leftMovable) {
				for(int r = 0; r < 22; r++) {
					for(int c = 1; c < 10; c++) {
						if(panelField[r][c] != null && panelField[r][c].isMoving()) {
							panelField[r][c-1] = panelField[r][c];
							panelField[r][c] = null;
							panelField[r][c-1].moveLeft();
						}
					}
				}
			}
			panelGame.repaintGraphics(panelField);
		}
		
		if(code == KeyEvent.VK_RIGHT) {
			
			Boolean rightMovable = true;
			
			for(Block block: currentElement) {
				
				int col = block.getCol();
				int row = block.getRow();
				
				if(col == 9) {
					rightMovable = false;
					break;
				}
				
				try {
					if(panelField[row][col + 1] != null && !(panelField[row][col+1].isMoving())) {
						rightMovable = false;
						break;
					}
				} catch(NullPointerException ex){
					continue;
				}
			}
			
			if(rightMovable) {
				for(int r = 0; r < 22; r++) {
					for(int c = 8; c > -1; c--) {
						if(panelField[r][c] != null && panelField[r][c].isMoving()) {
							panelField[r][c+1] = panelField[r][c];
							panelField[r][c] = null;
							panelField[r][c+1].moveRight();
						}
					}
				}
			}
			panelGame.repaintGraphics(panelField);
			
		}
		
		if(code == KeyEvent.VK_E) {
			int type = currentElement.get(0).getType();
			//TODO case 0, implement a check before rotating
			switch(type) {
				
				//The Type of Element the Block belongs to
				//0: I Cyan
				//1: J Blue
				//2: L Orange
				//3: O Yellow
				//4: S Green
				//5: T Purple
				//6: Z Red
				
			
				//Since the I block doesnt rotate around a block we hard code every case
				case 0:	//Check whether the bar is in a horizontal or vertical position
						boolean horizontal = true;
						int rowtemp = rotator.getRow();
						int coltemp = rotator.getCol();
						
						if(rotator.getCol() == currentElement.get(0).getCol() && rotator.getCol() == currentElement.get(1).getCol()) {
							horizontal = false;
						}
						if(horizontal) {
							//Check whether the block is in the starting position
							boolean starting = true;
							
							try{
								if(currentElement.contains(panelField[rowtemp][coltemp + 2])) {
									starting = false;
								}
							}catch(ArrayIndexOutOfBoundsException ex) {
								starting = true;
							}
							
							if(starting) {
								//The Block is in its starting position so we now know where all the other elements are and need to be projected relative to the rotator element.
								//Move the left most block
								panelField[rowtemp-2][coltemp-1] = panelField[rowtemp][coltemp-2];
								panelField[rowtemp][coltemp-2] = null;
								Block newpos = panelField[rowtemp-2][coltemp-1];
								newpos.moveRight();
								newpos.moveUp();
								newpos.moveUp();
								
								//2nd most left block
								panelField[rowtemp-1][coltemp-1] = panelField[rowtemp][coltemp-1];
								panelField[rowtemp][coltemp-1] = null;
								newpos = panelField[rowtemp-1][coltemp-1];
								newpos.moveUp();
								
								//rotator block
								panelField[rowtemp][coltemp-1] = panelField[rowtemp][coltemp];
								panelField[rowtemp][coltemp] = null;
								newpos = panelField[rowtemp][coltemp-1];
								newpos.moveLeft();
								
								//the last element
								panelField[rowtemp+1][coltemp-1] = panelField[rowtemp][coltemp+1];
								panelField[rowtemp][coltemp+1] = null;
								newpos = panelField[rowtemp+1][coltemp-1];
								newpos.moveLeft();
								newpos.moveLeft();
								newpos.moveDown();
								
								panelGame.repaintGraphics(panelField);
							}
							
							else {
								//The block is in the horizontal position where the rotator element is second from left
								//Move the first block (right most)
								panelField[rowtemp+2][coltemp+1] = panelField[rowtemp][coltemp+2];
								panelField[rowtemp][coltemp+2] = null;
								Block newpos = panelField[rowtemp+2][coltemp+1];
								newpos.moveLeft();
								newpos.moveDown();
								newpos.moveDown();
								
								//2nd Block from right
								panelField[rowtemp+1][coltemp+1] = panelField[rowtemp][coltemp+1];
								panelField[rowtemp][coltemp+1] = null;
								newpos = panelField[rowtemp+1][coltemp+1];
								newpos.moveDown();
								
								//rotator element
								panelField[rowtemp][coltemp+1] = panelField[rowtemp][coltemp];
								panelField[rowtemp][coltemp] = null;
								newpos = panelField[rowtemp][coltemp+1];
								newpos.moveRight();
								
								//Last block
								panelField[rowtemp-1][coltemp+1] = panelField[rowtemp][coltemp-1];
								panelField[rowtemp][coltemp-1] = null;
								newpos = panelField[rowtemp-1][coltemp+1];
								newpos.moveRight();
								newpos.moveRight();
								newpos.moveUp();
								
								panelGame.repaintGraphics(panelField);
							}
						}
						
						else {
							//Determine in which vertical position the block is in. Left means that the rotator element is 2nd from the bottom. Right means 3rd.
							Boolean left = true;
							
							try {
								if(currentElement.contains(panelField[rowtemp+2][coltemp])) {
									left = false;
								}
							} catch(ArrayIndexOutOfBoundsException ex) {
								left = true;
							}
							
							if(left) {
								//The Block is in the left position hence we can rotate it relative to the rotator position
								//Move the bottom most Block
								panelField[rowtemp-1][coltemp-1] = panelField[rowtemp+1][coltemp];
								panelField[rowtemp+1][coltemp] = null;
								Block newpos = panelField[rowtemp-1][coltemp-1];
								newpos.moveLeft();
								newpos.moveUp();
								newpos.moveUp();
								
								//Move the 2nd top Block because otherwise we would overwrite it if we first moved the Rotator block
								panelField[rowtemp-1][coltemp+1] = panelField[rowtemp-1][coltemp];
								panelField[rowtemp-1][coltemp] = null;
								newpos = panelField[rowtemp-1][coltemp+1];
								newpos.moveRight();
								
								//Now we can move the rotator Block
								panelField[rowtemp-1][coltemp] = panelField[rowtemp][coltemp];
								panelField[rowtemp][coltemp] = null;
								newpos = panelField[rowtemp-1][coltemp];
								newpos.moveUp();
								
								//and the last top most block
								panelField[rowtemp-1][coltemp+2] = panelField[rowtemp-2][coltemp];
								panelField[rowtemp-2][coltemp] = null;
								newpos = panelField[rowtemp-1][coltemp+2];
								newpos.moveRight();
								newpos.moveRight();
								newpos.moveDown();
								                                      
								panelGame.repaintGraphics(panelField);
							}
							
							else {
								//The Block is in the right position with the rotator second from the bottom. Again we can exactly determine the location of our current and target elements
								//Move the bottom most Block
								panelField[rowtemp+1][coltemp-2] = panelField[rowtemp+2][coltemp];
								panelField[rowtemp+2][coltemp] = null;
								Block newpos = panelField[rowtemp+1][coltemp-2];
								newpos.moveUp();
								newpos.moveLeft();
								newpos.moveLeft();
								
								//The 2nd bottom block
								panelField[rowtemp+1][coltemp-1] = panelField[rowtemp+1][coltemp];
								panelField[rowtemp+1][coltemp] = null;
								newpos = panelField[rowtemp+1][coltemp-1];
								newpos.moveLeft();
								
								//Rotator Element
								panelField[rowtemp+1][coltemp] = panelField[rowtemp][coltemp];
								panelField[rowtemp][coltemp] = null;
								newpos = panelField[rowtemp+1][coltemp];
								newpos.moveDown();
								
								//The top most block
								panelField[rowtemp+1][coltemp+1] = panelField[rowtemp-1][coltemp];
								panelField[rowtemp-1][coltemp] = null;
								newpos = panelField[rowtemp+1][coltemp+1];
								newpos.moveDown();
								newpos.moveDown();
								newpos.moveRight();
								
								panelGame.repaintGraphics(panelField);
							}
						}
						break;
				
				case 1:
					
			}
		}
		
		if(code == KeyEvent.VK_Q) {
			
		}
		
		if(code == KeyEvent.VK_DOWN) {
			
			Boolean movePossible = true;
			
			for(Block block: currentElement) {
				int row = block.getRow();
				int col = block.getCol();
				
				//Check whether or not there is a moving element in the last row.
				if(row == 21) {
					movePossible = false;
					break;
				}
				
				try{
					if(panelField[row + 1][col] != null && !(panelField[row + 1][col].isMoving())) {
						movePossible = false;
						break;
					}
				} catch(NullPointerException ex) {
					continue;
				}
			}
			
			//If movepossible is still true then move all blocks in currentElement one field down otherwise fix it in position by removing it from Currentelement
			if(movePossible) {
				for(int r = 1; r < 22; r++) {
					for(int col = 0; col < 10; col++) {
						if(panelField[21-r][col] != null && panelField[21-r][col].isMoving()) {
							panelField[21-r+1][col] = panelField[21-r][col];
							panelField[21-r][col] = null;
							
							//Increment the row coordinate of the block by 1
							panelField[21-r+1][col].moveDown();
						}
					}
				}
				panelGame.repaintGraphics(panelField);
			}
			
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public void actionPerformed(ActionEvent arg0) {
		//First check whether or not were controlling an element that can be moved. If not, then we make a new one
		if(currentElement.isEmpty()) {
			generateElement();
		}
		
		//If we are already controlling an element then we want to move it down if possible or fix it in place
		//And then empty the currentElement ArrayList to obtain a new Element
		else {
			//Create a boolean to assert whether or not we can make a down movement or if we have to Fix it in place.
			Boolean movePossible = movePossible();
			
			//If movepossible is still true then move all blocks in currentelement one field down otherwise fix it in position by removing it from Currentelement
			if(movePossible) {
				moveDown();
			}
			
			else {
				for(Block block: currentElement) {
					block.halt();
				}
				currentElement.clear();
			}
		}
		
		panelGame.repaintGraphics(panelField);
	}
	
	//Generate a new Element of Random type, add the Blocks into the Grid and set their moving values to true (Done automatically by constructor)
	public void generateElement() {
		int type = r.nextInt(7);
		
		//The Type of Element the Block belongs to
		//0: I Cyan
		//1: J Blue
		//2: L Orange
		//3: O Yellow
		//4: S Green
		//5: T Purple
		//6: Z Red
		switch(type) {
			
			case 0: for(int col = 3; col < 7; col++) {
						panelField[1][col] = new Block(1,col);
						panelField[1][col].setType(0);
						if(col == 5) {
							rotator = panelField[1][5];
						}
						currentElement.add(panelField[1][col]);
					}
					break;
			
			case 1: for(int col = 3; col < 6; col++) {
						panelField[1][col] = new Block(1,col);
						panelField[1][col].setType(1);
						if(col == 4) {
							rotator = panelField[1][4];
						}
						currentElement.add(panelField[1][col]);
					}
					
					panelField[0][3] = new Block(0,3);
					panelField[0][3].setType(1);
					currentElement.add(panelField[0][3]);
					break;
					
			case 2: for(int col = 3; col < 6; col++) {
						panelField[1][col] = new Block(1,col);
						panelField[1][col].setType(2);
						if(col == 4) {
							rotator = panelField[1][4];
						}
						currentElement.add(panelField[1][col]);
					}
			
					panelField[0][5] = new Block(0,5);
					panelField[0][5].setType(2);
					currentElement.add(panelField[0][5]);
					break;
			
			case 3: for(int col = 4; col < 6; col++) {
						for(int row = 0; row < 2; row++) {
							panelField[row][col] = new Block(row,col);
							panelField[row][col].setType(3);
							currentElement.add(panelField[row][col]);
						}	
					}
					break;
			
			case 4: for(int col = 3; col < 5; col++) {
						panelField[1][col] = new Block(1,col); 
						panelField[1][col].setType(4);
						if(col == 4) {
							rotator = panelField[1][4];
						}
						currentElement.add(panelField[1][col]);
					}
			
					for(int col = 4; col < 6; col++) {
						panelField[0][col] = new Block(0,col);
						panelField[0][col].setType(4);
						currentElement.add(panelField[0][col]);
					}
					break;
			
			case 5: for(int col = 3; col < 6; col++) {
						panelField[1][col] = new Block(1,col);
						panelField[1][col].setType(5);
						if(col == 4) {
							rotator = panelField[1][4];
						}
						currentElement.add(panelField[1][col]);
					}
			
					panelField[0][4] = new Block(0,4);
					panelField[0][4].setType(5);
					currentElement.add(panelField[0][4]);
					break;
					
			
			case 6: for(int col = 4; col < 6; col++) {
						panelField[1][col] = new Block(1,col);
						panelField[1][col].setType(6);
						if(col == 4) {
							rotator = panelField[1][4];
						}
						currentElement.add(panelField[1][col]);
					}
					
					for(int col = 3; col < 5; col++) {
						panelField[0][col] = new Block(0,col);
						panelField[0][col].setType(6);
						currentElement.add(panelField[0][col]);
					}
					break;
			
		}
		
	}
	
	public void createView() {
		
		//Shortcut
		Container contents = getContentPane();
		
		//Set a GridBagLayout so that Java respects the fucking size restrictions on JPanel elements
		this.setLayout(new GridBagLayout());
		
		//New gridbagconstraints
		GridBagConstraints c = new GridBagConstraints();
		
		//Initialize the Panel Array
		panelField = new Block[22][10];
		
		//Initialize the gamePanel
		panelGame = new GamePanel(panelField);
		
		//Add the initialized Grid to the JFrame
		contents.add(panelGame,c);
		
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		pack();
		setBackground(Color.WHITE);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void rotateRight() {
		
		//We will use this method to generalize right Rotation for all tetroids apart from the "I" tetroid
		Block[][] rotategrid = new Block[3][3];
		rotategrid[1][1] = rotator;
		
		//Backup if a rotation isnt possible
		Block[][] restore = panelField;
		
		//Fill in the rotategrid Array
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c++) {
				if(r == 1 && c == 1) {
					continue;
				}
				
				rotategrid[r][c] = panelField[rotator.getRow()-1+r][rotator.getCol()-1+c];
			}
		}
		
		int state = rotator.getRotate();
		
		switch(state) {
			case 0:	Block[][] temp = startRight(rotategrid);
					if(temp == rotategrid) {
						//rotation failed
						panelField = restore;
						break;
					}
					
					for(int r = 0; r < 3; r++) {
						for(int c = 0; c < 3; c++) {
							if(currentElement.contains(temp[r][c])) {
								//Old coordinates for deletion
								int deleter = temp[r][c].getRow();
								int deletec = temp[r][c].getCol();
								
								//Input the new coordinates
								temp[r][c].setRow(rotator.getRow()-1+r);
								temp[r][c].setCol(rotator.getCol()-1+c);
								
								//And update the panel Field by adding the new coordinates (which are null as asserted by our rotation method)...
								Block b = temp[r][c];
								panelField[b.getRow()][b.getCol()] = b;
								
								//And deleting the old one
								panelField[deleter][deletec] = null;
								
							}
						}
					}
					panelGame.repaintGraphics(panelField);
					break;
			
			case 1: //startBottom();
					break;
			
			case 2:	//startLeft();
					break;
			
			case 3: //startTop();
					break;
		}
		
		
		
		
	}
	
	public Block[][] startRight(Block[][] input){
		Block[][] temp = input;
		
		//Backup Block if rotation fails
		Block[][] restore = input;
		
		//ArrayList for restoring the currentElement List
		ArrayList<Block> restoreElement = currentElement;
		
		//Go from right to left and then down up to rotate all the elements. This algorithm has been emperically tested and works for all blocks in their starting position
		//Without ever accidentally overwriting non moved blocks.
		for(int c = 0; c < 3; c++) {
			for(int r = 0; r < 3; r++) {
				if(currentElement.contains(temp[2-r][2-c])) {
					
					//Through practial tests or a simple check one can see that with this Algorithm used on tetroids in the starting position no feel that a block gets copied to
					//Is allowed to be null, hence we can practically assert whether or not a rotation is possible or not.
					if(temp[2-c][2-(2-r)] != null) {
						temp = restore;
						return temp;
					}
					
					temp[2-c][2-(2-r)] = temp[2-r][2-c];
					temp[2-r][2-c] = null;					
					currentElement.remove(temp[2-c][2-(2-r)]);
				}
			}
		}
		currentElement = restoreElement;
		return temp;
	}
	
	public void moveDown() {
		
		for(int r = 1; r < 22; r++) {
			for(int col = 0; col < 10; col++) {
				if(panelField[21-r][col] != null && panelField[21-r][col].isMoving()) {
					panelField[21-r+1][col] = panelField[21-r][col];
					panelField[21-r][col] = null;
					
					//Increment the row coordinate of the block by 1
					panelField[21-r+1][col].moveDown();
				}
			}
		}
		
	}
	
	public boolean movePossible() {
		
		Boolean movePossible = true;
		
		for(Block block: currentElement) {
			int row = block.getRow();
			int col = block.getCol();
			
			//Check whether or not there is a moving element in the last row.
			if(row == 21) {
				movePossible = false;
				break;
			}
			
			try{
				if(panelField[row + 1][col] != null && !(panelField[row + 1][col].isMoving())) {
					movePossible = false;
					break;
				}
			} catch(NullPointerException e) {
				continue;
			}
		}
		
		return movePossible;
	}
}
