package boardGame.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Result implements Initializable {

    private String resultL;

    @FXML
    private Label resultLabel;
    @FXML
    private Label circleP1;
    @FXML
    private Label circleP2;
    @FXML
    private Label point1;
    @FXML
    private Label point2;

    @FXML
    private void exitBtn(ActionEvent event) {
        System.exit(0);
    }


    public void setResult(int pointP1, int pointP2) {

        circleP1.setText(pointP1 + "");
        circleP2.setText(pointP2 + "");

        if (pointP1 > pointP2) {
            resultL = "Player 1 WON!";
            resultLabel.setText(resultL);

        } else if (pointP1 < pointP2) {
            resultL = "Player 2 WON!";
            resultLabel.setText(resultL);

        } else {
            resultL = "DRAW";
            resultLabel.setText(resultL);
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
