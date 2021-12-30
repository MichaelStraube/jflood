package jflood;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Draws the tiles and handles the game logic.
 */
public class Board extends JPanel {
	/**
	 * All the tiles.
	 */
	private ArrayList<Tile> tiles;

	/**
	 * The tile colors.
	 */
	private ArrayList<Color> colors;

	/**
	 * Number of tiles in each row, col.
	 */
	private final int boardSize = 14;

	/**
	 * Number of maximum moves.
	 */
	private final int maxMoves = 25;

	/**
	 * Difficulty level.
	 */
	private Level level = Level.Normal;

	/**
	 * True if moves equals maxMoves.
	 */
	private boolean gameOver;

	/**
	 * True if game was solved within maximum moves.
	 */
	private boolean win;

	/**
	 * True if gamw was not solved within maximum moves.
	 */
	private boolean loose;

	/**
	 * Number of moves made.
	 */
	private int moves;

	/**
	 * Reference to the moves label of the bottom panel.
	 */
	private JLabel movesLabel;

	/**
	 * Initializes a new board.
	 * @param infoLabel reference to the moves label of the bottom panel
	 */
	public Board(JLabel movesLabel) {
		setPreferredSize(new Dimension(560, 560));
		this.movesLabel = movesLabel;

		// listener for mouse clicks
		addMouseListener(new MouseAdapter() {
			public void mouseClicked(final MouseEvent e) {
				onMouseClicked(e.getPoint());
			}
		});

		// listener for resize events
		addComponentListener(new ComponentAdapter() {
			public void componentResized(final ComponentEvent e) {
				scale();
			}
		});

		// init colors
		colors = new ArrayList<Color>();
		colors.add(new Color(255,   0,   0));
		colors.add(new Color(  0, 255,   0));
		colors.add(new Color(  0,   0, 255));
		colors.add(new Color(255, 255,   0));
		colors.add(new Color(  0, 255, 255));
		colors.add(new Color(255,   0, 255));
		colors.add(new Color(255, 255, 255));

		// init tiles
		tiles = new ArrayList<Tile>();
		for (int i = 0; i < boardSize * boardSize; i++) {
			tiles.add(new Tile());
		}
	}

	/**
	 * Sets the difficulty level.
	 * @param level the difficulty level to be set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * Starts a new game.
	 */
	public void startNewGame() {
		gameOver = false;
		win = false;
		loose = false;
		moves = 0;
		Random random = new Random();
		int numColors = 6;

		switch (level) {
		case Easy:
			numColors = 4;
			break;
		case Normal:
			numColors = 6;
			break;
		case Hard:
			numColors = 7;
			break;
		}

		for (Tile tile : tiles) {
			tile.setColor(colors.get(random.nextInt(numColors)));
		}
		repaint();

		updateMovesLabel();
	}

	/**
	 * Flood fills the board starting with the specified tile and colors.
	 * @param i row of the start tile
	 * @param j column of the start tile
	 * @param oldColor color of the start tile
	 * @param newColor fill color
	 */
	private void floodFill(int i, int j, Color oldColor, Color newColor) {
		if (!tiles.get(j * boardSize + i).getColor().equals(oldColor) || newColor.equals(oldColor)) {
			return;
		}

		tiles.get(j * boardSize + i).setColor(newColor);

		if (j + 1 < boardSize) {
			floodFill(i, j + 1, oldColor, newColor);
		}
		if (j - 1 >= 0) {
			floodFill(i, j - 1, oldColor, newColor);
		}
		if (i - 1 >= 0) {
			floodFill(i - 1, j, oldColor, newColor);
		}
		if (i + 1 < boardSize) {
			floodFill(i + 1, j, oldColor, newColor);
		}
	}

	/**
	 * Checks if all tiles have the same color.
	 * @return true if all tiles have the same color; false otherwise
	 */
	private boolean checkWinning() {
		for (Tile tile : tiles) {
			if (!tile.getColor().equals(tiles.get(0).getColor())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Checks if a tile was clicked and updates the game.
	 * @param p the clicked point
	 */
	private void onMouseClicked(Point p) {
		if (gameOver) {
			return;
		}

		for (Tile tile : tiles) {
			if (!tile.contains(p)) {
				continue;
			}

			moves++;
			updateMovesLabel();

			floodFill(0, 0, tiles.get(0).getColor(), tile.getColor());
			repaint();

			if (checkWinning()) {
				gameOver = true;
				win = true;
			}

			if (!win && moves == maxMoves) {
				gameOver = true;
				loose = true;
			}
		}
	}

	/**
	 * Updates the text of the bottom panel info label.
	 */
	private void updateMovesLabel() {
		movesLabel.setText(moves + " / " + maxMoves);	}

	/**
	 * Scales the tiles.
	 */
	private void scale() {
		int width = getWidth();
		int height = getHeight();
		int tileSize = width <= height ? width / boardSize : height / boardSize;

		// center horizontal
		int xoffset = 0;
		if (tileSize * boardSize < width) {
			xoffset = width / 2 - tileSize * boardSize / 2;
		}

		// set tile bounds
		for (int x = 0; x < boardSize; x++) {
			for (int y = 0; y < boardSize; y++) {
				Tile tile = tiles.get(y * boardSize + x);
				tile.setBounds(x * tileSize + xoffset, y * tileSize, tileSize, tileSize);
			}
		}

		repaint();
	}

	/**
	 * Draws the tiles.
	 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Tile tile : tiles) {
			tile.draw(g);
		}
	}
}
