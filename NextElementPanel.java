package tetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;

public class NextElementPanel extends JPanel {
	
	private int type;
	private Random r = new Random();
	private JPanel[][] state = new JPanel[4][4];

	public NextElementPanel() {
		type = r.nextInt(7);
		createView();
	}

	public int pass() {
		int temp = type;
		type = r.nextInt(7);
		updatePanel();
		return temp;
	}

	public void createView() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
				state[row][col] = new JPanel();
				state[row][col].setPreferredSize(new Dimension(32, 32));
				state[row][col].setBackground(Color.BLACK);
				state[row][col].setBorder(new LineBorder(Color.BLACK));
				c.gridx = col;
				c.gridy = row;

				this.add(state[row][col], c);
			}
		}
	}

	public void updatePanel() {
		for(JPanel[] line: state) {
			for(JPanel panel: line) {
				panel.setBackground(Color.BLACK);
			}
		}
		switch (type) {
		case 0:
			for(int col = 0; col < 4; col++) {
				state[3][col].setBackground(Color.CYAN); 
			}
			break;
		case 1:
			for(int row = 1; row < 4; row++) {
				state[row][1].setBackground(Color.BLUE);
			}
			state[3][0].setBackground(Color.BLUE);
			break;
		case 2:
			for(int row = 1; row < 4; row++) {
				state[row][0].setBackground(Color.ORANGE);
			}
			state[3][1].setBackground(Color.ORANGE);
			break;
		case 3:
			for(int row = 1; row < 3; row++) {
				for(int col  = 1; col < 3; col++) {
					state[row][col].setBackground(Color.YELLOW);
				}
			}
			break;
		case 4:
			state[3][0].setBackground(Color.GREEN);
			state[3][1].setBackground(Color.GREEN);
			state[2][1].setBackground(Color.GREEN);
			state[2][2].setBackground(Color.GREEN);
			break;
		case 5:
			for(int col = 0; col < 3; col++) {
				state[3][col].setBackground(new Color(128,0,128));
			}
			state[2][1].setBackground(new Color(128,0,128));
			break;
		case 6:
			state[2][0].setBackground(Color.RED);
			state[2][1].setBackground(Color.RED);
			state[3][1].setBackground(Color.RED);
			state[3][2].setBackground(Color.RED);
			break;
		}
	}
}
