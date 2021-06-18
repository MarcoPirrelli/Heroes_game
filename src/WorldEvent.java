public class WorldEvent {
    int num;
    String description;
    Option[] options = new Option[4];
    int baseWeight;
    double healthWeight, fameWeight, moneyWeight, loyaltyWeight; //weight per point

    public WorldEvent(int num, int baseWeight, int healthWeight, int fameWeight, int moneyWeight, int loyaltyWeight) {
        this.num = num;
        this.baseWeight = baseWeight;
        this.healthWeight = healthWeight;
        this.fameWeight = fameWeight;
        this.moneyWeight = moneyWeight;
        this.loyaltyWeight = loyaltyWeight;
    }

    public int fullWeight() {
        return baseWeight + (int) (Hero.health * healthWeight + Hero.fame * fameWeight + Hero.money * moneyWeight + Hero.loyalty * loyaltyWeight + Hero.luck);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOption(int n, Option option) {
        options[n] = option;
    }

}
