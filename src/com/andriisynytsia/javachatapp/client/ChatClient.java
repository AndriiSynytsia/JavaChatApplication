package com.andriisynytsia.javachatapp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class ChatClient {
    private Socket socket = null;
    private BufferedReader inputConsole = null;
    private PrintWriter out = null;
    private BufferedReader in = null;

    public ChatClient(String address, int port) {

        // Connection to the server with specified address and port
        try {
            socket = new Socket(address, port);
            System.out.println("Connected to the chat server");

            // 'inputConsole' reads the messages from console
            inputConsole = new BufferedReader(new InputStreamReader(System.in));
            // 'out' prints the messages to the server side
            out = new PrintWriter(socket.getOutputStream(), true);
            // 'in' reads message from the server
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line = "";

            // Client runs in a loop until client the user write 'exit' to the console
            while (!line.equals("exit")) {
                line = inputConsole.readLine();
                out.println(line);
                System.out.println(in.readLine());
            }

            socket.close();
            inputConsole.close();
            out.close();
        } catch (UnknownHostException e) {
            System.out.println("Host unknown: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Unexpected exception: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ChatClient client = new ChatClient("127.0.0.1", 3000);
    }
}
