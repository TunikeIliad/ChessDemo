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
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
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
        ChessboardPoint source = getChessboardPoint();ChessColor color = getChessColor();//System.out.println(color);
        int x0 = source.getX(), x1 = destination.getX();
        if(color == ChessColor.BLACK){
            if(Math.abs(destination.getY()-source.getY())==1){
                if(x0+1 == x1){
                    if (!(chessComponents[x0+1][destination.getY()] instanceof EmptySlotComponent))
                        return true;
                }
                else return false;
            }
            if(source.getY() == destination.getY()){
                if(x0 == 1 && x0+2 == x1){
                    if (!(chessComponents[x0+1][destination.getY()] instanceof EmptySlotComponent) ||
                            !(chessComponents[x0+2][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                }
                else if(x0+1 == x1){
                    if (!(chessComponents[x0+1][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                }
                else return false;
            }
            else return false;
        }
        if(color == ChessColor.WHITE){
            if(Math.abs(destination.getY()-source.getY())==1){
                if(x0-1 == x1){
                    if (!(chessComponents[x0-1][destination.getY()] instanceof EmptySlotComponent))
                        return true;
                }
                else return false;
            }
            if(source.getY() == destination.getY()){
                if(x0 == 6 && x0-2 == x1){
                    if (!(chessComponents[x0-1][destination.getY()] instanceof EmptySlotComponent) ||
                            !(chessComponents[x0-2][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                }
                else if(x0-1 == x1){
                    if (!(chessComponents[x0-1][destination.getY()] instanceof EmptySlotComponent))
                        return false;
                }
                else return false;
            }
            else return false;

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
