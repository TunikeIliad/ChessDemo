package model;

import view.Chessboard;//王不吃王
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
public class KingChessComponent extends ChessComponent{
    private static Image KING_WHITE;
    private static Image KING_BLACK;
    private Image kingImage;

    public void loadResource() throws IOException {
        if (KING_WHITE == null) {
            KING_WHITE = ImageIO.read(new File("./images/king-white.png"));
        }

        if (KING_BLACK == null) {
            KING_BLACK = ImageIO.read(new File("./images/king-black.png"));
        }
    }

    private void initiateRookImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                kingImage = KING_WHITE;
            } else if (color == ChessColor.BLACK) {
                kingImage = KING_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KingChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateRookImage(color);
    }


    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int lengthX = destination.getX()-source.getX();
        int lengthY = destination.getY()-source.getY();
        if((lengthX-lengthX%2)/2 != 0 || (lengthY-lengthY%2)/2 != 0)
                return false;
        else{
            if(!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent))
                return false;
            /*else{
                int a = (chessColor == ChessColor.WHITE)? chessboard.getBlackKingPoint().getX() : chessboard.getWhiteKingPoint().getX();
                int b = (chessColor == ChessColor.WHITE)? chessboard.getBlackKingPoint().getY() : chessboard.getWhiteKingPoint().getY();
                int c = (a-destination.getX())-(a-destination.getX())%2;System.out.println(a);System.out.println(b);
                int d = (b-destination.getY())-(b-destination.getY())%2;
                if(c/2 == 0 && d/2 == 0)return false;
            }*/
        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(kingImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
