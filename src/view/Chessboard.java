package view;


import controller.GameController;
import model.*;
import controller.ClickController;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    public ChessGameFrame frame;
    private final ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private ChessboardPoint blackKingPoint = new ChessboardPoint(0,CHESSBOARD_SIZE - 4),
            whiteKingPoint = new ChessboardPoint(CHESSBOARD_SIZE - 1,CHESSBOARD_SIZE - 4);
    //存储棋盘
    private List<String> boardList = new ArrayList<>();

    //各棋子
    /*private int rook=0;
    private int knight=0;
    private int bishop=0;
    private int pawn=0;
    private int king=0;
    private int queen=0;
    private ChessComponent Rooks[]=new ChessComponent[4];
    private ChessComponent Knights[]=new ChessComponent[4];
    private ChessComponent Bishops[]=new ChessComponent[4];
    private ChessComponent Pawns[]=new ChessComponent[16];
    private ChessComponent Kings[]=new ChessComponent[2];
    private ChessComponent Queens[]=new ChessComponent[2];*/



    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();
        initBoard();
    }
    public Chessboard() {
        setLayout(null); // Use absolute layout.
        setSize(608 , 608);
        CHESS_SIZE = 608 / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", 608, CHESS_SIZE);
        initiateEmptyChessboard();
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }
    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }
    public void test(){
        System.out.println(chessComponents[0][0]);
        remove(chessComponents[0][0]);
        ChessComponent c;
        add(c = new EmptySlotComponent(chessComponents[0][0].getChessboardPoint(), chessComponents[0][0].getLocation(), clickController, CHESS_SIZE));
        //System.out.println(chessComponents[0][0]);
        //System.out.println(c);
        chessComponents[0][0] = c;c.repaint();
        System.out.println(chessComponents[0][0]);
        putChessOnBoard(new QueenChessComponent(new ChessboardPoint(0, 0), calculatePoint(0, 0), ChessColor.BLACK, clickController, CHESS_SIZE));
        chessComponents[0][0].repaint();
        System.out.println(chessComponents[0][0]);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if (!(chess2 instanceof EmptySlotComponent)) {
            remove(chess2);
            add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
        }
        chess1.swapLocation(chess2);
        int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
        chessComponents[row1][col1] = chess1;
        int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
        chessComponents[row2][col2] = chess2;

        chess1.repaint();
        chess2.repaint();

        //每一次王行棋，王都存一次自己的位置
        if(chess1 instanceof KingChessComponent){
            if(chess1.getChessColor() == ChessColor.BLACK) setBlackKingPoint(chess1.getChessboardPoint());
            else setWhiteKingPoint(chess1.getChessboardPoint());
        }
        //每一次行棋，各棋子存一次棋盘
        for(ChessComponent[] c1 : chessComponents){
            for(ChessComponent c : c1) c.setChessboard(this);
        }
    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
                chessComponents[i][j].repaint();
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
    }

    //初始化棋子位置
    private void initBoard(){
        // FIXME: Initialize chessboard for testing only.
        //放棋子
        //black
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initQueenOnBoard(0,CHESSBOARD_SIZE - 5,ChessColor.BLACK);
        initKingOnBoard(0,CHESSBOARD_SIZE - 4,ChessColor.BLACK);
        initKnightOnBoard(0,CHESSBOARD_SIZE - 2,ChessColor.BLACK);
        initKnightOnBoard(0,CHESSBOARD_SIZE - 7,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE - 3,ChessColor.BLACK);
        initBishopOnBoard(0,CHESSBOARD_SIZE - 6,ChessColor.BLACK);

        initPawnOnBoard(1,0,ChessColor.BLACK);
        initPawnOnBoard(1,1,ChessColor.BLACK);
        initPawnOnBoard(1,2,ChessColor.BLACK);
        initPawnOnBoard(1,3,ChessColor.BLACK);
        initPawnOnBoard(1,4,ChessColor.BLACK);
        initPawnOnBoard(1,5,ChessColor.BLACK);
        initPawnOnBoard(1,6,ChessColor.BLACK);
        initPawnOnBoard(1,7,ChessColor.BLACK);

        //white
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initQueenOnBoard(CHESSBOARD_SIZE - 1,CHESSBOARD_SIZE - 5,ChessColor.WHITE);
        initKingOnBoard(CHESSBOARD_SIZE - 1,CHESSBOARD_SIZE - 4,ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1,CHESSBOARD_SIZE - 2,ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1,CHESSBOARD_SIZE - 7,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1,CHESSBOARD_SIZE - 3,ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1,CHESSBOARD_SIZE - 6,ChessColor.WHITE);

        initPawnOnBoard(CHESSBOARD_SIZE - 2,0,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,1,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,2,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,3,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,4,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,5,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,6,ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2,7,ChessColor.WHITE);
        boardList.add(saveGame());
        System.out.println(saveGame());
        //System.out.println(chessComponents[0][0]);
    }
    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        //Rooks[rook]=chessComponent;
        //rook++;
        putChessOnBoard(chessComponent);
    }
    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        //Queens[queen]=chessComponent;
        //queen++;
        putChessOnBoard(chessComponent);
    }
    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
        //Kings[king]=chessComponent;
        //king++;
        chessComponent.setChessboard(this);
    }
    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        //Knights[knight]=chessComponent;
        //knight++;
        putChessOnBoard(chessComponent);
    }
    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        //Bishops[bishop]=chessComponent;
        //bishop++;
        putChessOnBoard(chessComponent);
    }
    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        chessComponent.setVisible(true);
        //Pawns[pawn]=chessComponent;
        //pawn++;
        putChessOnBoard(chessComponent);
    }
    
    public void setBlackKingPoint(ChessboardPoint chessboardPoint){
        blackKingPoint = chessboardPoint;
    }
    public ChessboardPoint getBlackKingPoint(){
        return blackKingPoint;
    }
    public void setWhiteKingPoint(ChessboardPoint chessboardPoint){
        whiteKingPoint = chessboardPoint;
    }
    public ChessboardPoint getWhiteKingPoint(){
        return whiteKingPoint;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }


    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public int loadGame(List<String> chessData) {
        if(chessData.size()!=9){
            if(chessData.get(chessData.size()-1).length()==1){
                return 101;
            }
            else{
                return 103;
            }
        }
        for(int j=0;j<8;j++){
            for(int i=0;i<8;i++){
                switch(chessData.get(j).charAt(i)){
                    case 'K':
                        initKingOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                        break;
                    case 'k':
                        initKingOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                        break;
                    case'Q':
                        initQueenOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                        break;
                    case'q':
                        initQueenOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                        break;
                    case'R':
                        initRookOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                        break;
                    case'r':
                            initRookOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                    case'B':
                            initBishopOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                    case'b':
                            initBishopOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                    case'N':
                            initKnightOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                    case'n':
                            initKnightOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                    case'P':
                            initPawnOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                    case'p':
                            initPawnOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                    default:
                        return 102;
                    }
            }
            if(chessData.get(8).equals("w")){
                currentColor=ChessColor.WHITE;
            }
            else if(chessData.get(8).equals("b")){
                currentColor=ChessColor.BLACK;
            }
            else{
                return 102;
            }
        }
        return 0;
    }

    public String saveGame()  {
        StringBuilder sb=new StringBuilder();
        for(ChessComponent[] chesses:chessComponents){

            for(ChessComponent chess:chesses){
                sb.append(chess.Name());
            }
            sb.append("\n");
        }
        if(currentColor ==ChessColor.BLACK ){
            sb.append("b");
        }
        else{
            sb.append("w");
        }
        return sb.toString();
    }
    public void saveBoard(String s){boardList.add(s);}

    //悔棋时转换棋盘格式
    public boolean retract(int steps){
        if(boardList.size() >= steps*2+1){
            List<String> c = boardList.subList(boardList.size()-steps*2, boardList.size());
            System.out.println(c);
            boardList.removeAll(c);
            StringBuilder str = new StringBuilder(boardList.get(boardList.size()-1).replaceAll("\n", ""));
            List<String> s = new ArrayList<>();
            for(int i = 0; i < 64; i+=8){
                s.add(str.substring(i,i+8));
            }
            s.add(str.substring(64));System.out.println(s);
            initiateEmptyChessboard();
            //loadGame(s);
            for(int j=0;j<8;j++){
                for(int i=0;i<8;i++){
                    switch(s.get(j).charAt(i)){
                        case 'K':
                            initKingOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                        case 'k':
                            initKingOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                        case'Q':
                            initQueenOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                        case'q':
                            initQueenOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                        case'R':
                            initRookOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                        case'r':
                            initRookOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                        case'B':
                            initBishopOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                        case'b':
                            initBishopOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                        case'N':
                            initKnightOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                        case'n':
                            initKnightOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                        case'P':
                            initPawnOnBoard(j, i, ChessColor.BLACK);chessComponents[j][i].repaint();
                            break;
                        case'p':
                            initPawnOnBoard(j, i, ChessColor.WHITE);chessComponents[j][i].repaint();
                            break;
                    }
                }
            }
            return true;
        }
        else
            return false;
    }

    public void setFrame(ChessGameFrame frame) {
        this.frame = frame;
    }

}
