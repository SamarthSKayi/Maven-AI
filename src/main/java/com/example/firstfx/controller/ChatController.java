//package yourpackage;
package com.example.firstfx.controller;
import com.example.firstfx.Elements.MessageBubble;
import com.example.firstfx.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import org.json.JSONObject;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class ChatController {

    @FXML
    private VBox messageContainer;

    @FXML
    private TextArea messageInput;

    @FXML
    private ScrollPane scrollPane;

    private HttpClient client = HttpClient.newHttpClient();

    @FXML
    public void initialize() {
        messageContainer.heightProperty().addListener((obs, oldVal, newVal) -> {
            scrollPane.setVvalue((Double) newVal);
        });
    }

    @FXML
    public void sendMessage() {
        String messageText = messageInput.getText();
        if (!messageText.trim().isEmpty()) {
            MessageBubble userBubble = new MessageBubble(messageText, true);
            messageContainer.getChildren().add(userBubble);
            messageInput.clear();
//            System.out.println(messageText);
            sendApiRequest(messageText);
        }
    }

    private void sendApiRequest(String messageText) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://open-ai34.p.rapidapi.com/v1/chat/completions"))
                    .header("x-rapidapi-key", "9d17f29aebmsh142f06fdade924ap1ef2a7jsn781f2d8f0ec9")
                    .header("x-rapidapi-host", "open-ai34.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"model\":\"databricks/dbrx-instruct\",\"messages\":[{\"content\":\"" + messageText + "\",\"role\":\"user\"}],\"max__new_tokens\":512,\"temperature\":0.2,\"top_p\":0.7,\"top_k\":50,\"repetition_penalty\":1,\"stop\":[\"<|im_start|>\",\"<|im_end|>\"]}"))
                    .build();

            CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, HttpResponse.BodyHandlers.ofString());

            response.thenApply(HttpResponse::body).thenAccept(this::displayApiResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayApiResponse(String response) {
        try {
            JSONObject jsonResponse = new JSONObject(response);
            String content = jsonResponse.getJSONArray("choices")
                    .getJSONObject(0)
                    .getJSONObject("message")
                    .getString("content");
            Platform.runLater(() -> {
                MessageBubble apiBubble = new MessageBubble(content, false);
                messageContainer.getChildren().add(apiBubble);
            });
        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> {
                MessageBubble errorBubble = new MessageBubble("Error parsing response", false);
                messageContainer.getChildren().add(errorBubble);
            });
        }
    }

    public void home(ActionEvent actionEvent) {
        Main.switchScene("main.fxml");
    }
}




//    HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://chatgpt-gpt5.p.rapidapi.com/ask"))
//                    .header("x-rapidapi-key", "9d17f29aebmsh142f06fdade924ap1ef2a7jsn781f2d8f0ec9")
//                    .header("x-rapidapi-host", "chatgpt-gpt5.p.rapidapi.com")
//                    .header("Content-Type", "application/json")
//                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"query\":\""+messageText+"?\"}"))
//                    .build();
// result key is "response"
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://open-ai21.p.rapidapi.com/chatbotapi"))
//                    .header("x-rapidapi-key", "9d17f29aebmsh142f06fdade924ap1ef2a7jsn781f2d8f0ec9")
//                    .header("x-rapidapi-host", "open-ai21.p.rapidapi.com")
//                    .header("Content-Type", "application/json")
//                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"bot_id\":\"OEXJ8qFp5E5AwRwymfPts90vrHnmr8yZgNE171101852010w2S0bCtN3THp448W7kDSfyTf3OpW5TUVefz\",\"messages\":[{\"role\":\"user\",\"content\":\"hello\"}],\"user_id\":\"\",\"temperature\":0.9,\"top_k\":5,\"top_p\":0.9,\"max_tokens\":256,\"model\":\"gpt 3.5\"}"))
//                    .build();
//result key "result"
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://cheapest-gpt-4-turbo-gpt-4-vision-chatgpt-openai-ai-api.p.rapidapi.com/v1/chat/completions"))
//                    .header("x-rapidapi-key", "9d17f29aebmsh142f06fdade924ap1ef2a7jsn781f2d8f0ec9")
//                    .header("x-rapidapi-host", "cheapest-gpt-4-turbo-gpt-4-vision-chatgpt-openai-ai-api.p.rapidapi.com")
//                    .header("Content-Type", "application/json")
//                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"messages\":[{\"role\":\"user\",\"content\":\""+messageText+"\"}],\"model\":\"gpt-4-turbo-2024-04-09\",\"max_tokens\":100,\"temperature\":0.9}"))
//                    .build();
//very limited text " JSONObject jsonResponse = new JSONObject(response);
//            String content = jsonResponse.getJSONArray("choices")
//                    .getJSONObject(0)
//                    .getJSONObject("message")
//                    .getString("content");"


//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create("https://chat-gpt26.p.rapidapi.com/"))
//                    .header("x-rapidapi-key", "9d17f29aebmsh142f06fdade924ap1ef2a7jsn781f2d8f0ec9")
//                    .header("x-rapidapi-host", "chat-gpt26.p.rapidapi.com")
//                    .header("Content-Type", "application/json")
//                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\""+messageText+"\"}]}"))
//                    .build();
//to specific but 1000 per month requests key is "JSONObject jsonResponse = new JSONObject(response);
//            String content = jsonResponse.getJSONArray("choices")
//                    .getJSONObject(0)
//                    .getJSONObject("message")
//                    .getString("content");"     .getString("content");"