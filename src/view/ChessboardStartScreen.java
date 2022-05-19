package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class ChessboardStartScreen extends JFrame{
    private static ImageIcon chessboardStartScreen =new ImageIcon("./images/Chessboard.jpg");
    private int WIDTH;
    private int HEIGTH;

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

        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false); //Set the window to visible
                ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
                mainFrame.setVisible(true);
                mainFrame.chessboard.setFrame(mainFrame);
            }
        });
        setBounds(30,20,WIDTH,HEIGTH);
        setVisible(true); //Set the window to visible
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //let the window can be close by click "x"
    }
}

