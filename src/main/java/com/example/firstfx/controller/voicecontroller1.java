package com.example.firstfx.controller;

//package com.example.firstfx.controller;

import com.example.firstfx.APICalls;
import com.example.firstfx.Elements.MessageBubble;
import com.example.firstfx.Elements.MessageBubble1;
import com.example.firstfx.Main;
import javafx.application.Platform;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Random;

public class voicecontroller1 {

    private String res = null;

    @FXML
    void sendMessage(MouseEvent event) {
        res = inputArea.getText();
    }

    @FXML
    private VBox messageBox;

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
    private static final String API_HOST = "natural-text-to-speech-converter-at-lowest-price.p.rapidapi.com";

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

    private void animateBobs() {
        animateBob(bob1);
        animateBob(bob2);
        animateBob(bob3);
        animateBob(bob4);
    }

    private void animateBob(AnchorPane bob) {
        double currentHeight = bob.getPrefHeight();
        double newHeight = 50 + random.nextInt(200);

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
    }

    private void convertTextToSpeech(String text) throws Exception {
        // Prepare request
        String jsonPayload = "{"
                + "\"ttsService\":\"polly\","
                + "\"audioKey\":\"" +"62cc2f1bff7e29001da9e243" + "\","
                + "\"storageService\":\"s3\","
                + "\"text\":\"<speak><p>" + text + "</p></speak>\","
                + "\"voice\":{\"value\":\"" + "en-IN-Standard-B" + "\",\"lang\":\"en-US\"},"
                + "\"audioOutput\":{\"fileFormat\":\"mp3\",\"sampleRate\":24000}"
                + "}";
            // Define the API endpoint
            String apiUrl = "https://natural-text-to-speech-converter-at-lowest-price.p.rapidapi.com/backend/ttsNewDemo";

            // Create the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(apiUrl))
                    .header("x-rapidapi-key", API_KEY)
                    .header("x-rapidapi-host", API_HOST)
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response
            System.out.println("Response Code: " + response.statusCode());
            System.out.println("Response Body: " + response.body());


        if (response.statusCode() == 200) {
            // Parse JSON array response
            JSONObject jsonObject = new JSONObject(response.body());
            String audioUrl = jsonObject.getString("url");

            System.out.println(audioUrl);
                // Play the audio file
                Media media = new Media(audioUrl);
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.play();
                timeline.play();

                // Stop animation and clean up after audio is done playing
                mediaPlayer.setOnEndOfMedia(() -> {
                    mediaPlayer.stop();
                    mediaPlayer.dispose();
                    timeline.stop();
                    resetBobs();
                });
        } else {
            System.err.println("Failed to retrieve audio: " + response.statusCode());
        }
    }

    public void sendMessage() throws Exception {
        String messageText = inputArea.getText();
        if (!messageText.trim().isEmpty()) {
            MessageBubble1 userBubble = new MessageBubble1(messageText, true);
            messageBox.getChildren().add(userBubble);
            inputArea.clear();
            APICalls textassist=new APICalls();
            String response=textassist.t2t5(messageText);
            MessageBubble bot=new MessageBubble(response,false);
            messageBox.getChildren().add(bot);

            // Convert text to speech and play audio
            convertTextToSpeech(processResponse(response));
        }
    }
    public String processResponse(String response) {
        // Look for the index of the first occurrence of the code delimiter ```
        int codeIndex = response.indexOf("```");
        if (codeIndex != -1) {
            return response.substring(0, codeIndex);
        }
        response = response.replaceAll("\\n", " ").trim();
        return response;
    }

    public void displayApiResponse(String response) {
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
}
