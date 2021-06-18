import java.util.ArrayList;
import java.util.Random;

public class EventManager {
    WorldEvent currentEvent;
    WorldEvent nextEvent = null;
    WorldEvent babysitter, cat, investigation, catacombsEntrance, catacombs;
    int completedEvents = 0;

    /**
     * Constructor for EventManager.
     * Initializes all events.
     */
    public EventManager() {
        int num = 1;
        babysitter = new WorldEvent(num, 1, 0, 0, 0, 0);
        babysitter.setDescription("Every hero has to start somewhere... The local school's teacher has asked you to escort the children to the nearby village.");
        babysitter.setOption(0, new Option("Accept", "You safely escort the children and the teacher is grateful.", 0, 5, 0, 0, 0, 0));
        babysitter.setOption(1, new Option("Accept and buy them breakfast.", "You treat the children to a tasty breakfast on the road. That's all it takes to get them to like you.", 2, 10, -5, 0, 0, 0));
        babysitter.setOption(2, new Option("Refuse.", "Sometimes you'd rather just sleep...", 5, 0, 0, 0, 0, 0));
        babysitter.setOption(3, new Option("Accept, but kill the children on the way there.", "The village won't forget that.", 0, -25, 0, 0, 0, 0));
        num++;

        cat = new WorldEvent(num, 1, 0, 0, 0, 0);
        cat.setDescription("Every hero has to start somewhere... A lady has asked you to rescue her cat from a tree.");
        cat.setOption(0, new Option("Climb the tree and grab the cat.", "The cat scratches your arm on the way down. Ouch!", -2, 5, 0, 0, 0, 0));
        cat.setOption(1, new Option("Pet the cat and bring it back to the lady.", "The cat seems to like you.", 0, 5, 0, 0, 0, 1));
        cat.setOption(2, new Option("Walk away.", "The lady seems disappointed.", 0, -3, 0, 0, 0, 0));
        cat.setOption(3, new Option("Chop down the tree.", "The lady calls the king's guards, but they decide to help you, because the king hates that tree.", 0, -10, 0, +10, 0, 1));
        num++;

        investigation = new WorldEvent(num, 1, 0, 0, 0, 0);
        investigation.setDescription("You've heard rumors of a mysterious figure roaming near the catacombs. Should you investigate?");
        investigation.setOption(0, new Option("Yes", "You arrive at night. As you approach the hooded figure, it runs into the catacombs.", 0, 0, 0, 0, 0, 0));
        investigation.setOption(1, new Option("No", "Some things are best left in mystery...", 0, 0, 0, 0, 0, 0));
        num++;

        catacombsEntrance = new WorldEvent(num, 0, 0, 0, 0, 0);
        investigation.options[0].setNextEvent(catacombsEntrance);
        catacombsEntrance.setDescription("The mysterious figure ran into the catacombs. What will you do?");
        catacombsEntrance.setOption(0, new Option("Chase it into the catacombs.", "As you run into the catacombs, a sense of dread befalls you.", 0, 0, 0, 0, 0, -3));
        catacombsEntrance.setOption(1, new Option("The stone archway marking the entrance of the catacombs seems frail. Perhaps you could collapse it to block the entrance...", "After only a few blows, the entrance collapses.", 0, -5, 0, 0, 0, 0));
        catacombsEntrance.setOption(2, new Option("Walk away.", "As you walk back, you hear faint echoes coming from the catacombs. None of your business.", 0, 0, 0, 0, 0, 0));
        num++;

        catacombs = new WorldEvent(num, 0, 0, 0, 0, 0);
        catacombsEntrance.options[0].setNextEvent(catacombs);
        catacombs.setDescription("You follow the echoes of an ancient ritual until you find the hooded figure. It has transformed into a demon.");
        catacombs.setOption(0, new Option("Draw your sword and get ready to fight.", "You manage to defeat the demon, but it's cursed you!", -10, 10, 0, 0, 0, 0));
        catacombs.options[0].setItem(Hero.CURSE);
        catacombs.setOption(1, new Option("Bargain for a pact with the demon.", "The demon seems to have taken a liking to you, and it gifts you a magic wand!", 0, 0, 0, 0, 0, 0));
        catacombs.options[1].setItem(Hero.WAND);
        catacombs.setOption(2, new Option("Magic: Banish the demon back to hell.", "With a zap of your magic wand, the demon is sent back to hell. How ironic...", 0, 15, 0, 0, 0, 0));
        catacombs.options[2].setMagic(true);
        num++;

    }

    private WorldEvent getRandomEvent() {
        ArrayList<WorldEvent> possibleEvents = new ArrayList<>();
        if (completedEvents == 0) {
            possibleEvents.add(babysitter);
            possibleEvents.add(cat);
        } else {
            possibleEvents.add(investigation);
        }
        int weightSum = 0;
        for (WorldEvent e : possibleEvents) {
            weightSum += e.fullWeight();
        }
        Random rand = new Random();
        int r = rand.nextInt(weightSum) + 1;
        for (WorldEvent e : possibleEvents) {
            r -= e.fullWeight();
            if (r <= 0) return e;
        }
        return null; //should never get here
    }

    /**
     * Function which sets the new current event.
     * If the option previously chosen by the user calls for a chain event, it will be selected.
     * If there's no predetermined event to be played next, a random one will be chosen.
     */
    public void getEvent() {
        if (nextEvent != null) {
            currentEvent = nextEvent;
            nextEvent = null;
        } else
            currentEvent = getRandomEvent();
    }

    /**
     * Updates the hero's statistics when an option is chosen.
     * @param n The option number (0=North, 1=South, 2=West, 3=East)
     */
    public void pickOption(int n) {
        currentEvent.options[n].pick();
        completedEvents++;
        if (currentEvent.options[n].nextEvent != null)
            nextEvent = currentEvent.options[n].nextEvent;
    }

    /**
     * Returns the internal number of the current event.
     * The number is defined in the EventManager constructor and is often subject to change.
     * @return int (1 is the first event)
     */
    public int getEventNumber() {
        return currentEvent.num;
    }

    public String getEventDescription() {
        return currentEvent.description;
    }

    public String getDescN() {
        return currentEvent.options[0].description;
    }

    public String getDescS() {
        return currentEvent.options[1].description;
    }

    public String getDescW() {
        return currentEvent.options[2].description;
    }

    public String getDescE() {
        return currentEvent.options[3].description;
    }

    public String getResultN() {
        return currentEvent.options[0].result;
    }

    public String getResultS() {
        return currentEvent.options[1].result;
    }

    public String getResultW() {
        return currentEvent.options[2].result;
    }

    public String getResultE() {
        return currentEvent.options[3].result;
    }
}
