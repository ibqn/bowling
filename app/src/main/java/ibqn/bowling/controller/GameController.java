package ibqn.bowling.controller;

import java.util.List;
import java.util.Scanner;

import ibqn.bowling.game.BowlingFrame;
import ibqn.bowling.game.BowlingGame;
import ibqn.bowling.game.BowlingGameException;
import ibqn.bowling.game.BowlingRoll;

public class GameController {

    private Scanner scanner;
    private BowlingGame game;

    public GameController(BowlingGame game) {
        this.scanner = new Scanner(System.in);
        this.game = game;
    }

    public void welcomeMessage() {
        System.out.println("Welcome to Bowling game");
        System.out.println("===============================================================");
        System.out.println();
    }

    public void requestGameInput() {
        int currentFrame = game.getCurrentFrameNumber();
        int numberOfAvailablePins = game.getNumberOfAvailablePins();

        System.out.print("Please enter a roll for frame " + currentFrame + " between '0' and '" + numberOfAvailablePins
                + "' or 'q' to quit: ");

        String input = requestUserInput();

        switch (input) {
            case "q":
                System.out.println("Buy!");
                System.exit(0);
            default:
                try {
                    int rolledPins = Integer.parseInt(input);
                    game.addRoll(rolledPins);
                } catch (BowlingGameException bowlingException) {
                    System.out.println("Invalid input: " + bowlingException.getMessage());
                } catch (NumberFormatException ignored) {
                    System.out.println("Invalid input. Entered: '" + input + "'");
                }
        }
    }

    String requestUserInput() {
        return scanner.nextLine();
    }

    public void showGameProgress() {
        System.out.println();
        System.out.println("+-------------------------------------------------------------+");
        System.out.println("|  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |  10   |");
        System.out.println("+-----+-----+-----+-----+-----+-----+-----+-----+-----+-------+");

        List<BowlingFrame> frames = game.getFrames();

        showRollsInFrames(frames);

        showScoreInFrames(frames);

        System.out.println();
    }

    private void showRollsInFrames(List<BowlingFrame> frames) {
        System.out.print("| ");
        int frameCount = 0;
        for (BowlingFrame frame : frames) {
            List<BowlingRoll> rolls = frame.getRolls();

            var frameRolls = new StringBuilder();

            int rollCount = 0;
            for (BowlingRoll roll : rolls) {
                String rollScore = String.format("%d", roll.getNumberOfPins());

                if (roll.isStrike()) {
                    rollScore = "X";
                }

                if (frame.isSpare() && rollCount == 1) {
                    rollScore = "/";
                }

                rollCount++;
                frameRolls.append(rollScore);
                frameRolls.append(" ");
            }

            String formattedFrameRolls = frameRolls.toString();

            if (frame.isComplete()) {
                formattedFrameRolls = appendLeftPadding(formattedFrameRolls, 4);
            } else {
                formattedFrameRolls = appendRightPadding(formattedFrameRolls, 4);
            }

            if (frame.isLastFrame()) {
                formattedFrameRolls = appendRightPadding(formattedFrameRolls, 6);
            }

            System.out.print(formattedFrameRolls);
            System.out.print("| ");

            frameCount++;
        }

        appendRightBorderIfNeeded(frameCount);

        showSeparator();
    }

    private String appendLeftPadding(String string, int padding) {
        String format = String.format("%%%ds", padding);
        return String.format(format, string);
    }

    private String appendRightPadding(String string, int padding) {
        String format = String.format("%%-%ds", padding);
        return String.format(format, string);
    }

    private void appendRightBorderIfNeeded(int frameCount) {
        if (frameCount >= BowlingGame.NUMBER_OF_FRAMES) {
            System.out.println();
            return;
        }

        var paddingWithBorder = new StringBuilder();
        for (int frame = frameCount; frame < BowlingGame.NUMBER_OF_FRAMES; frame++) {
            paddingWithBorder.append(appendLeftPadding("", 6));
        }
        paddingWithBorder.append("|");

        System.out.println(paddingWithBorder.toString());
    }

    private void showScoreInFrames(List<BowlingFrame> frames) {
        System.out.print("| ");
        boolean haveScore = true;
        int totalScore = 0;
        int frameCount = 0;
        for (BowlingFrame frame : frames) {
            if (haveScore && frame.haveScore()) {
                try {
                    totalScore += frame.getScore();
                    String frameScore = String.format("%-4d", totalScore);

                    System.out.print(frameScore);
                } catch (BowlingGameException ignored) {
                }
            } else {
                haveScore = false;
                System.out.print("    ");
            }

            if (frame.isLastFrame()) {
                System.out.print("  ");
            }
            System.out.print("| ");
            frameCount++;
        }

        appendRightBorderIfNeeded(frameCount);

        showSeparator();
    }

    private void showSeparator() {
        System.out.println("+-------------------------------------------------------------+");
    }

}
