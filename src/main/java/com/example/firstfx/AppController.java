package com.example.firstfx;

//package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;


public class AppController {

    @FXML
    private TextArea fileContentTextArea;

    public void handleChooseFile() {
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
            } else if (fileExtension.equals("doc") || fileExtension.equals("docx")) {
                displayDocFileContent(file);
            } else if (fileExtension.equals("ppt") || fileExtension.equals("pptx")) {
                displayPPTFileContent(file);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayTextFileContent(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        fileContentTextArea.setText(content);
    }

    private void displayPDFFileContent(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            String text = pdfStripper.getText(document);
            System.out.println(text);
            fileContentTextArea.setText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void displayDocFileContent(File file) {

    }

    private void displayDocxFileContent(File file) {


    }

    private void displayPPTFileContent(File file) throws IOException {
        // You'll need to use a library like Apache POI to read PPT/PPTX files
        // Example:
        // HSLFSlideShow ppt = new HSLFSlideShow(new HSLFSlideShowImpl(file));
        // SlideShowExtractor extractor = new SlideShowExtractor(ppt);
        // String text = extractor.getText();
        // fileContentTextArea.setText(text);
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        } else {
            return "";
        }
    }
}
