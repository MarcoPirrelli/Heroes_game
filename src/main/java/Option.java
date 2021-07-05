/**
 * Small class to represent options that can be picked as a response to a game event.
 */
public class Option {
    String description;
    String result;

    boolean magic = false;
    int nextId = 0;
    int deltaHealth, deltaFame, deltaMoney, deltaLoyalty, deltaMana, deltaLuck;
    int[] deltaArtefacts = new int[Hero.artefacts.length];


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
        this.description = description;
        this.result = result;
        this.deltaHealth = deltaHealth;
        this.deltaFame = deltaFame;
        this.deltaMoney = deltaMoney;
        this.deltaLoyalty = deltaLoyalty;
        this.deltaMana = deltaMana;
        this.deltaLuck = deltaLuck;

        for (int i = 0; i < Hero.artefacts.length; i++) {
            deltaArtefacts[i] = 0;
        }
    }

    /**
     * Sets whether an option is for mages only.
     *
     * @param magic true if the option requires a wand
     */
    public void setMagic(boolean magic) {
        this.magic = magic;
    }

    /**
     * Sets the specified action for the item
     * 1 = set to true
     * 0 = no change
     * -1 = set to false
     *
     * @param item   Item (artefact) to be modified.
     * @param action int (-1, 0 , 1)
     */
    public void setItem(int item, int action) {
        if (item >= Hero.artefacts.length || action > 1 || action < -1) {
            return;
        }
        deltaArtefacts[item] = action;
    }

    /**
     * When this option is picked the next game event will be the specified one.
     *
     * @param nextId The next event that will happen
     */
    public void setNextEvent(int nextId) {
        this.nextId = nextId;
    }

    /**
     * Updates the hero's statistics as a consequence of picking this option.
     * Mana is only updated if the hero has the wand.
     * Applies health loss due to aging.
     * Applies the effects of the enchanted scale.
     */
    public void pick() {
        Hero.addHealth(deltaHealth);
        Hero.addFame(deltaFame);
        Hero.addMoney(deltaMoney);
        Hero.addLoyalty(deltaLoyalty);
        if (Hero.hasWand())
            Hero.addMana(deltaMana);
        Hero.addLuck(deltaLuck);

        Hero.addHealth(-(Hero.age / 50 + 1));

        if (Hero.hasScale()) {
            int min = 101;
            int max = -1;
            int minIndex = 0;
            int maxIndex = 0;
            for (int i = 0; i < Hero.stats.length; i++) {
                if (i == 4 && !Hero.hasWand()) continue;
                if (Hero.stats[i] < min) {
                    minIndex = i;
                    min = Hero.stats[i];
                }
                if (Hero.stats[i] > max) {
                    maxIndex = i;
                    max = Hero.stats[i];
                }
            }
            Hero.stats[minIndex]++;
            Hero.stats[maxIndex]--;
        }

        if (Hero.getHealth() < 0) Hero.setHealth(0);
        else if (Hero.getHealth() > 100) Hero.setHealth(100);

        if (Hero.getFame() < 0) Hero.setFame(0);
        else if (Hero.getFame() > 100) Hero.setFame(100);

        if (Hero.getMoney() < 0) Hero.setMoney(0);
        else if (Hero.getMoney() > 100) Hero.setMoney(100);

        if (Hero.getLoyalty() < 0) Hero.setLoyalty(0);
        else if (Hero.getLoyalty() > 100) Hero.setLoyalty(100);

        if (Hero.getMana() < 0) Hero.setMana(0);
        else if (Hero.getMana() > 100) Hero.setMana(100);

        if (Hero.getLuck() < 0) Hero.setLuck(0);
        else if (Hero.getLuck() > 20) Hero.setLuck(20);

        for (int i = 0; i < Hero.artefacts.length; i++) {
            if (deltaArtefacts[i] == 1)
                Hero.artefacts[i] = true;
            else if (deltaArtefacts[i] == -1)
                Hero.artefacts[i] = false;
        }
    }
}
