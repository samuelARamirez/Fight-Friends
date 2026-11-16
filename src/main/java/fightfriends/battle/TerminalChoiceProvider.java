package fightfriends.battle;

import fightfriends.friends.Friend;
import fightfriends.strategy.*;
import java.io.PrintStream;
import java.util.Random;
import java.util.Scanner;
import java.util.NoSuchElementException;

public class TerminalChoiceProvider implements ChoiceProvider {
    private final Scanner input;
    private final PrintStream output;
    private final Random random;

    public TerminalChoiceProvider(Scanner input, PrintStream output, Random random) {
        this.input = input;
        this.output = output;
        this.random = random;
    }

    @Override
    public BattleStrategy chooseStrategy(Battle battle, Friend friend) {
        while (true) {
            output.println("1. Normal | 2. Aggressive | 3. Defensive | 4. Run");
            output.print("Choose for " + friend.getName() + " (1-4): ");
            output.flush();
            if (!input.hasNextLine()) {
                throw new NoSuchElementException("Terminal input closed");
            }
            switch (input.nextLine().trim()) {
                case "1": return new NormalStrategy();
                case "2": return new AggressiveStrategy();
                case "3": return new DefensiveStrategy();
                case "4": return new RunStrategy(random);
                default: output.println("Please enter 1, 2, 3, or 4.");
            }
        }
    }
}
