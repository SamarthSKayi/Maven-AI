package com.example.firstfx.controller;

import com.example.firstfx.TaskController;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.DatePicker;
//import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class TaskFormController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField descriptionFiel;

    @FXML
    private DatePicker dueDateField;

    @FXML
    private DatePicker dueTimeField;

    private TaskController parentController;

    public void setParentController(TaskController parentController) {
        this.parentController = parentController;
    }


    @FXML
    private void handleAddTask() {
        String title = titleField.getText();
        String description = descriptionFiel.getText();
        LocalDate dueDate = dueDateField.getValue();
//        LocalTime dueTime = LocalTime.from(dueTimeField.getValue());



        if (title.isEmpty() || description.isEmpty() || dueDate == null ) {
            // Handle empty fields, maybe show an error
            return;
        }
        parentController.addTask(title, description, dueDate);

        // Close the pop-up window
        Stage stage = (Stage) titleField.getScene().getWindow();
        stage.close();
    }


}
