public class Option {
    String description;
    String result;

    boolean magic = false;
    int nextId = 0;
    int deltaHealth, deltaFame, deltaMoney, deltaLoyalty, deltaMana, deltaLuck;
    int[] deltaArtefacts = new int[Hero.ART_NUM];


    public Option(String description, String result, int deltaHealth, int deltaFame, int deltaMoney, int deltaLoyalty, int deltaMana, int deltaLuck) {
        this.description = description;
        this.result = result;
        this.deltaHealth = deltaHealth;
        this.deltaFame = deltaFame;
        this.deltaMoney = deltaMoney;
        this.deltaLoyalty = deltaLoyalty;
        this.deltaMana = deltaMana;
        this.deltaLuck = deltaLuck;

        for (int i = 0; i < Hero.ART_NUM; i++) {
            deltaArtefacts[i] = 0;
        }
    }

    public void setMagic(boolean magic) {
        this.magic = magic;
    }

    public void setItem(int item, int action) {
        if (item >= Hero.ART_NUM || action > 1 || action < -1) {
            return;
        }
        deltaArtefacts[item] = action;
    }

    public void setNextEvent(int nextId) {
        this.nextId = nextId;
    }

    /**
     * Updates the hero's statistics as a consequence of picking this option.
     * Mana is only updated if the hero has the wand.
     * Luck will not increase above 0 with the curse.
     */
    public void pick() {
        Hero.health += deltaHealth;
        if (Hero.health < 0) Hero.health = 0;
        else if (Hero.health > 100) Hero.health = 100;

        Hero.fame += deltaFame;
        if (Hero.fame < 0) Hero.fame = 0;
        else if (Hero.fame > 100) Hero.fame = 100;

        Hero.money += deltaMoney;
        if (Hero.money < 0) Hero.money = 0;
        else if (Hero.money > 100) Hero.money = 100;

        Hero.loyalty += deltaLoyalty;
        if (Hero.loyalty < 0) Hero.loyalty = 0;
        else if (Hero.loyalty > 100) Hero.loyalty = 100;

        if (Hero.hasWand()) {
            Hero.mana += deltaMana;
            if (Hero.mana < 0) Hero.mana = 0;
            else if (Hero.mana > 100) Hero.mana = 100;
        }

        if (Hero.hasCurse())
            Hero.luck = 0;
        else {
            Hero.luck += deltaLuck;
            if (Hero.luck < 0) Hero.luck = 0;
            else if (Hero.luck > 20) Hero.luck = 20;
        }

        for (int i = 0; i < Hero.ART_NUM; i++) {
            if (deltaArtefacts[i] == 1)
                Hero.artefacts[i] = true;
            else if (deltaArtefacts[i] == -1)
                Hero.artefacts[i] = false;
        }
    }
}
