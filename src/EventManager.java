import java.lang.reflect.Array;
import java.util.*;

public class EventManager {

    WorldEvent e;
    WorldEvent babysitter, cat, beh, baby, c;
    int completedEvents = 0;

    int currentEventNumber = 0;
    String currentDescription;
    Option[] currentOptions = new Option[4];

    String[] names;
    String heroname;

    int numberevent = 4;
    int [] newevents = new int[4];
    int number;

    Option option0, option1,option2,option3; //per le opzioni
    String descN, descS, descW, descE; //scelte dell'opzione
    String optionN, optionS, optionW, optionE; //risultati opzioni

    public EventManager() {

        names = new String[]{"Certosino", "Guglielmo", "Tenebroso", "Kight", "Hero", "Ben", "John", "Mikela", "Sister Graziana"};

        babysitter = new WorldEvent(1, 0, 0, 0, 0);
        babysitter.setNumber(0);
        babysitter.setDescription("Every hero has to start somewhere... The local school's teacher has asked you to escort the children to the nearby village.");
        babysitter.setOption(0, new Option("Accept", "You safely escort the children and the teacher is grateful.", 0, 5, 0, 0, 0, 0));
        babysitter.setOption(1, new Option("Accept and buy them breakfast.", "You treat the children to a tasty breakfast on the road. That's all it takes to get them to like you.", 2, 10, -5, 0, 0, 0));
        babysitter.setOption(2, new Option("Refuse.", "Sometimes you'd rather just sleep...", 5, 0, 0, 0, 0, 0));
        babysitter.setOption(3, new Option("Accept, but kill the children on the way there.", "The village won't forget that.", 0, -25, 0, 0, 0, 0));

        cat = new WorldEvent(1, 0, 0, 0, 0);
        cat.setNumber(1);
        cat.setDescription("Every hero has to start somewhere... A lady has asked you to rescue her cat from a tree.");
        cat.setOption(0, new Option("Climb the tree and grab the cat.", "The cat scratches your arm on the way down. Ouch!", -2, 5, 0, 0, 0, 0));
        cat.setOption(1, new Option("Pet the cat and bring it back to the lady.", "The cat seems to like you.", 0, 5, 0, 0, 0, 1));
        cat.setOption(2, new Option("Walk away.", "The lady seems disappointed.", 0, -3, 0, 0, 0, 0));
        cat.setOption(3, new Option("Chop down the tree.", "The lady calls the king's guards, but they decide to help you, because the king hates that tree.", 0, -10, 0, +10, 0, 1));

        baby = new WorldEvent(1, 0, 0, 0, 0);
        baby.setNumber(2);
        baby.setDescription("Every hero has to start somewhere... The local school's teacher has asked you to escort the children to the nearby village.");
        baby.setOption(0, new Option("Accept", "You safely escort the children and the teacher is grateful.", 0, 5, 0, 0, 0, 0));
        baby.setOption(1, new Option("Accept and buy them breakfast.", "You treat the children to a tasty breakfast on the road. That's all it takes to get them to like you.", 2, 10, -5, 0, 0, 0));
        baby.setOption(2, new Option("Refuse.", "Sometimes you'd rather just sleep...", 5, 0, 0, 0, 0, 0));
        baby.setOption(3, new Option("Accept, but kill the children on the way there.", "The village won't forget that.", 0, -25, 0, 0, 0, 0));

        c = new WorldEvent(1, 0, 0, 0, 0);
        c.setNumber(3);
        c.setDescription("The stone archway marking the entrance of the catacombs seems frail. Perhaps you could collapse it to block the entrance...");
        c.setOption(0, new Option("Climb the tree and grab the cat.", "The cat scratches your arm on the way down. Ouch!", -2, 5, 0, 0, 0, 0));
        c.setOption(1, new Option("Pet the cat and bring it back to the lady.", "The cat seems to like you.", 0, 5, 0, 0, 0, 1));
        c.setOption(2, new Option("Walk away.", "The lady seems disappointed.", 0, -3, 0, 0, 0, 0));
        c.setOption(3, new Option("Chop down the tree.", "The lady calls the king's guards, but they decide to help you, because the king hates that tree.", 0, -10, 0, +10, 0, 1));



    }

    public WorldEvent getEvent(){

        for (int i = 0; i < numberevent ; i ++){
            // int newevents[] = new int[i+1];
            newevents[i] = i;
        }

        int min = 0;
        int max = newevents.length - 1;
        int randomnumber = (int)Math.floor(Math.random()*(max-min+1)+min);
        number = (int)Array.get(newevents, randomnumber);

        ArrayList<WorldEvent> possibleEvents = new ArrayList<>();
        possibleEvents.add(babysitter);
        possibleEvents.add(cat);
        possibleEvents.add(baby);
        possibleEvents.add(c);

        WorldEvent e = possibleEvents.get(number);
        currentDescription = e.description;
        currentOptions = e.options;

        option0 = currentOptions[0];
        descN = option0.description;
        optionN = option0.result;

        option1 = currentOptions[1];
        descS = option1.description;
        optionS = option1.result;

        option2 = currentOptions[2];
        descW = option2.description;
        optionW = option2.result;

        option3 = currentOptions[3];
        descE = option3.description;
        optionE = option3.result;

        currentEventNumber = randomnumber;

        return e;
    }

    /*public WorldEvent getRandomEvent() {
        ArrayList<WorldEvent> possibleEvents = new ArrayList<>();  //possibleevents array containts all possible events
        if (completedEvents == 0) {

          possibleEvents.add(babysitter);
           possibleEvents.add(cat);
        }

      int weightSum = 0;    //chiedere spiegazioni?
        for (WorldEvent e : possibleEvents) {
            weightSum += e.fullWeight();
        }

        Random rand = new Random();
        int r = rand.nextInt(weightSum)+1;

        for (WorldEvent e : possibleEvents){
           // currentDescription = e.description;
            r -= e.fullWeight();
            if(r<=0) return e;

        }

      return null; //should never get here
    }
*/

    public String getHeroName () {
        int min = 0;
        int max = names.length - 1;
        int randomnumber = (int)Math.floor(Math.random()*(max-min+1)+min);
        heroname = (String)Array.get(names, randomnumber);
        return heroname;
    }

    public int getEventNumber (){
        return currentEventNumber;
    }

    public String getEventDescription (){
        return currentDescription;
    }

    public String getResultN(){
        return optionN;
    }

    public String getResultS(){
        return optionS;
    }

    public String getResultW(){
        return optionW;
    }

    public String getResultE(){
        return optionE;
    }

    public String getDescN(){
        return descN;
    }
    public String getDescS(){
        return descS;
    }
    public String getDescW(){
        return descW;
    }
    public String getDescE(){
        return descE;
    }

   /* public int getTime(){
        return time;
    }

    public void starttime (){
        timertime.schedule(timerchange, 50, 50); //Ogni 5 secondi
        }

    TimerTask timerchange = new TimerTask() {
        @Override
        public void run() {
        time++;
        }
    };*/

}
