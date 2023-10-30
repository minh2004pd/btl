public class Knight extends Piece {

    public Knight(int x, int y, boolean is_white, String file_path, ChessBoard board) {
        super(x, y, is_white, file_path, board);
    }

    @Override
    public boolean canMove(int destination_x, int destination_y) {
        int dx = Math.abs(destination_x - x);
        int dy = Math.abs(destination_y - y);
    
        // Check if the movement is in an L-shape (2 squares in one direction and 1 square in the other)
        if (destination_x >= 0 && destination_x < 8 && 
            destination_y >= 0 && destination_y < 8 &&
            ((dx == 2 && dy == 1) || (dx == 1 && dy == 2))) {
            // Check if the destination position is empty or occupied by an opponent's piece
            return !board.isGridFilled(destination_x, destination_y);
        }
    
        return false;
    }
    
}
