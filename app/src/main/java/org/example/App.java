package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.JSONArray;
import org.json.JSONObject;

public class App {
    private static final int PORT = 8888;
    private static Map<String, String> dictionary = new HashMap<>();

    public static void main(String[] args) {
        loadDictionaryFromJson();

        ExecutorService executor = Executors.newFixedThreadPool(10);

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Dictionary Server started on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket);

                executor.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private static void loadDictionaryFromJson() {
        InputStream inputStream = App.class.getResourceAsStream("/league.json");

        if (inputStream == null) {
            System.err.println("File not found!");
            return;
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
            String word = wordObj.getString("champion");
            String meaning = wordObj.getString("role");
            dictionary.put(word, meaning);
        });

        scanner.close();
    }

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        @Override
        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String word;
                while ((word = in.readLine()) != null) {
                    String meaning = dictionary.getOrDefault(word.trim(), "Word not found");
                    out.println(meaning);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                closeSocket();
            }
        }

        private void closeSocket() {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
