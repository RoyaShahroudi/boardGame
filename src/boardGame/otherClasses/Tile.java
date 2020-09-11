package boardGame.otherClasses;

import boardGame.controllers.DesignWalls;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Tile extends StackPane {

    protected Rectangle tile;
    private double posX;
    private double posY;
    private static boolean player1 = false;
    private static boolean player2 = false;
    private String[][][] board;

    public Tile(int x, int y, int side, int column, int row, int id, String[][][] board) {
        this.board = new String[column][row][3];
        this.board = board;
        tile = new Rectangle(side, side);

        tile.setFill(Color.GRAY);
        tile.setStroke(Color.BLACK);
        getChildren().addAll(tile);

        posX = ( (1079 - (column * side))/2 + x * side );
        posY = ((506 - (row * side))/2 + y * side);

        setOnMouseClicked(event -> {

            switch (id) {
                case 0:
                    break;
                case 1:
                    if (wallNom(column, row)) {
                        buildWall();
                        board[x][y][0] = "wall";
                    }
                    break;
                case 2:
                    if (starNom(column,row) && board[x][y][0].equals("empty")) {
                        buildStar();
                        board[x][y][0] = "star";
                    }
                    break;
                case 3:
                    if (board[x][y][0].equals("empty") && !player1) {
                        designPlayer1();
                        board[x][y][0] = "player1";
                        player1 = true;
                    }
                    else if (board[x][y][0].equals("empty") && !player2) {
                        designPlayer2();
                        board[x][y][0] = "player2";
                        player2 = true;
                    }

            }
        });
    }

    private boolean wallNom(int column, int row) {
        int empty = 0;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row ; j++) {
                if (board[i][j][0].equals("empty")) {
                    empty++;
                }
            }
        }
        if (empty > 3) {
            return true;
        } else {
            return false;
        }
    }

    private boolean starNom(int column, int row) {
        int empty = 0;
        for (int i = 0; i < column; i++) {
            for (int j = 0; j < row ; j++) {
                if (board[i][j][0].equals("empty")) {
                    empty++;
                }
            }
        }
        if (empty > 2) {
            return true;
        } else {
            return false;
        }
    }

    public void buildWall() {
        tile.setFill(Color.BLACK);
    }

    public void buildStar() {
        tile.setFill(Color.YELLOW);
    }

    public void designPlayer1() {
        tile.setFill(Color.BLUE);
    }
    public void designPlayer2() {
        tile.setFill(Color.RED);
    }
}
