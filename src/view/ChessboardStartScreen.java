package view;

import model.User;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.applet.AudioClip;
import java.io.*;
import java.applet.Applet;
import java.net.MalformedURLException;
import java.net.URL;


public class ChessboardStartScreen extends JFrame{
    private static ImageIcon chessboardStartScreen =new ImageIcon("./images/Chessboard.jpg");
    private int WIDTH;
    private int HEIGTH;
    public AudioClip aau;
    public User user;

    public ChessboardStartScreen() {
        setTitle("2022 CS102A Project Demo");
        this.WIDTH = chessboardStartScreen.getIconWidth();
        this.HEIGTH = chessboardStartScreen.getIconHeight();

        setSize(WIDTH, HEIGTH);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(new FlowLayout(FlowLayout.LEADING,0,0));

        JLabel label=new JLabel();
        JButton button=new JButton("Start Game");

        button.setBounds(600,200,200,60);
        label.add(button);
        label.setIcon(chessboardStartScreen);
        add(label);
        setBgm();
        User user1=new User("1","1");
        User user2=new User("2","2");

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false); //Set the window to visible
                JFrame login = new JFrame("用户登录");
                login.setLayout(null) ;
                login.setLocationRelativeTo(null);
                login.setSize(300, 220);
                JLabel jl1 = new JLabel("用户名：");
                final JTextField jtf1 = new JTextField();
                JLabel jl2 = new JLabel("密码:");
                final JPasswordField jpf1 = new JPasswordField();
                jpf1.setEchoChar('*');
                JButton button1=new JButton("login");

                jl1.setBounds(10, 20, 90, 30);
                jtf1.setBounds(60, 20, 210, 30);
                jl2.setBounds(25, 60, 90, 30);
                jpf1.setBounds(60, 60, 210, 30);
                button1.setBounds(80, 100, 70, 50);

                login.add(jl1);
                login.add(jtf1);
                login.add(jl2);
                login.add(jpf1);
                login.add(button1);
                login.setVisible(true);

                button1.addActionListener(r-> {
                    login.setVisible(false);
                    if(jtf1.getText().equals(user1.userName)&&jpf1.getText().equals(user1.password)){
                        ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
                        mainFrame.setVisible(true);
                        mainFrame.chessboard.setFrame(mainFrame);
                        mainFrame.user = user1;
                    }
                    else if(jtf1.getText().equals(user2.userName)&&jpf1.getText().equals(user2.password)){
                        ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
                        mainFrame.setVisible(true);
                        mainFrame.chessboard.setFrame(mainFrame);
                        mainFrame.user = user2;
                    }
                    else{
                        JFrame error = new JFrame("用户名或密码错误");
                        error.setLayout(null) ;
                        error.setLocationRelativeTo(null);
                        error.setVisible(true);
                        error.setSize(300, 220);
                        JButton button2=new JButton("返回主界面");
                        button2.setBounds(80, 100, 70, 50);
                        error.add(button2);
                        button2.addActionListener(m-> {
                            login.setVisible(true);
                            error.setVisible(false);
                        });
                    }
                });
            }
        });
        setBounds(30,20,WIDTH,HEIGTH);
        setVisible(true); //Set the window to visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //let the window can be close by click "x"
    }
    public void setBgm(){
        try{
            URL cb;
            File f=new File("./music/bgm.wav");
            cb=f.toURL();

            aau=Applet.newAudioClip(cb);
            aau.loop();
            System.out.println("bgm is run") ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
