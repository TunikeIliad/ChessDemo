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

public class QueenChessComponent extends ChessComponent{
    private static Image QUEEN_WHITE;
    private static Image QUEEN_BLACK;
    private Image queenImage;
    private boolean isEnter=false;

    public void loadResource() throws IOException {
        if (QUEEN_WHITE == null) {
            QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
        }

        if (QUEEN_BLACK == null) {
            QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
        }
    }

    private void initiateQueenImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                queenImage = QUEEN_WHITE;
            } else if (color == ChessColor.BLACK) {
                queenImage = QUEEN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, MoveController moveController, ClickController listener, int size) {
        super(chessboardPoint, location, color,moveController, listener, size);
        initiateQueenImage(color);
        if(color==ChessColor.BLACK)
            name='Q';
        else
            name='q';
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
        if (source.getX() == destination.getX()) {
            int row = source.getX();
            for (int col = Math.min(source.getY(), destination.getY()) + 1;
                 col < Math.max(source.getY(), destination.getY()); col++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if (source.getY() == destination.getY()) {
            int col = source.getY();
            for (int row = Math.min(source.getX(), destination.getX()) + 1;
                 row < Math.max(source.getX(), destination.getX()); row++) {
                if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                    return false;
                }
            }
        } else if(Math.abs(destination.getX()-source.getX()) == Math.abs(destination.getY()-source.getY())){
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
                int a = i; int b = j;
                while(getChessboardPoint().offset(i,j) != null){
                    view.ChessboardPoint p = getChessboardPoint().offset(i, j);
                    ChessComponent c = chessComponents[p.getX()][p.getY()];
                    if(c.getChessColor() == ChessColor.NONE){
                        way.add(c);
                        i += a;j += b;
                    }
                    else if(c.getChessColor() == getChessColor()){
                        break;
                    }
                    else{
                        way.add(c);
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
//        g.drawImage(QUEENImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
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
