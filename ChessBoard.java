import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.geom.*;

public class ChessBoard extends JComponent {
    private int x = 0;
    private int y = 0;
    public static int xMove[] = { 2, 1, -1, -2, -2, -1, 1, 2 }; 
    public static int yMove[] = { 1, 2, 2, 1, -1, -2, -2, -1 }; 
    public int turnCounter = 0;
    private static Image NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

    private final int Square_Width = 65;
    public ArrayList<Piece> Black_pieces;
    public ArrayList<Piece> Standing_pieces;
    public ArrayList<Piece> Check_Pieces;

    private Window window;
    

    public ArrayList<DrawingShape> Static_Shapes;
    public ArrayList<DrawingShape> Piece_Graphics;

    public Piece Active_Piece;
    public Piece Stop_Piece;

    private final int rows = 8;
    private final int cols = 8;
    private Integer[][] BoardGrid;
    private String board_file_path = "D:\\OneDrive\\Máy tính\\btl\\images\\board.png";
    private String active_square_file_path = "active_square.png";
    private String stop_square_file_path = "stop.png";

    private Knight knight;
    private Knight knight2;

    public ChessBoard(int x, int y, Window window) {
        this.x = x;
        this.y = y;
        this.window = window;

        BoardGrid = new Integer[rows][cols];
        Static_Shapes = new ArrayList();
        Piece_Graphics = new ArrayList();
        Black_pieces = new ArrayList();
        Standing_pieces = new ArrayList();
        Check_Pieces = new ArrayList<>();

        initGrid();

        this.setBackground(new Color(37,13,84));
        this.setPreferredSize(new Dimension(520, 520));
        this.setMinimumSize(new Dimension(100, 100));
        this.setMaximumSize(new Dimension(1000, 1000));

        knight = new Knight(x,y,false,"knight1.png",this);
        Black_pieces.add(knight);

        drawBoard();
    }
    public boolean isValidMove(int x, int y) {
        return x >= 0 && x < 8 && y >= 0 && y < 8 && !isGridFilled(x, y);
    }

    public int KnightTour2(int x, int y, int movei) {
        int nextx, nexty;
        if (movei == 8*8) {
            drawBoard();
            return 1;
        }
    
        int minDegreeIndex = -1;
        int minDegree = 9;
    
        for (int i = 0; i < 8; i++) {
            nextx = x + xMove[i];
            nexty = y + yMove[i];
            int degree = getDegree(nextx, nexty) - 1;
            if (isValidMove(nextx, nexty) && BoardGrid[nexty][nextx] == 0) {
                Active_Piece = new Knight(nextx, nexty, true, active_square_file_path, this);
                window.setDegreeShow(degree + "");
                if (degree < minDegree) {
                    Stop_Piece = new Knight(nextx, nexty, false, stop_square_file_path, this);
                    minDegree = degree;
                    minDegreeIndex = i;
                }
            }
        }
    
        if (minDegreeIndex != -1) {
            nextx = x + xMove[minDegreeIndex];
            nexty = y + yMove[minDegreeIndex];
            knight.setX(nextx);
            knight.setY(nexty);
            BoardGrid[y][x] = 1;
            knight2 = new Knight(x, y, false, movei + ".png", this);
            Standing_pieces.add(knight2);
            Black_pieces.remove(0);
            Black_pieces.add(knight);
            if (KnightTour2(nextx, nexty, movei + 1) == 1) {
                return 1;
            } else {
                knight.setX(x);
                knight.setY(y);
                BoardGrid[y][x] = 0;
                Standing_pieces.remove(Standing_pieces.size()-1);
            }
        }
        return 0;
    }

    public int KnightTour1(int x, int y, int movei) throws InterruptedException {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            // TODO: handle exception
            return 1;
        }
        int nextx, nexty;
        if (movei == 8*8) {
            return 1;
        }

        for (int i = 0; i < 8; i++) {
            nextx = x + xMove[i];
            nexty = y + yMove[i];
            if (isValidMove(nextx, nexty)) {
                knight.setX(nextx);
                knight.setY(nexty);
                BoardGrid[y][x] = 1;
                knight2 = new Knight(x, y, true, movei + ".png", this);
                Standing_pieces.add(knight2);
                Black_pieces.remove(0);
                Black_pieces.add(knight);
                drawBoard();
                if (KnightTour1(nextx, nexty, movei + 1) == 1) {
                    return 1;
                } else {
                    knight.setX(x);
                    knight.setY(y);
                    BoardGrid[y][x] = 0;
                    Standing_pieces.remove(Standing_pieces.size()-1);
                    drawBoard();
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        // TODO: handle exception
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    public int KnightTour(int x, int y, int movei) throws InterruptedException {
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            // TODO: handle exception
            return 1;
        }
        int nextx, nexty;
        if (movei == 8*8) {
            return 1;
        }
    
        int minDegreeIndex = -1;
        int minDegree = 9;
    
        for (int i = 0; i < 8; i++) {
            nextx = x + xMove[i];
            nexty = y + yMove[i];
            int degree = getDegree(nextx, nexty) - 1;
            if (isValidMove(nextx, nexty) && BoardGrid[nexty][nextx] == 0) {
                Active_Piece = new Knight(nextx, nexty, true, active_square_file_path, this);
                window.setDegreeShow(degree + "");
                drawBoard();
                try {
                    Thread.sleep(900);
                } catch (InterruptedException e) {
                    // TODO: handle exception
                    return 1;
                }
                if (degree < minDegree) {
                    System.out.println(nextx + " " + nexty);
                    Stop_Piece = new Knight(nextx, nexty, false, stop_square_file_path, this);
                    drawBoard();
                    try {
                        Thread.sleep(900);
                    } catch (InterruptedException e) {
                        // TODO: handle exception
                        return 1;
                    }
                    minDegree = degree;
                    minDegreeIndex = i;
                }
            }
        }
    
        if (minDegreeIndex != -1) {
            nextx = x + xMove[minDegreeIndex];
            nexty = y + yMove[minDegreeIndex];
            knight.setX(nextx);
            knight.setY(nexty);
            BoardGrid[y][x] = 1;
            knight2 = new Knight(x, y, false, movei + ".png", this);
            Standing_pieces.add(knight2);
            Black_pieces.remove(0);
            Black_pieces.add(knight);
            drawBoard();
            if (KnightTour(nextx, nexty, movei + 1) == 1) {
                return 1;
            } else {
                knight.setX(x);
                knight.setY(y);
                BoardGrid[y][x] = 0;
                Standing_pieces.remove(Standing_pieces.size()-1);
                drawBoard();
                try {
                    Thread.sleep(800);
                } catch (InterruptedException e) {
                    // TODO: handle exception
                    return 1;
                }
            }
        }
        return 0;
    }
    
    private int getDegree(int x, int y) {
        int degree = 0;
        for (int i = 0; i < 8; i++) {
            int nextx = x + xMove[i];
            int nexty = y + yMove[i];
            if (isValidMove(nextx, nexty) && BoardGrid[nexty][nextx] == 0) {
                degree++;
            }
        }
        return degree;
    }

    public void initGrid() {
        for (int i=0; i<rows; i++) {
            for (int j=0; j<cols; j++) {
                BoardGrid[i][j] = 0;
            }
        }
    }

    public static Image loadImage(String imageFile) {
        try {
            if (imageFile.contains("knight")) {
                Image image = ImageIO.read(new File(imageFile));
                return image.getScaledInstance(600/8, 600/8, Image.SCALE_SMOOTH);
            }
            else return ImageIO.read(new File(imageFile));
        }
        catch (IOException e) {
                return NULL_IMAGE;
        }
    }
    
    public void drawBoard()
    {
        Piece_Graphics.clear();
        Static_Shapes.clear();
        // initGrid();
        
        Image board = loadImage(board_file_path);
        Static_Shapes.add(new DrawingImage(board, new Rectangle2D.Double(0, 0, board.getWidth(null), board.getHeight(null))));
        if (Active_Piece != null)
        {
            Image active_square = loadImage("images" + File.separator + Active_Piece.getFilePath());
            Static_Shapes.add(new DrawingImage(active_square, new Rectangle2D.Double(Square_Width*Active_Piece.getX(),Square_Width*Active_Piece.getY(), active_square.getWidth(null), active_square.getHeight(null))));
        }
        if (Stop_Piece != null)
        {
            Image stop_square = loadImage("images" + File.separator + Stop_Piece.getFilePath());
            Static_Shapes.add(new DrawingImage(stop_square, new Rectangle2D.Double(Square_Width*Stop_Piece.getX(),Square_Width*Stop_Piece.getY(), stop_square.getWidth(null), stop_square.getHeight(null))));
        }
        for (int i = 0; i < Black_pieces.size(); i++)
        {
            int COL = Black_pieces.get(i).getX();
            int ROW = Black_pieces.get(i).getY();
            // for (int i1=0; i1<rows; i1++) {
            //     for(int j=0; j<cols; j++) {
            //         System.out.print(BoardGrid[i1][j]);
            //     }
            //     System.out.println("");
            // }
            Image piece = loadImage("images" + File.separator + "black_pieces" + File.separator + Black_pieces.get(i).getFilePath());  
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width*COL,Square_Width*ROW, piece.getWidth(null), piece.getHeight(null))));
        }
        for (int i = 0; i < Standing_pieces.size(); i++)
        {
            int COL = Standing_pieces.get(i).getX();
            int ROW = Standing_pieces.get(i).getY();
            Image piece = loadImage("images" + File.separator + "standing_pieces" + File.separator + Standing_pieces.get(i).getFilePath());  
            Piece_Graphics.add(new DrawingImage(piece, new Rectangle2D.Double(Square_Width*COL,Square_Width*ROW, piece.getWidth(null), piece.getHeight(null))));
        }
        this.repaint();
    }

    public boolean isGridFilled(int x, int y) {
        return BoardGrid[y][x] == 1;
    }

    @Override
    protected void paintComponent(Graphics g) {

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;
        drawBackground(g2);
        drawShapes(g2);
    }

    private void drawBackground(Graphics2D g2) {
        g2.setColor(getBackground());
        g2.fillRect(0,  0, getWidth(), getHeight());
    }

    private void drawShapes(Graphics2D g2) {
        for (DrawingShape shape : Static_Shapes) {
            shape.draw(g2);
        }	
        for (DrawingShape shape : Piece_Graphics) {
            shape.draw(g2);
        }
    }

    public Piece getPiece(int x, int y) {
        for (Piece p : Black_pieces)
        {
            if (p.getX() == x && p.getY() == y)
            {
                return p;
            }
        }
        for (Piece p : Standing_pieces)
        {
            if (p.getX() == x && p.getY() == y)
            {
                return p;
            }
        }
        return null;
    }

    // public void paint(Graphics g) {
    //     g.fillRect(100, 100, 400, 400);
    //     for (int i = 100; i <= 400; i += 100) {
    //         for (int j = 100; j<=400; j+=100) {
    //             g.clearRect(i, j, 50, 50);
    //         }
    //     }

    //     for (int i=150; i<=450; i+=100) {
    //         for (int j=150; j<=450; j+=100) {
    //             g.clearRect(i, j, 50, 50);
    //         }
    //     }
    // }

    public static void main(String[] args) {
        System.out.println("Hello");
    }

}
