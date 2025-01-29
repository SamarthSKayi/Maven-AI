package com.example.firstfx.controller;
import com.example.firstfx.TaskController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.converter.LocalDateStringConverter;

import java.time.LocalDate;

public class TaskTemplateController<LocalDateDate> {

    @FXML
    private Label titleLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label dueDateLabel;

    @FXML
    private Label Scheduled;


    private TaskController parentController;

    @FXML
    private AnchorPane taskTemplate;

    public void setParentController(TaskController parentController) {
        this.parentController = parentController;
    }

    public void setTaskDetails(String title, String description, LocalDate dueDate) {
        titleLabel.setText(title);
        descriptionLabel.setText(description);
        dueDateLabel.setText(dueDate.toString());
    }

    @FXML
    void deleteTask(MouseEvent event) {
        parentController.removeTask(taskTemplate);
    }


}
