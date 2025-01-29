package com.example.firstfx;
import com.example.firstfx.controller.TaskFormController;
import com.example.firstfx.controller.TaskTemplateController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskController {

    @FXML
    private VBox taskList;

    private List<TaskData> tasks = new ArrayList<TaskData>();

    @FXML
    void addtask(MouseEvent event) {
        try {
            // Load the task form FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("taskform.fxml"));
            AnchorPane taskForm = loader.load();

            // Get the controller of the task form to set up the parent controller
            TaskFormController taskFormController = loader.getController();
            taskFormController.setParentController(this);

            // Show the task form in a new window
            Stage stage = new Stage();
            stage.setScene(new Scene(taskForm));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addTask(String title, String description, LocalDate dueDate) {
        TaskData newTask = new TaskData(title, description, dueDate.toString());
        tasks.add(newTask);
        addTaskToUI(newTask);
    }

    private void addTaskToUI(TaskData task) {
        try {
            // Load the task template FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("tasktemplate.fxml"));
            AnchorPane newTaskNode = loader.load();

            // Get the controller of the task template to set up the task details
            TaskTemplateController taskTemplateController = loader.getController();
            taskTemplateController.setParentController(this);
            taskTemplateController.setTaskDetails(task.getTitle(), task.getDescription(), task.getDueDate());

            // Add the new task to the taskList VBox
            taskList.getChildren().add(newTaskNode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to remove a task from the taskList and the tasks list
    public void removeTask(Node taskNode) {
        int index = taskList.getChildren().indexOf(taskNode);
        if (index >= 0) {
            tasks.remove(index);
            taskList.getChildren().remove(taskNode);
        }
    }

    public void addTask(MouseEvent mouseEvent) {
    }

    public void home(ActionEvent actionEvent) {
        Main.switchScene("main.fxml");
    }
}
