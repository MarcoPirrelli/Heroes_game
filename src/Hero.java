import java.util.Random;

public class Hero {
    static String name;
    static int health, fame, money, loyalty;
    static int mana;
    static int luck;
    static boolean[] artefacts = new boolean[2];

    final static int WAND = 0;
    final static int CURSE = 1;

    public static void reset() {
        String[] names = new String[]{"Certosino", "Guglielmo", "Tenebroso", "Kight", "Hero", "Ben", "John", "Mikela", "Sister Graziana"};
        Random rand = new Random();
        name=names[rand.nextInt(names.length)];
        health = 80;
        fame = 50;
        money = 50;
        loyalty = 50;
        mana = 50;
        luck = 10;
        for (int i = 0; i < 2; i++)
            artefacts[i] = false;
    }

    public static String getHeroName () {
        return name;
    }
}
