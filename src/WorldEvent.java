public class WorldEvent {

    int number;
    String description;
    Option[] options = new Option[4];
    int baseWeight;
    double healthWeight, fameWeight, moneyWeight, loyaltyWeight; //weight per point

    int opp;

    public WorldEvent(int baseWeight, int healthWeight, int fameWeight, int moneyWeight, int loyaltyWeight) {
        this.baseWeight = baseWeight;
        this.healthWeight = healthWeight;
        this.fameWeight = fameWeight;
        this.moneyWeight = moneyWeight;
        this.loyaltyWeight = loyaltyWeight;
    }

    public int fullWeight() {
        return baseWeight + (int)(Hero.health*healthWeight + Hero.fame*fameWeight + Hero.money*moneyWeight + Hero.loyalty*loyaltyWeight + Hero.luck);
    }
    public void setNumber (int number) {
        this.number = number;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOption(int n, Option option){
        options[n]= option;
    }

    public int getNumber () {
        return number;
    }

}
