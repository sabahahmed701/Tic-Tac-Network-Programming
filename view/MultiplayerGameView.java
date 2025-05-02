package tictactoe.view;

import tictactoe.network.Server;
import tictactoe.network.Client;
import tictactoe.viewmodel.GameViewModel;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MultiplayerGameView extends JFrame {
    private JButton[] buttons = new JButton[9];
    private GameViewModel viewModel = new GameViewModel();
    private boolean myTurn;
    private Server server;
    private Client client;
    private String playerName;
    private boolean isHost;

    public MultiplayerGameView(String playerName, boolean isHost) {
        this.playerName = playerName;
        this.isHost = isHost;

        setTitle("Tic Tac Toe - Multiplayer");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 3));

        initBoard();
        setVisible(true);

        new Thread(() -> {
            try {
                if (isHost) {
                    server = new Server(5000);
                    myTurn = true;
                } else {
                    client = new Client("localhost", 5000);
                    myTurn = false;
                }
                listenForMoves();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void initBoard() {
        for (int i = 0; i < 9; i++) {
            final int index = i;
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            add(buttons[i]);
            buttons[i].addActionListener(e -> {
                if (myTurn && buttons[index].getText().equals("")) {
                    buttons[index].setText(isHost ? "X" : "O");
                    viewModel.makeMove(index, isHost ? "X" : "O");
                    sendMove(index);
                    myTurn = false;
                    checkGameStatus();
                }
            });
        }
    }

    private void sendMove(int index) {
        if (isHost) {
            server.send(String.valueOf(index));
        } else {
            client.send(String.valueOf(index));
        }
    }

    private void listenForMoves() {
        while (true) {
            try {
                String move = isHost ? server.receive() : client.receive();
                int index = Integer.parseInt(move);
                SwingUtilities.invokeLater(() -> {
                    buttons[index].setText(isHost ? "O" : "X");
                    viewModel.makeMove(index, isHost ? "O" : "X");
                    myTurn = true;
                    checkGameStatus();
                });
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
    }

    private void checkGameStatus() {
        String winner = viewModel.checkWinner();
        if (!winner.equals("")) {
            String message = "Winner: " + (winner.equals(isHost ? "X" : "O") ? playerName : "Opponent");
            int option = JOptionPane.showOptionDialog(this, message, "Game Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Restart", "Quit"}, "Restart");

            try {
                if (server != null) server.close();
                if (client != null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (option == JOptionPane.YES_OPTION) {
                dispose();
                new MainMenuView();
            } else {
                System.exit(0);
            }
        }
    }
}
