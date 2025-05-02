package tictactoe.view;

import javax.swing.*;
import java.awt.*;

public class MultiplayerLobbyView extends JFrame {
    private JTextField nameField;
    private JButton hostButton;
    private JButton joinButton;

    public MultiplayerLobbyView() {
        setTitle("Tic Tac Toe - Multiplayer Lobby");
        setSize(400, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        nameField = new JTextField();
        hostButton = new JButton("Create Game (Host)");
        joinButton = new JButton("Join Game (Client)");

        JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
        panel.add(nameField);
        panel.add(hostButton);
        panel.add(joinButton);

        add(panel);

        hostButton.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                dispose();
                new MultiplayerGameView(playerName, true);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter your name.");
            }
        });

        joinButton.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                dispose();
                new MultiplayerGameView(playerName, false);
            } else {
                JOptionPane.showMessageDialog(null, "Please enter your name.");
            }
        });

        setVisible(true);
    }
}