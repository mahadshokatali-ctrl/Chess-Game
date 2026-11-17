// class Bishop extends Piece {

//     public Bishop(String color) {
//         super(color);
//     }

//     @Override
//     boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {
//         return Math.abs(ex - sx) == Math.abs(ey - sy);
//     }
    
// }
class Bishop extends Piece {

    public Bishop(String color) {
        super(color);
    }

    @Override
    boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {

        if (Math.abs(ex - sx) != Math.abs(ey - sy))
            return false;

        int xStep = (ex > sx) ? 1 : -1;
        int yStep = (ey > sy) ? 1 : -1;

        int x = sx + xStep;
        int y = sy + yStep;

        while (x != ex && y != ey) {

            if (board[x][y] != null)
                return false;

            x += xStep;
            y += yStep;
        }

        return true;
    }
}