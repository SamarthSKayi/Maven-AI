package com.example.firstfx.Elements;

import org.json.JSONObject;
import org.json.JSONException;

public class ApiResponseProcessor {

    public static String processResponse(String response) {
        try {
            // Attempt to parse the JSON response
            JSONObject jsonResponse = new JSONObject(response);

            // Extract the relevant data
            String data = jsonResponse.optString("data", "");

            // Look for the index of the first occurrence of the code delimiter ```
            int codeIndex = data.indexOf("```");

            // If the delimiter is found, extract the content up to that point
            if (codeIndex != -1) {
                data = data.substring(0, codeIndex);
            }

            // Remove unwanted characters such as newlines and extra spaces
            data = data.replaceAll("\\s+", " ").trim(); // Replace multiple spaces/newlines with a single space and trim

            return data;

        } catch (JSONException e) {
            // Handle JSON parsing error
            System.err.println("Error parsing JSON: " + e.getMessage());
            return "Error processing response.";
        }
    }

    public static void main(String[] args) {
        // Example usage with valid JSON
        String validJson = "{\"data\":\"Here is some text.\\n its ok```\\npublic class Example {\\n    public static void main(String[] args) {\\n        System.out.println(\\\"Hello, World!\\\");\\n    }\\n}\\n```\"}";
        // Example usage with invalid JSON
        String invalidJson = "{\"data\":\"Here is some text.\\n```\\npublic class Example {\\n    public static void main(String[] args) {\\n        System.out.println(\\\"Hello, World!\\\");\\n    }\\n}\\n```";

        System.out.println("Processed Response (valid JSON):\n" + processResponse(validJson));
        System.out.println("Processed Response (invalid JSON):\n" + processResponse(invalidJson));
    }
}
