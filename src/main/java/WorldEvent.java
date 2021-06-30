public class WorldEvent {
    String description;
    Option[] options = new Option[4];
    int baseWeight;
    double healthWeight, fameWeight, moneyWeight, loyaltyWeight; //weight per point

    public WorldEvent(int baseWeight, double healthWeight, double fameWeight, double moneyWeight, double loyaltyWeight) {
        this.baseWeight = baseWeight;
        this.healthWeight = healthWeight;
        this.fameWeight = fameWeight;
        this.moneyWeight = moneyWeight;
        this.loyaltyWeight = loyaltyWeight;
    }

    /**
     * Returns the weight of an event to be used for random generation.
     * Is affected by hero statistics and the type of event.
     *
     * @return positive int
     */
    public int fullWeight() {
        int fw = baseWeight + (int) (Hero.getHealth() * healthWeight + Hero.getFame() * fameWeight + Hero.getMoney() * moneyWeight + Hero.getLoyalty() * loyaltyWeight);
        if(!Hero.hasCurse())
            fw += Hero.luck;
        return Math.max(fw, 0);
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOption(int n, Option option) {
        options[n] = option;
    }
}
