package chess;

import javax.swing.ImageIcon;

/**
 * Holds all piece types.
 * 
 * @author Anthony Vu
 *
 */
abstract class Piece {
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
     * Checks if first move has passed.
     */
    private boolean doneFirstMove = false;

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

    /**
     * sets first move to true.
     */
    public void firstMoveComplete() {
        doneFirstMove = true;
    }

    /**
     * Checks if first move has been completed.
     * 
     * @return returns true if first move has been completed
     */
    public boolean isFirstMoveCompleted() {
        return doneFirstMove;
    }
}
