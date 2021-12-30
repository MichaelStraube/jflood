package jflood;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Executable main class.
 */
public class JFlood {
	/**
	 * Creates the application window and starts a new game.
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame("JFlood");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// label for showing moves
		JLabel movesLabel = new JLabel();

		// create game board
		Board board = new Board(movesLabel);

		// top panel for buttons
		JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

		// bottom panel for info label and exit button
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
		bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


		// new game button
		JButton buttonNewGame = new JButton("New Game");
		buttonNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.startNewGame();
			}
		});

		// level radio buttons
		JRadioButton buttonEasy = new JRadioButton("Easy");
		buttonEasy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.setLevel(Level.Easy);
			}
		});

		JRadioButton buttonNormal = new JRadioButton("Normal", true);
		buttonNormal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.setLevel(Level.Normal);
			}
		});

		JRadioButton buttonHard = new JRadioButton("Hard");
		buttonHard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				board.setLevel(Level.Hard);
			}
		});

		// exit button
		JButton buttonExit = new JButton("Exit");
		buttonExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});

		// group the level buttons
		ButtonGroup levelButtons = new ButtonGroup();
		levelButtons.add(buttonEasy);
		levelButtons.add(buttonNormal);
		levelButtons.add(buttonHard);

		// add buttons to their panels
		topPanel.add(buttonNewGame);
		topPanel.add(buttonEasy);
		topPanel.add(buttonNormal);
		topPanel.add(buttonHard);

		bottomPanel.add(movesLabel);
		bottomPanel.add(Box.createHorizontalGlue());
		bottomPanel.add(buttonExit);

		// add panels to the main frame
		frame.add(topPanel, BorderLayout.NORTH);
		frame.add(board, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);

		// show window
		frame.pack();
		frame.setVisible(true);

		board.startNewGame();
	}
}
