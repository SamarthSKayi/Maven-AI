package com.example.firstfx;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.json.JSONArray;
import org.json.JSONObject;

public class APICalls {
   public String t2t1(String message){
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://chatgpt-42.p.rapidapi.com/gpt4"))
                .header("x-rapidapi-key", "7d5c9a32c4mshdc33227ae9c206dp122229jsn0cb5b61f286a")
                .header("x-rapidapi-host", "chatgpt-42.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"messages\":[{\"role\":\"user\",\"content\":\""+message+"\"}],\"web_access\":false}"))
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

//        #limit 50-3
        // Parse the response body
        JSONObject jsonObject = new JSONObject(response.body());

        // Access the result
        String result = jsonObject.getString("result");

       return result;
    }

    public String t2t2(String message) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://open-ai21.p.rapidapi.com/chatgpt"))
                .header("x-rapidapi-key", "7d5c9a32c4mshdc33227ae9c206dp122229jsn0cb5b61f286a")
                .header("x-rapidapi-host", "open-ai21.p.rapidapi.com")
                .header("Content-Type", "application/json")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"messages\":[{\"role\":\"user\",\"content\":\""+message+"\"}],\"web_access\":false}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        JSONObject jsonObject=new JSONObject(response);
        String result=jsonObject.getString("result");
//                limit 100
       return result;
    }

    public String t2t3(String message) {
        JSONObject messageObject = null;
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://open-ai34.p.rapidapi.com/v1/chat/completions"))
                    .header("x-rapidapi-key", "7d5c9a32c4mshdc33227ae9c206dp122229jsn0cb5b61f286a")
                    .header("x-rapidapi-host", "open-ai34.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .method("POST", HttpRequest.BodyPublishers.ofString("{\"model\":\"Qwen/Qwen2-72B-Instruct\",\"messages\":[{\"content\":\"Hi there!\",\"role\":\"user\"}],\"max_new_tokens\":1,\"temperature\":0.2,\"top_p\":0.7,\"top_k\":50,\"repetition_penalty\":1,\"stop\":[\"<|im_start|>\",\"<|im_end|>\"]}"))
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            System.out.println(response.body());
            JSONObject jsonObject=new JSONObject(response);
            String result=jsonObject.getString("result");
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        String content = messageObject.getString("content");
        return content;
    }
    public String t2t4(String message){
       try{
           HttpRequest request = HttpRequest.newBuilder()
                   .uri(URI.create("https://open-ai32.p.rapidapi.com/geminipro"))
                   .header("x-rapidapi-key", "7d5c9a32c4mshdc33227ae9c206dp122229jsn0cb5b61f286a")
                   .header("x-rapidapi-host", "open-ai32.p.rapidapi.com")
                   .header("Content-Type", "application/json")
                   .method("POST", HttpRequest.BodyPublishers.ofString("{\"messages\":[{\"role\":\"user\",\"content\":\"hello\"}],\"temperature\":0.9,\"top_k\":5,\"top_p\":0.9,\"max_tokens\":256,\"web_access\":false}"))
                   .build();
           HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
           System.out.println(response.body());
           JSONObject jsonObject = new JSONObject(response);
           return  jsonObject.getString("ressult");//limit 50
       }catch (Exception e){
           e.printStackTrace();
       }
       return "no response";
    }
    public String t2t5(String message){
       try{
           HttpRequest request = HttpRequest.newBuilder()
                   .uri(URI.create("https://chatgpt-best-price.p.rapidapi.com/v1/chat/completions"))
                   .header("x-rapidapi-key", "7d5c9a32c4mshdc33227ae9c206dp122229jsn0cb5b61f286a")
                   .header("x-rapidapi-host", "chatgpt-best-price.p.rapidapi.com")
                   .header("Content-Type", "application/json")
                   .method("POST", HttpRequest.BodyPublishers.ofString("{\"model\":\"gpt-3.5-turbo\",\"messages\":[{\"role\":\"user\",\"content\":\""+message+"\"}]}"))
                   .build();
           HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
           JSONObject jsonObject = new JSONObject(response.body());
           JSONArray choicesArray = jsonObject.getJSONArray("choices");
           JSONObject firstChoice = choicesArray.getJSONObject(0);
           JSONObject messageObject = firstChoice.getJSONObject("message");
           String content = messageObject.getString("content");
           return  content;
          }catch (Exception e){
           e.printStackTrace();
       }
       return "no response";
    }
}
