package controller;


import model.ChessColor;
import model.ChessComponent;
import sun.applet.AppletAudioClip;
import view.Chessboard;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;


    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                first = chessComponent;
                first.repaint();
                paintMovePoint(first);
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
                removeMovePoint(recordFirst);
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                removeMovePoint(first);
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                chessboard.frame.setStatusLabeText(chessboard.getCurrentColor());
                setBgm();
                first.setSelected(false);
                first = null;
                chessboard.saveBoard(chessboard.saveGame());
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }

    private void paintMovePoint(ChessComponent chessComponent){
        ArrayList<ChessComponent> c = new ArrayList<>(chessComponent.getCanMovePoints(chessboard.getChessComponents()));
        if(c.size() != 0){
            for(int i = 0; i < c.size(); i++){
                c.get(i).setAttacked(true);
                c.get(i).repaint();
            }
        }

    }

    private void removeMovePoint(ChessComponent chessComponent){
        ArrayList<ChessComponent> c = new ArrayList<>(chessComponent.getCanMovePoints(chessboard.getChessComponents()));
        if(c.size() != 0){
            for(int i = 0; i < c.size(); i++){
                c.get(i).setAttacked(false);
                c.get(i).repaint();
            }
        }

    }
    public void setBgm(){
        try{
            URL cb;
            File f=new File("./music/setChess.wav");
            cb=f.toURL();
            AudioClip aau=new AppletAudioClip(cb);
            aau= Applet.newAudioClip(cb);
            aau.play();
            System.out.println("bgm is run") ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
