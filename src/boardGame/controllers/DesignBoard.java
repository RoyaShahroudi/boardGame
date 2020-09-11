package boardGame.controllers;

import boardGame.otherClasses.Tile;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DesignBoard implements Initializable {

    @FXML
    private ComboBox<Integer> rowCombo;
    @FXML
    private ComboBox<Integer> columnCombo;
    @FXML
    private AnchorPane anchorDesignBoard;
    @FXML
    private Button showBtn;


    public static Group tileGroup = new Group();
    private int row = 6;
    private int column = 10;
    private int side;
    private String[][][] board;

    @FXML
    private void showBtn(ActionEvent event) {

            row = rowCombo.getValue();
            column = columnCombo.getValue();

            if (row > 6 || column > 12){
                side = 60;
            } else {
                side = 80;
            }
            board = new String[column][row][3];

            for (int x = 0; x < column; x++) {
                for (int y = 0; y < row; y++) {
                    Tile tile = new Tile(x, y, side, column, row, 0, board);

                    tile.setTranslateX( (1079 - (column * side))/2 + x * side );
                    tile.setTranslateY((506 - (row * side))/2 + y * side);

                    tileGroup.getChildren().add(tile);
                }
            }
            anchorDesignBoard.getChildren().addAll(tileGroup);

        showBtn.setDisable(true);
    }

    @FXML
    private void nextBtn(ActionEvent event) throws IOException {


        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../fxmlFiles/designWalls.fxml"));
        Parent signInParent = loader.load();

        DesignWalls designWalls = loader.getController();
        designWalls.setBoard(row, column);

        Scene signInScene = new Scene(signInParent);
        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(signInScene);
        window.show();

    }


    // comboBox
    ObservableList<Integer> rowList = FXCollections.observableArrayList(
            2, 3, 4, 5, 6, 7, 8
    );

    ObservableList<Integer> columnList = FXCollections.observableArrayList(
            2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17
    );

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rowCombo.setItems(rowList);
        columnCombo.setItems(columnList);

    }
}
