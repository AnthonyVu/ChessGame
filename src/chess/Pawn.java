package chess;

import java.io.Serializable;

import javax.swing.ImageIcon;

/**
 * Pawn.
 * 
 * @author Anthony Vu
 *
 */
public class Pawn extends Piece implements Serializable {
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
    public Pawn(String color) {
        if (color.equals("black")) {
            icon = new ImageIcon("Pawn.png");
        } else {
            icon = new ImageIcon("grayPawn.png");
        }
        this.color = color;
        type = "Pawn";
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
