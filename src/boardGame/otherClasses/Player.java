package boardGame.otherClasses;

import boardGame.controllers.DesignWalls;
import boardGame.controllers.Game;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Player extends StackPane {

    private int oldColumn, oldRow;
    private int mouseX, mouseY;
    private String name;
    private int point = 0;
    private String[][][] board;

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point += point;
    }

    public String getName() {
        return name;
    }

    public int getOldColumn() {
        return oldColumn;
    }

    public int getOldRow() {
        return oldRow;
    }

    private boolean check = false;

    public Player(String name, int side, int x, int y, int column, int row, String[][][] board) {

        this.board = new String[column][row][3];
        this.board = board;
        this.name = name;
        // move "player" to correct direction
        if (!check) {
            move(column, row, x, y, side);
            check = true;
        }

        Ellipse ellipse = new Ellipse(side * 0.3125, side * 0.26);
        if (name.equals("player1")) {
            ellipse.setFill(Color.BLUE);
        } else {
            ellipse.setFill(Color.RED);
        }

        ellipse.setStroke(Color.BLACK);
        ellipse.setStrokeWidth(side * 0.03);

        ellipse.setTranslateX((side - side * 0.3125 * 2) / 2);
        ellipse.setTranslateY((side - side * 0.26 * 2) / 2 );

        getChildren().add(ellipse);

        setOnMousePressed(e -> {
            mouseX = (int) e.getSceneX();
            mouseY = (int) e.getSceneY();
        });

        setOnMouseDragged(e -> {
            if (name.equals("player1") && Game.player1Move) {
                relocate(e.getSceneX() - side/2  , e.getSceneY() - side/2);
            } else if (name.equals("player2") && !Game.player1Move){
                relocate(e.getSceneX() - side/2  , e.getSceneY() - side/2);
            }
        });
    }

    public int makeX (int column, int x, int side) {
        return (1079 - (column * side))/2 + x * side;
    }
    public int makeY (int row, int y, int side) {
        return (506 - (row * side))/2 + y * side;
    }

    public void move (int column, int row, int x, int y, int side) {
        board[oldColumn][oldRow][0] = "empty";
        oldColumn = x;
        oldRow = y;
        relocate(makeX(column, x, side), makeY(row, y, side));
        board[x][y][0] = name;
    }

    public void abortMove(int column, int row, int side) {
        relocate(makeX(column,oldColumn , side), makeY(row, oldRow, side));
    }

}
