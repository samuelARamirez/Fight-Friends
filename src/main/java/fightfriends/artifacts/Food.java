package fightfriends.artifacts;

public class Food extends Artifact{
    private final double nutritionValue;

    public Food(String name, double nutritionValue){
        super(name);
        this.nutritionValue = nutritionValue;
    }

    public double getNutrition(){
        return this.nutritionValue;
    }

    @Override
    public String toString(){
        return String.format("%s ({%.2f})", this.getName(), nutritionValue);
    }
}
