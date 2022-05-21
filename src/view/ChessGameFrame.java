package view;

import controller.GameController;
import model.ChessColor;
import model.User;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;


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
    public JLabel label=new JLabel();
    public Container container;
    private ImageIcon Theme;
    private static ImageIcon chessboardTheme1 =new ImageIcon("./images/theme1.jpg");
    private static ImageIcon chessboardTheme2 =new ImageIcon("./images/theme2.jpg");
    public AudioClip aau;
    public User user=new User();
    JLabel userLabel = new JLabel();
    JLabel userRankLabel = new JLabel();

    public ChessGameFrame(int width, int height) {
        container=this.getContentPane();
        container.setLayout(null);
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGTH = height;
        this.CHESSBOARD_SIZE = HEIGTH * 4 / 5;

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        label.setVisible(true);
        label.setBounds(0,0,WIDTH,HEIGTH);
        Theme=chessboardTheme1;
        label.setIcon(Theme);
        container.add(label);

        label.add(addChessboard());
        label.add(addLabel());
        label.add(addResetButton());
        label.add(addLoadButton());
        label.add(addSaveButton());
        label.add(addSetBackButton());
        label.add(addRetractButton());
        label.add(addUserLabel());
        label.add(userRankLabel);
    }


    /**
     * 在游戏面板中添加棋盘
     */
    private Chessboard addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
        chessboard.setFrame(this);
        return chessboard;
    }

    /**
     * 在游戏面板中添加标签
     */
    private JLabel addLabel() {
        JLabel statusLabe2 = new JLabel("行棋方是白色方");
        statusLabe2.setLocation(HEIGTH, HEIGTH / 10+20);
        statusLabe2.setSize(200, 60);
        statusLabe2.setFont(new Font("Rockwell", Font.BOLD, 20));
        statusLabe=statusLabe2;
        return statusLabe;
    }


    public void setStatusLabeText(ChessColor cl) {
        if(cl==ChessColor.BLACK){
            statusLabe.setText("行棋方是黑色方");
        }
        else{
            statusLabe.setText("行棋方是白色方");
        }
    }

    /**
     * 重置游戏的按钮
     */

    private JButton addResetButton() {
        JButton button = new JButton("Reset");
        button.setLocation(HEIGTH-20, HEIGTH / 10 + 120);
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                        chessboard.setVisible(false);
                        remove(chessboard);
                        label.add(addChessboard());
                        setStatusLabeText(ChessColor.WHITE);
                }
        );
        return button;
    }

    //读档按钮
    private JButton addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH+90, HEIGTH / 10 + 120);
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path ="./resource/"+JOptionPane.showInputDialog(this,"Input Path here");
            if(!path.contains(".txt")){
                JFrame errorTip = new JFrame("错误提示");
                errorTip.setLocationRelativeTo(null);
                errorTip.setSize(400,150);
                JLabel textLabel = new JLabel("错误代码：104  文件格式错误");
                textLabel.setSize(350, 90);
                textLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
                textLabel.setVisible(true) ;
                errorTip.setDefaultCloseOperation(errorTip.getDefaultCloseOperation()); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
                errorTip.setLayout(null);
                errorTip.add(textLabel);
                errorTip.setVisible(true);
            }
            else{
                chessboard.setVisible(false);
                remove(chessboard);
                chessboard = new Chessboard();
                gameController = new GameController(chessboard);
                chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
                chessboard.setFrame(this);
                label.add(chessboard);
                switch(gameController.loadGameFromFile(path)){
                    case 0:
                        break;
                    case 101:
                        JFrame errorTip = new JFrame("错误提示");
                        errorTip.setLocationRelativeTo(null);
                        errorTip.setSize(400,150);
                        JLabel textLabel = new JLabel("错误代码：101  棋盘错误");
                        textLabel.setSize(350, 90);
                        textLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
                        textLabel.setVisible(true) ;
                        errorTip.setDefaultCloseOperation(errorTip.getDefaultCloseOperation()); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
                        errorTip.setLayout(null);
                        errorTip.add(textLabel);
                        errorTip.setVisible(true);
                        break;
                    case 102:
                        JFrame errorTip1 = new JFrame("错误提示");
                        errorTip1.setLocationRelativeTo(null);
                        errorTip1.setSize(400,150);
                        JLabel textLabel1 = new JLabel("错误代码：102  棋子错误");
                        textLabel1.setSize(350, 90);
                        textLabel1.setFont(new Font("Rockwell", Font.BOLD, 20));
                        textLabel1.setVisible(true) ;
                        errorTip1.setDefaultCloseOperation(errorTip1.getDefaultCloseOperation()); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
                        errorTip1.setLayout(null);
                        errorTip1.add(textLabel1);
                        errorTip1.setVisible(true);
                        break;
                    case 103:
                        JFrame errorTip2 = new JFrame("错误提示");
                        errorTip2.setLocationRelativeTo(null);
                        errorTip2.setSize(400,150);
                        JLabel textLabel2 = new JLabel("错误代码：103  缺少行棋方");
                        textLabel2.setSize(350, 90);
                        textLabel2.setFont(new Font("Rockwell", Font.BOLD, 20));
                        textLabel2.setVisible(true) ;
                        errorTip2.setDefaultCloseOperation(errorTip2.getDefaultCloseOperation()); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
                        errorTip2.setLayout(null);
                        errorTip2.add(textLabel2);
                        errorTip2.setVisible(true);
                        break;
                }
            }
        });
        return button;
    }

    private JButton addSaveButton(){
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH-20, HEIGTH / 10 + 200);
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));

        button.addActionListener(e -> {
            try {
                PrintStream ps=new PrintStream("./resource/save1.txt");
                System.setOut(ps);
                System.out.println(chessboard.saveGame());
                ps.close();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        return button;
    }
    //切换壁纸
    private JButton addSetBackButton() {
        JButton button = new JButton("Theme");
        button.setLocation(HEIGTH+90, HEIGTH / 10 + 200);
        button.setSize(100, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                    Theme = Theme == chessboardTheme1 ? chessboardTheme2 : chessboardTheme1;
                    label.setIcon(Theme);
                }
        );
        return button;
    }

    //悔棋
    private JButton addRetractButton(){
        JButton button = new JButton("Retract");
        button.setLocation(HEIGTH-15, HEIGTH / 10 + 280);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                    if(!(chessboard.retract(1))){
                        JOptionPane.showMessageDialog(null,"You can't retract", "Message", JOptionPane.PLAIN_MESSAGE);
                    }
                }
        );
        return button;
    }

    //用户信息显示
    private JLabel addUserLabel() {
        JLabel userLabel1 = new JLabel("用户名：");
        System.out.println(user.userName);
        userLabel1.setLocation(HEIGTH, HEIGTH / 10+340);
        userLabel1.setSize(100, 100);
        userLabel1.setFont(new Font("Rockwell", Font.BOLD, 20));
        userLabel=userLabel1;
        JLabel userLabel2 = new JLabel("用户名：");
        userLabel2.setLocation(HEIGTH, HEIGTH / 10+370);
        userLabel2.setSize(400, 100);
        userLabel2.setFont(new Font("Rockwell", Font.BOLD, 20));
        userRankLabel=userLabel2;
        return userLabel1;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public void setUserText(User user){
        userLabel.setText("用户名："+user .userName) ;
        userRankLabel.setText("用户排行："+user.rank);
    }
}
