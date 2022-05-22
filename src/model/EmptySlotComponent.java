package model;

import controller.MoveController;
import view.ChessboardPoint;
import controller.ClickController;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {
    private boolean isEnter=false;
    public EmptySlotComponent(ChessboardPoint chessboardPoint, MoveController moveController, Point location, ClickController listener, int size) {
        super(chessboardPoint, location, ChessColor.NONE,moveController, listener, size);
        name='_';
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
    public boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination) {
        return false;
    }

    @Override
    public List<ChessComponent> getCanMovePoints(ChessComponent[][] chessComponents){
        return new ArrayList<ChessComponent>();
    }

    @Override
    public void loadResource() throws IOException {
        //No resource!
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
