// class Queen extends Piece {

//     public Queen(String color) {
//         super(color);
//     }

//     @Override
//     boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {
//         return (sx == ex || sy == ey ||
//                 Math.abs(ex - sx) == Math.abs(ey - sy));
//     }
// }
class Queen extends Piece {

    public Queen(String color) {
        super(color);
    }

    @Override
    boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {

        // Rook move
        if (sx == ex || sy == ey) {

            Rook rook = new Rook(color);

            return rook.isValidMove(sx, sy, ex, ey, board);
        }

        // Bishop move
        if (Math.abs(ex - sx) == Math.abs(ey - sy)) {

            Bishop bishop = new Bishop(color);

            return bishop.isValidMove(sx, sy, ex, ey, board);
        }

        return false;
    }
}