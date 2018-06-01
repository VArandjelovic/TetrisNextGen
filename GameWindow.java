package tetris;

import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;

public class GameWindow extends JFrame implements ActionListener, KeyListener {

	// TODO: Remove hard coded integers

	// Random Number Generator for new Elements
	Random r = new Random();

	// JPanel for the right Side
	private RightPanel rightPanel;

	// Array for keeping the game state in
	private Block[][] panelField;

	// The Tetris Blocks were currently controlling
	private Element currentElement;

	// The JPanel containing the Blocks
	private GamePanel panelGame;

	// Constructor
	public GameWindow() {

		// Method to instantiate visuals
		createView();

		// Timer for our ActionListener and refresh rate of once per second
		Timer t = new Timer(300, this);
		t.start();

	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if (code == KeyEvent.VK_LEFT) {

			if (currentElement != null) {
				Boolean leftMovable = currentElement.leftMovePossible(panelField);

				if (leftMovable) {
					currentElement.moveLeft(panelField);
				}
				panelGame.repaintGraphics(panelField);
			}
		}

		if (code == KeyEvent.VK_RIGHT) {
			if (currentElement != null) {
				Boolean rightMovable = currentElement.rightMovePossible(panelField);

				if (rightMovable) {
					currentElement.moveRight(panelField);
				}
				panelGame.repaintGraphics(panelField);
			}
		}

		if (code == KeyEvent.VK_E) {
			if (currentElement != null) {
				switch (currentElement.getList().get(0).getType()) {
				case 0:
					currentElement.rotateI(1, panelField);
					break;

				case 3:
					break;

				default:
					try {
						Boolean movepossible = currentElement.rightRotationPossible(panelField);
						if (movepossible) {
							currentElement.rotateRight(panelField);
						}
					} catch (ArrayIndexOutOfBoundsException ex) {
						boolean left = false;
						for (Block block : currentElement.getList()) {
							if (block.getCol() == 0) {
								left = true;
								break;
							}
						}
						if (left) {
							if (currentElement.rightMovePossible(panelField)) {
								currentElement.moveRight(panelField);
								if (currentElement.rightRotationPossible(panelField)) {
									currentElement.rotateRight(panelField);
								} else {
									currentElement.moveLeft(panelField);
								}
							}
						} else {
							if (currentElement.leftMovePossible(panelField)) {
								currentElement.moveLeft(panelField);
								if (currentElement.rightRotationPossible(panelField)) {
									currentElement.rotateRight(panelField);
								} else {
									currentElement.moveRight(panelField);
								}
							}
						}
					}
				}

				panelGame.repaintGraphics(panelField);
			}
		}

		if (code == KeyEvent.VK_Q) {
			if (currentElement != null) {
				switch (currentElement.getList().get(0).getType()) {
				case 0:
					currentElement.rotateI(0, panelField);
					break;
				case 3:
					break;
				default:
					try {
						Boolean movepossible = currentElement.leftRotationPossible(panelField);
						if (movepossible) {
							currentElement.rotateLeft(panelField);
						}
					} catch (ArrayIndexOutOfBoundsException ex) {
						boolean left = false;
						for (Block block : currentElement.getList()) {
							if (block.getCol() == 0) {
								left = true;
								break;
							}
						}
						if (left) {
							if (currentElement.rightMovePossible(panelField)) {
								currentElement.moveRight(panelField);
								if (currentElement.leftRotationPossible(panelField)) {
									currentElement.rotateLeft(panelField);
								} else {
									currentElement.moveLeft(panelField);
								}
							}
						} else {
							if (currentElement.leftMovePossible(panelField)) {
								currentElement.moveLeft(panelField);
								if (currentElement.leftRotationPossible(panelField)) {
									currentElement.rotateLeft(panelField);
								} else {
									currentElement.moveRight(panelField);
								}
							}
						}
					}
				}

				panelGame.repaintGraphics(panelField);
			}
		}

		if (code == KeyEvent.VK_DOWN) {
			if (currentElement != null) {
				Boolean movePossible = currentElement.movePossible(panelField);

				// If movepossible is still true then move all blocks in currentElement one
				// field down otherwise fix it in position by removing it from Currentelement
				if (movePossible) {
					currentElement.moveDown(panelField);
					panelGame.repaintGraphics(panelField);
				} else {
					currentElement.halt();
					currentElement = null;
					boolean gameover = gameOver();
					if (gameover) {
						restart();
					}
					deleteRows();
					int nextInt = rightPanel.getNextElementPanel().pass();
					currentElement = new Element(nextInt);
					currentElement.generateElement(panelField);
				}
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
		// First check whether or not were controlling an element that can be moved. If
		// not, then we make a new one
		if (currentElement == null) {
			int nextInt = rightPanel.getNextElementPanel().pass();
			currentElement = new Element(nextInt);
			currentElement.generateElement(panelField);
			currentElement.moveDown(panelField);
		}

		// If we are already controlling an element then we want to move it down if
		// possible or fix it in place
		// And then empty the currentElement ArrayList to obtain a new Element
		else {
			// Create a boolean to assert whether or not we can make a down movement or if
			// we have to Fix it in place.
			Boolean movePossible = currentElement.movePossible(panelField);

			// If movepossible is still true then move all blocks in currentelement one
			// field down otherwise fix it in position by removing it from Currentelement
			if (movePossible) {
				currentElement.moveDown(panelField);
			}

			else {
				currentElement.halt();
				currentElement = null;
				boolean gameover = gameOver();
				if (gameover) {
					restart();
				}
				deleteRows();
			}
		}
		panelGame.repaintGraphics(panelField);
	}

	// Generate a new Element of Random type, add the Blocks into the Grid and set
	// their moving values to true (Done automatically by constructor)

	public void createView() {

		// Shortcut
		Container contents = getContentPane();

		// Set a GridBagLayout so that Java respects the fucking size restrictions on
		// JPanel elements
		this.setLayout(new GridBagLayout());

		// New gridbagconstraints
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 1;
		c.gridy = 1;
		c.insets = new Insets(25, 25, 25, 25);

		// Initialize the Panel Array
		panelField = new Block[22][10];

		// Initialize the gamePanel
		panelGame = new GamePanel(panelField);

		// Add the initialized Grid to the JFrame
		contents.add(panelGame, c);
		c.insets = new Insets(0, 0, 0, 25);

		// Initialize the scorePanel
		rightPanel = new RightPanel();
		c.gridx = 2;
		c.gridy = 1;
		contents.add(rightPanel, c);

		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		pack();
		setResizable(true);
		contents.setBackground(Color.GREEN);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public void deleteRows() {

		int counter;
		int deletedrows = 0;

		for (int row = 21; row > 0; row--) {
			counter = 0;
			for (int col = 0; col < 10; col++) {
				if (panelField[row][col] != null) {
					counter++;
				}
			}
			if (counter == 10) {
				deleteRow(row);
				deletedrows++;
				row += 1;
			}
		}
		rightPanel.getScorePanel().updateScore(deletedrows);
	}

	private void deleteRow(int row) {
		for (int col = 0; col < 10; col++) {
			panelField[row][col] = null;
		}

		for (int r = row - 1; r > 0; r--) {
			for (int c = 0; c < 10; c++) {
				if (panelField[r][c] != null) {
					panelField[r][c].moveDown();
					panelField[r + 1][c] = panelField[r][c];
					panelField[r][c] = null;
				}
			}
		}
	}

	public void restart() {
		for (int row = 0; row < 22; row++) {
			for (int col = 0; col < 10; col++) {
				panelField[row][col] = null;
			}
		}
	}

	public boolean gameOver() {
		boolean gameover = false;
		for (int row = 0; row < 2; row++) {
			for (int col = 0; col < 10; col++) {
				if (panelField[row][col] != null) {
					gameover = true;
					return gameover;
				}
			}
		}

		return gameover;
	}
}
