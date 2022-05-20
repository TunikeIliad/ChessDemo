package view;

import static view.ChessboardStartScreen.setBgm;

public class Thread2 extends Thread {
    public Thread2() {
    }

    public void run() {
        setBgm();
    }
}
