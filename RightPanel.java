package tetris;
import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import java.awt.Color;

public class RightPanel extends JPanel {
	
	private ScorePanel panelScore;
	private NextElementPanel panelNextElement = new NextElementPanel();
	
	public RightPanel() {
		createView();
	}

	private void createView() {
		panelScore = new ScorePanel();
		GridBagConstraints c = new GridBagConstraints();
		this.setLayout(new GridBagLayout());
		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 1;
		c.gridheight = 1;
		c.insets = new Insets(0,0,128,0);
		this.add(panelNextElement,c);
		c.gridy = 2;
		this.add(panelScore,c);
		this.setBackground(Color.GREEN);
	}
	
	public ScorePanel getScorePanel() {
		return panelScore;
	}
	
	public NextElementPanel getNextElementPanel() {
		return panelNextElement;
	}
}
