package model;

import controller.MoveController;
import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类是一个抽象类，主要表示8*8棋盘上每个格子的棋子情况，当前有两个子类继承它，分别是EmptySlotComponent(空棋子)和RookChessComponent(车)。
 */
public abstract class ChessComponent extends JComponent {

    /**
     * CHESSGRID_SIZE: 主要用于确定每个棋子在页面中显示的大小。
     * <br>
     * 在这个设计中，每个棋子的图案是用图片画出来的（由于国际象棋那个棋子手动画太难了）
     * <br>
     * 因此每个棋子占用的形状是一个正方形，大小是50*50
     */

//    private static final Dimension CHESSGRID_SIZE = new Dimension(1080 / 4 * 3 / 8, 1080 / 4 * 3 / 8);
    private static final Color[] BACKGROUND_COLORS = {Color.WHITE, Color.LIGHT_GRAY,Color.CYAN};
    /**
     * handle click event
     */
    private ClickController clickController;
    private MoveController moveController;
    /**
     * chessboardPoint: 表示8*8棋盘中，当前棋子在棋格对应的位置，如(0, 0), (1, 0), (0, 7),(7, 7)等等
     * <br>
     * chessColor: 表示这个棋子的颜色，有白色，黑色，无色三种
     * <br>
     * selected: 表示这个棋子是否被选中
     */
    private ChessboardPoint chessboardPoint;
    protected  ChessColor chessColor;
    private boolean AI;
    private boolean selected;
    private boolean attacked;
    protected Chessboard chessboard;//王不吃王
    protected List<ChessComponent> attackWay = new ArrayList<>();//攻击范围
    protected List<ChessComponent> beingAttacked = new ArrayList<>();//会被谁攻击
    protected boolean tryToAttackKing = false;
    protected boolean checkPawnNear = false;//吃过路兵
    protected int cpstep = 0;//过路兵计时
    protected char name;
    public Graphics g;

    protected ChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor,MoveController moveController, ClickController clickController, int size) {
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);
        setLocation(location);
        setSize(size, size);
        this.chessboardPoint = chessboardPoint;
        this.chessColor = chessColor;
        this.selected = false;
        this.attacked = false;
        this.clickController = clickController;
        this.moveController=moveController;
        this.AI = false;
    }

    public ChessboardPoint getChessboardPoint() {
        return chessboardPoint;
    }
    public void setChessboardPoint(ChessboardPoint chessboardPoint) {
        this.chessboardPoint = chessboardPoint;
    }
    public ChessColor getChessColor() {
        return chessColor;
    }
    public boolean isSelected() {
        return selected;
    }
    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    public boolean isAttacked(){return attacked;}
    public void setAttacked(boolean attacked){
        this.attacked = attacked;
    }
    public boolean isAI(){return AI;}
    public void setAI(boolean AI) {
        this.AI = AI;
    }

    public void setChessboard(Chessboard chessboard){this.chessboard = chessboard;}//王不吃王
    public void setCheckPawnNear(boolean b){checkPawnNear = b;}
    public boolean getCheckPawnNear(){return checkPawnNear;}
    public void setCpstep(int cpstep) {
        this.cpstep = cpstep;
    }
    public int getCpstep() {
        return cpstep;
    }

    public void setAttackWay(List<ChessComponent> chessComponentList){
        attackWay.clear();
        attackWay.addAll(chessComponentList);
        //System.out.println(attackWay);
    }
    public List<ChessComponent> getAttackWay(){return attackWay;}

    public void setBeingAttacked(List<ChessComponent> chessComponentList) {
        beingAttacked.clear();
        beingAttacked.addAll(chessComponentList);
    }
    public List<ChessComponent> getBeingAttacked(){return beingAttacked;}

    public boolean checkAttackKing(){
        boolean b = false;
        for(int i = 0; i < attackWay.size(); i++){
            if(attackWay.get(i) instanceof KingChessComponent){
                b = true;
            }
        }
        if(b){
            if(chessColor == ChessColor.BLACK) chessboard.setWhiteKiller(this);
            if(chessColor == ChessColor.WHITE) chessboard.setBlackKiller(this);
            tryToAttackKing = true; return true;
        }
        else{
            tryToAttackKing = false; return false;
        }
    }

    public List<ChessComponent> whoKillMe(ChessComponent[][] chessComponents){
        List<ChessComponent> meKiller = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            for(int j = 0; j < 8; j++){
                for(ChessComponent c : chessComponents[i][j].getAttackWay()){
                    if(c.getChessboardPoint().toString().equals(chessboardPoint.toString())){
                        meKiller.add(chessComponents[i][j]);
                        break;
                        //System.out.printf(String.valueOf(name));System.out.println(c);
                    }
                }
            }
        }
        return meKiller;
    }
    /**
     * @param another 主要用于和另外一个棋子交换位置
     *                <br>
     *                调用时机是在移动棋子的时候，将操控的棋子和对应的空位置棋子(EmptySlotComponent)做交换
     */
    public void swapLocation(ChessComponent another) {
        ChessboardPoint chessboardPoint1 = getChessboardPoint(), chessboardPoint2 = another.getChessboardPoint();
        Point point1 = getLocation(), point2 = another.getLocation();
        setChessboardPoint(chessboardPoint2);
        setLocation(point2);
        another.setChessboardPoint(chessboardPoint1);
        another.setLocation(point1);
    }

    /**
     * @param e 响应鼠标监听事件
     *          <br>
     *          当接收到鼠标动作的时候，这个方法就会自动被调用，调用所有监听者的onClick方法，处理棋子的选中，移动等等行为。
     */
    @Override
    protected void processMouseEvent(MouseEvent e) {
        super.processMouseEvent(e);

        if (e.getID() == MouseEvent.MOUSE_PRESSED) {
            System.out.printf("Click [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
            clickController.onClick(this);
        }

        if(e.getID()==MouseEvent.MOUSE_MOVED ){
            moveController.changeColor(this);
        }
    }

    /**
     * @param chessboard  棋盘
     * @param destination 目标位置，如(0, 0), (0, 7)等等
     * @return this棋子对象的移动规则和当前位置(chessboardPoint)能否到达目标位置
     * <br>
     * 这个方法主要是检查移动的合法性，如果合法就返回true，反之是false
     */
    public abstract boolean canMoveTo(ChessComponent[][] chessboard, ChessboardPoint destination);
    public abstract List<ChessComponent> getCanMovePoints(ChessComponent[][] chessboard);
    /**
     * 这个方法主要用于加载一些特定资源，如棋子图片等等。
     *
     * @throws IOException 如果一些资源找不到(如棋子图片路径错误)，就会抛出异常
     */
    public abstract void loadResource() throws IOException;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        //System.out.printf("repaint chess [%d,%d]\n", chessboardPoint.getX(), chessboardPoint.getY());
        Color squareColor = BACKGROUND_COLORS[(chessboardPoint.getX() + chessboardPoint.getY()) % 2];
        g.setColor(squareColor);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());
        this.g=g;
    }


    public char Name() {
        return name;
    }

    protected void mouseEntered(MouseEvent e) {
    }

    protected void mouseExited(MouseEvent e) {
    }
}
