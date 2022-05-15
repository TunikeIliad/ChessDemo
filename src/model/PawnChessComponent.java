package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PawnChessComponent extends ChessComponent{
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;
    private Image pawnImage;

    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/Pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/Pawn-black.png"));
        }
    }

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();ChessColor color = getChessColor();
        int x0 = source.getX(), x1 = destination.getX();
        if((getChessColor() == ChessColor.BLACK && source.getX() == 1) || (getChessColor() == ChessColor.WHITE && source.getX() == 6)){
            if(source.getY() == destination.getY() && Math.abs(destination.getX()-source.getX())==2){
                if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent))
                    return false;
            }
        }
        if(source.getY() == destination.getY()){
            if((color == ChessColor.BLACK && x0+1 == x1) || (color == ChessColor.WHITE && x0-1 == x1)){
                if (!(chessComponents[x1][destination.getY()] instanceof EmptySlotComponent))
                    return false;
            }
            if((color == ChessColor.BLACK && x0 == 1 && x0+2 == x1) || (color == ChessColor.WHITE && x0 == 6 && x0-2 == x1)){

            }
        }
        if(chessColor == ChessColor.BLACK){
            if(source.getY() == destination.getY()){
                if(x0 == 1 && x0+2 == x1){
                    while(x0 < x1){
                        if (!(chessComponents[x0+1][destination.getY()] instanceof EmptySlotComponent)){
                            return false;
                        }
                        else x0++;
                    }
                }
                else if(x0+1 == x1){
                    if (!(chessComponents[x0+1][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                }
            }

        }
        return true;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(PawnImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
