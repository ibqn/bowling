package ibqn.bowling.game;

public class BowlingRoll {
    public static final int MAX_NUMBER_OF_PINS = 10;

    private int pins;

    public BowlingRoll(int pins) throws BowlingGameException {
        if (pins < 0 || pins > MAX_NUMBER_OF_PINS) {
            throw new BowlingGameException("Roll should contain number of pins between 0 and 10");
        }
        this.pins = pins;
    }

    public int getNumberOfPins() {
        return pins;
    }

    public boolean isStrike() {
        return pins == MAX_NUMBER_OF_PINS;
    }

}
