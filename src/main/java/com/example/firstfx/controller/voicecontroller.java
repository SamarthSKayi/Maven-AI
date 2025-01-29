package com.example.firstfx.controller;

import com.example.firstfx.APICalls;
import com.example.firstfx.Elements.MessageBubble;
import com.example.firstfx.Elements.MessageBubble1;
import com.example.firstfx.Main;
import javafx.application.Platform;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
//import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.json.JSONObject;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class voicecontroller {
    String res=null;
    @FXML
    void sendMessage(MouseEvent event) {
        res= inputArea.getText();
    }

    @FXML
    private VBox messageBox;

    @FXML
    private AnchorPane display;

    @FXML
    private AnchorPane bob1;

    @FXML
    private AnchorPane bob2;

    @FXML
    private AnchorPane bob3;

    @FXML
    private AnchorPane bob4;

    @FXML
    private TextArea inputArea;

    private final Random random = new Random();
    private static final String API_KEY = "d2d5153144msh301b2619ace4400p13b691jsn3f40a9764fe7";
    private static final String API_HOST = "open-ai-text-to-speech1.p.rapidapi.com";

    private Timeline timeline;
    private MediaPlayer mediaPlayer;
    private Path tempFile;

    private double initialHeightBob1;
    private double initialHeightBob2;
    private double initialHeightBob3;
    private double initialHeightBob4;

    private double initialYBob1;
    private double initialYBob2;
    private double initialYBob3;
    private double initialYBob4;

    private HttpClient client = HttpClient.newHttpClient();



    @FXML
    public void initialize() {
        initialHeightBob1 = bob1.getPrefHeight();
        initialHeightBob2 = bob2.getPrefHeight();
        initialHeightBob3 = bob3.getPrefHeight();
        initialHeightBob4 = bob4.getPrefHeight();

        initialYBob1 = bob1.getLayoutY();
        initialYBob2 = bob2.getLayoutY();
        initialYBob3 = bob3.getLayoutY();
        initialYBob4 = bob4.getLayoutY();

        timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> animateBobs())
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
    }


//     messageBox.heightProperty().addListener((obs, oldVal, newVal) -> {
//        display.setVvalue((Double) newVal);
//    });


    private void animateBobs() {
        animateBob(bob1);
        animateBob(bob2);
        animateBob(bob3);
        animateBob(bob4);
    }

    private void animateBob(AnchorPane bob) {
        double currentHeight = bob.getPrefHeight();
        double newHeight = 50 + random.nextInt(200); // Adjust the range as needed

        // Calculate the new layoutY to keep the center
        double currentCenterY = bob.getLayoutY() + currentHeight / 2;
        double newLayoutY = currentCenterY - newHeight / 2;

        bob.setPrefHeight(newHeight);
        bob.setLayoutY(newLayoutY);
    }

    private void resetBobs() {
        bob1.setPrefHeight(initialHeightBob1);
        bob2.setPrefHeight(initialHeightBob2);
        bob3.setPrefHeight(initialHeightBob3);
        bob4.setPrefHeight(initialHeightBob4);

        bob1.setLayoutY(initialYBob1);
        bob2.setLayoutY(initialYBob2);
        bob3.setLayoutY(initialYBob3);
        bob4.setLayoutY(initialYBob4);
    }
    public void handleButtonClick(ActionEvent actionEvent) throws Exception {
//        APICalls apiCalls=new APICalls();
        String req =inputArea.getText();
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                timeline.stop();
                resetBobs();
                if (tempFile != null && Files.exists(tempFile)) {
                    Files.delete(tempFile);
                }
            }
            sendMessage();

        } catch (Exception e) {
            e.printStackTrace();
        }
        convertTextToSpeech(req, "alloy");
    }

    private void convertTextToSpeech(String text, String voice) throws Exception {
        // Prepare request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://open-ai-text-to-speech1.p.rapidapi.com/"))
                .header("x-rapidapi-key", API_KEY)
                .header("x-rapidapi-host", API_HOST)
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString(
                        "{\"model\":\"tts-1\",\"input\":\"" + text + "\",\"voice\":\"" + voice + "\"}"))
                .build();

        // Send request and handle response
        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        // Check if response is successful
        if (response.statusCode() == 200) {
            // Save the audio file to a temporary location
            tempFile = Files.createTempFile("tts_audio", ".mp3");
            Files.write(tempFile, response.body());

            // Play the audio file
            Media media = new Media(tempFile.toUri().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            timeline.play();

            // Stop animation and clean up after audio is done playing
            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                timeline.stop();
                resetBobs();
                try {
                    Files.delete(tempFile);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } else {
            System.err.println("Failed to retrieve audio: " + response.statusCode());
        }
    }
    public void sendMessage() throws IOException, InterruptedException {
        String messageText = inputArea.getText();
        if (!messageText.trim().isEmpty()) {
            MessageBubble1 userBubble = new MessageBubble1(messageText, true);
            messageBox.getChildren().add(userBubble);
            inputArea.clear();
            APICalls textassist=new APICalls();
            String response=textassist.t2t5(res);
            res=response;
//            sendApiRequest(messageText);
            displayApiResponse(response);
        }
    }


    private void displayApiResponse(String response) {
        try {
            Platform.runLater(() -> {
                MessageBubble apiBubble = new MessageBubble(response, false);
                messageBox.getChildren().add(apiBubble);
            });
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                MessageBubble errorBubble = new MessageBubble("Error parsing response", false);
                messageBox.getChildren().add(errorBubble);
            });
        }
    }

    public void home(ActionEvent actionEvent) {
        Main.switchScene("main.fxml");
    }

//    public void sendMessage(ActionEvent actionEvent) {
//    }
}
