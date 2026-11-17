 
// class Pawn extends Piece {

//     public Pawn(String color) {
//         super(color);
//     }

//     @Override
//     boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {

//         if (color.equals("white")) {

//             // Forward move
//             if (sy == ey && board[ex][ey] == null) {

//                 // One step
//                 if (ex == sx - 1)
//                     return true;

//                 // First move two steps
//                 if (sx == 6 && ex == 4)
//                     return true;
//             }

//             // Diagonal capture
//             if (Math.abs(ey - sy) == 1 &&
//                 ex == sx - 1 &&
//                 board[ex][ey] != null &&
//                 !board[ex][ey].color.equals(color)) {

//                 return true;
//             }

//         } else {

//             // Forward move
//             if (sy == ey && board[ex][ey] == null) {

//                 // One step
//                 if (ex == sx + 1)
//                     return true;

//                 // First move two steps
//                 if (sx == 1 && ex == 3)
//                     return true;
//             }

//             // Diagonal capture
//             if (Math.abs(ey - sy) == 1 &&
//                 ex == sx + 1 &&
//                 board[ex][ey] != null &&
//                 !board[ex][ey].color.equals(color)) {

//                 return true;
//             }
//         }

//         return false;
//     }
// }




class Pawn extends Piece {

    public Pawn(String color) {
        super(color);
    }

    @Override
    boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {

        int direction;

        if (color.equals("white")) {
            direction = -1;
        } else {
            direction = 1;
        }

        // Move forward
        if (ex == sx + direction &&
            sy == ey &&
            board[ex][ey] == null) {

            return true;
        }

        // First move 2 squares
        if ((color.equals("white") && sx == 6) ||
            (color.equals("black") && sx == 1)) {

            if (ex == sx + 2 * direction &&
                sy == ey &&
                board[ex][ey] == null) {

                return true;
            }
        }

        // Capture diagonally
        if (ex == sx + direction &&
            Math.abs(ey - sy) == 1 &&
            board[ex][ey] != null &&
            !board[ex][ey].color.equals(color)) {

            return true;
        }

        return false;
    }
}






