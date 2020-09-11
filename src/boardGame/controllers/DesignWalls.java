package boardGame.controllers;

import boardGame.otherClasses.Tile;
import javafx.application.Application;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DesignWalls implements Initializable {

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

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxmlFiles/designStars.fxml"));
        Parent signInParent = loader.load();

        DesignStars designStars = loader.getController();
        designStars.setBoard(row, column, board);

        Scene signInScene = new Scene(signInParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(signInScene);
        window.show();

    }


    public void setBoard(int boardRow, int boardColumn) {
        row = boardRow;
        column = boardColumn;
        board = new String[column][row][3];

        for (int x = 0; x < column ; x++) {
            for (int y = 0; y < row; y++) {
                board[x][y][0] = "empty";
                board[x][y][1] = "0";
                board[x][y][2] = "0";
            }
        }
        board();
    }

    public void board() {
        if (row > 6 || column > 12){
            side = 60;
        } else {
            side = 80;
        }
        for (int x = 0; x < column ; x++) {
            for (int y = 0; y < row ; y++) {
                tile = new Tile(x, y, side, column, row, 1, board);

                board[x][y][1] = ((1079 - (column * side))/2 + x * side ) + "";
                board[x][y][2] = ((506 - (row * side))/2 + y * side) + "";

                tile.setTranslateX(Integer.parseInt(board[x][y][1]));
                tile.setTranslateY(Integer.parseInt(board[x][y][2]));

                tileGroup.getChildren().add(tile);
            }
        }
        anchorDesignWalls.getChildren().addAll(tileGroup);
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
