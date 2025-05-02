package tictactoe.view;
import tictactoe.viewmodel.GameViewModel;
import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class SinglePlayerView extends JFrame {
    private JButton[] buttons = new JButton[9];
    private GameViewModel viewModel = new GameViewModel();
    private boolean playerTurn = true;

    public SinglePlayerView() {
        setTitle("Tic Tac Toe - Single Player");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 3));
        initBoard();
        setVisible(true);
    }

    private void initBoard() {
        for (int i = 0; i < 9; i++) {
            final int index = i;
            buttons[i] = new JButton("");
            buttons[i].setFont(new Font("Arial", Font.BOLD, 40));
            add(buttons[i]);
            
            buttons[i].addActionListener(e -> {
                if (playerTurn && buttons[index].getText().equals("")) {
                    buttons[index].setText("X");
                    viewModel.makeMove(index, "X");
                    playerTurn = false;
                    checkGameStatus();
                    computerMove();
                }
            });
        }
    }

    private void computerMove() {
        if (viewModel.isGameOver()) return;

        Random rand = new Random();
        int index;
        do {
            index = rand.nextInt(9);
        } while (!buttons[index].getText().equals(""));

        buttons[index].setText("O");
        viewModel.makeMove(index, "O");
        playerTurn = true;
        checkGameStatus();
    }

    private void checkGameStatus() {
        String winner = viewModel.checkWinner();
        if (!winner.equals("") || viewModel.isGameOver()) {
            String message = winner.equals("") ? "It's a draw!" : "Winner: " + winner;
            int option = JOptionPane.showOptionDialog(this, message, "Game Over",
                    JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                    new String[]{"Restart", "Quit"}, "Restart");

            if (option == JOptionPane.YES_OPTION) {
                dispose();
                new MainMenuView();
            } else {
                System.exit(0);
            }
        }
    }
}