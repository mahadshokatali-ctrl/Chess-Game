// class King extends Piece {

//     public King(String color) {
//         super(color);
//     }

//     @Override
//     boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {

//         int dx = Math.abs(ex - sx);
//         int dy = Math.abs(ey - sy);

//         return dx <= 1 && dy <= 1;
//     }
// }

class King extends Piece {

    public King(String color) {
        super(color);
    }

    @Override
    boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {

        int dx = Math.abs(ex - sx);
        int dy = Math.abs(ey - sy);

        return dx <= 1 && dy <= 1;
    }
}