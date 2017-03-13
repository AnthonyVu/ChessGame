package chess;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Chessboard.
 * 
 * @author Anthony Vu
 *
 */
public class Board implements MouseListener, Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * User interface.
     */
    private JFrame board;
    /**
     * Patterned board.
     */
    private Square[][] squares;
    /**
     * Chess image.
     */
    private ImageIcon tempImg = null;
    /**
     * Old position of chess piece.
     */
    private int xCoord = -1, yCoord = -1;
    /**
     * Player with gray pieces.
     */
    private Player gray;
    /**
     * Player with black pieces.
     */
    private Player black;
    /**
     * Current player's turn.
     */
    private Player currentPlayer;
    /**
     * File to save and load.
     */
    private File file;

    /**
     * Constructor.
     */
    public Board() {
        gui();
        setPieces();
    }

    /**
     * User interface for chess game.
     */
    public void gui() {
        board = new JFrame();
        board.setTitle("Chess Board");
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenuItem save = new JMenuItem("Save");
        JMenuItem load = new JMenuItem("Load");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveGameAction();
            }
        });

        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadGameAction();
            }
        });
        file.add(load);
        file.add(save);
        board.setJMenuBar(menuBar);
        board.setSize(700, 700);
        board.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        board.setLayout(new GridLayout(8, 8));
        squares = new Square[8][8];
        drawBoard();
        board.setVisible(true);
    }

    /**
     * draws checkered board.
     */
    private void drawBoard() {
        squares = new Square[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    squares[i][j] = new Square(Color.LIGHT_GRAY);
                    board.add(squares[i][j].getButton());
                } else {
                    squares[i][j] = new Square(Color.white);
                    board.add(squares[i][j].getButton());
                }
                squares[i][j].getButton().addMouseListener(this);
            }
        }
    }

    /**
     * Sets all pieces on the board.
     */
    private void setPieces() {
        setBlackPieces();
        setGrayPieces();
        currentPlayer = new Player("gray");
        // to make it so that any player can go first, set color to white
        // and uncomment code in mousePressed.
    }

    /**
     * Sets black pieces on the board.
     */
    private void setBlackPieces() {
        black = new Player("black");
        squares[0][0].setPiece(new Rook(black.getColor()));
        squares[0][1].setPiece(new Bishop(black.getColor()));
        squares[0][2].setPiece(new Knight(black.getColor()));
        squares[0][3].setPiece(new King(black.getColor()));
        squares[0][4].setPiece(new Queen(black.getColor()));
        squares[0][5].setPiece(new Knight(black.getColor()));
        squares[0][6].setPiece(new Bishop(black.getColor()));
        squares[0][7].setPiece(new Rook(black.getColor()));
        for (int i = 0; i < 8; i++) {
            squares[1][i].setPiece(new Pawn(black.getColor()));
        }
    }

    /**
     * Sets gray pieces on the board.
     */
    private void setGrayPieces() {
        gray = new Player("gray");
        squares[7][0].setPiece(new Rook(gray.getColor()));
        squares[7][1].setPiece(new Bishop(gray.getColor()));
        squares[7][2].setPiece(new Knight(gray.getColor()));
        squares[7][3].setPiece(new King(gray.getColor()));
        squares[7][4].setPiece(new Queen(gray.getColor()));
        squares[7][5].setPiece(new Knight(gray.getColor()));
        squares[7][6].setPiece(new Bishop(gray.getColor()));
        squares[7][7].setPiece(new Rook(gray.getColor()));
        for (int i = 0; i < 8; i++) {
            squares[6][i].setPiece(new Pawn(gray.getColor()));
        }
    }

    /**
     * Save game.
     */
    public void saveGameAction() {
        try {
            // Files.deleteIfExists(this.file.getPath().to);
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                this.file = fileChooser.getSelectedFile();
            }
            FileOutputStream fs = new FileOutputStream(this.file.getPath());
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(board);
            os.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Load game.
     */
    public void loadGameAction() {
        try {
            JFileChooser fileChooser = new JFileChooser();
            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                this.file = fileChooser.getSelectedFile();
            }
            board.dispose();
            FileInputStream fileStream = new FileInputStream(this.file.getPath());
            ObjectInputStream is = new ObjectInputStream(fileStream);
            JFrame restore = (JFrame) is.readObject();
            restore.setVisible(true);
            is.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * Validate bishop movement.
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void bishopValidation(int i, int j) {
        if (checkBishopMovement(xCoord, yCoord, i, j) == true) {
            squares[i][j].setPiece(squares[xCoord][yCoord].getPiece());
            squares[i][j].setImage(tempImg);
            squares[xCoord][yCoord].setImage(null);
            tempImg = null;
        }
    }

    /**
     * Validate knight movement.
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void knightValidation(int i, int j) {
        if (checkKnightMovement(xCoord, yCoord, i, j) == true) {
            squares[i][j].setPiece(squares[xCoord][yCoord].getPiece());
            squares[i][j].setImage(tempImg);
            squares[xCoord][yCoord].setImage(null);
            tempImg = null;
        }
    }

    /**
     * Validate king movement.
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void kingValidation(int i, int j) {
        if (checkKingMovement(xCoord, yCoord, i, j) == true) {
            squares[i][j].setPiece(squares[xCoord][yCoord].getPiece());
            squares[i][j].setImage(tempImg);
            squares[xCoord][yCoord].setImage(null);
            tempImg = null;
        }
    }

    /**
     * Validate rook movement.
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void rookValidation(int i, int j) {
        if (checkRookMovement(xCoord, yCoord, i, j) == true) {
            squares[i][j].setPiece(squares[xCoord][yCoord].getPiece());
            squares[i][j].setImage(tempImg);
            squares[xCoord][yCoord].setImage(null);
            tempImg = null;
        }
    }

    /**
     * Validate queen movement.
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void queenValidation(int i, int j) {
        if (checKQueenMovement(xCoord, yCoord, i, j) == true) {
            squares[i][j].setPiece(squares[xCoord][yCoord].getPiece());
            squares[i][j].setImage(tempImg);
            squares[xCoord][yCoord].setImage(null);
            tempImg = null;
        }
    }

    /**
     * Validate gray pawn movement.
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void grayPawnValidation(int i, int j) {
        if (checkGrayPawnMovement(xCoord, yCoord, i, j) == true) {
            squares[i][j].setPiece(squares[xCoord][yCoord].getPiece());
            squares[i][j].setImage(tempImg);
            squares[xCoord][yCoord].setImage(null);
            tempImg = null;
        }
    }

    /**
     * Validate black pawn movement.
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void blackPawnValidation(int i, int j) {
        if (checkBlackPawnMovement(xCoord, yCoord, i, j) == true) {
            squares[i][j].setPiece(squares[xCoord][yCoord].getPiece());
            squares[i][j].setImage(tempImg);
            squares[xCoord][yCoord].setImage(null);
            tempImg = null;
        }
    }

    /**
     * Validate all chess piece movements
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void moveValidation(int i, int j) {
        if (squares[xCoord][yCoord].getPiece().pieceType().equals("Knight")) {
            knightValidation(i, j);
        } else if (squares[xCoord][yCoord].getPiece().pieceType().equals("Bishop")) {
            bishopValidation(i, j);
        } else if (squares[xCoord][yCoord].getPiece().pieceType().equals("Rook")) {
            rookValidation(i, j);
        } else if (squares[xCoord][yCoord].getPiece().pieceType().equals("King")) {
            kingValidation(i, j);
        } else if (squares[xCoord][yCoord].getPiece().pieceType().equals("Queen")) {
            queenValidation(i, j);
        } else if (squares[xCoord][yCoord].getPiece().pieceType().equals("Pawn")
                && squares[xCoord][yCoord].getPieceColor().equals("gray")) {
            grayPawnValidation(i, j);
        } else if (squares[xCoord][yCoord].getPiece().pieceType().equals("Pawn")
                && squares[xCoord][yCoord].getPieceColor().equals("black")) {
            blackPawnValidation(i, j);
        } else {
            squares[i][j].setPiece(squares[xCoord][yCoord].getPiece());
            squares[i][j].setImage(tempImg);
            squares[xCoord][yCoord].setImage(null);
            tempImg = null;
        }
    }

    /**
     * Switch players.
     */
    private void switchTurns() {
        //Can also do: 
        //currentPlayer = gray
        //currentPlayer = black
        if (currentPlayer.getColor().equals("black")) {
            currentPlayer.setColor("gray");
        } else {
            currentPlayer.setColor("black");
        }
    }

    /**
     * Repeated loop.
     * 
     * @param i
     *            position x
     * @param j
     *            position y
     */
    private void loop(int i, int j) {
        if (squares[xCoord][yCoord].getPieceColor().equals(currentPlayer.getColor())) {
            if (squares[i][j].getButton() != squares[xCoord][yCoord].getButton()) {
                if (squares[i][j].getPieceColor() == null) {
                    moveValidation(i, j);
                } else if (!squares[i][j].getPieceColor().equals(squares[xCoord][yCoord].getPieceColor())) {
                    moveValidation(i, j);
                }
            }
        }
    }

    /**
     * Finds clicked square and validates moves.
     * 
     * @param e
     *            source
     */
    @Override
    public void mousePressed(MouseEvent e) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (e.getSource() == squares[i][j].getButton()) {
                    if (tempImg == null) {
                        tempImg = squares[i][j].getImage();
                        xCoord = i;
                        yCoord = j;
                        /*
                         * if (currentPlayer.getColor().equals("white") &&
                         * squares[i][j].getPieceColor() != null) {
                         * currentPlayer.setColor(squares[i][j].getPieceColor())
                         * ; }
                         */
                    } else if (squares[i][j].holdingPiece() == true) {
                        if (squares[i][j].getPieceColor().equals(squares[xCoord][yCoord].getPieceColor())) {
                            tempImg = squares[i][j].getImage();
                            xCoord = i;
                            yCoord = j;
                        } else if (!squares[i][j].getPieceColor().equals(squares[xCoord][yCoord].getPieceColor())
                                && squares[i][j].getPieceColor().equals(currentPlayer.getColor())) {
                            tempImg = squares[i][j].getImage();
                            xCoord = i;
                            yCoord = j;
                        } else {
                            loop(i, j);
                        }
                    } else {
                        loop(i, j);
                    }
                }
            }
        }
    }

    /**
     * Validate gray pawn movement.
     * 
     * @param origX
     *            - original x position
     * @param origY
     *            - original y position
     * @param newX
     *            - new x position
     * @param newY
     *            - new y position
     * @return true if valid move.
     */
    private boolean checkGrayPawnMovement(int origX, int origY, int newX, int newY) {
        if ((newX >= 0 && newX < 8) && (newY >= 0 && newY < 8)) {
            if (newY == origY) {
                if (squares[xCoord][yCoord].getPiece().isFirstMoveCompleted() == false) {
                    if (newX == origX - 2 || newX == origX - 1) {
                        switchTurns();
                        squares[xCoord][yCoord].getPiece().firstMoveComplete();
                        return true;
                    }
                } else if (squares[newX][newY].holdingPiece() == false) {
                    if (newX == origX - 1) {
                        switchTurns();
                        squares[xCoord][yCoord].getPiece().firstMoveComplete();
                        return true;
                    }
                }
            } else if (newY == origY + 1) {
                if (newX == origX - 1) {
                    if (squares[newX][newY].holdingPiece() == true) {
                        switchTurns();
                        squares[xCoord][yCoord].getPiece().firstMoveComplete();
                        return true;
                    }
                }
            } else if (newY == origY - 1) {
                if (newX == origX - 1) {
                    if (squares[newX][newY].holdingPiece() == true) {
                        switchTurns();
                        squares[xCoord][yCoord].getPiece().firstMoveComplete();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Validate gray pawn movement.
     * 
     * @param origX
     *            - original x position
     * @param origY
     *            - original y position
     * @param newX
     *            - new x position
     * @param newY
     *            - new y position
     * @return true if valid move.
     */
    private boolean checkBlackPawnMovement(int origX, int origY, int newX, int newY) {
        if ((newX >= 0 && newX < 8) && (newY >= 0 && newY < 8)) {
            if (newY == origY) {
                if (squares[xCoord][yCoord].getPiece().isFirstMoveCompleted() == false) {
                    if (newX == origX + 2 || newX == origX + 1) {
                        switchTurns();
                        squares[xCoord][yCoord].getPiece().firstMoveComplete();
                        return true;
                    }
                } else if (squares[newX][newY].holdingPiece() == false)
                    if (squares[newX][newY].holdingPiece() == false) {
                        if (newX == origX + 1) {
                            switchTurns();
                            squares[xCoord][yCoord].getPiece().firstMoveComplete();
                            return true;
                        }
                    }
            } else if (newY == origY + 1) {
                if (newX == origX + 1) {
                    if (squares[newX][newY].holdingPiece() == true) {
                        switchTurns();
                        squares[xCoord][yCoord].getPiece().firstMoveComplete();
                        return true;
                    }
                }
            } else if (newY == origY - 1) {
                if (newX == origX + 1) {
                    if (squares[newX][newY].holdingPiece() == true) {
                        switchTurns();
                        squares[xCoord][yCoord].getPiece().firstMoveComplete();
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Validate queen movement.
     * 
     * @param origX
     *            - original x position
     * @param origY
     *            - original y position
     * @param newX
     *            - new x position
     * @param newY
     *            - new y position
     * @return true if valid move.
     */
    private boolean checKQueenMovement(int origX, int origY, int newX, int newY) {
        if (checkRookMovement(origX, origY, newX, newY) == true) {
            return true;
        } else if (checkBishopMovement(origX, origY, newX, newY) == true) {
            return true;
        }
        return false;
    }

    /**
     * Validate king movement.
     * 
     * @param origX
     *            - original x position
     * @param origY
     *            - original y position
     * @param newX
     *            - new x position
     * @param newY
     *            - new y position
     * @return true if valid move.
     */
    private boolean checkKingMovement(int origX, int origY, int newX, int newY) {
        if ((Math.abs(newY - origY) == 1 || newY - origY == 0)
                && ((Math.abs(newX - origX) == 1) || newX - origX == 0)) {
            switchTurns();
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validate rook movement.
     * 
     * @param origX
     *            - original x position
     * @param origY
     *            - original y position
     * @param newX
     *            - new x position
     * @param newY
     *            - new y position
     * @return true if valid move.
     */
    private boolean checkRookMovement(int origX, int origY, int newX, int newY) {
        int minX = 0;
        int maxX = 7;
        int minY = 0;
        int maxY = 7;
        for (int i = origX + 1; i < 8; i++) {
            if (squares[i][origY].holdingPiece() == true) {
                maxX = i;
                break;
            }
        }
        for (int i = origX - 1; i > 0; i--) {
            if (squares[i][origY].holdingPiece() == true) {
                minX = i;
                break;
            }
        }
        for (int i = origY + 1; i < 8; i++) {
            if (squares[origX][i].holdingPiece() == true) {
                maxY = i;
                break;
            }
        }
        for (int i = origY - 1; i > 0; i--) {
            if (squares[origX][i].holdingPiece() == true) {
                minY = i;
                break;
            }
        }
        if (origY == newY) {
            if (newX >= minX && newX <= maxX) {
                switchTurns();
                return true;
            }
        } else if (origX == newX) {
            if (newY >= minY && newY <= maxY) {
                switchTurns();
                return true;
            }
        }
        return false;
    }

    /**
     * Validate bishop movement.
     * 
     * @param origX
     *            - original x position
     * @param origY
     *            - original y position
     * @param newX
     *            - new x position
     * @param newY
     *            - new y position
     * @return true if valid move.
     */
    private boolean checkBishopMovement(int origX, int origY, int newX, int newY) {
        int n2 = newY - origY;
        int n = newX - origX;
        if (Math.abs(n2) == Math.abs(n)) {
            if (n > 0 && n2 > 0) {
                for (int i = 1; i < n; i++) {
                    if (squares[origX + i][origY + i].holdingPiece() == true) {
                        return false;
                    }
                }
            }
            if (n < 0 && n2 > 0) {
                for (int i = 1; i < Math.abs(n); i++) {
                    if (squares[origX - i][origY + i].holdingPiece() == true) {
                        return false;
                    }
                }
            }
            if (n > 0 && n2 < 0) {
                for (int i = 1; i < n; i++) {
                    if (squares[origX + i][origY - i].holdingPiece() == true) {
                        return false;
                    }
                }
            }
            if (n < 0 && n2 < 0) {
                for (int i = 1; i < Math.abs(n); i++) {
                    if (squares[origX - i][origY - i].holdingPiece() == true) {
                        return false;
                    }
                }
            }
            switchTurns();
            return true;
        }
        return false;
    }

    /**
     * Validate bishop movement.
     * 
     * @param origX
     *            - original x position
     * @param origY
     *            - original y position
     * @param newX
     *            - new x position
     * @param newY
     *            - new y position
     * @return true if valid move.
     */
    private boolean checkKnightMovement(int origX, int origY, int newX, int newY) {
        if (Math.abs(newY - origY) == 2 && Math.abs(newX - origX) == 1
                || Math.abs(newY - origY) == 1 && Math.abs(newX - origX) == 2) {
            switchTurns();
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

}
