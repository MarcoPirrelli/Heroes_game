import java.util.Random;

public class Hero {
    static String name;
    static int health, fame, money, loyalty;
    static int mana;
    static int luck;
    static boolean[] artefacts = new boolean[2];

    final static int WAND = 0;
    final static int CURSE = 1;


    /**
     * Resets the hero's statistics and generates a new random name.
     * Should be called when a new game is started.
     */
    public static void reset() {
        String[] names = new String[]{"Certosino", "Guglielmo", "Tenebroso", "Kight", "Hero", "Ben", "John", "Mikela", "Sister Graziana"};
        Random rand = new Random();
        name = names[rand.nextInt(names.length)];
        health = 80;
        fame = 50;
        money = 50;
        loyalty = 50;
        mana = 50;
        luck = 10;
        for (int i = 0; i < 2; i++)
            artefacts[i] = false;
    }

    /**
     * Current hero's name.
     *
     * @return String with the name
     */
    public static String getHeroName() {
        return name;
    }

    public static int getHealth() {
        return health;
    }

    public static int getFame() {
        return fame;
    }

    public static int getMoney() {
        return money;
    }

    public static int getLoyalty() {
        return loyalty;
    }

    public static int getMana() {
        return mana;
    }

    public static boolean hasWand() {
        return artefacts[WAND];
    }

    public static boolean hasCurse() {
        return artefacts[CURSE];
    }
}
