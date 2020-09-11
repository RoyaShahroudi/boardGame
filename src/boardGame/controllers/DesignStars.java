package boardGame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import boardGame.otherClasses.Main;
import boardGame.otherClasses.Tile;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DesignStars implements Initializable {

    public static Group tileGroup = new Group();
    private static int row;
    private static int column;
    private boolean show = false;
    private Tile tile;
    private int side = 60;
    public String[][][] board;


    @FXML
    private AnchorPane anchorDesignWalls;
    @FXML
    private Label label;


    @FXML
    private void nextBtn(ActionEvent event) throws IOException {

        if (!noStar()) {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../fxmlFiles/designPlayers.fxml"));
            Parent signInParent = loader.load();

            DesignPlayers designPlayers = loader.getController();
            designPlayers.setBoard(row, column, board);

            Scene signInScene = new Scene(signInParent);
            Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
            window.setScene(signInScene);
            window.show();

        }
    }

    public void setBoard(int boardRow, int boardColumn, String[][][] board) {
        row = boardRow;
        column = boardColumn;
        this.board = new String[column][row][3];
        this.board = board;
        board();

    }

    public void board() {
        if (row > 6 || column > 12){
            side = 60;
        } else {
            side = 80;
        }
        for (int x = 0 ; x < column ; x++) {
            for (int y = 0 ; y < row ; y++) {
                tile = new Tile(x, y, side, column, row, 2, board);

                tile.setTranslateX( (1079 - (column * side))/2 + x * side );
                tile.setTranslateY((506 - (row * side))/2 + y * side);
                if (board[x][y][0].equals("wall")) {
                    tile.buildWall();
                }

                tileGroup.getChildren().add(tile);
            }
        }
        anchorDesignWalls.getChildren().addAll(tileGroup);

    }
    private boolean noStar () {
        for (int i = 0; i <column ; i++) {
            for (int j = 0; j < row ; j++) {
                if (board[i][j][0].equals("star")) {
                    return false;
                }
            }
        }
        return true;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
