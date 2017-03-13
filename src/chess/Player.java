package chess;

import java.io.Serializable;

/**
 * Players of the game.
 * 
 * @author Anthony Vu
 *
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Player color.
     */
    private String color;

    /**
     * Constructor.
     * 
     * @param color
     *            player color
     */
    public Player(String color) {
        this.color = color;
    }

    /**
     * Gets player color.
     * 
     * @return player color
     */
    public String getColor() {
        return this.color;
    }

    /**
     * Set player color.
     * 
     * @param color
     *            new color
     */
    public void setColor(String color) {
        this.color = color;
    }
}
