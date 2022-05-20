package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BishopChessComponent extends ChessComponent{
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;
    private Image bishopImage;

    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE = ImageIO.read(new File("./images/Bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/Bishop-black.png"));
        }
    }

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
        if(color==ChessColor.BLACK)
            name='B';
        else
            name='b';
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if(Math.abs(destination.getX()-source.getX()) == Math.abs(destination.getY()-source.getY())){
            int row = source.getX(), col = source.getY(), x = (destination.getX()-source.getX() < 0)? -1 : 1,
                    y = (destination.getY()-source.getY() < 0)? -1 : 1, counter = Math.abs(destination.getX()-source.getX());
            while(counter-1 > 0){
                row += x;col += y;
                if (!(chessComponents[row][col] instanceof EmptySlotComponent))
                    return false;
                counter--;
            }
        }else{return false;}
        return true;
    }
    @Override
    public List<ChessComponent> getCanMovePoints(ChessComponent[][] chessComponents){
        ArrayList<ChessComponent> way = new ArrayList<>();
        for(int i = -1; i < 2; i++){
            for(int j = -1; j < 2; j++){
                if(i == 0) i++;
                if(j == 0) j++;
                int a = i; int b = j;
                while(getChessboardPoint().offset(i,j) != null){
                    ChessboardPoint p = getChessboardPoint().offset(i, j);
                    ChessComponent c = chessComponents[p.getX()][p.getY()];
                    if(c.getChessColor() == ChessColor.NONE){
                        way.add(c);
                        i += a;j += b;
                    }
                    else if(c.getChessColor() == getChessColor()){
                        i = a; j = b;
                        break;
                    }
                    else{
                        way.add(c);
                        i = a; j = b;
                        break;
                    }
                }
                i = a; j = b;
            }
        }
        return way;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(BishopImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
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
