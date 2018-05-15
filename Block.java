package tetris;

public class Block{
	
	//Gives us the position of the Block in an Element object
	private int rowcord, colcord;
	
	//Boolean wether the Block is currently part of a Tetris block in Movement
	private boolean moving;
	
	//Used for rotating
	private boolean marked = false;
	
	//The Type of Element the Block belongs to
	//0: I Cyan
	//1: J Blue
	//2: L Orange
	//3: O Yellow
	//4: S Green
	//5: T Purple
	//6: Z Red
	private int type;
	
	//Rotation state used for determining which algorithm to use
	//0: starting position
	//1: one rotation right or three left
	//2: 180 degree rotation
	//3: one left rotation or three right rotations
	private int rotationstate;
	
	//Constructor
	public Block(int rowcord, int colcord) {
		this.rowcord = rowcord;
		this.colcord = colcord;
		rotationstate = 0;
		moving = true;
		
	}
	
	//Set the status of the Block to not moving
	public void halt() {
		moving = false;
	}
	
	//Move the Block one square down
	public void moveDown() {
		rowcord += 1;
	}
	
	//Move the Block one square left
	public void moveLeft() {
		colcord -= 1;
	}
	
	//Move the Block one square to the right
	public void moveRight() {
		colcord += 1;
	}
	
	//Moves the block one square up
	public void moveUp() {
		rowcord -=1;
	}
	
	//Returns rowcord
	public int getRow() {
		return rowcord;
	}
	
	//Returns colcord
	public int getCol() {
		return colcord;
	}
	
	//Returns whether the Block is moving or not
	public boolean isMoving() {
		return moving;
	}
	
	//Returns the type of tetris Element this Block belongs to
	public int getType() {
		return type;
	}
	
	//Set the type of a Block
	public void setType(int i) {
		type = i;
	}
	
	//Updates the rotationstate upon left rotation
	public void rotateLeft() {
		if(rotationstate == 0) {
			rotationstate = 3;
		}
		
		else {
			rotationstate -= 1;
		}
	}
	
	//updates the rotationstate upon right rotation
	public void rotateRight() {
		rotationstate = (rotationstate + 1) % 4;
	}
	
	//get the rotation state
	public int getRotate() {
		return rotationstate;
	}
	
	//set the row of the block manually
	public void setRow(int r) {
		rowcord = r;
	}
	
	//set the column of the block manually
	public void setCol(int c) {
		colcord = c;
	}
	
	public void setMarked(boolean i) {
		this.marked = i;
	}
	
	public boolean isMarked() {
		return marked;
	}
}
