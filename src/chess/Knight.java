package chess;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Knight.
 * 
 * @author Anthony Vu
 *
 */
public class Knight extends Piece implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Chess image.
     */
    private ImageIcon icon;
    /**
     * Team color.
     */
    private String color;
    /**
     * Piece type.
     */
    private String type;

    /**
     * Constructor.
     * 
     * @param color
     *            piece color
     */
    public Knight(String color) {
        if (color.equals("black")) {
            icon = new ImageIcon("Knight.png");
        } else {
            icon = new ImageIcon("grayKnight.png");
        }
        this.color = color;
        type = "Knight";
    }

    /**
     * return piece image.
     */
    public ImageIcon getImage() {
        return icon;
    }

    /**
     * return piece color.
     */
    public String getColor() {
        return color;
    }

    /**
     * return piece type.
     */
    public String pieceType() {
        return type;
    }
}
