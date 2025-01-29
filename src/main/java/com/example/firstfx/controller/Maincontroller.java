package com.example.firstfx.controller;

import com.example.firstfx.Main;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class Maincontroller {

    @FXML
    private AnchorPane menu;

    @FXML
    private ImageView profile_img;



    @FXML
    void Chat(MouseEvent event) {
        Main.switchScene("ChatApp.fxml");
    }

    @FXML
    void closemenuf(MouseEvent event) {
        menu.setVisible(false);
    }

    @FXML
    void openmenut(MouseEvent event) {
        menu.setVisible(true);
    }

    public void Tasks(MouseEvent mouseEvent) {
        Main.switchScene("task.fxml");
    }

    public void voicechat(MouseEvent mouseEvent) {
        Main.switchScene("voice.fxml");
    }

    public void bussines(MouseEvent mouseEvent) {
        Main.switchScene("business.fxml");
    }

    public void files(MouseEvent mouseEvent) {
        Main.switchScene("file.fxml");
    }
}
