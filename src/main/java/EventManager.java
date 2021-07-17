import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Class that creates and manages game events and options.
 * GameListeners can be added to be notified of certain game events like achievements and time passing.
 */
public class EventManager {
    static HashMap<Integer, WorldEvent> events;
    static ArrayList<GameListener> listeners;

    static {
        events = new HashMap<>();
        listeners = new ArrayList<>();

        WorldEvent babysitter = new WorldEvent(1, 0, 0, 0, 0);
        events.put(1, babysitter);
        babysitter.setDescription("Every hero has to start somewhere... The local school's teacher has asked you to escort the children to the nearby village.");
        babysitter.setOption(0, new Option("Accept", "You safely escort the children and the teacher is grateful.", 0, 5, 0, 0, 0, 0));
        babysitter.setOption(1, new Option("Accept and buy them breakfast", "You treat the children to a tasty breakfast on the road. That's all it takes to get them to like you.", 3, 5, -5, 0, 0, 0));
        babysitter.setOption(2, new Option("Refuse", "Sometimes you'd rather just sleep...", 5, 0, 0, 0, 0, 0));
        babysitter.setOption(3, new Option("Accept, but kill the children on the way there", "The people of the village won't forget that...", 0, -25, 0, 0, 0, 0));

        WorldEvent cat = new WorldEvent(1, 0, 0, 0, 0);
        events.put(2, cat);
        cat.setDescription("Every hero has to start somewhere... A lady has asked you to rescue her cat from a tree.");
        cat.setOption(0, new Option("Climb the tree and grab the cat", "The cat scratches your arm on the way down. Ouch!", -2, 5, 0, 0, 0, 0));
        cat.setOption(1, new Option("Pet the cat and bring it back to the lady", "The cat seems to like you.", 0, 7, 0, 0, 0, 1));
        cat.setOption(2, new Option("Walk away", "The lady seems disappointed.", 0, -5, 0, 0, 0, 0));
        cat.setOption(3, new Option("Chop down the tree", "The lady calls the king's guards, but they decide to help you, because the king hates that tree.", 0, -10, 0, +10, 0, 0));

        WorldEvent investigation = new WorldEvent(25, 0, 0, 0, 0);
        events.put(10, investigation);
        investigation.setDescription("You've heard rumors of a mysterious figure roaming near the catacombs. Should you investigate?");
        investigation.setOption(0, new Option("Yes", "You arrive at night. As you approach the hooded figure, it runs into the catacombs.", 0, 0, 0, 0, 0, 0));
        investigation.setOption(1, new Option("No", "Some things are best left in mystery...", 0, -7, 0, -7, 0, 0));

        WorldEvent catacombsEntrance = new WorldEvent(0, 0, 0, 0, 0);
        events.put(11, catacombsEntrance);
        investigation.options[0].setNextEvent(11);
        catacombsEntrance.setDescription("The mysterious figure ran into the catacombs. What will you do?");
        catacombsEntrance.setOption(0, new Option("Chase it into the catacombs", "As you run into the catacombs, a sense of dread befalls you.", 0, 0, 0, 0, 0, 0));
        catacombsEntrance.setOption(1, new Option("Collapse the entrance", "After only a few blows, the entrance collapses.", 0, -10, 10, 0, 0, 0));
        catacombsEntrance.setOption(2, new Option("Walk away", "As you walk back, you hear faint echoes coming from the catacombs. None of your business.", 0, -5, 0, -5, 0, 0));

        WorldEvent catacombs = new WorldEvent(0, 0, 0, 0, 0);
        events.put(12, catacombs);
        catacombsEntrance.options[0].setNextEvent(12);
        catacombs.setDescription("You follow the echoes of an ancient ritual until you find the hooded figure. It has transformed into a demon.");
        catacombs.setOption(0, new Option("Draw your sword and get ready to fight", "You manage to defeat the demon, but it's cursed you!", -15, 25, 10, 0, 0, 1));
        catacombs.options[0].setItem(Hero.CURSE, 1);
        catacombs.setOption(1, new Option("Bargain for a pact with the demon", "The demon gives you a magic wand, but at what cost?", 0, 0, 0, 0, 0, -3));
        catacombs.options[1].setItem(Hero.WAND, 1);
        catacombs.setOption(2, new Option("Banish the demon with a spell", "With a zap of your magic wand, the demon is sent back to hell.", 0, 15, 10, 0, -25, 0));
        catacombs.options[2].setMagic(true);

        WorldEvent exorcist = new WorldEvent(15, 0, 0, 0, 0);
        events.put(13, exorcist);
        exorcist.setDescription("An old man in a tunic approaches you saying he can perceive a curse upon you. He claims he can exorcise it.");
        exorcist.setOption(0, new Option("Ask him to exorcise the curse", "The old man chants words you can't understand. The curse has been lifted.", 0, 0, 0, 0, -20, 2));
        exorcist.options[0].setItem(Hero.CURSE, -1);
        exorcist.setOption(1, new Option("Refuse", "The old man walks away yelling that you will only bring misfortune to the realm.", 0, -10, 0, 0, 0, 0));
        exorcist.setOption(2, new Option("Kill the old man", "He'd gone crazy. Better put him out of his misery...", 0, 0, 0, 0, 0, 0));

        WorldEvent potionMerchant = new WorldEvent(35, -0.4, 0, 0, 0);
        events.put(20, potionMerchant);
        potionMerchant.setDescription("You arrive at a potion merchant's stand.");
        potionMerchant.setOption(0, new Option("Buy a health potion", "You drink the health potion and feel rejuvenated.", 15, 0, -8, 0, 0, 0));
        potionMerchant.setOption(1, new Option("Don't buy anything", "You bought nothing.", 0, 0, 0, 0, 0, 0));
        potionMerchant.setOption(2, new Option("Buy a mana potion", "You drink the mana potion and you suddenly feel attuned to the elements.", 0, 0, -8, 0, 15, 1));
        potionMerchant.options[2].setMagic(true);

        WorldEvent massacre = new WorldEvent(5, 0, 0, 0, 0.2);
        events.put(21, massacre);
        massacre.setDescription("The king has requested your assistance in culling a village.");
        massacre.setOption(0, new Option("Accept", "You must do what the king requests to not get on his bad side...", 0, -25, 15, 15, 0, 0));
        massacre.setOption(1, new Option("Refuse", "You refused. The king will we displeased.", 0, 0, 0, -20, 0, 0));

        WorldEvent slimes = new WorldEvent(40, 0, 0, 0, 0);
        events.put(22, slimes);
        slimes.setDescription("You've run into some slimes in the swamp.");
        slimes.setOption(0, new Option("Fight the slimes", "Killing slimes with a sword can be tough, but you managed to pull through.", -8, 8, 0, 0, 0, 0));
        slimes.setOption(1, new Option("Run away", "It's too hard to run when your feet sink in the swamp's mud and the slimes attack you.", -12, 0, 0, 0, 0, 0));
        slimes.setOption(2, new Option("Cast an explosion spell", "That worked better than you expected.", 0, 8, 0, 0, -15, 0));
        slimes.options[2].setMagic(true);

        WorldEvent crystals = new WorldEvent(10, 0, 0, 0.1, 0);
        events.put(23, crystals);
        crystals.setDescription("The merchants guild has asked you to recover a lost shipment of magic crystals.");
        crystals.setOption(0, new Option("Help the merchants", "You find a cart full of shiny crystals. You can feel the electricity in the air.", 0, 0, 15, 0, 30, 0));
        crystals.setOption(1, new Option("Refuse", "The merchants are disappointed.", 0, -5, 0, 0, 0, 0));

        WorldEvent flower = new WorldEvent(35, 0, 0, 0, 0);
        events.put(24, flower);
        flower.setDescription("A nurse has asked you to pick find a rare flower that grows in the north, to heal a comatose patient.");
        flower.setOption(0, new Option("Head north to find the flower", "After a long journey, you manage to find the flower, but it wilts on the way back.", -3, -5, 0, 0, 0, 0));
        flower.setOption(1, new Option("Look for merchants selling the flower", "You find a back alley merchant selling the flower and, although expensive, you purchase it.", 0, 20, -20, 0, 0, 0));
        flower.setOption(2, new Option("Refuse", "The nurse walks away on the verge of tears.", 0, -10, 0, 0, 0, 0));

        WorldEvent thief = new WorldEvent(25, 0, 0, 0, 0);
        events.put(25, thief);
        thief.setDescription("While walking at night you notice a thief trying to sneak into a house.");
        thief.setOption(0, new Option("Alert the guards", "The guards manage to detain the thief", 0, 0, 0, 8, 0, 0));
        thief.setOption(1, new Option("Intervene yourself", "You manage to detain thief without a fight", 0, 8, 0, 0, 0, 0));

        WorldEvent crow = new WorldEvent(15, 0, 0, 0, 0);
        events.put(26, crow);
        crow.setDescription("A crow lands near you and demands shiny objects.");
        crow.setOption(0, new Option("Give the crow some money", "The crow takes your money and flies away", 0, 0, -15, 0, 0, 0));
        crow.setOption(1, new Option("Kill the crow", "You kill the crow. You can hear a flow of crow cawing in the distance.", 0, 0, 0, 0, 0, 0));
        crow.options[1].setItem(Hero.CROW, 1);

        WorldEvent fortuneTeller = new WorldEvent(10, 0, 0, 0, 0);
        events.put(27, fortuneTeller);
        fortuneTeller.setDescription("You find a fortune teller. Should you ask her about your future?");
        fortuneTeller.setOption(0, new AbstractOption("Yes", "", 0, 0, -6, 0, 0, 0) {
            @Override
            public void pick() {
                defaultPick();
                switch ((Hero.getLuck() - 1) / 4) {
                    case 4 -> result = "Your stars are aligned. Great fortune will guide you on your journey.";
                    case 3 -> result = "The stars gently shine on your path. You'll experience above average luck.";
                    case 2 -> result = "The heavens feel neutral to your cause.";
                    case 1 -> result = "Your stars are in disarray. Be careful on your path, for bad fortune looms ahead.";
                    default -> result = "Great misfortune and adversity awaits you. You are doomed.";
                }
            }
        });
        fortuneTeller.setOption(1, new Option("No", "You may choose to walk blind, but you'll find what the future holds in due time...", 0, 0, 0, 0, 0, 0));

        WorldEvent siren = new WorldEvent(15, 0, 0, 0, 0);
        events.put(28, siren);
        siren.setDescription("You hear a sweet voice singing from the river.");
        siren.setOption(0, new AbstractOption("Go to her", "While under her charm, you almost drown to death.", 0, 0, -12, 0, 0, 0) {
            @Override
            public void pick() {
                defaultPick();
                newAchievement("Odysseus");
            }
        });
        siren.setOption(1, new Option("Go away", "You get lost on your way home.", -2, -10, 0, 0, 0, 0));
        siren.setOption(2, new Option("Curse at the creature", "The creature doesn't like it. She sings you a curse.", 0, 0, 0, 0, -20, 0));
        siren.options[2].setItem(Hero.CURSE, 1);
        siren.setOption(3, new Option("Kill the creature", "You heard siren meat brings immortality, so you kill the creature and make a broth.", 40, 0, 0, 0, 10, 0));

        WorldEvent dice = new WorldEvent(40, 0, 0, 0, 0);
        events.put(29, dice);
        dice.setDescription("A man has challenged you to a game of dice for money.");
        dice.setOption(0, new AbstractOption("Throw the dice", "", 0, 0, 0, 0, 0, 0) {
            @Override
            public void pick() {
                Random rand = new Random();
                int r = rand.nextInt(80) + Hero.getLuck();
                if (r > 40) {
                    Hero.addMoney(10);
                    result = "You won!";
                    if (r % 2 == 0)
                        Hero.addLuck(1);
                } else {
                    Hero.addMoney(-10);
                    result = "You lost. Better luck next time!";
                }
                defaultPick();
            }
        });
        dice.setOption(1, new Option("Refuse", "The man calls you a chicken as you walk away.", 0, -3, 0, 0, 0, 0));

        WorldEvent dragon1 = new WorldEvent(5, 0, 0, 0, 0);
        events.put(40, dragon1);
        dragon1.setDescription("A dragon has been terrorizing the area and you've been asked to kill it.");
        dragon1.setOption(0, new Option("Search for its lair and kill it", "After a hard fought battle you manage to defeat the dragon. You also obtain an enchanted scale!", -30, 25, 25, 25, 0, 0));
        dragon1.options[0].setItem(Hero.SCALE, 1);
        dragon1.setOption(1, new Option("Try to communicate with the dragon", "When you arrive at the dragon lair you realize the dragon can talk.", 0, 0, 0, 0, 0, 0));
        dragon1.setOption(2, new Option("Refuse", "Still alive, the dragon burns down and entire village.", 0, -25, 0, -25, 0, 0));

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
        dragonEgg.setOption(1, new Option("Sell it", "You sell the egg for a considerable amount of money.", 0, 0, 30, 0, 0, 0));
        dragonEgg.setOption(2, new Option("Cook it", "You cook a nice dragon egg omelet.", 30, 0, 0, 0, 0, 0));
        dragonEgg.setOption(3, new Option("Give it to the king", "You gift the egg to the king.", 0, 0, 0, 30, 0, 0));
    }

    /**
     * Resets hero statistics and picks the first event.
     */
    static public void newGame() {
        Hero.reset();
        newEvent();
    }

    /**
     * Returns a random event based on weight.
     *
     * @return Int (Key of the event in "events"). Positive
     */
    static private int getRandomEvent() {
        Random rand = new Random();
        ArrayList<Integer> possibleEvents = new ArrayList<>();
        //Starting events
        if (Hero.completedEvents == 0) {
            possibleEvents.add(1);
            possibleEvents.add(2);
        } else {
            //Normal events
            for (int i = 20; i <= 29; i++) {
                if (Hero.currentId != i)
                    possibleEvents.add(i);
            }
            //Catacombs
            if (Hero.completedEvents > 5 && Hero.currentId / 10 != 1)
                possibleEvents.add(10);
            //Exorcist
            if (Hero.hasCurse() && Hero.currentId != 13)
                possibleEvents.add(13);
            //Dragon
            if (Hero.completedEvents > 10 && Hero.currentId / 10 != 4)
                possibleEvents.add(40);
        }
        int weightSum = 0;
        for (int i : possibleEvents) {
            weightSum += events.get(i).fullWeight();
        }
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
    static public void newEvent() {
        if (Hero.nextId != 0) {
            Hero.currentId = Hero.nextId;
            Hero.nextId = 0;
        } else
            Hero.currentId = getRandomEvent();

        if (Hero.completedEvents != 0)
            DBManager.save();
    }

    /**
     * Search the cause of death and get the correct line.
     * Stops aging.
     *
     * @param stat the statistic that is 0 or 100
     * @return description of the death
     */
    static public String getDeath(int stat) {
        Hero.stopTimer();

        String descDeath = "";
        switch (stat) {
            case 0:
                if (Hero.stats[0] == 0) {
                    descDeath = "You have no energy to continue living, so you let yourself die under a tree.";
                } else {
                    descDeath = "You've never felt better in your life, but a cripple is envious of your health and poisons you.";
                }
                break;
            case 1:
                if (Hero.stats[1] == 0) {
                    descDeath = "Nobody recognizes you anymore. You feel too lonely to continue living.";
                } else {
                    descDeath = "One of your fan proposes to you, but upon your rejection, he kills you.";
                }
                break;
            case 2:
                if (Hero.stats[2] == 0) {
                    descDeath = "The king doesn't like your unwillingness to obey. So he sentences you to an horrible death.";
                } else {
                    descDeath = "You are so loyal to the king that people start calling you 'the loyal dog'. Some rebels decide to kill you to spite the king.";
                }
                break;
            case 3:
                if (Hero.stats[3] == 0) {
                    descDeath = "You are hungry, but there is no more money left. You die miserably.";
                } else {
                    descDeath = "You are now one of the richest men in town. Some thieves want to steal from your home and in the process they kill you.";
                }
                break;
            case 4:
                if (Hero.stats[4] == 0) {
                    descDeath = "You try to light a fire with a spell, but you're so devoid of mana, it takes away your life.";
                } else {
                    descDeath = "The mana inside you is too much, you explode.";
                }
                break;
        }

        return descDeath;
    }

    /**
     * Updates the hero's statistics when an option is chosen and increased the completed events counter.
     *
     * @param n The option number (0 to 3)
     */
    static public void pickOption(int n) {
        events.get(Hero.currentId).options[n].pick();
        Hero.completedEvents++;
        if (Hero.completedEvents == 100)
            newAchievement("Achilles");
        if (events.get(Hero.currentId).options[n].nextId != 0)
            Hero.nextId = events.get(Hero.currentId).options[n].nextId;
    }

    /**
     * Returns the internal number of the current event.
     * The number is defined in the EventManager constructor and may be subject to change!
     *
     * @return int (1 is the first event)
     */
    static public int getEventNumber() {
        return Hero.currentId;
    }

    /**
     * Returns the current event's initial description.
     * If the hero has been crowed, a different description may be returned.
     *
     * @return String (Could be long)
     */
    static public String getEventDescription() {
        if (Hero.isCrowed()) {
            return "The cawing of a thousand crows fills your mind.";
        }
        return events.get(Hero.currentId).description;
    }

    /**
     * Returns the number of AVAILABLE options for the current event.
     * If an option is only available to mages, it will not be counted.
     *
     * @return int (1 to 4)
     */
    static public int getOptionNumber() {
        int i = 0;
        for (AbstractOption o : events.get(Hero.currentId).options) {
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
    static public String getOptionDescription(int n) {
        return events.get(Hero.currentId).options[n].description;
    }

    /**
     * Returns the result of the event according to which option was picked.
     *
     * @param n The option number (0 to 3)
     * @return String (Could be several lines long)
     */
    static public String getResult(int n) {
        return events.get(Hero.currentId).options[n].getResult();
    }

    /**
     * Parameter object will be notified when achievements are obtained and an in-game year passes.
     *
     * @param gameListener Object to be notified
     */
    public static void addGameListener(GameListener gameListener) {
        listeners.add(gameListener);
    }

    /**
     * Notifies listeners that the achievement has been obtained.
     *
     * @param achievement String with the name of the achievement.
     */
    public static void newAchievement(String achievement) {
        if (DBManager.isNameAvailable(achievement)) return;
        DBManager.addName(achievement);
        for (GameListener i : listeners)
            i.achievementObtained(achievement);
    }

}
