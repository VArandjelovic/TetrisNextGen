package tetris;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class ScorePanel extends JPanel {
	private JLabel labelScore;
	private Integer score = 0;

	public ScorePanel() {
		createView();
	}

	public void updateScore(int rowsdeleted) {
		int t = rowsdeleted;
		switch (t) {
		case 1:
			score += 120;
			labelScore.setText(score.toString());
			break;
		case 2:
			score += 300;
			labelScore.setText(score.toString());
			break;
		case 3:
			score += 900;
			labelScore.setText(score.toString());
			break;
		case 4:
			score += 3600;
			labelScore.setText(score.toString());
			break;
		}

	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(4 * 32, 32);
	}

	public void createView() {
		
		this.setLayout(new GridBagLayout());
		LineBorder border = new LineBorder(Color.BLACK, 5);
		this.setBorder(border);
		labelScore = new JLabel(score.toString());
		labelScore.setPreferredSize(new Dimension(2*32,16));
		
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(0, 40, score, score);
		
		
		this.add(labelScore,c);
	}
}
