package com.example.firstfx;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class APIcall {
    public String call1(String s) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://chatgpt-best-price.p.rapidapi.com/v1/chat/completions"))
                .header("x-rapidapi-key", "fc7ae29fcdmsh046b8c0c026b136p12906cjsn9b9d7fad7d93")
                .header("x-rapidapi-host", "chatgpt-best-price.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\""+s+"\"}]}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());

        String r=response.body();

        JSONObject jsonObject = new JSONObject(response.body());

        // Accessing content from the choices array
        JSONArray choicesArray = jsonObject.getJSONArray("choices");
            JSONObject firstChoice = choicesArray.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            String content = message.getString("content");

            return content;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        APIcall apIcall=new APIcall();
        String r=apIcall.call1("sum of n using java");
        System.out.println(r);
    }
}
