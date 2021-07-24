package ibqn.bowling.game;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

@DisplayName("A Bowling Frame")
class BowlingFrameTest {

    BowlingFrame frame;

    @BeforeEach
    void setUp() {
        frame = new BowlingFrame();
    }

    @Nested
    @DisplayName("Normal Frame")
    class NormalFrame {

        @Test
        @DisplayName("should run single roll in frame successfully")
        void runNormalRollInFrame() throws BowlingGameException {
            int pins = 7;

            frame.addRoll(pins);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(1, rolls.size());

            Assertions.assertEquals(false, frame.isStrike());
            Assertions.assertEquals(false, frame.isSpare());

            Assertions.assertNotEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should verify that normal frame is not marked as last frame")
        void verifyNotLastFrame() throws BowlingGameException {

            Assertions.assertNotEquals(true, frame.isLastFrame());
        }

        @Test
        @DisplayName("should run two rolls in frame successfully")
        void runTwoNormalRollsInFrame() throws BowlingGameException {
            int pinsInFirstRoll = 3;
            int pinsInSecondRoll = 5;

            frame.addRoll(pinsInFirstRoll);
            frame.addRoll(pinsInSecondRoll);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(2, rolls.size());

            Assertions.assertNotEquals(true, frame.isStrike());
            Assertions.assertNotEquals(true, frame.isSpare());

            Assertions.assertEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run into exception with three rolls in frame")
        void addInvalidNumberOfRollsInFrame() throws BowlingGameException {
            int pinsInRoll = 1;

            frame.addRoll(pinsInRoll);
            frame.addRoll(pinsInRoll);

            Assertions.assertThrows(BowlingGameException.class, () -> frame.addRoll(pinsInRoll));
        }

        @Test
        @DisplayName("should run strike roll in frame successfully")
        void runStrikeRollInFrame() throws BowlingGameException {
            int pinsInRoll = 10;

            frame.addRoll(pinsInRoll);

            Assertions.assertEquals(true, frame.isStrike());
            Assertions.assertNotEquals(true, frame.isSpare());

            Assertions.assertEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run into exception when adding another roll after strike roll in frame")
        void addInvalidRollAfterStrikeRollToFrame() throws BowlingGameException {
            int pinsInStrikeRoll = 10;
            int anotherRoll = 5;

            frame.addRoll(pinsInStrikeRoll);

            Assertions.assertThrows(BowlingGameException.class, () -> frame.addRoll(anotherRoll));
        }

        @Test
        @DisplayName("should run two rolls as spare in frame successfully")
        void runTwoRollsAsSpareInFrame() throws BowlingGameException {
            int pinsInFirstRoll = 2;
            int pinsInSecondRoll = 8;

            frame.addRoll(pinsInFirstRoll);
            frame.addRoll(pinsInSecondRoll);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(2, rolls.size());

            Assertions.assertNotEquals(true, frame.isStrike());
            Assertions.assertEquals(true, frame.isSpare());

            Assertions.assertEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run into exception when adding invalid number of pins after two rolls in frame")
        void addInvalidNumberOfPinsAfterTwoRollsInFrame() throws BowlingGameException {
            int pinsInFirstRoll = 6;
            int pinsInSecondRoll = 5;

            frame.addRoll(pinsInFirstRoll);

            Assertions.assertThrows(BowlingGameException.class, () -> frame.addRoll(pinsInSecondRoll));
        }

        @Test
        @DisplayName("should run into exception when adding invalid number of pins to a single roll in frame")
        void addInvalidNumberOfPinsForRollInFrame() throws BowlingGameException {
            int invalidNumberOfPins = 12;

            Assertions.assertThrows(BowlingGameException.class, () -> frame.addRoll(invalidNumberOfPins));
        }

    }

    @Nested
    @DisplayName("Last Frame")
    class LastFrame {

        @BeforeEach
        void setUp() {
            frame.setLastFrame();
        }

        @Test
        @DisplayName("should run single roll in last frame successfully")
        void runNormalRollInFrame() throws BowlingGameException {
            int pins = 3;

            frame.addRoll(pins);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(1, rolls.size());

            Assertions.assertEquals(false, frame.isStrike());
            Assertions.assertEquals(false, frame.isSpare());

            Assertions.assertNotEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should verify that last frame is marked as last frame successfully")
        void verifyNotLastFrame() throws BowlingGameException {

            Assertions.assertEquals(true, frame.isLastFrame());
        }

        @Test
        @DisplayName("should run two rolls in last frame successfully")
        void runTwoNormalRollsInFrame() throws BowlingGameException {
            int pinsInFirstRoll = 3;
            int pinsInSecondRoll = 5;

            frame.addRoll(pinsInFirstRoll);
            frame.addRoll(pinsInSecondRoll);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(2, rolls.size());

            Assertions.assertNotEquals(true, frame.isStrike());
            Assertions.assertNotEquals(true, frame.isSpare());

            Assertions.assertEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run three rolls in last frame after strike successfully")
        void runThreeRollsInFrameWithStrike() throws BowlingGameException {
            int pinsInStrikeRoll = 10;
            int pinsInSecondRoll = 5;
            int pinsInThirdRoll = 5;

            frame.addRoll(pinsInStrikeRoll);
            frame.addRoll(pinsInSecondRoll);
            frame.addRoll(pinsInThirdRoll);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(3, rolls.size());

            Assertions.assertEquals(true, frame.isStrike());
            Assertions.assertEquals(true, frame.isSpare());

            Assertions.assertEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run into exception when adding invalid number of pins after two rolls in last frame")
        void addInvalidNumberOfPinsAfterTwoRollsInLastFrame() throws BowlingGameException {
            int pinsInStrikeRoll = 10;
            int pinsInSecondRoll = 5;
            int pinsInThirdRoll = 7;

            frame.addRoll(pinsInStrikeRoll);
            frame.addRoll(pinsInSecondRoll);

            Assertions.assertEquals(BowlingRoll.MAX_NUMBER_OF_PINS - pinsInSecondRoll,
                    frame.getNumberOfAvailablePins());

            Assertions.assertThrows(BowlingGameException.class, () -> frame.addRoll(pinsInThirdRoll));
        }

        @Test
        @DisplayName("should run tree rolls in last frame after spare successfully")
        void runThreeRollsInFrameWithSpare() throws BowlingGameException {
            int pinsInStrikeRoll = 7;
            int pinsInSecondRoll = 3;
            int pinsInThirdRoll = 6;

            frame.addRoll(pinsInStrikeRoll);
            frame.addRoll(pinsInSecondRoll);

            Assertions.assertNotEquals(true, frame.isStrike());
            Assertions.assertEquals(true, frame.isSpare());

            frame.addRoll(pinsInThirdRoll);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(3, rolls.size());

            Assertions.assertEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run into exception with three rolls in last frame without spare or strike")
        void addInvalidNumberOfRollsInFrame() throws BowlingGameException {
            int pinsInRoll = 1;

            frame.addRoll(pinsInRoll);
            frame.addRoll(pinsInRoll);

            Assertions.assertThrows(BowlingGameException.class, () -> frame.addRoll(pinsInRoll));
        }

        @Test
        @DisplayName("should run first strike roll in last frame successfully")
        void runStrikeRollInLastFrame() throws BowlingGameException {
            int pinsInRoll = 10;

            frame.addRoll(pinsInRoll);

            Assertions.assertEquals(BowlingRoll.MAX_NUMBER_OF_PINS, frame.getNumberOfAvailablePins());

            Assertions.assertEquals(true, frame.isStrike());
            Assertions.assertNotEquals(true, frame.isSpare());

            Assertions.assertNotEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run after adding another roll after strike roll in last frame successfully")
        void runSecondRollAfterStrikeRollInLastFrame() throws BowlingGameException {
            int pinsInStrikeRoll = 10;
            int anotherRoll = 5;

            frame.addRoll(pinsInStrikeRoll);
            frame.addRoll(anotherRoll);

            Assertions.assertEquals(BowlingRoll.MAX_NUMBER_OF_PINS - anotherRoll, frame.getNumberOfAvailablePins());

            Assertions.assertEquals(true, frame.isStrike());
            Assertions.assertNotEquals(true, frame.isSpare());

            Assertions.assertNotEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run two rolls as spare in last frame successfully")
        void runTwoRollsAsSpareInLastFrame() throws BowlingGameException {
            int pinsInFirstRoll = 2;
            int pinsInSecondRoll = 8;

            frame.addRoll(pinsInFirstRoll);
            frame.addRoll(pinsInSecondRoll);

            Assertions.assertEquals(BowlingRoll.MAX_NUMBER_OF_PINS, frame.getNumberOfAvailablePins());

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(2, rolls.size());

            Assertions.assertNotEquals(true, frame.isStrike());
            Assertions.assertEquals(true, frame.isSpare());

            Assertions.assertNotEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run three rolls with strikes in last frame successfully")
        void runThreeRollsWithStrikesInLastFrame() throws BowlingGameException {
            int pinsInStrikeRoll = 10;

            frame.addRoll(pinsInStrikeRoll);

            Assertions.assertEquals(BowlingRoll.MAX_NUMBER_OF_PINS, frame.getNumberOfAvailablePins());

            frame.addRoll(pinsInStrikeRoll);

            Assertions.assertEquals(BowlingRoll.MAX_NUMBER_OF_PINS, frame.getNumberOfAvailablePins());

            frame.addRoll(pinsInStrikeRoll);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(3, rolls.size());

            Assertions.assertEquals(true, frame.isStrike());
            Assertions.assertNotEquals(true, frame.isSpare());

            Assertions.assertEquals(true, frame.isComplete());
        }

        @Test
        @DisplayName("should run into exception when adding invalid number of pins after first roll in last frame")
        void addInvalidNumberOfPinsAfterFirstRollInLastFrame() throws BowlingGameException {
            int pinsInFirstRoll = 3;
            int pinsInSecondRoll = 8;

            frame.addRoll(pinsInFirstRoll);

            Assertions.assertEquals(BowlingRoll.MAX_NUMBER_OF_PINS - pinsInFirstRoll, frame.getNumberOfAvailablePins());

            Assertions.assertThrows(BowlingGameException.class, () -> frame.addRoll(pinsInSecondRoll));
        }

        @Test
        @DisplayName("should run when adding pins in second roll after strike in last frame")
        void addSecondRollAfterStrikeInLastFrame() throws BowlingGameException {
            int pinsInFirstRoll = 10;
            int pinsInSecondRoll = 5;

            frame.addRoll(pinsInFirstRoll);

            Assertions.assertEquals(BowlingRoll.MAX_NUMBER_OF_PINS, frame.getNumberOfAvailablePins());

            frame.addRoll(pinsInSecondRoll);

            List<BowlingRoll> rolls = frame.getRolls();

            Assertions.assertEquals(2, rolls.size());

            Assertions.assertEquals(true, frame.isStrike());
            Assertions.assertNotEquals(true, frame.isSpare());

            Assertions.assertNotEquals(true, frame.isComplete());

        }

    }

}
