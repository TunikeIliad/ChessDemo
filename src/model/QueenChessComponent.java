package model;

import controller.ClickController;
import view.ChessboardPoint;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class QueenChessComponent extends ChessComponent {
        /**
         * 黑后和白后的图片，static使得其可以被所有后对象共享
         * <br>
         * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
         */
        private static Image QUEEN_WHITE;
        private static Image QUEEN_BLACK;

        /**
         * 后棋子对象自身的图片，是上面两种中的一种
         */
        private Image queenImage;

    public QueenChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor chessColor, ClickController clickController, int size) {
        super(chessboardPoint, location, chessColor, clickController, size);
        initiateQueenImage(chessColor);
    }

    /**
         * 读取加载后棋子的图片
         *
         * @throws IOException
         */
        public void loadResource() throws IOException {
            if (QUEEN_WHITE == null) {
                QUEEN_WHITE = ImageIO.read(new File("./images/queen-white.png"));
            }

            if (QUEEN_BLACK == null) {
                QUEEN_BLACK = ImageIO.read(new File("./images/queen-black.png"));
            }
        }


        /**
         * 在构造棋子对象的时候，调用此方法以根据颜色确定queenImage的图片是哪一种
         *
         * @param color 棋子颜色
         */

        private void initiateQueenImage(ChessColor color) {
            try {
                loadResource();
                if (color == ChessColor.WHITE) {
                    queenImage = QUEEN_WHITE;
                } else if (color == ChessColor.BLACK) {
                    queenImage = QUEEN_BLACK;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        /**
         * 后棋子的移动规则（未修改）
         *
         * @param chessComponents 棋盘
         * @param destination     目标位置，如(0, 0), (0, 7)等等
         * @return 后棋子移动的合法性
         */

        @Override
        public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
            ChessboardPoint source = getChessboardPoint();
            if (source.getX() == destination.getX()) {
                int row = source.getX();
                for (int col = Math.min(source.getY(), destination.getY()) + 1;
                     col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else if (source.getY() == destination.getY()) {
                int col = source.getY();
                for (int row = Math.min(source.getX(), destination.getX()) + 1;
                     row < Math.max(source.getX(), destination.getX()); row++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
            } else { // Not on the same row or the same column.
                return false;
            }
            return true;
        }

        /**
         * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
         *
         * @param g 可以类比于画笔
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
            g.drawImage(queenImage, 0, 0, getWidth() , getHeight(), this);
            g.setColor(Color.BLACK);
            if (isSelected()) { // Highlights the model if selected.
                g.setColor(Color.RED);
                g.drawOval(0, 0, getWidth() , getHeight());
            }
        }
    }
