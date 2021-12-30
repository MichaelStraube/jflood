package jflood;

import java.awt.*;

/**
 * Rectangle with a color attribute and a draw method.
 */
public class Tile extends Rectangle {
	/**
	 * The color of this tile.
	 */
	private Color color;

	/**
	 * Sets the color of this tile.
	 * @param color the color to be set
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Returns the color of this tile.
	 * @return the color of this tile
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Draws this tile to the specified Graphics object.
	 * @param g the Graphics object to draw to
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(x, y, width, height);
	}
}
