package chess;

import java.awt.Color;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JButton;

/**
 * Squares on a board.
 * 
 * @author Anthony Vu
 *
 */
public class Square implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * Squares on the board.
     */
    private JButton button;
    /**
     * Square color.
     */
    private Color color;
    /**
     * Image on square.
     */
    private ImageIcon img;
    /**
     * Piece on square.
     */
    Piece piece;

    /**
     * Constructor
     * 
     * @param color
     *            square color.
     */
    public Square(Color color) {
        button = new JButton();
        this.color = color;
        button.setBackground(color);
        img = null;
    }

    /**
     * Checks if a square is holding a piece.
     * 
     * @return true if holding a piece
     */
    public boolean holdingPiece() {
        if (img != null) {
            return true;
        }
        return false;
    }

    /**
     * Return square color.
     * 
     * @return square color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns button.
     * 
     * @return button
     */
    public JButton getButton() {
        return button;
    }

    /**
     * Returns piece image.
     * 
     * @return piece image
     */
    public ImageIcon getImage() {
        return this.img;
    }

    /**
     * Set piece on a square.
     * 
     * @param piece
     *            chess piece
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
        this.img = piece.getImage();
        button.setIcon(img);
    }

    /**
     * Get chess piece on the square.
     * 
     * @return chess piece
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Get chess piece color.
     * 
     * @return chess piece color
     */
    public String getPieceColor() {
        if (holdingPiece() == true) {
            return this.piece.getColor();
        }
        return null;
    }

    /**
     * Change squares image.
     * 
     * @param img
     *            new image
     */
    public void setImage(ImageIcon img) {
        this.img = img;
        button.setIcon(this.img);
    }

    /**
     * Set a new color to the square.
     * 
     * @param color
     *            new color
     */
    public void setColor(Color color) {
        this.color = color;
    }
}
