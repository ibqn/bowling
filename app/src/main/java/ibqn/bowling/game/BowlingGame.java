package ibqn.bowling.game;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BowlingGame {
    public static final int NUMBER_OF_FRAMES = 10;
    private ArrayList<BowlingFrame> frames;
    private int currentFrameIndex;

    public BowlingGame() {
        newGame();
    }

    public void newGame() {
        currentFrameIndex = 0;

        frames = new ArrayList<>();

        for (int frameIndex = 0; frameIndex < NUMBER_OF_FRAMES; frameIndex++) {
            BowlingFrame frame = new BowlingFrame();
            frames.add(frame);
        }
        frames.get(frames.size() - 1).setLastFrame();
    }

    public int getCurrentFrameNumber() {
        return currentFrameIndex + 1;
    }

    public int getNumberOfAvailablePins() {
        BowlingFrame currentFrame = frames.get(currentFrameIndex);
        return currentFrame.getNumberOfAvailablePins();
    }

    public List<BowlingFrame> getFrames() {
        int numberOfActiveFrames = currentFrameIndex + 1;
        return this.frames.stream().limit(numberOfActiveFrames).collect(Collectors.toList());
    }

    public void addRoll(int rolledPins) throws BowlingGameException {
        if (isGameComplete()) {
            throw new BowlingGameException("This game is already completed");
        }

        BowlingFrame currentFrame = frames.get(currentFrameIndex);

        currentFrame.addRoll(rolledPins);

        if (currentFrame.isComplete() && currentFrameIndex < frames.size() - 1) {
            currentFrameIndex++;
        }

        handleScoreIfPossible();
    }

    private void handleScoreIfPossible() {
        int frameIndex = 0;

        for (BowlingFrame frame : frames) {
            if (!frame.isComplete() || frame.isLastFrame()) {
                return;
            }

            if (!frame.haveScore()) {
                handleScoreForFrameIndex(frameIndex);
            }

            frameIndex++;
        }
    }

    private void handleScoreForFrameIndex(int frameIndex) {
        int rollCount = 0;

        BowlingFrame currentFrame = frames.get(frameIndex);

        if (currentFrame.isStrike()) {
            rollCount = 2;
        } else if (currentFrame.isSpare()) {
            rollCount = 1;
        }

        int bonusPins = 0;

        for (int nextFrameIndex = frameIndex + 1; rollCount > 0; nextFrameIndex++) {
            if (nextFrameIndex > frames.size() - 1) {
                return;
            }

            BowlingFrame nextFrame = frames.get(nextFrameIndex);
            List<BowlingRoll> rolls = nextFrame.getRolls();
            for (BowlingRoll roll : rolls) {
                bonusPins += roll.getNumberOfPins();
                rollCount--;
            }
        }

        currentFrame.setBonusPins(bonusPins);
    }

    public boolean isGameComplete() {
        int lastFrameIndex = frames.size() - 1;

        BowlingFrame lastFrame = frames.get(lastFrameIndex);

        return lastFrame.isComplete();
    }

    public int getScoreInFrame(int frameIndex) throws BowlingGameException {
        int score = 0;

        if (frameIndex < 1 || frameIndex > NUMBER_OF_FRAMES) {
            throw new BowlingGameException("Invalid frame index is specified");
        }

        int currentFrameIndex = 0;
        for (BowlingFrame frame : frames) {
            if (currentFrameIndex >= frameIndex) {
                break;
            }

            if (!frame.haveScore()) {
                throw new BowlingGameException("No Score available yet");
            }

            score += frame.getScore();

            currentFrameIndex++;
        }

        return score;

    }

    public int getScore() throws BowlingGameException {
        return getScoreInFrame(NUMBER_OF_FRAMES);
    }
}
