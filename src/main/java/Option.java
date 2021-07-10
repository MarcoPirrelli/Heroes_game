/**
 * Small class to represent options that can be picked as a response to a game event.
 */
public class Option extends AbstractOption {
    /**
     * Event option constructor.
     *
     * @param description  Short string
     * @param result       String
     * @param deltaHealth  Amount added if this option is picked
     * @param deltaFame    Amount added if this option is picked
     * @param deltaMoney   Amount added if this option is picked
     * @param deltaLoyalty Amount added if this option is picked
     * @param deltaMana    Amount added if this option is picked
     * @param deltaLuck    Amount added if this option is picked
     */
    public Option(String description, String result, int deltaHealth, int deltaFame, int deltaMoney, int deltaLoyalty, int deltaMana, int deltaLuck) {
        super(description, result, deltaHealth, deltaFame, deltaMoney, deltaLoyalty, deltaMana, deltaLuck);
    }

    /**
     * This function should apply all the effects of picking this special option.
     */
    @Override
    public void pick() {
        defaultPick();
    }

}
