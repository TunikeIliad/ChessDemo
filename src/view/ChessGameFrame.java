package view;

import controller.GameController;
import model.ChessColor;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    private final int WIDTH;
    private final int HEIGTH;
    public final int CHESSBOARD_SIZE;
    public Chessboard chessboard;
    private GameController gameController;
    JLabel statusLabe;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        addChessboard();
        addLabel();
        addResetButton();
        addLoadButton();
        addSaveButton();
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        chessboard.setFrame(this);
        add(chessboard);
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("行棋方是");
        statusLabel.setLocation(HEIGTH, HEIGTH / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
        JLabel statusLabe2 = new JLabel("黑色方");
        statusLabe2.setLocation(HEIGTH, HEIGTH / 10+20);
        statusLabe2.setSize(200, 60);
        statusLabe2.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabe=statusLabe2;
        add(statusLabe);
    }

    public void setStatusLabeText(ChessColor cl) {
        if(cl==ChessColor.BLACK){
            statusLabe.setText("黑色方");
        }
        else{
            statusLabe.setText("白色方");
        }
    }

    /**
     * 重置游戏的按钮
     */

    private void addResetButton() {
        JButton button = new JButton("Reset");
        button.setLocation(HEIGTH, HEIGTH / 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                        chessboard.setVisible(false);
                        remove(chessboard);
                        addChessboard();
                        setStatusLabeText(ChessColor.BLACK);
                }
        );
        add(button);
    }

    //读档按钮
    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH, HEIGTH / 10 + 200);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path ="./resource/"+JOptionPane.showInputDialog(this,"Input Path here");
            if(path.contentEquals(".txt")){
                chessboard.setVisible(false);
                remove(chessboard);
                chessboard = new Chessboard();
                gameController = new GameController(chessboard);
                chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
                chessboard.setFrame(this);
                add(chessboard);
                gameController.loadGameFromFile(path);
            }
            else{

            }
        });
    }

    private void addSaveButton(){
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            try {
                PrintStream ps=new PrintStream("./resource/save1.txt");
                System.setOut(ps);
                System.out.print(chessboard.saveGame());
                ps.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
    }
}
