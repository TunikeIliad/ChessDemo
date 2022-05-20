package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        if(color==ChessColor.BLACK)
            name='P';
        else
            name='p';
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
    public List<ChessComponent> getCanMovePoints(ChessComponent[][] chessComponents){
        ArrayList<ChessComponent> way = new ArrayList<>();
        if(getChessColor() == ChessColor.BLACK){
            for(int i = -1; i < 2; i++){
                if(getChessboardPoint().offset(1, i) != null){
                    view.ChessboardPoint p = getChessboardPoint().offset(1, i);
                    ChessComponent c = chessComponents[p.getX()][p.getY()];
                    if(i == 0){
                        if(c.getChessColor() == ChessColor.NONE)
                            way.add(c);
                        if(getChessboardPoint().getX() == 1){
                            view.ChessboardPoint q = p.offset(1,0);
                            ChessComponent d = chessComponents[q.getX()][q.getY()];
                            if(d.getChessColor() == ChessColor.NONE)
                                way.add(d);
                        }
                    }
                    else{
                        if(c.getChessColor() == ChessColor.WHITE)
                            way.add(c);
                    }

                }
            }
        }
        if(getChessColor() == ChessColor.WHITE){
            for(int i = -1; i < 2; i++){
                if(getChessboardPoint().offset(-1, i) != null){
                    view.ChessboardPoint p = getChessboardPoint().offset(-1, i);
                    ChessComponent c = chessComponents[p.getX()][p.getY()];
                    if(i == 0){
                        if(c.getChessColor() == ChessColor.NONE)
                            way.add(c);
                        if(getChessboardPoint().getX() == 6){
                            view.ChessboardPoint q = p.offset(-1,0);
                            ChessComponent d = chessComponents[q.getX()][q.getY()];
                            if(d.getChessColor() == ChessColor.NONE)
                                way.add(d);
                        }
                    }
                    else{
                        if(c.getChessColor() == ChessColor.BLACK)
                            way.add(c);
                    }

                }
            }
        }
        return way;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(PawnImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.GREEN);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        if(isAttacked()){
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
