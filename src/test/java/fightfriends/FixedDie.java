package fightfriends;

public class FixedDie implements Die {
    private final int value;

    public FixedDie(int value) {
        this.value = value;
    }

    @Override
    public int roll() {
        return value;
    }
}
