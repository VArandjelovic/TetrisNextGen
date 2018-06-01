package tetris;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.awt.Point;

public class Element {

	// pointer to the state array
	private int state = 0;

	// Element Type
	private int type;

	// Rotator Block
	private Block rotator;

	// Container for holding the Blocks of this element
	private ArrayList<Block> list = new ArrayList<>();

	public Element(int type) {
		this.type = type;
	}

	public void generateElement(Block[][] panelField) {
		// The Type of Element the Block belongs to
		// 0: I Cyan
		// 1: J Blue
		// 2: L Orange
		// 3: O Yellow
		// 4: S Green
		// 5: T Purple
		// 6: Z Red
		switch (type) {

		case 0:
			for (int col = 3; col < 7; col++) {
				panelField[1][col] = new Block(1, col);
				panelField[1][col].setType(0);
				list.add(panelField[1][col]);
				if (col == 5) {
					rotator = panelField[1][5];
				}
			}
			break;

		case 1:
			for (int col = 3; col < 6; col++) {
				panelField[1][col] = new Block(1, col);
				panelField[1][col].setType(1);
				list.add(panelField[1][col]);
				if (col == 4) {
					rotator = panelField[1][4];
				}
			}

			panelField[0][3] = new Block(0, 3);
			panelField[0][3].setType(1);
			list.add(panelField[0][3]);

			break;

		case 2:
			for (int col = 3; col < 6; col++) {
				panelField[1][col] = new Block(1, col);
				panelField[1][col].setType(2);
				list.add(panelField[1][col]);
				if (col == 4) {
					rotator = panelField[1][4];
				}
			}

			panelField[0][5] = new Block(0, 5);
			panelField[0][5].setType(2);
			list.add(panelField[0][5]);
			break;

		case 3:
			for (int col = 4; col < 6; col++) {
				for (int row = 0; row < 2; row++) {
					panelField[row][col] = new Block(row, col);
					panelField[row][col].setType(3);
					list.add(panelField[row][col]);
				}
			}
			break;

		case 4:
			for (int col = 3; col < 5; col++) {
				panelField[1][col] = new Block(1, col);
				panelField[1][col].setType(4);
				list.add(panelField[1][col]);
				if (col == 4) {
					rotator = panelField[1][4];
				}
			}

			for (int col = 4; col < 6; col++) {
				panelField[0][col] = new Block(0, col);
				panelField[0][col].setType(4);
				list.add(panelField[0][col]);
			}
			break;

		case 5:
			for (int col = 3; col < 6; col++) {
				panelField[1][col] = new Block(1, col);
				panelField[1][col].setType(5);
				list.add(panelField[1][col]);
				if (col == 4) {
					rotator = panelField[1][4];
				}
			}

			panelField[0][4] = new Block(0, 4);
			panelField[0][4].setType(5);
			list.add(panelField[0][4]);
			break;

		case 6:
			for (int col = 4; col < 6; col++) {
				panelField[1][col] = new Block(1, col);
				panelField[1][col].setType(6);
				list.add(panelField[1][col]);
				if (col == 4) {
					rotator = panelField[1][4];
				}
			}

			for (int col = 3; col < 5; col++) {
				panelField[0][col] = new Block(0, col);
				panelField[0][col].setType(6);
				list.add(panelField[0][col]);
			}
			break;

		}

	}

	public void halt() {
		for (Block block : list) {
			block.halt();
		}
	}

	public boolean movePossible(Block[][] panelField) {

		Boolean movePossible = true;

		for (Block block : list) {
			int row = block.getRow();
			int col = block.getCol();

			// Check whether or not there is a moving element in the last row.
			if (row == 21) {
				movePossible = false;
				return movePossible;
			}

			try {
				if (panelField[row + 1][col] != null && !(panelField[row + 1][col].isMoving())) {
					movePossible = false;
					return movePossible;
				}
			} catch (NullPointerException e) {
				continue;
			}
		}

		return movePossible;
	}

	public void moveDown(Block[][] panelField) {

		for (int r = 1; r < 22; r++) {
			for (int col = 0; col < 10; col++) {
				if (panelField[21 - r][col] != null && panelField[21 - r][col].isMoving()) {
					panelField[21 - r + 1][col] = panelField[21 - r][col];
					panelField[21 - r][col] = null;

					// Increment the row coordinate of the block by 1
					panelField[21 - r + 1][col].moveDown();
				}
			}
		}
	}

	public boolean leftMovePossible(Block[][] panelField) {
		Boolean leftMovable = true;

		for (Block block : list) {

			int col = block.getCol();
			int row = block.getRow();

			if (col == 0) {
				leftMovable = false;
				return leftMovable;
			}

			try {
				if (panelField[row][col - 1] != null && !(panelField[row][col - 1].isMoving())) {
					leftMovable = false;
					return leftMovable;
				}
			} catch (NullPointerException ex) {
				continue;
			}
		}

		return leftMovable;
	}

	public void moveLeft(Block[][] panelField) {

		for (int r = 0; r < 22; r++) {
			for (int c = 1; c < 10; c++) {
				if (panelField[r][c] != null && panelField[r][c].isMoving()) {
					panelField[r][c - 1] = panelField[r][c];
					panelField[r][c] = null;
					panelField[r][c - 1].moveLeft();
				}
			}
		}

	}

	public boolean rightMovePossible(Block[][] panelField) {

		Boolean rightMovable = true;

		for (Block block : list) {

			int col = block.getCol();
			int row = block.getRow();

			if (col == 9) {
				rightMovable = false;
				return rightMovable;
			}

			try {
				if (panelField[row][col + 1] != null && !(panelField[row][col + 1].isMoving())) {
					rightMovable = false;
					return rightMovable;
				}
			} catch (NullPointerException ex) {
				continue;
			}
		}
		return rightMovable;

	}

	public void moveRight(Block[][] panelField) {

		for (int r = 0; r < 22; r++) {
			for (int c = 8; c > -1; c--) {
				if (panelField[r][c] != null && panelField[r][c].isMoving()) {
					panelField[r][c + 1] = panelField[r][c];
					panelField[r][c] = null;
					panelField[r][c + 1].moveRight();
				}
			}
		}
	}

	public Boolean leftRotationPossible(Block[][] panelField) {
		Block[][] rotategrid = new Block[3][3];
		rotategrid[1][1] = rotator;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (r == 1 && c == 1) {
					continue;
				}

				rotategrid[r][c] = panelField[rotator.getRow() - 1 + r][rotator.getCol() - 1 + c];
			}
		}

		boolean movepossible = true;

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (list.contains(rotategrid[row][col]) && rotategrid[row][col] != rotator) {
					if (rotategrid[2 - col][row] != null && !(list.contains(rotategrid[2 - col][row]))) {
						movepossible = false;
						return movepossible;
					}
				}
			}
		}

		return movepossible;
	}

	public void rotateLeft(Block[][] panelField) {
		// saves the location of the blocks in the panelfield (relative to the rotator)
		// while we changee the rotategrid to hold future locations
		HashMap<Block, Point> locsaver = new HashMap<>();

		Boolean rotationfinished = false;
		rotator.setMarked(true);

		Block[][] rotategrid = new Block[3][3];
		rotategrid[1][1] = rotator;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (r == 1 && c == 1) {
					continue;
				}

				rotategrid[r][c] = panelField[rotator.getRow() - 1 + r][rotator.getCol() - 1 + c];
			}
		}

		while (!rotationfinished) {
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {

					if (list.contains(rotategrid[row][col]) && !list.contains(rotategrid[2 - col][row])
							&& !rotategrid[row][col].isMarked()) {
						rotategrid[2 - col][row] = rotategrid[row][col];
						rotategrid[row][col] = null;
						rotategrid[2 - col][row].setMarked(true);
					}

				}
			}

			rotationfinished = true;

			for (Block block : list) {
				if (block.isMarked() == false) {
					rotationfinished = false;
					break;
				}
			}

		}

		for (Block block : list) {
			block.setMarked(false);
		}

		// Fill the hashlist with the target coordinates of the blocks
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (list.contains(rotategrid[row][col])) {
					locsaver.put(rotategrid[row][col],
							new Point(rotator.getCol() - 1 + col, rotator.getRow() - 1 + row));
				}
			}
		}

		boolean rotfinished = false;
		rotator.setMarked(true);

		while (!rotfinished) {

			for (Block block : list) {
				if (block.isMarked()) {
					continue;
				}
				int oldrow = block.getRow();
				int oldcol = block.getCol();
				Double newrow = locsaver.get(block).getY();
				Double newcol = locsaver.get(block).getX();
				if (panelField[newrow.intValue()][newcol.intValue()] == null) {
					panelField[newrow.intValue()][newcol.intValue()] = panelField[oldrow][oldcol];
					panelField[oldrow][oldcol] = null;
					panelField[newrow.intValue()][newcol.intValue()].setMarked(true);
					panelField[newrow.intValue()][newcol.intValue()].setRow(newrow.intValue());
					panelField[newrow.intValue()][newcol.intValue()].setCol(newcol.intValue());

				}
			}

			rotfinished = true;

			for (Block block : list) {
				if (block.isMarked() == false) {
					rotfinished = false;
					break;
				}
			}
		}

		for (Block block : list) {
			block.setMarked(false);
		}

	}

	public boolean rightRotationPossible(Block[][] panelField) {

		Block[][] rotategrid = new Block[3][3];
		rotategrid[1][1] = rotator;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (r == 1 && c == 1) {
					continue;
				}

				rotategrid[r][c] = panelField[rotator.getRow() - 1 + r][rotator.getCol() - 1 + c];

			}
		}

		boolean movepossible = true;

		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (list.contains(rotategrid[row][col]) && rotategrid[row][col] != rotator) {
					if (rotategrid[col][2 - row] != null && !(list.contains(rotategrid[col][2 - row]))) {
						movepossible = false;
						return movepossible;
					}
				}
			}
		}

		return movepossible;
	}

	public void rotateRight(Block[][] panelField) {

		// saves the location of the blocks in the panelfield (relative to the rotator)
		// while we changee the rotategrid to hold future locations
		HashMap<Block, Point> locsaver = new HashMap<>();

		Boolean rotationfinished = false;
		rotator.setMarked(true);

		Block[][] rotategrid = new Block[3][3];
		rotategrid[1][1] = rotator;

		for (int r = 0; r < 3; r++) {
			for (int c = 0; c < 3; c++) {
				if (r == 1 && c == 1) {
					continue;
				}

				rotategrid[r][c] = panelField[rotator.getRow() - 1 + r][rotator.getCol() - 1 + c];
			}
		}

		while (!rotationfinished) {
			for (int row = 0; row < 3; row++) {
				for (int col = 0; col < 3; col++) {

					if (list.contains(rotategrid[row][col]) && !list.contains(rotategrid[col][2 - row])
							&& !rotategrid[row][col].isMarked()) {
						rotategrid[col][2 - row] = rotategrid[row][col];
						rotategrid[row][col] = null;
						rotategrid[col][2 - row].setMarked(true);
					}

				}
			}

			rotationfinished = true;

			for (Block block : list) {
				if (block.isMarked() == false) {
					rotationfinished = false;
					break;
				}
			}

		}

		for (Block block : list) {
			block.setMarked(false);
		}

		// Fill the hashlist with the target coordinates of the blocks
		for (int row = 0; row < 3; row++) {
			for (int col = 0; col < 3; col++) {
				if (list.contains(rotategrid[row][col])) {
					locsaver.put(rotategrid[row][col],
							new Point(rotator.getCol() - 1 + col, rotator.getRow() - 1 + row));
				}
			}
		}

		boolean rotfinished = false;
		rotator.setMarked(true);

		while (!rotfinished) {

			for (Block block : list) {
				if (block.isMarked()) {
					continue;
				}
				int oldrow = block.getRow();
				int oldcol = block.getCol();
				Double newrow = locsaver.get(block).getY();
				Double newcol = locsaver.get(block).getX();
				if (panelField[newrow.intValue()][newcol.intValue()] == null) {
					panelField[newrow.intValue()][newcol.intValue()] = panelField[oldrow][oldcol];
					panelField[oldrow][oldcol] = null;
					panelField[newrow.intValue()][newcol.intValue()].setMarked(true);
					panelField[newrow.intValue()][newcol.intValue()].setRow(newrow.intValue());
					panelField[newrow.intValue()][newcol.intValue()].setCol(newcol.intValue());

				}
			}

			rotfinished = true;

			for (Block block : list) {
				if (block.isMarked() == false) {
					rotfinished = false;
					break;
				}
			}
		}

		for (Block block : list) {
			block.setMarked(false);
		}

	}

	public void rotateI(int direction, Block[][] panelField) {

		HashMap<Block, Point> locsaver = new HashMap<>();

		// left rotation
		if (direction == 0) {
			int rowtemp = rotator.getRow();
			int coltemp = rotator.getCol();

			switch (state) {

			// starting position
			case 0:
				// boolean for determining wether or not a rotation is possible
				boolean rotatable = true;

				// new goal coordinates for the blocks
				for (int temp = 0; temp < 4; temp++) {
					locsaver.put(panelField[rowtemp][coltemp - 2 + temp], new Point(coltemp, rowtemp + 1 - temp));
				}

				// check wether or not the coordinates are valid
				for (Block block : list) {
					Double newcol = locsaver.get(block).getX();
					Double newrow = locsaver.get(block).getY();
					if (panelField[newrow.intValue()][newcol.intValue()] != null
							&& !list.contains(panelField[newrow.intValue()][newcol.intValue()])) {
						rotatable = false;
					}

				}

				// enter the new position into the panelfield and update their row and col
				// coordinates
				if (rotatable) {

					// delete the old block positions from the panelField
					for (Block block : list) {
						int oldrow = block.getRow();
						int oldcol = block.getCol();
						panelField[oldrow][oldcol] = null;
					}

					// and add them again in their new positions
					for (Block block : list) {
						Double newcol = locsaver.get(block).getX();
						Double newrow = locsaver.get(block).getY();
						// insert it into the panelField in the new coordinates and update the
						// coordinates
						panelField[newrow.intValue()][newcol.intValue()] = block;
						block.setRow(newrow.intValue());
						block.setCol(newcol.intValue());
					}
					// update the state after updating the positions
					state = 3;
				}
				break;

			// right rotation from starting position
			case 1:
				// boolean for determining wether or not a rotation is possible
				boolean rotatable1 = true;

				// new goal coordinates for the blocks
				for (int temp = 0; temp < 4; temp++) {
					locsaver.put(panelField[rowtemp + 1 - temp][coltemp], new Point(coltemp + 2 - temp, rowtemp));
				}

				// check wether or not the coordinates are valid
				for (Block block : list) {
					Double newcol = locsaver.get(block).getX();
					Double newrow = locsaver.get(block).getY();
					if (panelField[newrow.intValue()][newcol.intValue()] != null
							&& !list.contains(panelField[newrow.intValue()][newcol.intValue()])) {
						rotatable1 = false;
					}

				}

				// enter the new position into the panelfield and update their row and col
				// coordinates
				if (rotatable1) {

					// delete the old block positions from the panelField
					for (Block block : list) {
						int oldrow = block.getRow();
						int oldcol = block.getCol();
						panelField[oldrow][oldcol] = null;
					}

					// and add them again in their new positions
					for (Block block : list) {
						Double newcol = locsaver.get(block).getX();
						Double newrow = locsaver.get(block).getY();
						// insert it into the panelField in the new coordinates and update the
						// coordinates
						panelField[newrow.intValue()][newcol.intValue()] = block;
						block.setRow(newrow.intValue());
						block.setCol(newcol.intValue());
					}
					// update the state after updating the positions
					state = 0;
				}
				break;
			// 180 rotation from starting position
			case 2:
				// boolean for determining wether or not a rotation is possible
				boolean rotatable2 = true;

				// new goal coordinates for the blocks
				for (int temp = 0; temp < 4; temp++) {
					locsaver.put(panelField[rowtemp][coltemp - 1 + temp], new Point(coltemp, rowtemp + 2 - temp));
				}

				// check wether or not the coordinates are valid
				for (Block block : list) {
					Double newcol = locsaver.get(block).getX();
					Double newrow = locsaver.get(block).getY();
					if (panelField[newrow.intValue()][newcol.intValue()] != null
							&& !list.contains(panelField[newrow.intValue()][newcol.intValue()])) {
						rotatable2 = false;
					}

				}

				// enter the new position into the panelfield and update their row and col
				// coordinates
				if (rotatable2) {

					// delete the old block positions from the panelField
					for (Block block : list) {
						int oldrow = block.getRow();
						int oldcol = block.getCol();
						panelField[oldrow][oldcol] = null;
					}

					// and add them again in their new positions
					for (Block block : list) {
						Double newcol = locsaver.get(block).getX();
						Double newrow = locsaver.get(block).getY();
						// insert it into the panelField in the new coordinates and update the
						// coordinates
						panelField[newrow.intValue()][newcol.intValue()] = block;
						block.setRow(newrow.intValue());
						block.setCol(newcol.intValue());
					}
					// update the state after updating the positions
					state = 1;
				}
				break;
			// left rotation from starting position
			case 3:
				// boolean for determining wether or not a rotation is possible
				boolean rotatable3 = true;

				// new goal coordinates for the blocks
				for (int temp = 0; temp < 4; temp++) {
					locsaver.put(panelField[rowtemp - 1 + temp][coltemp], new Point(coltemp - 2 + temp, rowtemp));
				}

				// check wether or not the coordinates are valid
				for (Block block : list) {
					Double newcol = locsaver.get(block).getX();
					Double newrow = locsaver.get(block).getY();
					if (panelField[newrow.intValue()][newcol.intValue()] != null
							&& !list.contains(panelField[newrow.intValue()][newcol.intValue()])) {
						rotatable3 = false;
					}

				}

				// enter the new position into the panelfield and update their row and col
				// coordinates
				if (rotatable3) {

					// delete the old block positions from the panelField
					for (Block block : list) {
						int oldrow = block.getRow();
						int oldcol = block.getCol();
						panelField[oldrow][oldcol] = null;
					}

					// and add them again in their new positions
					for (Block block : list) {
						Double newcol = locsaver.get(block).getX();
						Double newrow = locsaver.get(block).getY();
						// insert it into the panelField in the new coordinates and update the
						// coordinates
						panelField[newrow.intValue()][newcol.intValue()] = block;
						block.setRow(newrow.intValue());
						block.setCol(newcol.intValue());
					}
					// update the state after updating the positions
					state = 2;
				}
				break;
			}
		} else {
			int rowtemp = rotator.getRow();
			int coltemp = rotator.getCol();

			switch (state) {
			case 0:
				// boolean for determining wether or not a rotation is possible
				boolean rotatable = true;

				// new goal coordinates for the blocks
				for (int temp = 0; temp < 4; temp++) {
					locsaver.put(panelField[rowtemp][coltemp - 2 + temp], new Point(coltemp - 1, rowtemp - 2 + temp));
				}

				// check wether or not the coordinates are valid
				for (Block block : list) {
					Double newcol = locsaver.get(block).getX();
					Double newrow = locsaver.get(block).getY();
					if (panelField[newrow.intValue()][newcol.intValue()] != null
							&& !list.contains(panelField[newrow.intValue()][newcol.intValue()])) {
						rotatable = false;
					}

				}

				// enter the new position into the panelfield and update their row and col
				// coordinates
				if (rotatable) {

					// delete the old block positions from the panelField
					for (Block block : list) {
						int oldrow = block.getRow();
						int oldcol = block.getCol();
						panelField[oldrow][oldcol] = null;
					}

					// and add them again in their new positions
					for (Block block : list) {
						Double newcol = locsaver.get(block).getX();
						Double newrow = locsaver.get(block).getY();
						// insert it into the panelField in the new coordinates and update the
						// coordinates
						panelField[newrow.intValue()][newcol.intValue()] = block;
						block.setRow(newrow.intValue());
						block.setCol(newcol.intValue());
					}
					// update the state after updating the positions
					state = 1;
				}
				break;

			case 1:// boolean for determining wether or not a rotation is possible
				boolean rotatable1 = true;

				// new goal coordinates for the blocks
				for (int temp = 0; temp < 4; temp++) {
					locsaver.put(panelField[rowtemp + 1 - temp][coltemp], new Point(coltemp - 1 + temp, rowtemp - 1));
				}

				// check wether or not the coordinates are valid
				for (Block block : list) {
					Double newcol = locsaver.get(block).getX();
					Double newrow = locsaver.get(block).getY();
					if (panelField[newrow.intValue()][newcol.intValue()] != null
							&& !list.contains(panelField[newrow.intValue()][newcol.intValue()])) {
						rotatable1 = false;
					}

				}

				// enter the new position into the panelfield and update their row and col
				// coordinates
				if (rotatable1) {

					// delete the old block positions from the panelField
					for (Block block : list) {
						int oldrow = block.getRow();
						int oldcol = block.getCol();
						panelField[oldrow][oldcol] = null;
					}

					// and add them again in their new positions
					for (Block block : list) {
						Double newcol = locsaver.get(block).getX();
						Double newrow = locsaver.get(block).getY();
						// insert it into the panelField in the new coordinates and update the
						// coordinates
						panelField[newrow.intValue()][newcol.intValue()] = block;
						block.setRow(newrow.intValue());
						block.setCol(newcol.intValue());
					}
					// update the state after updating the positions
					state = 2;
				}
				break;
			case 2:// boolean for determining wether or not a rotation is possible
				boolean rotatable2 = true;

				// new goal coordinates for the blocks
				for (int temp = 0; temp < 4; temp++) {
					locsaver.put(panelField[rowtemp][coltemp - 1 + temp], new Point(coltemp + 1, rowtemp - 1 + temp));
				}

				// check wether or not the coordinates are valid
				for (Block block : list) {
					Double newcol = locsaver.get(block).getX();
					Double newrow = locsaver.get(block).getY();
					if (panelField[newrow.intValue()][newcol.intValue()] != null
							&& !list.contains(panelField[newrow.intValue()][newcol.intValue()])) {
						rotatable2 = false;
					}

				}

				// enter the new position into the panelfield and update their row and col
				// coordinates
				if (rotatable2) {

					// delete the old block positions from the panelField
					for (Block block : list) {
						int oldrow = block.getRow();
						int oldcol = block.getCol();
						panelField[oldrow][oldcol] = null;
					}

					// and add them again in their new positions
					for (Block block : list) {
						Double newcol = locsaver.get(block).getX();
						Double newrow = locsaver.get(block).getY();
						// insert it into the panelField in the new coordinates and update the
						// coordinates
						panelField[newrow.intValue()][newcol.intValue()] = block;
						block.setRow(newrow.intValue());
						block.setCol(newcol.intValue());
					}
					// update the state after updating the positions
					state = 3;
				}
				break;
			case 3:// boolean for determining wether or not a rotation is possible
				boolean rotatable3 = true;

				// new goal coordinates for the blocks
				for (int temp = 0; temp < 4; temp++) {
					locsaver.put(panelField[rowtemp - 1 + temp][coltemp], new Point(coltemp + 1 - temp, rowtemp + 1));
				}

				// check wether or not the coordinates are valid
				for (Block block : list) {
					Double newcol = locsaver.get(block).getX();
					Double newrow = locsaver.get(block).getY();
					if (panelField[newrow.intValue()][newcol.intValue()] != null
							&& !list.contains(panelField[newrow.intValue()][newcol.intValue()])) {
						rotatable3 = false;
					}

				}

				// enter the new position into the panelfield and update their row and col
				// coordinates
				if (rotatable3) {

					// delete the old block positions from the panelField
					for (Block block : list) {
						int oldrow = block.getRow();
						int oldcol = block.getCol();
						panelField[oldrow][oldcol] = null;
					}

					// and add them again in their new positions
					for (Block block : list) {
						Double newcol = locsaver.get(block).getX();
						Double newrow = locsaver.get(block).getY();
						// insert it into the panelField in the new coordinates and update the
						// coordinates
						panelField[newrow.intValue()][newcol.intValue()] = block;
						block.setRow(newrow.intValue());
						block.setCol(newcol.intValue());
					}
					// update the state after updating the positions
					state = 0;
				}
				break;

			}
		}
	}

	public ArrayList<Block> getList() {
		return list;
	}

	public Block getRotator() {
		return rotator;
	}
}
