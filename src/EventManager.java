import java.util.*;

public class EventManager {
    int currentId;
    int nextId = 0;
    HashMap<Integer, WorldEvent> events = new HashMap<>();
    int completedEvents = 0;

    /**
     * Constructor for EventManager.
     * Initializes all events with the corresponding weight.
     * Higher weight = event is more likely to happen.
     * For weight to work noticeably, weight and luck should be comparable.
     */
    public EventManager() {
        /*
        [EventId=event]
        1=babysitter
        2=cat
        10=investigation
        11=catacombsEntrance
        12=catacombs
        20=potionMerchant
        21=massacre
         */
        WorldEvent babysitter = new WorldEvent(1, 0, 0, 0, 0);
        events.put(1, babysitter);
        babysitter.setDescription("Every hero has to start somewhere... The local school's teacher has asked you to escort the children to the nearby village.");
        babysitter.setOption(0, new Option("Accept.", "You safely escort the children and the teacher is grateful.", 0, 5, 0, 20, 0, 0));
        babysitter.setOption(1, new Option("Accept and buy them breakfast.", "You treat the children to a tasty breakfast on the road. That's all it takes to get them to like you.", 2, 10, -5, 0, 0, 0));
        babysitter.setOption(2, new Option("Refuse.", "Sometimes you'd rather just sleep...", 5, 0, 0, 0, 0, 0));
        babysitter.setOption(3, new Option("Accept, but kill the children on the way there.", "The village won't forget that.", 0, -25, 0, 0, 0, 0));

        WorldEvent cat = new WorldEvent(1, 0, 0, 0, 0);
        events.put(2, cat);
        cat.setDescription("Every hero has to start somewhere... A lady has asked you to rescue her cat from a tree.");
        cat.setOption(0, new Option("Climb the tree and grab the cat.", "The cat scratches your arm on the way down. Ouch!", -2, 5, 0, 0, 0, 0));
        cat.setOption(1, new Option("Pet the cat and bring it back to the lady.", "The cat seems to like you.", 0, 5, 0, 0, 0, 1));
        cat.setOption(2, new Option("Walk away.", "The lady seems disappointed.", 0, -3, 0, 0, 0, 0));
        cat.setOption(3, new Option("Chop down the tree.", "The lady calls the king's guards, but they decide to help you, because the king hates that tree.", 0, -10, 0, +10, 0, 0));

        WorldEvent investigation = new WorldEvent(10, 0, 0, 0, 0);
        events.put(10, investigation);
        investigation.setDescription("You've heard rumors of a mysterious figure roaming near the catacombs. Should you investigate?");
        investigation.setOption(0, new Option("Yes", "You arrive at night. As you approach the hooded figure, it runs into the catacombs.", 0, 0, 0, 0, 0, 0));
        investigation.setOption(1, new Option("No", "Some things are best left in mystery...", 0, 0, 0, 0, 0, 0));

        WorldEvent catacombsEntrance = new WorldEvent(0, 0, 0, 0, 0);
        events.put(11, catacombsEntrance);
        investigation.options[0].setNextEvent(11);
        catacombsEntrance.setDescription("The mysterious figure ran into the catacombs. What will you do?");
        catacombsEntrance.setOption(0, new Option("Chase it into the catacombs.", "As you run into the catacombs, a sense of dread befalls you.", 0, 0, 0, 0, 0, -3));
        catacombsEntrance.setOption(1, new Option("The stone archway marking the entrance of the catacombs seems frail. Perhaps you could collapse it to block the entrance...", "After only a few blows, the entrance collapses.", 0, -5, 0, 0, 0, 0));
        catacombsEntrance.setOption(2, new Option("Walk away.", "As you walk back, you hear faint echoes coming from the catacombs. None of your business.", 0, 0, 0, 0, 0, 0));

        WorldEvent catacombs = new WorldEvent(0, 0, 0, 0, 0);
        events.put(12, catacombs);
        catacombsEntrance.options[0].setNextEvent(12);
        catacombs.setDescription("You follow the echoes of an ancient ritual until you find the hooded figure. It has transformed into a demon.");
        catacombs.setOption(0, new Option("Draw your sword and get ready to fight.", "You manage to defeat the demon, but it's cursed you!", -10, 10, 0, 0, 0, 0));
        catacombs.options[0].setItem(Hero.CURSE);
        catacombs.setOption(1, new Option("Bargain for a pact with the demon.", "The demon seems to have taken a liking to you, and it gifts you a magic wand!", 0, 0, 0, 0, 0, 0));
        catacombs.options[1].setItem(Hero.WAND);
        catacombs.setOption(2, new Option("Magic: Banish the demon back to hell.", "With a zap of your magic wand, the demon is sent back to hell. How ironic...", 0, 15, 0, 0, 0, 0));
        catacombs.options[2].setMagic(true);

        WorldEvent potionMerchant = new WorldEvent(10,-0.3,0,0,0);
        events.put(20, potionMerchant);
        potionMerchant.setDescription("You arrive at a potion merchant's stand.");
        potionMerchant.setOption(0, new Option("Buy a health potion", "You drink the health potion and feel rejuvenated", 10,0,-10,0,0,0));
        potionMerchant.setOption(1, new Option("Don't buy anything.", "You bought nothing", 0,0,0,0,0,0));
        potionMerchant.setOption(2, new Option("Buy a mana potion", "You drink the mana potion and you suddenly feel attuned to the elements", 0,0,-10,0,10,1));
        potionMerchant.options[2].setMagic(true);

        WorldEvent massacre = new WorldEvent(0,0,0,0,0.2);
        events.put(21, massacre);
        massacre.setDescription("The king has requested your assistance in culling a village");
        massacre.setOption(0, new Option("Accept", "You must to do what the king requests to not get on his bad side...", 0,-25,15,10,0,0));
        massacre.setOption(1, new Option("Refuse", "You refused. The king will we displeased", 0,0,0,0,-10,0));


        //Dovranno essere spostati
        Hero.reset();
        Timer aging = new Timer();
        aging.schedule(Hero.changeAge, 5000, 5000);
    }

    private int getRandomEvent() {
        ArrayList<Integer> possibleEvents = new ArrayList<>();
        if (completedEvents == 0) {
            possibleEvents.add(1);
            possibleEvents.add(2);
        } else {
            possibleEvents.add(10);
            possibleEvents.add(20);
            possibleEvents.add(21);
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
    }

    /**
     * Updates the hero's statistics when an option is chosen.
     *
     * @param n The option number (0=North, 1=South, 2=West, 3=East)
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

    public String getEventDescription() {
        return events.get(currentId).description;
    }

    public int getOptionNumber() {
        int i = 0;
        while ( i <= 3 && events.get(currentId).options[i] != null ){
            i++;
        }
        return i;
    }

    public String getDesc(int n) {
        return events.get(currentId).options[n].description;
    }

    public String getResult(int n) {
        return events.get(currentId).options[n].result;
    }

    public int getDeltaHealth(int n){
        return events.get(currentId).options[n].deltaHealth;
    }

    public int getDeltaFame(int n){
        return events.get(currentId).options[n].deltaFame;
    }

    public int getDeltaMoney(int n){
        return events.get(currentId).options[n].deltaMoney;
    }

    public int getDeltaLoyalty(int n) {
        return events.get(currentId).options[n].deltaLoyalty;
    }

}
