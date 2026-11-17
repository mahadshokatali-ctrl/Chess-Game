class Knight extends Piece {

    public Knight(String color) {
        super(color);
    }

    @Override
    boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board) {

        int dx = Math.abs(ex - sx);
        int dy = Math.abs(ey - sy);

        return (dx == 2 && dy == 1) ||
               (dx == 1 && dy == 2);
    }
}