import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TimerTask;

public class Hero {
    static String name;
    static int age;
    static int yearsOfService;

    static int[] stats = new int[5];
    static int luck;

    static boolean[] artefacts = new boolean[3];
    final static int WAND = 0;
    final static int CURSE = 1;
    final static int SCALE = 2;


    /**
     * Resets the hero's statistics and generates a new random name.
     * Should be called when a new game is started.
     */
    public static void reset() {
        //String[] names = new String[]{"Certosino", "Guglielmo", "Tenebroso", "Kight", "Hero", "Ben", "John", "Mikela", "Sister Graziana"};
        String[] names = new String[]{"Certosino", "Guglielmo"};
        Random rand = new Random();
        name = names[rand.nextInt(names.length)];

        int max = 35;
        int min = 13;
        age = rand.nextInt(max - min + 1) + min;
        yearsOfService = 0;

        stats[0] = 80; //health
        stats[1] = 50; //fame
        stats[2] = 50; //loyalty
        stats[3] = 50; //money
        stats[4] = 50; //mana
        luck = 10;
        Arrays.fill(artefacts, false);
    }

    /**
     * Current hero's name.
     *
     * @return String with the name
     */
    public static String getHeroName() {
        return name;
    }

    public static int getAge() {
        return age;
    }

    public static int getYearsOfService() {
        return yearsOfService;
    }

    public static int getHealth() {
        return stats[0];
    }

    public static int getFame() {
        return stats[1];
    }

    public static int getLoyalty() {
        return stats[2];
    }

    public static int getMoney() {
        return stats[3];
    }

    public static int getMana() {
        return stats[4];
    }

    public static int getLuck() {
        return luck;
    }

    public static boolean hasWand() { return artefacts[WAND]; }

    public static boolean hasCurse() {
        return artefacts[CURSE];
    }


    public static boolean hasScale() {
        return artefacts[SCALE];
    }

    public static void setHealth(int health) {
        stats[0] = health;
    }

    public static void setFame(int fame) {
        stats[1] = fame;
    }

    public static void setLoyalty(int loyalty) {
        stats[2] = loyalty;
    }

    public static void setMoney(int money) {
        stats[3] = money;
    }

    public static void setMana(int mana) {
        stats[4] = mana;
    }

    public static void setLuck(int luck) {
        Hero.luck = luck;
    }

    public static void addHealth(int health) {
        stats[0] += health;
    }

    public static void addFame(int fame) {
        stats[1] += fame;
    }

    public static void addLoyalty(int loyalty) {
        stats[2] += loyalty;
    }

    public static void addMoney(int money) {
        stats[3] += money;
    }

    public static void addMana(int mana) {
        stats[4] += mana;
    }

    public static void addLuck(int luck) {
        Hero.luck += luck;
    }

}
