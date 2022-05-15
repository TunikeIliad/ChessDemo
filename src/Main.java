import view.ChessGameFrame;
import view.ChessboardStartScreen;

import javax.swing.*;
//test github
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        ChessboardStartScreen newGame=new ChessboardStartScreen();
        newGame.setVisible(true);
        });
    }
}
