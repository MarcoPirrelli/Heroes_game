public class Option {
    String description;
    String result;
    int deltaHealth, deltaFame, deltaMoney, deltaLoyalty, deltaMana, deltaLuck;
    Object item;

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

    public Option(String description, String result, int deltaHealth, int deltaFame, int deltaMoney, int deltaLoyalty, int deltaMana, int deltaLuck, Object item) {
        this.description = description;
        this.result = result;
        this.deltaHealth = deltaHealth;
        this.deltaFame = deltaFame;
        this.deltaMoney = deltaMoney;
        this.deltaLoyalty = deltaLoyalty;
        this.deltaMana = deltaMana;
        this.deltaLuck = deltaLuck;
        this.item = item;
    }
}
