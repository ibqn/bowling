package ibqn.bowling;

import ibqn.bowling.controller.GameController;
import ibqn.bowling.game.BowlingGame;

public class App {
    private GameController controller;
    private BowlingGame game;

    public App() {
        game = new BowlingGame();
        controller = new GameController(game);
    }

    public void run() {
        controller.welcomeMessage();

        while (!game.isGameComplete()) {
            controller.showGameProgress();
            controller.requestGameInput();
        }
        controller.showGameProgress();
    }

    public static void main(String[] args) {
        App app = new App();
        app.run();

    }
}
