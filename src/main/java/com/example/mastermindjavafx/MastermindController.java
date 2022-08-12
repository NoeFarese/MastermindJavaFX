package com.example.mastermindjavafx;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;


public class MastermindController {
    @FXML
    public GridPane playGrid;
    int numberOne = 0;
    int numberTwo = 0;
    int numberThree = 0;

    Button checkButton = new Button();
    ChoiceBox[] inputColours = new ChoiceBox[4];
    int rowPosition = 0;
    String[] selectedColours = new String[4];
    String[] allColours = {"blue", "red", "orange", "yellow", "green", "black", "white", "grey"};


    public void initialize() {
        generateRandomColor();
        checkButton.setText("Check!!!");
        playGrid.setColumnSpan(checkButton, 4);
        createPlayRow();
        checkButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                checkInput();
            }

        });
    }

    private void popUp(String message) {
        Stage losingPopup = new Stage();
        losingPopup.initModality(Modality.APPLICATION_MODAL);
        GridPane popupGrid = new GridPane();
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(popupGrid);
        AnchorPane.setLeftAnchor(popupGrid, 0.0);
        AnchorPane.setRightAnchor(popupGrid, 0.0);
        AnchorPane.setTopAnchor(popupGrid, 0.0);
        AnchorPane.setBottomAnchor(popupGrid, 0.0);
        Scene popupScene = new Scene(anchorPane, 300, 250);
        losingPopup.setWidth(86);
        losingPopup.setHeight(70);
        losingPopup.setScene(popupScene);
        Label losingLabel = new Label(message);
        Button quitButton = new Button("Quit");
        quitButton.setOnAction(actionEvent -> {
            System.exit(0);
        });
        Button retryButton = new Button("Retry");
        retryButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                playGrid.getChildren().clear();
                rowPosition = 0;
                generateRandomColor();
                losingPopup.close();
                numberOne = 0;
                numberTwo = 0;
                numberThree = 0;
                checkButton.setVisible(true);
            }
        });
        popupGrid.add(losingLabel, 0, 0);
        popupGrid.add(quitButton, 0, 1);
        popupGrid.add(retryButton, 1, 1);

        popupGrid.setColumnSpan(losingLabel, 2);

        losingPopup.showAndWait();
    }

    private void generateRandomColor() {
        for (int i = 0; i < 4; i++) {
            int min = 0;
            int max = 7;
            int randomNumber = (int) Math.floor(Math.random() * (max - min + 1) + min);
            selectedColours[i] = allColours[randomNumber];
        }
        System.out.println(selectedColours[0] + selectedColours[1] + selectedColours[2] + selectedColours[3]);
    }

    public void createPlayRow() {
        for (int i = 4; i <= 7; i++) {
            ChoiceBox choiceBox = new ChoiceBox();
            choiceBox.setId("choiceBox" + i + rowPosition);
            playGrid.add(choiceBox, i, rowPosition);
            for (int j = 0; j < 8; j++) {
                choiceBox.getItems().add(allColours[j]);
            }
            inputColours[i - 4] = choiceBox;

        }
        playGrid.getChildren().remove(checkButton);
        playGrid.add(checkButton, 8, rowPosition);
        rowPosition++;
        System.out.println(rowPosition);
    }

    public void checkInput() {
        int counter = 0;
        for (int i = 0; i < inputColours.length; i++) {
            System.out.println(inputColours[i].getValue());
            if (inputColours[i].getValue() == null) {
                inputColours[i].setStyle("-fx-background-color:#ff4500");
            } else {
                counter++;
                inputColours[i].setStyle("-fx-background-color:#e4e4e4");
            }
        }
        if (counter == 4) {
            for (int i = 0; i < inputColours.length; i++) {
                inputColours[i].setDisable(true);
            }
            checkColor();
            createPlayRow();
        }
    }

    public void checkColor() {
        int colourExists = 0;
        int colourCorrectPlace = 0;
        String firstColour = inputColours[0].getValue().toString();

        String secondColour = inputColours[1].getValue().toString();

        String thirdColour = inputColours[2].getValue().toString();

        String fourthColour = inputColours[3].getValue().toString();

        String[] inputColours = {firstColour, secondColour, thirdColour, fourthColour};
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                numberOne = 1;
                numberTwo = 2;
                numberThree = 3;
            } else if (i == 1) {
                numberOne = 1;
                numberTwo = 2;
                numberThree = -1;
            } else if (i == 2) {
                numberOne = 1;
                numberTwo = -2;
                numberThree = -1;
            } else {
                numberOne = -3;
                numberTwo = -2;
                numberThree = -1;
            }

            if (selectedColours[i].equals(inputColours[i])) {
                colourCorrectPlace = colourCorrectPlace + 1;
                inputColours[i] = "";
            } else if (selectedColours[i].equals(inputColours[i + numberOne])) {
                colourExists = colourExists + 1;
                inputColours[i + numberOne] = "";
            } else if (selectedColours[i].equals(inputColours[i + numberTwo])) {
                colourExists = colourExists + 1;
                inputColours[i + numberTwo] = "";
            } else if (selectedColours[i].equals(inputColours[i + numberThree])) {
                colourExists = colourExists + 1;
                inputColours[i + numberThree] = "";
            }

        }
        for (int i = 0; i < colourCorrectPlace; i++) {
            Circle circleCorrectPlace = new Circle(10);
            circleCorrectPlace.setId("choiceBox" + i + rowPosition);
            playGrid.add(circleCorrectPlace, i, rowPosition - 1);
            circleCorrectPlace.setFill(Paint.valueOf("#89CFF0"));
        }
        for (int i = 0; i < colourExists; i++) {
            Circle circleCorrectColor = new Circle(10);
            circleCorrectColor.setId("choiceBox" + i + rowPosition);
            playGrid.add(circleCorrectColor, i + 8, rowPosition - 1);
            circleCorrectColor.setFill(Paint.valueOf("#1A4C39"));
        }


        if (colourCorrectPlace == 4) {
            checkButton.setVisible(false);
            popUp("You won!!!");

        }
        if (rowPosition == 12 && colourCorrectPlace != 4) {
            checkButton.setVisible(false);
            popUp("You lost!!!");
        }
/*
        if (j == 1) {
            System.out.println("You lost!");
            System.out.println("You are bad and don't try again.");
        }

 */

    }
}