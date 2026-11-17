import javax.swing.*;
import java.awt.*;

public class ChessGUI {

    JFrame frame;

    JLabel turnLabel;

    JButton[][] buttons = new JButton[8][8];

    Board board = new Board();

    int selectedX = -1;
    int selectedY = -1;

    public ChessGUI() {

        frame = new JFrame("Chess Game");

        frame.setLayout(new BorderLayout());

        // TURN LABEL
        turnLabel = new JLabel(
            "WHITE TURN",
            SwingConstants.CENTER
        );

        turnLabel.setFont(
            new Font("Arial", Font.BOLD, 30)
        );

        frame.add(turnLabel, BorderLayout.NORTH);

        // BOARD PANEL
        JPanel boardPanel = new JPanel();

        boardPanel.setLayout(new GridLayout(8, 8));

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                JButton btn = new JButton();

                btn.setFont(
                    new Font("Dialog", Font.PLAIN, 40)
                );

                // Board colors
                if ((i + j) % 2 == 0) {
                    btn.setBackground(Color.WHITE);
                } else {
                    btn.setBackground(Color.GRAY);
                }

                btn.setForeground(Color.BLACK);

                buttons[i][j] = btn;

                int x = i;
                int y = j;

                btn.addActionListener(
                    e -> handleClick(x, y)
                );

                boardPanel.add(btn);
            }
        }

        frame.add(boardPanel, BorderLayout.CENTER);

        updateBoard();

        frame.setSize(700, 750);

        frame.setDefaultCloseOperation(
            JFrame.EXIT_ON_CLOSE
        );

        frame.setVisible(true);
    }

    // HANDLE CLICK
    void handleClick(int x, int y) {

        Piece clickedPiece = board.board[x][y];

        // FIRST CLICK
        if (selectedX == -1) {

            // Allow only current turn piece
            if (clickedPiece != null &&
                clickedPiece.color.equals(board.currentTurn)) {

                selectedX = x;
                selectedY = y;

                resetColors();

                highlightMoves(x, y);
            }

        } else {

            // Reselect another same-turn piece
            if (clickedPiece != null &&
                clickedPiece.color.equals(board.currentTurn)) {

                selectedX = x;
                selectedY = y;

                resetColors();

                highlightMoves(x, y);

                return;
            }

            boolean moved =
                board.move(selectedX, selectedY, x, y);

            if (moved) {

                updateBoard();

                // UPDATE TURN LABEL
                turnLabel.setText(
                    board.currentTurn.toUpperCase()
                    + " TURN"
                );

                // CHECK message
                if (board.isKingInCheck(
                        board.currentTurn)) {

                    JOptionPane.showMessageDialog(
                        frame,
                        "CHECK on "
                        + board.currentTurn.toUpperCase()
                        + " king!",
                        "Check",
                        JOptionPane.WARNING_MESSAGE
                    );
                }

                // CHECKMATE
                if (board.isCheckmate(
                        board.currentTurn)) {

                    String winner;

                    if (board.currentTurn.equals(
                            "white")) {

                        winner = "Black";

                    } else {

                        winner = "White";
                    }

                    JOptionPane.showMessageDialog(
                        frame,
                        "CHECKMATE!\n"
                        + winner
                        + " wins!",
                        "Game Over",
                        JOptionPane.INFORMATION_MESSAGE
                    );

                    frame.dispose();
                }
            }

            selectedX = -1;
            selectedY = -1;

            resetColors();
        }
    }

    // HIGHLIGHT POSSIBLE MOVES
    void highlightMoves(int sx, int sy) {

        Piece p = board.board[sx][sy];

        if (p == null)
            return;

        // Only current turn
        if (!p.color.equals(board.currentTurn))
            return;

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                // Skip same square
                if (sx == i && sy == j)
                    continue;

                // Check move pattern
                if (p.isValidMove(
                        sx, sy, i, j, board.board)) {

                    // Prevent same color capture
                    if (board.board[i][j] != null &&
                        board.board[i][j]
                        .color.equals(p.color)) {

                        continue;
                    }

                    // Knight can jump
                    if (!(p instanceof Knight)) {

                        if (!board.isPathClear(
                                sx, sy, i, j)) {

                            continue;
                        }
                    }

                    buttons[i][j]
                        .setBackground(Color.BLUE);
                }
            }
        }
    }

    // RESET BOARD COLORS
    void resetColors() {

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                if ((i + j) % 2 == 0) {

                    buttons[i][j]
                        .setBackground(Color.WHITE);

                } else {

                    buttons[i][j]
                        .setBackground(Color.GRAY);
                }
            }
        }
    }

    // UPDATE GUI
    void updateBoard() {

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                Piece p = board.board[i][j];

                if (p == null) {

                    buttons[i][j].setText("");

                } else {

                    buttons[i][j]
                        .setText(getPieceSymbol(p));
                }
            }
        }
    }

    // CHESS SYMBOLS
    String getPieceSymbol(Piece p) {

        if (p instanceof Pawn)
            return p.color.equals("white")
                ? "♙" : "♟";

        if (p instanceof Rook)
            return p.color.equals("white")
                ? "♖" : "♜";

        if (p instanceof Knight)
            return p.color.equals("white")
                ? "♘" : "♞";

        if (p instanceof Bishop)
            return p.color.equals("white")
                ? "♗" : "♝";

        if (p instanceof Queen)
            return p.color.equals("white")
                ? "♕" : "♛";

        if (p instanceof King)
            return p.color.equals("white")
                ? "♔" : "♚";

        return "";
    }
}