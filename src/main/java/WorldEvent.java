/**
 * Small class to represent game events.
 */
public class WorldEvent {
    String description;
    AbstractOption[] options = new AbstractOption[4];
    int baseWeight;
    double healthWeight, fameWeight, moneyWeight, loyaltyWeight; //weight per point

    /**
     * Construct for WorldEvent.
     * Requires values to be used for random generation.
     *
     * @param baseWeight    Int. Base weight value
     * @param healthWeight  Double. Multiplied with health
     * @param fameWeight    Double. Multiplied with fame
     * @param moneyWeight   Double. Multiplied with money
     * @param loyaltyWeight Double. Multiplied with loyalty
     */
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
     * @return Non-negative int
     */
    public int fullWeight() {
        int fw = baseWeight + (int) (Hero.getHealth() * healthWeight + Hero.getFame() * fameWeight + Hero.getMoney() * moneyWeight + Hero.getLoyalty() * loyaltyWeight);
        if (!Hero.hasCurse())
            fw += Hero.luck;
        return Math.max(fw, 0);
    }

    /**
     * Sets the event description.
     *
     * @param description String (could be long)
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Sets one of the options.
     *
     * @param n              Which option will be set
     * @param abstractOption Option object
     */
    public void setOption(int n, AbstractOption abstractOption) {
        options[n] = abstractOption;
    }
}
