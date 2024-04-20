package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

class ClientHandler implements Runnable {
    Map<String, String> dictionary = new HashMap<>();

    private final Socket clientSocket;

    public ClientHandler(Socket socket, Map<String, String> dictionary) {
        this.clientSocket = socket;
        this.dictionary = dictionary;
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