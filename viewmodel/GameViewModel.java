package tictactoe.viewmodel;

import tictactoe.model.GameModel;

public class GameViewModel {
    private GameModel model = new GameModel();

    public void makeMove(int index, String player) {
        model.setCell(index, player);
    }

    public String checkWinner() {
        return model.checkWinner();
    }

    public boolean isGameOver() {
        return model.isBoardFull() || !checkWinner().equals("");
    }
}
