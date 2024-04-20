package org.example;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;

public class JsonReader {

    public static Map<String, String> loadDictionaryFromJson() {
        Map<String, String> dictionary = new HashMap<>();

        InputStream inputStream = App.class.getResourceAsStream("/wordDictonary.json");

        if (inputStream == null) {
            System.err.println("File not found!");
            System.exit(1);
        }

        Scanner scanner = new Scanner(inputStream);
        StringBuilder jsonBuilder = new StringBuilder();

        while (scanner.hasNextLine()) {
            jsonBuilder.append(scanner.nextLine());
        }

        JSONObject jsonObject = new JSONObject(jsonBuilder.toString());
        JSONArray data = jsonObject.getJSONArray("data");

        data.forEach(item -> {
            JSONObject wordObj = (JSONObject) item;
            String word = wordObj.getString("word");
            String meaning = wordObj.getString("meaning");
            dictionary.put(word, meaning);
        });

        scanner.close();
        return dictionary;
    }
}
