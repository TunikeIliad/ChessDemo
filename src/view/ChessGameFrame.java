package view;

import controller.GameController;
import model.*;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


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
    //主题音乐切换
    public AudioClip aau;
    public URL cb;
    public File BGM1=new File("./music/bgm.wav");
    public File BGM2=new File("./music/bgm2.wav");
    //用户
    public User user=new User();
    JLabel userLabel = new JLabel();
    JLabel userRankLabel = new JLabel();
    //时间显示
    public JLabel showTime = new JLabel();
    int timeCounter = 30;
    public int score=0;

    public ChessGameFrame(int width, int height) throws MalformedURLException {
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
        cb=BGM1.toURL();
        setBgm();


        label.setVisible(true);
        label.setBounds(0,0,WIDTH,HEIGTH);
        Theme=chessboardTheme1;
        label.setIcon(Theme);
        container.add(label);

        label.add(addChessboard());
        label.add(addLabel());//行棋方
        label.add(addResetButton());
        label.add(addLoadButton());
        label.add(addSaveButton());
        label.add(addSetBackButton());
        label.add(addRetractButton());
        label.add(addUserLabel());
        label.add(userRankLabel);
        label.add(addShowTime());
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
        statusLabe2.setLocation(HEIGTH-10, HEIGTH / 10);
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
    //显示时间
    private JLabel addShowTime(){
        showTime.setText(timeCounter+" "+ "秒");
        showTime.setLocation(HEIGTH+80, HEIGTH / 10+80);
        showTime.setSize(60, 30);
        showTime.setFont(new Font("Rockwell", Font.BOLD, 20));
        java.util.Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(timeCounter > 0){
                    timeCounter--;
                } else{
                    chessboard.swapColor();
                    setStatusLabeText(chessboard.getCurrentColor());
                    timeCounter = 30;
                }
                showTime.setText(String.valueOf(timeCounter) +" "+"秒");
            }

        };
        timer.schedule(timerTask, 100L, 1000L);
        return showTime;
    }
    //棋盘走了就要重置时间
    public void resetTime(){
        timeCounter = 30;
    }

    /**
     * 重置游戏的按钮
     */

    private JButton addResetButton() {
        JButton button = new JButton("Reset");
        button.setLocation(HEIGTH-20, HEIGTH / 10 + 180);
        button.setSize(210, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                        chessboard.setVisible(false);
                        remove(chessboard);
                        label.add(addChessboard());
                        setStatusLabeText(ChessColor.WHITE);
                        resetTime();
                }
        );
        return button;
    }

    //读档按钮
    private JButton addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGTH+90, HEIGTH / 10 + 260);
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
                switch(gameController.loadGameFromFile(path)){
                    case 0:
                        chessboard.setVisible(false);
                        remove(chessboard);
                        chessboard = new Chessboard();
                        gameController = new GameController(chessboard);
                        chessboard.setLocation(HEIGTH / 10, HEIGTH / 10);
                        chessboard.setFrame(this);
                        label.add(chessboard);
                        gameController.loadGameFromFile(path);
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
                        chessboard.retract(0);
                        setStatusLabeText(chessboard.getCurrentColor());
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
                        chessboard.retract(0);
                        setStatusLabeText(chessboard.getCurrentColor());
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
                        chessboard.retract(0);
                        setStatusLabeText(chessboard.getCurrentColor());
                        break;
                    case 104 :
                        JFrame errorTip3 = new JFrame("错误提示");
                        errorTip3.setLocationRelativeTo(null);
                        errorTip3.setSize(400,150);
                        JLabel textLabel3 = new JLabel("错误代码：104  文件格式错误");
                        textLabel3.setSize(350, 90);
                        textLabel3.setFont(new Font("Rockwell", Font.BOLD, 20));
                        textLabel3.setVisible(true) ;
                        errorTip3.setDefaultCloseOperation(errorTip3.getDefaultCloseOperation()); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
                        errorTip3.setLayout(null);
                        errorTip3.add(textLabel3);
                        errorTip3.setVisible(true);
                }
                resetTime();
            }
        });
        return button;
    }

    private JButton addSaveButton(){
        JButton button = new JButton("Save");
        button.setLocation(HEIGTH-20, HEIGTH / 10 + 260);
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
        button.setLocation(HEIGTH-20, HEIGTH / 10 + 340);
        button.setSize(210, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                    Theme = Theme == chessboardTheme1 ? chessboardTheme2 : chessboardTheme1;
                    label.setIcon(Theme);
                    aau.stop();
                    setBgm();
                }
        );
        return button;
    }

    //悔棋
    private JButton addRetractButton(){
        JButton button = new JButton("Retract");
        button.setLocation(HEIGTH-20, HEIGTH / 10 + 420);
        button.setSize(210, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        button.addActionListener((e) ->
                {
                    if(!(chessboard.retract(1))){
                        JOptionPane.showMessageDialog(null,"You can't retract", "Message", JOptionPane.PLAIN_MESSAGE);
                    }
                    setStatusLabeText(chessboard.getCurrentColor());
                }
        );
        return button;
    }
    //胜利
    public void showVictory(){
        if(chessboard.getWinner() == ChessColor.BLACK){
            JOptionPane.showMessageDialog(null,"黑方获胜！","Congratulations!", JOptionPane.PLAIN_MESSAGE);
        }

        else {
            JOptionPane.showMessageDialog(null,"白方获胜！","Congratulations!", JOptionPane.PLAIN_MESSAGE);
            score++;
            userRankLabel.setText("用户分数："+score);
        }
        chessboard.setVisible(false);
        remove(chessboard);
        label.add(addChessboard());
        setStatusLabeText(ChessColor.WHITE);
    }
    //兵底线升变
    public void pawnChangeTo(ChessComponent chessComponent){
        Object[] chess = {"后", "车", "马", "象"};
        int op = JOptionPane.showOptionDialog(null, "请选择要变成的棋子:\n","兵底线升变",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, chess, chess[0]);
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();
        switch (op){
            case 0 :
                chessboard.putChessOnBoard(new QueenChessComponent(chessComponent.getChessboardPoint(), chessComponent.getLocation(),
                        chessComponent.getChessColor(), chessboard.getMoveController(), chessboard.getClickController(), chessboard.getCHESS_SIZE()));
                ChessComponent[][] q = chessboard.getChessComponents();q[row][col].repaint();break;

            case 1 :
                chessboard.putChessOnBoard(new RookChessComponent(chessComponent.getChessboardPoint(), chessComponent.getLocation(),
                        chessComponent.getChessColor(), chessboard.getMoveController(), chessboard.getClickController(), chessboard.getCHESS_SIZE()));
                ChessComponent[][] r = chessboard.getChessComponents();r[row][col].repaint();break;

            case 2 :
                chessboard.putChessOnBoard(new KnightChessComponent(chessComponent.getChessboardPoint(), chessComponent.getLocation(),
                        chessboard.getMoveController(), chessComponent.getChessColor(), chessboard.getClickController(), chessboard.getCHESS_SIZE()));
                ChessComponent[][] n = chessboard.getChessComponents();n[row][col].repaint();break;

            case 3 :
                chessboard.putChessOnBoard(new BishopChessComponent(chessComponent.getChessboardPoint(), chessComponent.getLocation(),
                        chessComponent.getChessColor(), chessboard.getMoveController(), chessboard.getClickController(), chessboard.getCHESS_SIZE()));
                ChessComponent[][] b = chessboard.getChessComponents();b[row][col].repaint();break;
        }
    }
    //用户信息显示
    private JLabel addUserLabel() {
        JLabel userLabel1 = new JLabel("用户名：");
        System.out.println(user.userName);
        userLabel1.setLocation(HEIGTH, HEIGTH / 10+470);
        userLabel1.setSize(100, 100);
        userLabel1.setFont(new Font("Rockwell", Font.BOLD, 20));
        userLabel=userLabel1;
        JLabel userLabel2 = new JLabel("用户名：");
        userLabel2.setLocation(HEIGTH, HEIGTH / 10+500);
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
        userRankLabel.setText("用户分数："+score);
    }
    public void setBgm(){
        try{
            cb=cb==BGM1.toURL()?BGM2.toURL():BGM1.toURL();
            aau=Applet.newAudioClip(cb);
            aau.loop();
            System.out.println("bgm is run") ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
