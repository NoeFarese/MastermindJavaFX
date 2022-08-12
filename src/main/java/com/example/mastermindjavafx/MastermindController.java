package com.example.mastermindjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.GridPane;


public class MastermindController {
    @FXML
    private GridPane playGrid;

    Button checkButton = new Button();

    int rowPosition = 0;
    String[] allColours = {"blue", "red", "orange", "yellow", "green", "black", "white", "grey"};

    public void initialize() {
        checkButton.setText("Check!!!");
        playGrid.setColumnSpan(checkButton, 4);
        createPlayRow();
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                createPlayRow();
            }
        });
    }

    public void createPlayRow() {
        for (int i = 4; i <= 7; i++) {
            ChoiceBox choiceBox = new ChoiceBox();
            choiceBox.setId("choiceBox" + i + rowPosition);
            playGrid.add(choiceBox, i, rowPosition);
            for (int j = 0; j < 8; j++) {
                choiceBox.getItems().add(allColours[j]);
            }
        }
        playGrid.add(checkButton, 8, rowPosition);
        rowPosition++;
        System.out.println(rowPosition);
    }
}