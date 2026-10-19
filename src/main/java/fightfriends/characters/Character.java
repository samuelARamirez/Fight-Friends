package fightfriends.characters;

abstract public class Character {
    private final String name;

    public Character(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString(){
        return name;
    }

    public boolean isTrainer(){
        return false;
    }

    public boolean isMerchant(){
        return false;
    }

    public boolean isPlayer(){
        return false;
    }
}

