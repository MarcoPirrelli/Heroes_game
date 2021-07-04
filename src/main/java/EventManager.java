import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class EventManager {
    int currentId;
    int nextId = 0;
    HashMap<Integer, WorldEvent> events = new HashMap<>();
    int completedEvents = 0;

    Connection connection;
    Statement statement;
    PreparedStatement saveStatement;
    int saveSlot;
    final int MAX_SAVES = 3;

    /**
     * Constructor for EventManager.
     * Initializes all events with the corresponding weight.
     * Higher weight = event is more likely to happen.
     * For weight to work noticeably, weight and luck should be comparable.
     */
    public EventManager() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:src\\main\\resources\\databases\\project.db");
            statement = connection.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        try {
            statement.executeQuery("select SaveId, HeroName, HeroAge, Service, Health, Fame, Money, Loyalty, Mana, Luck, Completed, EventId, Wand, Curse, Scale from saves");
        } catch (SQLException e) {
            try {
                statement.executeUpdate("drop table if exists saves");
                statement.executeUpdate("""
                        create table saves(
                        SaveId int primary key,
                        HeroName text not null,
                        HeroAge int check(HeroAge >= 0),
                        Service int check(Service >= 0),
                        Health int check(Health between 0 and 100),
                        Fame int check(Fame between 0 and 100),
                        Money int check(Money between 0 and 100),
                        Loyalty int check(Loyalty between 0 and 100),
                        Mana int check(Mana between 0 and 100),
                        Luck int check(Luck >= 0),
                        Completed int check(Completed >= 0),
                        EventId int check(EventId >= 0),
                        Wand int check(Wand in (0, 1)),
                        Curse int check(Curse in (0, 1)),
                        Scale int check(Scale in (0, 1))
                        )""");
            } catch (Exception e2) {
                e2.printStackTrace();
                System.exit(0);
            }
        }
        try {
            saveStatement = connection.prepareStatement("insert or replace into saves values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        /*
        [EventId=event]
        1=babysitter
        2=cat
        10=investigation
        11=catacombsEntrance
        12=catacombs
        13=exorcist
        20=potionMerchant
        21=massacre
         */
        WorldEvent babysitter = new WorldEvent(1, 0, 0, 0, 0);
        events.put(1, babysitter);
        babysitter.setDescription("Every hero has to start somewhere... The local school's teacher has asked you to escort the children to the nearby village.");
        babysitter.setOption(0, new Option("Accept", "You safely escort the children and the teacher is grateful.", 0, 5, 0, 0, 0, 0));
        babysitter.setOption(1, new Option("Accept and buy them breakfast", "You treat the children to a tasty breakfast on the road. That's all it takes to get them to like you.", 2, 10, -5, 0, 0, 0));
        babysitter.setOption(2, new Option("Refuse", "Sometimes you'd rather just sleep...", 5, 0, 0, 0, 0, 0));
        babysitter.setOption(3, new Option("Accept, but kill the children on the way there", "The village won't forget that.", 0, -25, 0, 0, 0, 0));

        WorldEvent cat = new WorldEvent(1, 0, 0, 0, 0);
        events.put(2, cat);
        cat.setDescription("Every hero has to start somewhere... A lady has asked you to rescue her cat from a tree.");
        cat.setOption(0, new Option("Climb the tree and grab the cat", "The cat scratches your arm on the way down. Ouch!", -2, 5, 0, 0, 0, 0));
        cat.setOption(1, new Option("Pet the cat and bring it back to the lady", "The cat seems to like you.", 0, 5, 0, 0, 0, 1));
        cat.setOption(2, new Option("Walk away", "The lady seems disappointed.", 0, -3, 0, 0, 0, 0));
        cat.setOption(3, new Option("Chop down the tree", "The lady calls the king's guards, but they decide to help you, because the king hates that tree.", 0, -10, 0, +10, 0, 0));

        WorldEvent investigation = new WorldEvent(20, 0, 0, 0, 0);
        events.put(10, investigation);
        investigation.setDescription("You've heard rumors of a mysterious figure roaming near the catacombs. Should you investigate?");
        investigation.setOption(0, new Option("Yes", "You arrive at night. As you approach the hooded figure, it runs into the catacombs.", 0, 0, 0, 0, 0, 0));
        investigation.setOption(1, new Option("No", "Some things are best left in mystery...", 0, 0, 0, 0, 0, 0));

        WorldEvent catacombsEntrance = new WorldEvent(0, 0, 0, 0, 0);
        events.put(11, catacombsEntrance);
        investigation.options[0].setNextEvent(11);
        catacombsEntrance.setDescription("The mysterious figure ran into the catacombs. What will you do?");
        catacombsEntrance.setOption(0, new Option("Chase it into the catacombs", "As you run into the catacombs, a sense of dread befalls you.", 0, 0, 0, 0, 0, -1));
        catacombsEntrance.setOption(1, new Option("Collapse the entrance", "After only a few blows, the entrance collapses.", 0, -5, 10, 0, 0, 0));
        catacombsEntrance.setOption(2, new Option("Walk away", "As you walk back, you hear faint echoes coming from the catacombs. None of your business.", 0, 0, 0, 0, 0, 0));

        WorldEvent catacombs = new WorldEvent(0, 0, 0, 0, 0);
        events.put(12, catacombs);
        catacombsEntrance.options[0].setNextEvent(12);
        catacombs.setDescription("You follow the echoes of an ancient ritual until you find the hooded figure. It has transformed into a demon.");
        catacombs.setOption(0, new Option("Draw your sword and get ready to fight", "You manage to defeat the demon, but it's cursed you!", -10, 15, 0, 0, 0, 0));
        catacombs.options[0].setItem(Hero.CURSE, 1);
        catacombs.setOption(1, new Option("Bargain for a pact with the demon", "The demon gives you a magic wand, but at what cost?", 0, 0, 0, 0, 0, -3));
        catacombs.options[1].setItem(Hero.WAND, 1);
        catacombs.setOption(2, new Option("Banish the demon with a spell", "With a zap of your magic wand, the demon is sent back to hell.", 0, 15, 0, 0, -20, 0));
        catacombs.options[2].setMagic(true);

        WorldEvent exorcist = new WorldEvent(15, 0, 0, 0, 0);
        events.put(13, exorcist);
        exorcist.setDescription("An old man in a tunic approaches you saying he can perceive a curse upon you. He claims he can exorcise it.");
        exorcist.setOption(0, new Option("Ask him to exorcise the curse", "The old man chants words you can't understand. The curse has been lifted.", 0, 0, 0, 0, -15, 0));
        exorcist.options[0].setItem(Hero.CURSE, -1);
        exorcist.setOption(1, new Option("Refuse", "The old man walks away yelling that you will only bring misfortune to the realm.", 0, -10, 0, 0, 0, 0));
        exorcist.setOption(2, new Option("Kill the old man", "He'd gone crazy. Better put him out of his misery...", 0, 0, 0, 0, 0, 0));

        WorldEvent potionMerchant = new WorldEvent(25, -0.4, 0, 0, 0);
        events.put(20, potionMerchant);
        potionMerchant.setDescription("You arrive at a potion merchant's stand.");
        potionMerchant.setOption(0, new Option("Buy a health potion", "You drink the health potion and feel rejuvenated.", 10, 0, -10, 0, 0, 0));
        potionMerchant.setOption(1, new Option("Don't buy anything", "You bought nothing.", 0, 0, 0, 0, 0, 0));
        potionMerchant.setOption(2, new Option("Buy a mana potion", "You drink the mana potion and you suddenly feel attuned to the elements.", 0, 0, -10, 0, 10, 1));
        potionMerchant.options[2].setMagic(true);

        WorldEvent massacre = new WorldEvent(0, 0, 0, 0, 0.2);
        events.put(21, massacre);
        massacre.setDescription("The king has requested your assistance in culling a village.");
        massacre.setOption(0, new Option("Accept", "You must do what the king requests to not get on his bad side...", 0, -25, 15, 10, 0, 0));
        massacre.setOption(1, new Option("Refuse", "You refused. The king will we displeased.", 0, 0, 0, -10, 0, 0));

        WorldEvent slimes = new WorldEvent(40, 0, 0, 0, 0);
        events.put(22, slimes);
        slimes.setDescription("You've run into some slimes in the swamp.");
        slimes.setOption(0, new Option("Fight the slimes", "Killing slimes with a sword can be tough, but you managed to pull through.", -10, 10, 0, 0, 0, 0));
        slimes.setOption(1, new Option("Run away", "It's too hard to run when your feet sink in the swamp's mud and the slimes attack you.", -20, 0, 0, 0, 0, 0));
        slimes.setOption(2, new Option("Cast an explosion spell", "That worked better than you expected.", 0, 10, 0, 0, -10, 0));
        slimes.options[2].setMagic(true);

        WorldEvent crystals = new WorldEvent(10, 0, 0, 0.1, 0);
        events.put(23, crystals);
        crystals.setDescription("The merchants guild has asked you to recover a lost shipment of magic crystals.");
        crystals.setOption(0, new Option("Help the merchants", "You find a cart full of shiny crystals. You can feel the electricity in the air.", 0, 0, 10, 0, 30, 0));
        crystals.setOption(1, new Option("Refuse", "The merchants are disappointed.", 0, -5, 0, 0, 0, 0));

        WorldEvent flower = new WorldEvent(35,0,0,0,0);
        events.put(24, flower);
        flower.setDescription("A nurse has asked you to pick find a rare flower that grows in the north, to heal a comatose patient.");
        flower.setOption(0, new Option("Head north to find the flower", "After a long journey, you manage to find the flower, but it wilts on the way back.", -5,-5,0,0,0,0));
        flower.setOption(1, new Option("Look for merchants selling the flower", "You find a back alley merchant selling the flower and, although expensive, you purchase it.", 0,10,-20,0,0,0));
        flower.setOption(2, new Option("Refuse", "The nurse walks away on the verge of tears.", 0,-10,0,0,0,0));

        WorldEvent thief = new WorldEvent(40, 0,0,0,0);
        events.put(25, thief);
        thief.setDescription("While walking at night you notice a thief trying to sneak into a house.");
        thief.setOption(0, new Option("Alert the guards", "The guards manage to detain the thief", 0,0,0,10,0,0));
        thief.setOption(1, new Option("Intervene yourself", "You manage to detain thief without a fight", 0,10,0,0,0,0));

        WorldEvent dice1 = new WorldEvent(20, 0, 0, 0, 0);
        events.put(30, dice1);
        dice1.setDescription("A man has challenged you to a game of dice for money.");
        dice1.setOption(0, new Option("Throw the dice", "You lost. Better luck next time!", 0, 0, -15, 0, 0, 0));
        dice1.setOption(1, new Option("Refuse", "The man calls you a chicken as you walk away.", 0, -3, 0, 0, 0, 0));

        WorldEvent dice2 = new WorldEvent(5, 0, 0, 0, 0);
        events.put(31, dice2);
        dice2.setDescription("A man has challenged you to a game of dice for money.");
        dice2.setOption(0, new Option("Throw the dice", "You won!", 0, 0, 15, 0, 0, 1));
        dice2.setOption(1, new Option("Refuse", "The man calls you a chicken as you walk away.", 0, -3, 0, 0, 0, 0));

        WorldEvent dice3 = new WorldEvent(5, 0, 0, 0, 0);
        events.put(32, dice3);
        dice3.setDescription("A man has challenged you to a game of dice for money.");
        dice3.setOption(0, new Option("Throw the dice", "You won!", 0, 0, 15, 0, 0, 0));
        dice3.setOption(1, new Option("Refuse", "The man calls you a chicken as you walk away.", 0, -3, 0, 0, 0, 0));

        WorldEvent dragon1 = new WorldEvent(5, 0, 0, 0, 0);
        events.put(40, dragon1);
        dragon1.setDescription("A dragon has been terrorizing the area and you've been asked to kill it.");
        dragon1.setOption(0, new Option("Search for its lair and kill it", "After a hard fought battle you manage to defeat the dragon. You also obtain an enchanted scale!", -35, 25, 25, 25, 0, 0));
        dragon1.options[0].setItem(Hero.SCALE, 1);
        dragon1.setOption(1, new Option("Try to communicate with the dragon", "When you arrive at the dragon lair you realize the dragon can talk.", 0, 0, 0, 0, 0, 0));
        dragon1.setOption(2, new Option("Refuse", "Still alive, the dragon burns down and entire village.", 0, -20, 0, -20, 0, 0));

        WorldEvent dragon2 = new WorldEvent(0, 0, 0, 0, 0);
        events.put(41, dragon2);
        dragon1.options[1].setNextEvent(41);
        dragon2.setDescription("The dragon tells you it's laid an egg and needs someone to take care of it");
        dragon2.setOption(0, new Option("Refuse and destroy the egg", "Enraged, the dragon sends you flying with a flap of its wing.", -50, 0, 0, 0, 0, -2));
        dragon2.setOption(1, new Option("Refuse, but help the dragon hide its lair.", "The royal guards discover the lair and kill the dragon.", 0, 0, 0, -20, 0, 0));
        dragon2.setOption(2, new Option("Accept and take the egg with you", "The dragon thanks you for the help and brings you its egg.", 0, 0, 0, 0, 10, 0));

        WorldEvent dragonEgg = new WorldEvent(0, 0, 0, 0, 0);
        events.put(42, dragonEgg);
        dragon2.options[2].setNextEvent(42);
        dragonEgg.setDescription("What will you do with the egg?");
        dragonEgg.setOption(0, new Option("Keep it hidden", "You decide to keep it hidden. What happens when it hatches is a problem for another day...", 0, 0, 0, 0, 0, 2));
        dragonEgg.setOption(1, new Option("Sell it", "You sell the egg for a considerable amount of money.", 0, 0, 35, 0, 0, 0));
        dragonEgg.setOption(2, new Option("Cook it", "You cook a nice dragon egg omelet.", 40, 0, 0, 0, 0, 0));
        dragonEgg.setOption(3, new Option("Give it to the king", "You gift the egg to the king.", 0, 0, 0, 35, 0, 0));
    }

    public void newGame() {
        currentId = 0;
        nextId = 0;
        completedEvents = 0;
        Hero.reset();
    }

    private int getRandomEvent() {
        ArrayList<Integer> possibleEvents = new ArrayList<>();
        if (completedEvents == 0) {
            possibleEvents.add(1);
            possibleEvents.add(2);
        } else {
            possibleEvents.add(20);
            possibleEvents.add(21);
            possibleEvents.add(22);
            possibleEvents.add(23);
            possibleEvents.add(24);
            possibleEvents.add(25);
            possibleEvents.add(30);
            possibleEvents.add(31);
            possibleEvents.add(32);
            if (completedEvents > 5)
                possibleEvents.add(10);
            if (Hero.hasCurse())
                possibleEvents.add(13);
            if (completedEvents > 10)
                possibleEvents.add(40);
        }
        int weightSum = 0;
        for (int i : possibleEvents) {
            weightSum += events.get(i).fullWeight();
        }
        Random rand = new Random();
        int r = rand.nextInt(weightSum) + 1;
        for (int i : possibleEvents) {
            r -= events.get(i).fullWeight();
            if (r <= 0) return i;
        }
        return 0; //should never get here
    }

    /**
     * Function which sets the new current event.
     * If the option previously chosen by the user calls for a chain event, it will be selected.
     * If there's no predetermined event to be played next, a random one will be chosen.
     */
    public void getEvent() {
        if (nextId != 0) {
            currentId = nextId;
            nextId = 0;
        } else
            currentId = getRandomEvent();
        save();
    }

    /**
     * Updates the hero's statistics when an option is chosen.
     *
     * @param n The option number (0 to 3)
     */
    public void pickOption(int n) {
        events.get(currentId).options[n].pick();
        completedEvents++;
        if (events.get(currentId).options[n].nextId != 0)
            nextId = events.get(currentId).options[n].nextId;
    }

    /**
     * Returns the internal number of the current event.
     * The number is defined in the EventManager constructor and is often subject to change.
     *
     * @return int (1 is the first event)
     */
    public int getEventNumber() {
        return currentId;
    }

    /**
     * Returns the current event's initial description.
     *
     * @return String (Could be several lines long)
     */
    public String getEventDescription() {
        return events.get(currentId).description;
    }

    /**
     * Returns the number of AVAILABLE options for the current event.
     * If an option is only available to mages, it will not be counted.
     *
     * @return int (1 to 4)
     */
    public int getOptionNumber() {
        int i = 0;
        for (Option o : events.get(currentId).options) {
            if (o != null && (!o.magic || Hero.hasWand()))
                i++;
        }
        return i;
    }

    /**
     * Returns the initial prompt for an option.
     *
     * @param n The option number (0 to 3)
     * @return String (Normally pretty short)
     */
    public String getDesc(int n) {
        return events.get(currentId).options[n].description;
    }

    /**
     * Returns the result of the event according to which option was picked.
     *
     * @param n The option number (0 to 3)
     * @return String (Could be several lines long)
     */
    public String getResult(int n) {
        return events.get(currentId).options[n].result;
    }

    public int getDeltaHealth(int n) {
        return events.get(currentId).options[n].deltaHealth;
    }

    public int getDeltaFame(int n) {
        return events.get(currentId).options[n].deltaFame;
    }

    public int getDeltaMoney(int n) {
        return events.get(currentId).options[n].deltaMoney;
    }

    public int getDeltaLoyalty(int n) {
        return events.get(currentId).options[n].deltaLoyalty;
    }

    /**
     * Returns the first empty save slot number (>= 1).
     * If all save slots are full, returns 0.
     *
     * @return positive int
     */
    public int firstEmptySlot() {
        int first = 0;
        try {
            ResultSet r = statement.executeQuery("select SaveId from saves order by 1");
            for (int i = 1; i <= MAX_SAVES; i++) {
                if (!r.next() || r.getInt("SaveId") > i) {
                    first = i;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return first;
    }

    /**
     * Sets which save slot the EventManager saves the current game to.
     *
     * @param saveSlot int between 1 and MAX_SAVES
     */
    public void setSaveSlot(int saveSlot) {
        this.saveSlot = saveSlot;
    }

    /**
     * Load a game from a previous save.
     *
     * @param saveId int between 1 and MAX_SAVES
     */
    public void load(int saveId) {
        try {
            ResultSet r = statement.executeQuery("select * from saves where SaveId=" + saveId);
            if (r.isAfterLast()) return;
            saveSlot = r.getInt("SaveId");
            Hero.name = r.getString("HeroName");
            Hero.age = r.getInt("HeroAge");
            Hero.yearsOfService = r.getInt("Service");
            Hero.setHealth(r.getInt("Health"));
            Hero.setFame(r.getInt("Fame"));
            Hero.setMoney(r.getInt("Money"));
            Hero.setLoyalty(r.getInt("Loyalty"));
            Hero.setMana(r.getInt("Mana"));
            Hero.setLuck(r.getInt("Luck"));
            completedEvents = r.getInt("Completed");
            currentId = r.getInt("EventId");
            Hero.artefacts[Hero.WAND] = r.getBoolean("Wand");
            Hero.artefacts[Hero.CURSE] = r.getBoolean("Curse");
            Hero.artefacts[Hero.SCALE] = r.getBoolean("Scale");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Delete a game save to free up a slot.
     *
     * @param saveId int between 1 and MAX_SAVES
     */
    public void deleteSave(int saveId) {
        try {
            statement.executeUpdate("delete from saves where SaveId= " + saveId);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    private void save() throws RuntimeException {
        if (saveSlot <= 0 || saveSlot > MAX_SAVES) throw new RuntimeException("Error: Illegal save slot!");
        try {
            saveStatement.setInt(1, saveSlot);
            saveStatement.setString(2, Hero.name);
            saveStatement.setInt(3, Hero.age);
            saveStatement.setInt(4, Hero.yearsOfService);
            saveStatement.setInt(5, Hero.getHealth());
            saveStatement.setInt(6, Hero.getFame());
            saveStatement.setInt(7, Hero.getMoney());
            saveStatement.setInt(8, Hero.getLoyalty());
            saveStatement.setInt(9, Hero.getMana());
            saveStatement.setInt(10, Hero.getLuck());
            saveStatement.setInt(11, completedEvents);
            saveStatement.setInt(12, currentId);
            saveStatement.setInt(13, (Hero.artefacts[Hero.WAND] ? 1 : 0));
            saveStatement.setInt(14, (Hero.artefacts[Hero.CURSE] ? 1 : 0));
            saveStatement.setInt(15, (Hero.artefacts[Hero.SCALE] ? 1 : 0));

            saveStatement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    /**
     * Returns a ResultSet containing the saves' information meant for display.
     * <p>
     * Example usage:
     * ResultSet r = ev.getAllSaves();
     * while(r.next()){
     * i=r.getInt("SaveId");
     * arraypulsanti[i].setText(r.getString("HeroName"));
     * ...
     * }
     *
     * @return ResultSet with 0 to MAX_SAVES rows
     */
    public ResultSet getAllSaves() {
        ResultSet ret = null;
        try {
            ret = statement.executeQuery("select SaveId, HeroName, HeroAge, Completed from saves");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(0);
        }
        return ret;
    }
}
