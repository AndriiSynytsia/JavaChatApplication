package com.andriisynytsia.javachatapp.gui;

import com.andriisynytsia.javachatapp.client.ChatClient;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class ChatClientGUI extends JFrame {
    private JTextArea messageArea;
    private JTextField textField;
    private ChatClient client;

    public ChatClientGUI() {
        super("Chat Application");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getRootPane().putClientProperty("apple.awt.brushMetalLook", true);
        getRootPane().putClientProperty("apple.awt.transparentTitleBar", true);

        setSize(400, 500);
        setLocationRelativeTo(null);
        setVisible(true);

        messageArea = new JTextArea();
        messageArea.setEditable(false);
        add(new JScrollPane(messageArea), BorderLayout.CENTER);

        textField = new JTextField();
        textField.addActionListener(e -> {
            client.sendMessage(textField.getText());
            textField.setText("");
        });
        add(textField, BorderLayout.SOUTH);

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
    }
}
