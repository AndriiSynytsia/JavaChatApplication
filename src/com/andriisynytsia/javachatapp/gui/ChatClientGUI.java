package com.andriisynytsia.javachatapp.gui;

import com.andriisynytsia.javachatapp.client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private JTextPane textPane;
    private ChatClient client;
    private JButton exitButton;
    private static final String TITLE = "Chat Application";

    public ChatClientGUI() {
        super(TITLE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        Color backgroundColor = new Color(240, 240, 240);
        Color buttonColor = new Color(75,75,75);
        Color textColor = new Color(50,50,50);
        Font textFont = new Font("Arial", Font.PLAIN, 14);
        Font buttonFont = new Font("Arial", Font.BOLD, 12);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageArea.setBackground(backgroundColor);
        messageArea.setForeground(textColor);
        messageArea.setFont(textFont);
        JScrollPane scrollPane = new JScrollPane(messageArea);
        add(scrollPane, BorderLayout.CENTER);

        // Username entry through dialog box
        String name = JOptionPane.showInputDialog(this, "Enter your name:", "Name Entry", JOptionPane.PLAIN_MESSAGE);
        this.setTitle(TITLE + " - " + name);

        // Modified user message with timestamp and assigned username
        textField = new JTextField();
        textField.setFont(textFont);
        textField.setBackground(backgroundColor);
        textField.setForeground(textColor);
        textField.addActionListener(e -> {
            String timestamp = "[" + new SimpleDateFormat("HH:mm:ss").format(new Date()) + "] " + name + ": ";
            String message = timestamp + textField.getText();
            client.sendMessage(message);
            textField.setText("");
        });

        // Exit button
        exitButton = new JButton("Exit");
        exitButton.setBackground(buttonColor);
        exitButton.setFont(buttonFont);
        exitButton.setForeground(Color.WHITE);
        exitButton.addActionListener(e -> {
            // Initialized departure message
            String departureMessage = name + " has left the chat.";
            client.sendMessage(departureMessage);

            // Delayed exit to ensure that message has been sent
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            // Exit the application
            System.exit(0);
        });

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(textField, BorderLayout.CENTER);
        bottomPanel.add(exitButton, BorderLayout.EAST);
        add(bottomPanel, BorderLayout.SOUTH);

        try {
            this.client = new ChatClient("127.0.0.1", 3000, this::onMessageReceived);
            client.startClient();
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error connection to the server", "Connection error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    private void onMessageReceived(String message) {
        SwingUtilities.invokeLater(() -> messageArea.append(message + "\n"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ChatClientGUI().setVisible(true);
        });

        SwingUtilities.invokeLater(() -> {
            new ChatClientGUI().setVisible(true);
        });
    }
}
