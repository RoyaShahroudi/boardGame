package boardGame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import boardGame.otherClasses.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Game implements Initializable {


    public static Group tileGroup = new Group();
    public static Group playerGroup = new Group();
    public static Group starGroup = new Group();
    private static int row;
    private static int column;
    private Tile tile;
    private boolean start = false;
    private int x0 = 0;
    private int y0 = 0;
    private static int pointP1 = 0;
    private static int pointP2 = 0;
    private String otherPlayer;
    public static boolean player1Move = true;
    private int side = 60;
    private String[][][] board;

    private ArrayList<Star> starC = new ArrayList<>();


    @FXML
    public AnchorPane anchorDesignWalls;
    @FXML
    private Label bluePoint, redPoint, handout;
    @FXML
    private Button resultBtn;



    public void setBoard(int boardRow, int boardColumn, String[][][] board) {
        row = boardRow;
        column = boardColumn;
        this.board = new String[column][row][3];
        this.board = board;

        board();

    }

    public void board() {
        handout.setTextFill(Color.BLUE);
        if (row > 6 || column > 12){
            side = 60;
        } else {
            side = 80;
        }
        for (int x = 0; x < column ; x++) {
            for (int y = 0; y < row ; y++) {
                tile = new Tile(x, y, side, column, row, 3, board);

                tile.setTranslateX((1079 - (column * side))/2 + x * side );
                tile.setTranslateY((506 - (row * side))/2 + y * side);

                if (hasWall(x, y)) {
                    tile.buildWall();

                } else if (hasStar(x, y)) {
                    Star star = new Star(row, column, side, x, y);
                    starC.add(star);
                    starGroup.getChildren().add(star);

                } else if (board[x][y][0].equals("player1")) {
                    playerGroup.getChildren().add(makePlayer("player1", side, x, y));

                } else if (board[x][y][0].equals("player2")) {
                    playerGroup.getChildren().add(makePlayer("player2", side, x, y));
                }
                tileGroup.getChildren().add(tile);
            }
        }
        anchorDesignWalls.getChildren().addAll(tileGroup);
        anchorDesignWalls.getChildren().addAll(starGroup);
        anchorDesignWalls.getChildren().addAll(playerGroup);
    }

    public Player makePlayer (String name,int side, int x, int y) {

        Player player = new Player(name, side, x, y, column, row, board);

        player.setOnMouseReleased(event -> {
            if ((player.getName().equals("player1") && player1Move) || (name.equals("player2") && !Game.player1Move)){

                int newX = (int) event.getSceneX();
                int newY = (int) event.getSceneY();

                boolean checkX = false;
                boolean checkY = false;

                for (int i = 0; i < column ; i++) {
                    for (int j = 0; j < row ; j++) {
                        int boardX = Integer.parseInt(board[i][j][1]);
                        if ((newX >= boardX) && (newX < boardX + side)) {
                            newX = i;
                            checkX = true;
                        }
                        int boardY = Integer.parseInt(board[i][j][2]);
                        if ((newY >= boardY) && (newY < boardY + side)) {
                            newY = j;
                            checkY = true;
                        }
                    }
                }
                if (checkX && checkY) {
                    MoveType moveType = tryMove(player, newX, newY, side);
                    switch (moveType) {
                        case NONE:
                            player.abortMove(column, row, side);
                            break;
                        case NOPOINT:
                            player.move(column, row, newX, newY, side);
                            handout.setText(otherPlayer);
                            changeTextFill(otherPlayer);
                            player1Move = !player1Move;
                            try {
                                endGame();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case POINT:
                            player.move(column, row, newX, newY, side);
                            handout.setText(otherPlayer);
                            player1Move = !player1Move;
                            if (player.getName().equals("player1")) {
                                bluePoint.setText(player.getPoint() + "");
                                pointP1 = player.getPoint();
                            } else {
                                redPoint.setText(player.getPoint() + "");
                                pointP2 = player.getPoint();
                            }
                            changeTextFill(otherPlayer);
                            try {
                                endGame();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                } else {
                    player.abortMove(column, row, side);
                }
            }
        });
        return player;
    }

    private void endGame () throws IOException {
        boolean end = true;
        for (int i = 0 ; i < column; i++) {
            for (int j = 0 ; j < row; j++) {
                if (board[i][j][0].equals("star")) {
                    end = false;
                }
            }
        }
        if (end) {
            resultBtn.setDisable(false);
        }
    }

    private void changeTextFill(String otherPlayer) {
        if (otherPlayer.equals("player1")) {
            handout.setTextFill(Color.BLUE);
        } else {
            handout.setTextFill(Color.RED);
        }
    }

    public MoveType tryMove (Player player, int newX, int newY, int side) {
        x0 = player.getOldColumn();
        y0 = player.getOldRow();

        if (player.getName().equals("player1")) {
            otherPlayer = "player2";
        } else {
            otherPlayer = "player1";
        }

        if ((newX == x0) || (newY == y0)) {
            if ((newX == x0) && (newY == y0)) {
                return MoveType.NONE;
            } else if (newX == x0) {
                if ( checkWallY(y0, newY, x0) || checkPlayer(newX, newY, otherPlayer)) {
                    return MoveType.NONE;
                } else if (checkStarY(y0, newY, x0) > 0) {
                    player.setPoint(pointStarY(y0, newY, x0));

                    if (player.getName().equals("player1")) {
                        pointP1 += checkStarY(y0, newY, x0);
                    } else {
                        pointP2 += checkStarY(y0, newY, x0);
                    }
                    return MoveType.POINT;
                } else {
                    return MoveType.NOPOINT;
                }
            } else {
                if (checkWallX(x0, newX, y0) || checkPlayer(newX, newY, otherPlayer)) {
                    return MoveType.NONE;
                } else if (checkStarX(x0, newX, y0) > 0) {
                    player.setPoint(pointStarX(x0, newX, y0));
                    if (player.getName().equals("player1")) {
                        pointP1 += checkStarX(x0, newX, y0);
                    } else {
                        pointP2 += checkStarX(x0, newX, y0);
                    }
                    return MoveType.POINT;
                } else {
                    return MoveType.NOPOINT;
                }
            }
        }
        return MoveType.NONE;
    }

    public boolean checkWallX(int oldX, int newX, int y) {
        int max = newX;
        int min = oldX;
        if (oldX > newX ) {
            max = oldX;
            min = newX;
        }
        for (int i = min; i <= max ; i++) {
            if (board[i][y][0].equals("wall")) {
                return true;
            }
        }
        return false;
    }

    @FXML
    private void resultBtn(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxmlFiles/result.fxml"));
        Parent signInParent = loader.load();

        Result result = loader.getController();
        result.setResult(pointP1, pointP2);

        Scene signInScene = new Scene(signInParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(signInScene);
        window.show();
    }

    public boolean checkWallY(int oldY, int newY, int x) {
        int max = newY;
        int min = oldY;
        if (oldY > newY) {
            max = oldY;
            min = newY;
        }
        for (int i = min; i <= max; i++) {
            if (board[x][i][0].equals("wall")) {
                return true;
            }
        }
        return false;
    }

    public int checkStarX(int oldX, int newX, int y) {
        int max = newX;
        int min = oldX;
        if (oldX > newX) {
            max = oldX;
            min = newX;
        }
        int star = 0;
        for (int i = min; i <= max ; i++) {
            if (board[i][y][0].equals("star")) {
                star++;
            }
        }
        return star;
    }
    private int pointStarX(int oldX, int newX, int y) {
        int max = newX;
        int min = oldX;
        if (oldX > newX ) {
            max = oldX;
            min = newX;
        }
        int star = 0;
        for (int i = min; i <= max ; i++) {
            if (board[i][y][0].equals("star")) {
                star++;
                board[i][y][0] = "empty";
            }
        }
        //remove "star"
        for (int i = 0; i < starC.size(); i++) {
            for (int j = min; j <= max ; j++) {
                if (starC.get(i).getX() == j && starC.get(i).getY() == y) {
                    starGroup.getChildren().remove(starC.get(i));
                }
            }
        }
        return star;
    }

    public int checkStarY(int oldY, int newY, int x) {
        int max = newY;
        int min = oldY;
        if (oldY > newY) {
            max = oldY;
            min = newY;
        }
        int star = 0;
        for (int i = min; i <= max ; i++) {
            if (board[x][i][0].equals("star")) {
                star++;
            }
        }
        return star;
    }

    private int pointStarY(int oldY, int newY, int x) {
        int max = newY;
        int min = oldY;
        if (oldY > newY) {
            max = oldY;
            min = newY;
        }
        int star = 0;
        for (int i = min; i <= max ; i++) {
            if (board[x][i][0].equals("star")) {
                star++;
                board[x][i][0] = "empty";
            }
        }
        // remove "star"
        for (int i = 0; i < starC.size(); i++) {
            for (int j = min; j <= max ; j++) {
                if (starC.get(i).getY() == j && starC.get(i).getX() == x) {
                    starGroup.getChildren().remove(starC.get(i));
                }
            }
        }
        return star;
    }

    public boolean checkPlayer (int x, int y, String name) {
        if (board[x][y][0].equals(name)) {
            return true;
        }
        return false;
    }

    public boolean hasStar (int x, int y) {
        if (board[x][y][0].equals("star")) {
            return true;
        } else {
            return false;
        }
    }

    public boolean hasWall (int x, int y) {
        if (board[x][y][0].equals("wall")) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resultBtn.setDisable(true);
    }
}
