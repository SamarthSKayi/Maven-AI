package com.example.firstfx.Elements;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class TextToSpeechApp {

    private static final String API_KEY = "d2d5153144msh301b2619ace4400p13b691jsn3f40a9764fe7";
    private static final String API_HOST = "natural-text-to-speech-converter-at-lowest-price.p.rapidapi.com";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Get user input
        System.out.print("Enter the text you want to convert to speech: ");
        String text = scanner.nextLine();

        // Build the JSON payload
        String jsonPayload = "{"
                + "\"ttsService\":\"polly\","
                + "\"audioKey\":\"" +"62cc2f1bff7e29001da9e243" + "\","
                + "\"storageService\":\"s3\","
                + "\"text\":\"<speak><p>" + text + "</p></speak>\","
                + "\"voice\":{\"value\":\"" + "en-IN-Standard-B" + "\",\"lang\":\"en-US\"},"
                + "\"audioOutput\":{\"fileFormat\":\"mp3\",\"sampleRate\":24000}"
                + "}";

        try {
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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
//     is printed. Otherwise, the string's characters are converted into bytes according to the character encoding given to the constructor, or the default charset if none specified. These bytes are written in exactly the manner of the write(int) method.