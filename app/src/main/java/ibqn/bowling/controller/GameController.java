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

        List<BowlingFrame> frames = this.game.getFrames();

        System.out.print("| ");
        for (BowlingFrame frame : frames) {
            List<BowlingRoll> rolls = frame.getRolls();

            if (rolls.size() == 1 && frame.isComplete()) {
                System.out.print("  ");
            }

            int rollCount = 0;
            for (BowlingRoll roll : rolls) {
                String rollScore = String.format("%d", roll.getNumberOfPins());

                if (roll.isStrike()) {
                    rollScore = "X";
                }

                if (frame.isSpare() && rollCount == 1) {
                    rollScore = "/";
                }
                System.out.printf(rollScore);
                rollCount++;
                System.out.print(" ");

            }

            if (frame.isComplete()) {
                if (frame.isLastFrame() && rolls.size() == 2) {
                    System.out.print("  ");
                }

                System.out.print("| ");
            }

        }
        System.out.println();
        System.out.println("+-------------------------------------------------------------+");

        System.out.print("| ");
        boolean haveScore = true;
        int totalScore = 0;
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
        }

        System.out.println();
        System.out.println("+-------------------------------------------------------------+");
        System.out.println();
    }

}
