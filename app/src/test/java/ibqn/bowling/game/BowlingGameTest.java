package ibqn.bowling.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A Bowling Game")
public class BowlingGameTest {

    BowlingGame game;

    @BeforeEach
    void setUp() {
        game = new BowlingGame();
    }

    @Test
    @DisplayName("should handle a game with just zeros correctly")
    public void handleGameWithZerosCorrectly() throws BowlingGameException {
        for (int i = 0; i < 20; i++) {
            game.addRoll(0);
        }

        Assertions.assertEquals(true, game.isGameComplete());

        Assertions.assertEquals(0, game.getScore());
    }

    @Test
    @DisplayName("should handle a perfect game correctly")
    public void handlePerfectGameCorrectly() throws BowlingGameException {
        for (int i = 0; i < 12; i++) {
            game.addRoll(10);
        }

        Assertions.assertEquals(true, game.isGameComplete());

        Assertions.assertEquals(300, game.getScore());
    }

    @Test
    @DisplayName("should handle exemplary game correctly")
    public void handleExemplaryGameCorrectly() throws BowlingGameException {
        runExemplaryGame();

        Assertions.assertEquals(true, game.isGameComplete());

        Assertions.assertEquals(133, game.getScore());
    }

    private void runExemplaryGame() throws BowlingGameException {
        game.addRoll(1);
        game.addRoll(4);

        game.addRoll(4);
        game.addRoll(5);

        game.addRoll(6);
        game.addRoll(4);

        game.addRoll(5);
        game.addRoll(5);

        game.addRoll(10);

        game.addRoll(0);
        game.addRoll(1);

        game.addRoll(7);
        game.addRoll(3);

        game.addRoll(6);
        game.addRoll(4);

        game.addRoll(10);

        game.addRoll(2);
        game.addRoll(8);
        game.addRoll(6);
    }

}
