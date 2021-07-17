import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Class meant entirely for static usage.
 * Contains fields that should be accessible to the whole project.
 */
public class Hero {
    static String name;
    static int age;
    static int yearsOfService;
    static private Timer yearTimer;

    static int[] stats = new int[5];
    static int luck;

    static boolean[] artefacts = new boolean[4];
    final static int WAND = 0;
    final static int CURSE = 1;
    final static int SCALE = 2;
    final static int CROW = 3;

    static int currentId;
    static int nextId;
    static int completedEvents;


    /**
     * Resets the hero's statistics.
     */
    public static void reset() {
        name = DBManager.getRandomName();

        int max = 25;
        int min = 13;
        Random rand = new Random();
        age = rand.nextInt(max - min) + min;
        yearsOfService = -1;
        restartTimer();

        stats[0] = 80; //health
        stats[1] = 50; //fame
        stats[2] = 50; //loyalty
        stats[3] = 50; //money
        stats[4] = 50; //mana
        luck = 10;
        Arrays.fill(artefacts, false);

        if (name.equals("Merlin")) {
            artefacts[WAND] = true;
        }

        currentId = 0;
        nextId = 0;
        completedEvents = 0;
    }

    public static void restartTimer(){
        if (yearTimer != null)
            yearTimer.cancel();
        yearTimer = new Timer();
        yearTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                Hero.age++;
                Hero.yearsOfService++;
                //Hero.addFame(-1); !!!Dangerous
                for (GameListener i : EventManager.listeners)
                    i.heroAged();
            }
        }, 0, 5000);
    }

    /**
     * Current hero's name.
     *
     * @return String with the name
     */
    public static String getHeroName() {
        return name;
    }

    /**
     * Current hero's age.
     *
     * @return positive int
     */
    public static int getAge() {
        return age;
    }

    /**
     * Current hero's years of service.
     *
     * @return non-negative int
     */
    public static int getYearsOfService() {
        return yearsOfService;
    }

    /**
     * Current hero's health.
     *
     * @return int (0-100)
     */
    public static int getHealth() {
        return stats[0];
    }

    /**
     * Current hero's fame.
     *
     * @return int (0-100)
     */
    public static int getFame() {
        return stats[1];
    }

    /**
     * Current hero's loyalty.
     *
     * @return int (0-100)
     */
    public static int getLoyalty() {
        return stats[2];
    }

    /**
     * Current hero's money.
     *
     * @return int (0-100)
     */
    public static int getMoney() {
        return stats[3];
    }

    /**
     * Current hero's mana.
     *
     * @return int (0-100)
     */
    public static int getMana() {
        return stats[4];
    }

    /**
     * Current hero's luck.
     *
     * @return int (0-20)
     */
    public static int getLuck() {
        return luck;
    }

    /**
     * Returns true if the current hero has a wand.
     *
     * @return boolean
     */
    public static boolean hasWand() {
        return artefacts[WAND];
    }

    /**
     * Returns true if the current hero has a curse.
     *
     * @return boolean
     */
    public static boolean hasCurse() {
        return artefacts[CURSE];
    }

    /**
     * Returns true if the current hero has an enchanted scale.
     *
     * @return boolean
     */
    public static boolean hasScale() {
        return artefacts[SCALE];
    }

    /**
     * Returns true if the current hero has crows.
     *
     * @return boolean
     */
    public static boolean hasCrow() {
        return artefacts[CROW];
    }

    /**
     * Return true if the hero has the crow and if the description will be altered this time.
     *
     * @return Boolean
     */
    public static boolean isCrowed() {
        return hasCrow() && completedEvents % 3 == 0;
    }

    /**
     * Sets the hero's health.
     * Doesn't check whether the given amount is within bounds.
     *
     * @param health Health value
     */
    public static void setHealth(int health) {
        stats[0] = health;
    }

    /**
     * Sets the hero's fame.
     * Doesn't check whether the given amount is within bounds.
     *
     * @param fame Fame value
     */
    public static void setFame(int fame) {
        stats[1] = fame;
    }

    /**
     * Sets the hero's loyalty.
     * Doesn't check whether the given amount is within bounds.
     *
     * @param loyalty Loyalty value
     */
    public static void setLoyalty(int loyalty) {
        stats[2] = loyalty;
    }

    /**
     * Sets the hero's money.
     * Doesn't check whether the given amount is within bounds.
     *
     * @param money Money value
     */
    public static void setMoney(int money) {
        stats[3] = money;
    }

    /**
     * Sets the hero's mana.
     * Doesn't check whether the given amount is within bounds.
     *
     * @param mana Mana value
     */
    public static void setMana(int mana) {
        stats[4] = mana;
    }

    /**
     * Sets the hero's luck.
     * Doesn't check whether the given amount is within bounds.
     *
     * @param luck Luck value
     */
    public static void setLuck(int luck) {
        Hero.luck = luck;
    }

    /**
     * Adds the given amount to the hero's health.
     * Doesn't check whether the result is within bounds.
     *
     * @param health Health value
     */
    public static void addHealth(int health) {
        stats[0] += health;
    }

    /**
     * Adds the given amount to the hero's fame.
     * Doesn't check whether the result is within bounds.
     *
     * @param fame Fame value
     */
    public static void addFame(int fame) {
        stats[1] += fame;
    }


    /**
     * Adds the given amount to the hero's loyalty.
     * Doesn't check whether the result is within bounds.
     *
     * @param loyalty Loyalty value
     */
    public static void addLoyalty(int loyalty) {
        stats[2] += loyalty;
    }

    /**
     * Adds the given amount to the hero's Money.
     * Doesn't check whether the result is within bounds.
     *
     * @param money Money value
     */
    public static void addMoney(int money) {
        stats[3] += money;
    }

    /**
     * Adds the given amount to the hero's mana.
     * Doesn't check whether the result is within bounds.
     *
     * @param mana Mana value
     */
    public static void addMana(int mana) {
        stats[4] += mana;
    }

    /**
     * Adds the given amount to the hero's luck.
     * Doesn't check whether the result is within bounds.
     *
     * @param luck Luck value
     */
    public static void addLuck(int luck) {
        Hero.luck += luck;
    }
}
