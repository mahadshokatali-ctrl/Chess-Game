// class Rook extends Piece {

//     public Rook(String color) {
//         super(color);
//     }

//     @Override
//     boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {
//         return (sx == ex || sy == ey);
//     }
// }
class Rook extends Piece {

    public Rook(String color) {
        super(color);
    }

    @Override
    boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {

        if (sx != ex && sy != ey)
            return false;

        // Horizontal move
        if (sx == ex) {

            int step = (ey > sy) ? 1 : -1;

            for (int y = sy + step; y != ey; y += step) {

                if (board[sx][y] != null)
                    return false;
            }
        }

        // Vertical move
        if (sy == ey) {

            int step = (ex > sx) ? 1 : -1;

            for (int x = sx + step; x != ex; x += step) {

                if (board[x][sy] != null)
                    return false;
            }
        }

        return true;
    }
}