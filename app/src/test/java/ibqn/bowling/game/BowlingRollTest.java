package ibqn.bowling.game;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("A Bowling Roll")
public class BowlingRollTest {

    @Test
    @DisplayName("should throw a bowling game exception if number of pins is negative")
    void addInvalidNegativeNumberOfPinsToRoll() {
        int invalidPinsNumber = -1;

        Assertions.assertThrows(BowlingGameException.class, () -> new BowlingRoll(invalidPinsNumber));
    }

    @Test
    @DisplayName("should throw a bowling game exception if number of pins is invalid")
    void addInvalidPositiveNumberOfPinsToRoll() {
        int invalidPinsNumber = 15;

        Assertions.assertThrows(BowlingGameException.class, () -> new BowlingRoll(invalidPinsNumber));
    }

    @Test
    @DisplayName("should set number of pins in a roll successfully")
    void setValidNumberOfPinsToRoll() throws BowlingGameException {
        int validPinsNumber = 7;

        BowlingRoll roll = new BowlingRoll(validPinsNumber);
        Assertions.assertEquals(validPinsNumber, roll.getNumberOfPins());
    }

    @Test
    @DisplayName("should set minimal number of pins in a roll successfully")
    void setValidMinimalNumberOfPinsToRoll() throws BowlingGameException {
        int minimalPinsNumber = 0;

        BowlingRoll roll = new BowlingRoll(minimalPinsNumber);
        Assertions.assertEquals(minimalPinsNumber, roll.getNumberOfPins());
    }

    @Test
    @DisplayName("should set maximal number of pins in a roll successfully")
    void setValidMaximalNumberOfPinsToRoll() throws BowlingGameException {
        int maximalPinsNumber = 10;

        BowlingRoll roll = new BowlingRoll(maximalPinsNumber);
        Assertions.assertEquals(maximalPinsNumber, roll.getNumberOfPins());
    }

}
