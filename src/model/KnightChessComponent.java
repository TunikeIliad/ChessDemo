package model;

import controller.MoveController;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class KnightChessComponent extends ChessComponent{
    private static Image KNIGHT_WHITE;
    private static Image KNIGHT_BLACK;
    private Image knightImage;
    private boolean isEnter=false;

    public void loadResource() throws IOException {
        if (KNIGHT_WHITE == null) {
            KNIGHT_WHITE = ImageIO.read(new File("./images/knight-white.png"));
        }

        if (KNIGHT_BLACK == null) {
            KNIGHT_BLACK = ImageIO.read(new File("./images/knight-black.png"));
        }
    }

    private void initiateKnightImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                knightImage = KNIGHT_WHITE;
            } else if (color == ChessColor.BLACK) {
                knightImage = KNIGHT_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public KnightChessComponent(ChessboardPoint chessboardPoint, Point location, MoveController moveController, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color,moveController, listener, size);
        initiateKnightImage(color);
        if(color==ChessColor.BLACK)
            name='N';
        else
            name='n';
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                isEnter=true;
                repaint();
            }
            public void mouseExited(MouseEvent e){
                super.mouseExited(e);
                isEnter=false;
                repaint();

            }
        });
    }

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        if((Math.abs(destination.getX()-source.getX()) == 1 && Math.abs(destination.getY()-source.getY()) == 2) ||
                (Math.abs(destination.getX()-source.getX()) == 2 && Math.abs(destination.getY()-source.getY()) == 1)) {
            if ((chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent))
                return true;
        }
        else{return false;}
        return true;
    }

    @Override
    public List<ChessComponent> getCanMovePoints(ChessComponent[][] chessComponents){
        ArrayList<ChessComponent> way = new ArrayList<>();
        for(int i = -1; i < 2; i += 2){
            for(int j = -1; j < 2; j += 2){
                if(getChessboardPoint().offset(i,j*2) != null){
                    view.ChessboardPoint p = getChessboardPoint().offset(i, j*2);
                    ChessComponent c = chessComponents[p.getX()][p.getY()];
                    if(c.getChessColor() != getChessColor()){
                        way.add(c);
                    }
                }
                if(getChessboardPoint().offset(i*2,j) != null){
                    view.ChessboardPoint p = getChessboardPoint().offset(i*2, j);
                    ChessComponent c = chessComponents[p.getX()][p.getY()];
                    if(c.getChessColor() != getChessColor()){
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
//        g.drawImage(KnightImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(knightImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.GREEN);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        if(isAttacked()){
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
        if (isEnter==true){
            g.setColor(new Color(97, 2, 2, 151));
            g.fillRect(0, 0, this.getWidth(), this.getHeight());
        }
    }

}
