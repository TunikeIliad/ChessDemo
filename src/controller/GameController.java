package controller;

import view.Chessboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class GameController {
    private Chessboard chessboard;
    //test

    public GameController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }

    public Integer loadGameFromFile(String path) {
        try {
            List<String> chessData = Files.readAllLines(Paths.get(path));
            int a=chessboard.loadGame(chessData);
            return a;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
