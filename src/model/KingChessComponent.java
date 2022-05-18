package model;


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
        else{//王不能吃王
            int anotherKingX = (getChessColor() == ChessColor.BLACK)?
                    chessboard.getWhiteKingPoint().getX() : chessboard.getBlackKingPoint().getX();
            int anotherKingY = (getChessColor() == ChessColor.BLACK)?
                    chessboard.getWhiteKingPoint().getY() : chessboard.getBlackKingPoint().getY();
            int X = anotherKingX-destination.getX(), Y = anotherKingY-destination.getY();
            System.out.println(anotherKingX);System.out.println(anotherKingY);
            if((X-X%2)/2 == 0 && (Y-Y%2)/2 == 0)
                return false;
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
