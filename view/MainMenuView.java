 package tictactoe.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuView extends JFrame {
    public MainMenuView() {
        setTitle("Tic Tac Toe - Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JButton singlePlayerButton = new JButton("Single Player");
        JButton multiplayerButton = new JButton("Multiplayer");

        singlePlayerButton.addActionListener(e -> {
            dispose();
            new SinglePlayerView();
        });

        multiplayerButton.addActionListener(e -> {
            dispose();
            new MultiplayerLobbyView();
        });

        setLayout(new GridLayout(2, 1, 10, 10));
        add(singlePlayerButton);
        add(multiplayerButton);

        setVisible(true);
    }
}
 
