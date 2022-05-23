package controller;


import model.ChessColor;
import model.ChessComponent;
import model.KingChessComponent;
import model.PawnChessComponent;
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
        if (first == null && !(chessComponent.isAI())) {
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
                ChessComponent chess1 = first, chess2 = chessComponent;
                //先看看有没有过路兵可以吃
                if(checkNearBy(chess1,chess2)){
                    movePawnNearby(chessComponent);
                }
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();
                chessboard.frame.setStatusLabeText(chessboard.getCurrentColor());
                setBgm();
                first.setSelected(false);
                //走完棋要看看有没有新的过路兵
                if(first.getCheckPawnNear()){
                    chessboard.nearbyPawn.add(first);
                }
                checkChangeOther(first);//兵底线升变
                first = null;chessboard.frame.resetTime();
                chessboard.saveBoard(chessboard.saveGame());
                chessboard.showKingAttacked();
                chessboard.frame.resetTime();
                if(chess2 instanceof KingChessComponent){
                    chessboard.setWinner(chess1.getChessColor());chessboard.setCheckmate(true);
                    chessboard.frame.showVictory();
                }
                if(chessboard.getBlackKingAttackedAlert() && chessboard.checkmate(ChessColor.BLACK)){
                    chessboard.setWinner(ChessColor.WHITE);chessboard.setCheckmate(true);
                    chessboard.frame.showVictory();
                }
                if(chessboard.getWhiteKingAttackedAlert() && chessboard.checkmate(ChessColor.WHITE)){
                    chessboard.setWinner(ChessColor.BLACK);chessboard.setCheckmate(true);
                    chessboard.frame.showVictory();
                }

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

    //兵底线升变
    public void checkChangeOther(ChessComponent chessComponent){
        if(chessComponent instanceof PawnChessComponent){
            if(chessComponent.getChessboardPoint().getX() == 0 && chessComponent.getChessColor() == ChessColor.WHITE){
                chessboard.frame.pawnChangeTo(chessComponent);
            }
            if(chessComponent.getChessboardPoint().getX() == 7 && chessComponent.getChessColor() == ChessColor.BLACK){
                chessboard.frame.pawnChangeTo(chessComponent);
            }
        }
    }
    //吃过路兵
    public boolean checkNearBy(ChessComponent chess1, ChessComponent chess2){
        boolean b = false;
        if(chessboard.nearbyPawn.size() != 0){
            ChessComponent p = chessboard.nearbyPawn.get(0);
            if(p.getChessboardPoint().toString().equals(chess2.getChessboardPoint().toString()) &&
                    chess1 instanceof PawnChessComponent && chess2 instanceof PawnChessComponent &&
                    chess1.getChessboardPoint().getX() == chess2.getChessboardPoint().getX()){
                b = true;
                chessboard.nearbyPawn.clear();

            }
            else {
                p.setCheckPawnNear(false);
                chessboard.nearbyPawn.clear();
            }
        }
        return b;
    }
    public void movePawnNearby(ChessComponent chessComponent){
        ChessComponent[][] c = chessboard.getChessComponents();
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if(chessComponent.getChessColor() == ChessColor.BLACK){
            chessboard.swapChessComponents(chessComponent, c[row-1][col]);
        }
        else if(chessComponent.getChessColor() == ChessColor.WHITE){
            chessboard.swapChessComponents(chessComponent, c[row+1][col]);
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
