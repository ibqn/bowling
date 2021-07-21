package ibqn.bowling.game;

import java.util.ArrayList;
import java.util.List;

public class BowlingFrame {

    private Integer bonusPins;
    private boolean lastFrame;
    private ArrayList<BowlingRoll> rolls;

    public BowlingFrame() {
        bonusPins = null;
        lastFrame = false;
        rolls = new ArrayList<>();
    }

    public void setLastFrame() {
        lastFrame = true;
    }

    public boolean isLastFrame() {
        return lastFrame;
    }

    public boolean isStrike() {
        boolean strikeInFirstRoll = rolls.size() != 0 && rolls.get(0).isStrike();
        boolean strikeInThirdRoll = rolls.size() == 3 && rolls.get(2).isStrike();

        return strikeInFirstRoll || strikeInThirdRoll;
    }

    public boolean isSpare() {
        if (rolls.size() >= 2) {
            boolean firstRollIsNotStrike = !rolls.get(0).isStrike();
            boolean clearedAllPinsInTwoRolls = rolls.get(0).getNumberOfPins()
                    + rolls.get(1).getNumberOfPins() == BowlingRoll.MAX_NUMBER_OF_PINS;
            return firstRollIsNotStrike && clearedAllPinsInTwoRolls;
        }

        return false;
    }

    private int getNumberOfPins() {
        return rolls.stream().map(roll -> roll.getNumberOfPins()).reduce(0, Integer::sum);
    }

    public int getNumberOfAvailablePins() {
        int numberOfAvailablePins = BowlingRoll.MAX_NUMBER_OF_PINS - getNumberOfPins();

        while (numberOfAvailablePins <= 0) {
            numberOfAvailablePins += BowlingRoll.MAX_NUMBER_OF_PINS;
        }

        return numberOfAvailablePins;
    }

    public void addRoll(int pins) throws BowlingGameException {
        int numberOfAvailablePins = getNumberOfAvailablePins();

        if (pins > numberOfAvailablePins) {
            throw new BowlingGameException("Impossible number of rolled pins in frame");
        }

        if (isComplete()) {
            throw new BowlingGameException("Impossible number of rolls in frame");
        }

        BowlingRoll newRoll = new BowlingRoll(pins);
        rolls.add(newRoll);

        handleBonusPinsIfPossible();
    }

    public void setBonusPins(int bonusPins) {
        this.bonusPins = bonusPins;
    }

    public boolean haveScore() {
        return bonusPins != null;
    }

    private void handleBonusPinsIfPossible() {
        if (isComplete() && (lastFrame || (!isSpare() && !isStrike()))) {
            bonusPins = 0;
        }
    }

    public int getScore() throws BowlingGameException {
        if (!haveScore()) {
            throw new BowlingGameException("No score available yet");
        }

        int currentNumberOfPins = getNumberOfPins();
        return currentNumberOfPins + bonusPins;
    }

    public List<BowlingRoll> getRolls() {
        return rolls;
    }

    public boolean isComplete() {
        int numberOfRolls = 2;

        if (isStrike()) {
            numberOfRolls = 1;
        }

        if (lastFrame && (isStrike() || isSpare())) {
            numberOfRolls = 3;
        }

        return rolls.size() == numberOfRolls;
    }
}
