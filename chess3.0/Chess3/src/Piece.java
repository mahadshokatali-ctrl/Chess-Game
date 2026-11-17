abstract class Piece {
    String color;

    public Piece(String color) {
        this.color = color;
    }

    // Every piece MUST define its own movement rule
    abstract boolean isValidMove(int sx, int sy, int ex, int ey, Piece[][] board);
}