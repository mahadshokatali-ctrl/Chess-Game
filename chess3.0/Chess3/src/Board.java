class Board {

    Piece[][] board;

    String currentTurn = "white";

    // constructor
    public Board() {

        board = new Piece[8][8];

        setupBoard();
    }

    void setupBoard() {

        // Black pieces
        board[0][0] = new Rook("black");
        board[0][1] = new Knight("black");
        board[0][2] = new Bishop("black");
        board[0][3] = new Queen("black");
        board[0][4] = new King("black");
        board[0][5] = new Bishop("black");
        board[0][6] = new Knight("black");
        board[0][7] = new Rook("black");

        for (int i = 0; i < 8; i++) {
            board[1][i] = new Pawn("black");
        }

        // White pieces
        board[7][0] = new Rook("white");
        board[7][1] = new Knight("white");
        board[7][2] = new Bishop("white");
        board[7][3] = new Queen("white");
        board[7][4] = new King("white");
        board[7][5] = new Bishop("white");
        board[7][6] = new Knight("white");
        board[7][7] = new Rook("white");

        for (int i = 0; i < 8; i++) {
            board[6][i] = new Pawn("white");
        }
    }

    // CHECK IF PATH IS CLEAR
    boolean isPathClear(int sx, int sy, int ex, int ey) {

        int dx = Integer.compare(ex, sx);
        int dy = Integer.compare(ey, sy);

        int x = sx + dx;
        int y = sy + dy;

        while (x != ex || y != ey) {

            if (board[x][y] != null) {
                return false;
            }

            x += dx;
            y += dy;
        }

        return true;
    }

    // FIND KING
    int[] findKing(String color) {

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                Piece p = board[i][j];

                if (p instanceof King &&
                    p.color.equals(color)) {

                    return new int[]{i, j};
                }
            }
        }

        return null;
    }

    // CHECK IF KING IS IN CHECK
    boolean isKingInCheck(String color) {

        int[] kingPos = findKing(color);

        if (kingPos == null)
            return false;

        int kingX = kingPos[0];
        int kingY = kingPos[1];

        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                Piece p = board[i][j];

                // enemy piece
                if (p != null &&
                    !p.color.equals(color)) {

                    // KING
                    if (p instanceof King) {

                        if (Math.abs(i - kingX) <= 1 &&
                            Math.abs(j - kingY) <= 1) {

                            return true;
                        }
                    }

                    // KNIGHT
                    else if (p instanceof Knight) {

                        int dx = Math.abs(i - kingX);
                        int dy = Math.abs(j - kingY);

                        if ((dx == 2 && dy == 1) ||
                            (dx == 1 && dy == 2)) {

                            return true;
                        }
                    }

                    // OTHER PIECES
                    else {

                        if (p.isValidMove(
                                i, j,
                                kingX, kingY,
                                board)) {

                            // knights can jump
                            if (p instanceof Knight ||
                                isPathClear(
                                    i, j,
                                    kingX, kingY)) {

                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    boolean hasAnyValidMove(String color) {

    for (int sx = 0; sx < 8; sx++) {

        for (int sy = 0; sy < 8; sy++) {

            Piece p = board[sx][sy];

            if (p != null && p.color.equals(color)) {

                for (int ex = 0; ex < 8; ex++) {

                    for (int ey = 0; ey < 8; ey++) {

                        // Same position
                        if (sx == ex && sy == ey)
                            continue;

                        // Same color piece
                        if (board[ex][ey] != null &&
                            board[ex][ey].color.equals(color))
                            continue;

                        // Movement valid
                        if (p.isValidMove(sx, sy, ex, ey, board)) {

                            // Path check
                            if (!(p instanceof Knight)) {

                                if (!isPathClear(sx, sy, ex, ey))
                                    continue;
                            }

                            // Try move
                            Piece temp = board[ex][ey];

                            board[ex][ey] = p;
                            board[sx][sy] = null;

                            boolean check = isKingInCheck(color);

                            // Undo move
                            board[sx][sy] = p;
                            board[ex][ey] = temp;

                            // Found safe move
                            if (!check) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
    }

    return false;
}

    boolean isCheckmate(String color) {

    return isKingInCheck(color) &&
           !hasAnyValidMove(color);
}

    // MOVE PIECES
    public boolean move(int sx, int sy, int ex, int ey) {

        Piece p = board[sx][sy];

        // no piece selected
        if (p == null)
            return false;

        // wrong turn
        if (!p.color.equals(currentTurn))
            return false;

        // prevent same color capture
        if (board[ex][ey] != null &&
            board[ex][ey].color.equals(p.color)) {

            return false;
        }

        // check movement
        if (p.isValidMove(sx, sy, ex, ey, board)) {

            // knights can jump
            if (!(p instanceof Knight)) {

                if (!isPathClear(sx, sy, ex, ey)) {
                    return false;
                }
            }

            // save captured piece
            Piece temp = board[ex][ey];

            // move piece
            board[ex][ey] = p;
            board[sx][sy] = null;

            // check own king safety
            if (isKingInCheck(p.color)) {

                // undo move
                board[sx][sy] = p;
                board[ex][ey] = temp;

                return false;
            }

            // change turn
            if (currentTurn.equals("white")) {
                currentTurn = "black";
            } else {
                currentTurn = "white";
            }

            return true;
        }

        return false;
    }
}