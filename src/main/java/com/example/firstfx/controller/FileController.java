package com.example.firstfx.controller;

import com.example.firstfx.APICalls;
import com.example.firstfx.Elements.MessageBubble;
import com.example.firstfx.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class FileController {

    @FXML
    private VBox display;

    public void handleOpen() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Readable Files", "*.txt", "*.pdf", "*.doc", "*.docx", "*.ppt", "*.pptx"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Word Documents", "*.doc", "*.docx"),
                new FileChooser.ExtensionFilter("PowerPoint Presentations", "*.ppt", "*.pptx")
        );

        File selectedFile = fileChooser.showOpenDialog(new Stage());
        if (selectedFile != null) {
            displayFileContent(selectedFile);
        }
    }

    private void displayFileContent(File file) {
        try {
            String fileExtension = getFileExtension(file);
            if (fileExtension.equals("txt")) {
                displayTextFileContent(file);
            } else if (fileExtension.equals("pdf")) {
                displayPDFFileContent(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayTextFileContent(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        APICalls call=new APICalls();
        String result=call.t2t1(content);
        MessageBubble bot=new MessageBubble(result,false);
        display.getChildren().add(bot);
//        textArea.setText(result);
    }

    private void displayPDFFileContent(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            System.out.println(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        } else {
            return "";
        }
    }

    public void homepage(MouseEvent mouseEvent) {
        Main.switchScene("main.fxml");
    }

    public void main(ActionEvent actionEvent) {
        Main.switchScene("main.fxml");
    }
}
