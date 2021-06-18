public class Option {
    String description;
    String result;
    int deltaHealth, deltaFame, deltaMoney, deltaLoyalty, deltaMana, deltaLuck;

    boolean magic = false;
    int item = -1;
    WorldEvent nextEvent = null;

    public Option(String description, String result, int deltaHealth, int deltaFame, int deltaMoney, int deltaLoyalty, int deltaMana, int deltaLuck) {
        this.description = description;
        this.result = result;
        this.deltaHealth = deltaHealth;
        this.deltaFame = deltaFame;
        this.deltaMoney = deltaMoney;
        this.deltaLoyalty = deltaLoyalty;
        this.deltaMana = deltaMana;
        this.deltaLuck = deltaLuck;
    }

    public void setMagic(boolean magic) {
        this.magic = magic;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public void setNextEvent(WorldEvent nextEvent) {
        this.nextEvent = nextEvent;
    }

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
        Hero.mana += deltaMana;
        if (Hero.mana < 0) Hero.mana = 0;
        else if (Hero.mana > 100) Hero.mana = 100;
        Hero.luck += deltaLuck;
        if (Hero.luck < 0) Hero.luck = 0;
        else if (Hero.luck > 100) Hero.luck = 100;

        if(item!=-1) Hero.artefacts[item]=true;
    }
}
