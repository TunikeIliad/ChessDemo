package model;

import view.ChessboardPoint;
import controller.ClickController;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示棋盘上的空位置
 */
public class EmptySlotComponent extends ChessComponent {
    public EmptySlotComponent(ChessboardPoint chessboardPoint, Point location, ClickController listener, int size) {
        super(chessboardPoint, location, ChessColor.NONE, listener, size);
        name='_';
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
    }

}
