package fightfriends.friends;

public class FriendFactory {

    public Friend createBear(String name, int level) {
        return new Bear(name, level);
    }

    public Friend createBear(String name) {
        return new Bear(name);
    }

    public Friend createBear(String name, double health, double attack, double speed) {
        return new Bear(name, health, attack, speed);
    }

    public Friend createBear(String name, double health, double attack, double speed, int level) {
        return new Bear(name, health, attack, speed, level);
    }

    public Friend createElephant(String name, int level) {
        return new Elephant(name, level);
    }

    public Friend createElephant(String name) {
        return new Elephant(name);
    }

    public Friend createElephant(String name, double health, double attack, double speed) {
        return new Elephant(name, health, attack, speed);
    }

    public Friend createElephant(String name, double health, double attack, double speed, int level) {
        return new Elephant(name, health, attack, speed, level);
    }

    public Friend createFox(String name, int level) {
        return new Fox(name, level);
    }

    public Friend createFox(String name) {
        return new Fox(name);
    }

    public Friend createFox(String name, double health, double attack, double speed) {
        return new Fox(name, health, attack, speed);
    }

    public Friend createFox(String name, double health, double attack, double speed, int level) {
        return new Fox(name, health, attack, speed, level);
    }

    public Friend createWolf(String name, int level) {
        return new Wolf(name, level);
    }

    public Friend createWolf(String name) {
        return new Wolf(name);
    }

    public Friend createWolf(String name, double health, double attack, double speed) {
        return new Wolf(name, health, attack, speed);
    }

    public Friend createWolf(String name, double health, double attack, double speed, int level) {
        return new Wolf(name, health, attack, speed, level);
    }
}
