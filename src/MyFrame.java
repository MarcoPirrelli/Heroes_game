import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.*;
import java.util.Timer;




public class MyFrame extends JFrame implements ActionListener {

    //remember to change it
    String path_resources = "Resources/";
    Font f = new Font("serif", Font.PLAIN, 10);

    int width = 1920;
    int height = 1080;

    EventManager ev = new EventManager();

    //Hero Time
    int time;
    JLabel textyear;
    JLabel textheroage;
    Timer timertime = new Timer();
    Timer timerevent = new Timer();

    //JLabel
    JLabel backgroundimage;
    JLabel eventimage;
    JLabel eventtext;
    JLabel heroname;
    JLabel heroimage;
    JLabel loyaltyimage;

    //JButtons
    private final JButton b_newgame, b_load, b_settings, b_exit;
    private final JButton b_back;
    private final JButton click;
    //private final JButton b_n, b_s, b_w, b_e;
    private final JButton b_w, b_e, b_n, b_s;

    //Variables
    int a = 0;
    String text = "";
    String name;

    boolean KeyPressed = false;

    MyFrame () {

        JLayeredPane frame = new JLayeredPane();

        //Background Image
        backgroundimage = new JLabel(new ImageIcon());
        backgroundimage.setIcon(new ImageIcon(path_resources + "startgif.gif"));

        //Menu Buttons
        b_newgame = new JButton("New Game");
        b_load = new JButton("Load");
        b_settings = new JButton("Settings");
        b_exit = new JButton("Exit");

        JButton[] d0 = {b_newgame, b_load, b_settings, b_exit};

        for (JButton b : d0){
            b.addActionListener(this);
            b.setIcon(new ImageIcon (path_resources + "buttonstart.png"));
            b.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
            b.setVerticalTextPosition(JButton.CENTER);
        }

        //Settings Button
        b_back = new JButton("Menu");
        b_back.addActionListener(this);
        b_back.setIcon(new ImageIcon(path_resources + "buttonstart.png"));
        b_back.setHorizontalTextPosition(JButton.CENTER);
        b_back.setVerticalTextPosition(JButton.CENTER);

        //Event Image and Description
        eventimage = new JLabel(new ImageIcon());
        eventtext = new JLabel("Questo è l'evento");
        eventtext.setHorizontalAlignment(SwingConstants.CENTER);
        float size = 20;
        eventtext.setFont(eventtext.getFont().deriveFont(size));

        //Event Button
        click = new JButton(); //to click on nowhere
        click.addActionListener(this);

        b_w = new JButton("WEST");
        b_e = new JButton("EAST");
        b_n = new JButton("NORTH");
        b_s = new JButton("SOUTH");



        JButton[] d1 = {b_w, b_e, b_n, b_s};

        for (JButton b : d1){
            b.addActionListener(this);
            b.setIcon(new ImageIcon (path_resources + "scudo1.png"));
            b.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
            b.setVerticalTextPosition(JButton.CENTER);
        }

        loyaltyimage = new JLabel(new ImageIcon());

        //Timer
        time = 0;
        textheroage = new JLabel(String.valueOf(Hero.getAge()));
        textyear = new JLabel(String.valueOf(time));

        //Her
        heroname = new JLabel(name);
        heroimage = new JLabel(new ImageIcon());

        //Menu Frame
        frame.add(backgroundimage, 1, 0);  //c'è anche la versione con l'indice e basta ma non funziona
        frame.add(b_newgame, 3,0);
        frame.add(b_load, 3,0);
        frame.add(b_settings, 3,0);
        frame.add(b_exit, 3,0);

        backgroundimage.setBounds(0,0, 1920,1080);
        b_newgame.setBounds(1200,100, 590, 100);
        b_load.setBounds(1200,250,590,100);
        b_settings.setBounds(1200,400,590,100);
        b_exit.setBounds(1200,550,590,100);

        frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        frame.getActionMap().put("Escape", new Escape());

        frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "Down");
        frame.getActionMap().put("Down", new GoDown());

        setContentPane(frame);
        //setSize(Toolkit.getDefaultToolkit().getScreenSize());  //prova con uno di questi due a vedere se funziona?
         setExtendedState(JFrame.MAXIMIZED_BOTH);

        setUndecorated(true);
      //setSize(width, height);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);


    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == b_newgame){

            ev.getEvent();

            //remove menubuttons
            this.remove(b_newgame);
            this.remove(b_load);
            this.remove(b_settings);
            this.remove(b_exit);
            this.remove(b_back);

            //Add and setting things
            backgroundimage.setIcon(new ImageIcon(path_resources + "arazzogif.gif"));

            this.add(click, 3,0);
            click.setOpaque(false);
            click.setContentAreaFilled(false);
            click.setBorderPainted(false);
            click.setBounds(0, 0, width, height);

            this.add(heroname, 2,0);
            float size0 = 25;
            heroname.setBounds(70,30, 200,50);
            heroname.setForeground(Color.WHITE);
            heroname.setFont(heroname.getFont().deriveFont(size0));
            heroname.setText(Hero.getHeroName());

            this.add(heroimage, 2,0);
            heroimage.setIcon(new ImageIcon(path_resources + "Heroes/" + Hero.getHeroName() + ".jpg"));
            heroimage.setBounds(50, 90, 150,150);


            this.add(eventimage, 2, 0);
            this.add(eventtext, 2,0);
            timerevent.schedule(taskevent, 2000); //delay for the animation

            this.add(textyear,2,0);
            float size1 = 20;
            textyear.setBounds(50, 900, 200, 200);
            textyear.setFont(textyear.getFont().deriveFont(size1));
            textyear.setForeground(Color.WHITE);
            timertime.schedule(timerchange, 0, 5000); //the age changes every 5 sec

            this.add(textheroage,2,0);
            float size2 = 20;
            textheroage.setBounds(90, 55, 200, 50);
            textheroage.setFont(textheroage.getFont().deriveFont(size2));
            textheroage.setForeground(Color.WHITE);


            this.add(loyaltyimage, 2, 0);
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
            loyaltyimage.setBounds(700, 0, 150, 130);

            backgroundimage.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Continue");
            backgroundimage.getActionMap().put("Continue", new Continue());

        }

        if(e.getSource() == b_load){
            //if games are saved, here we will load them!
        }

        if(e.getSource() == b_settings){

            this.remove(b_newgame);
            this.remove(b_load);
            this.remove(b_settings);
            this.remove(b_exit);

            backgroundimage.setIcon(new ImageIcon(path_resources + "settings.jpg"));
            this.add(b_back, 3, 0);
            b_back.setBounds(1200,100, 590, 100);


        }

        if(e.getSource() == b_exit) {
            System.exit(0);
        }

        if(e.getSource() == b_back){

            this.add(b_newgame, 3, 0);
            this.add(b_load,3 ,0);
            this.add(b_settings, 3,0);
            this.add(b_exit, 3,0);

        }

        if(e.getSource() == click) {

            //clicking makes the shields to appear and disappear
        switch (a) {
            case 0:

                setButtons();
                a = 1;

            break;

            case 1:

                removeButtons();
                a = 0;

            break;

            case 2:
                ev.getEvent();
                this.add(eventimage, 2, 0);
                this.add(eventtext, 2,0);
                setDescription(ev.getEventDescription());
                a = 0;

                break;


        }

        }

        if (e.getSource() == b_w){

            removeButtons();
            ev.pickOption(0);
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
            setDescription(ev.getResult(0));
            a = 2;
        }

        if (e.getSource() == b_e){

            removeButtons();
            ev.pickOption(1);
            setDescription(ev.getResult(1));
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
            a = 2;
        }

        if (e.getSource() == b_n){

            removeButtons();
            ev.pickOption(2);
            setDescription(ev.getResult(2));
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
            a = 2;
        }

        if (e.getSource() == b_s){

            removeButtons();
            ev.pickOption(3);
            setDescription(ev.getResult(3));
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
            a = 2;
        }

    }

    /**
     * set description of the options
     * @param b
     * @param textbutton
     */
    public void setDescriptionButton(JButton b, String textbutton){

        if(textbutton.length() <= 20){
            b.setText(textbutton);
        }
        else if(textbutton.length() <= 40) {
            int c = 15;
            while (textbutton.charAt(c) != '.'&& textbutton.charAt(c) != ' ' && textbutton.charAt(c) != ','){
                c++;
            }
            b.setText("<html><div style='text-align: center;'>"+ textbutton.substring(0, c + 1) + "<br>" + textbutton.substring(c + 1,textbutton.length()) + "</div><html>");
        }
        else{
            int c = 15;
            while (textbutton.charAt(c) != '.'&& textbutton.charAt(c) != ' ' && textbutton.charAt(c) != ','){
                c++;
            }
            int d = 35;
            while (textbutton.charAt(d) != '.'&& textbutton.charAt(d) != ' ' && textbutton.charAt(d) != ','){
                d++;
            }
            b.setText("<html><div style='text-align: center;'>"+ textbutton.substring(0, c + 1) + "<br>" + textbutton.substring(c+1, d+1)+ "<br>" + textbutton.substring(d + 1, textbutton.length()) + "</div><html>");
        }

    }

    /**
     * set description of the event/consequences
     * @param description
     */
    public void setDescription(String description){

        //va cambiato con l'immagine risultante
        eventimage.setIcon(new ImageIcon(path_resources + "e" + ev.getEventNumber() + ".jpg"));
        eventimage.setBounds(760,160,400, 640);

        //text event on screen so that every line isn't interrupted
        int length = text.length();
        if (description.length() <= 40) {
            eventtext.setText(description);
        }

        else if ( description.length() <= 80 ){
            int   c = 35;
            while (description.charAt(c) != '.'&& description.charAt(c) != ' ' && description.charAt(c) != ','){
                c++;
            }
            eventtext.setText("<html><div style='text-align: center;'>"+description.substring(0, c + 1)+ "<br>" + description.substring(c + 1, description.length()) + "</div><html>");
        }

        else if (description.length() <= 120) {
            int   c = 35;
            int   d = 75;
            while (description.charAt(c) != '.'&& description.charAt(c) != ' ' && description.charAt(c) != ','){
                c++;
            }
            while (description.charAt(d) != '.'&& description.charAt(d) != ' ' && description.charAt(d) != ','){
                d++;
            }
            eventtext.setText("<html><div style='text-align: center;'>"+ description.substring(0, c + 1)+ "<br>"+ description.substring(c + 1, d)+ "<br>" + description.substring(d + 1, description.length()) + "</div><html>");

        }

        else {
            int   c = 35;
            int   d = 75;
            int   e = 110;
            while (description.charAt(c) != '.'&& description.charAt(c) != ' ' && description.charAt(c) != ','){
                c++;
            }
            while (description.charAt(d) != '.'&& description.charAt(d) != ' ' && description.charAt(d) != ','){
                d++;
            }
            while (description.charAt(e) != '.'&& description.charAt(e) != ' ' && description.charAt(e) != ','){
                e++;
            }
            eventtext.setText("<html><div style='text-align: center;'>"+ description.substring(0, c + 1)+ "<br>"+ description.substring(c + 1, d)+ "<br>" + description.substring(d + 1, e) + "<br>" + description.substring(e+1, description.length()) + "</div><html>");

        }

        eventtext.setBounds(720,825, 470, 150);
    }

    public void KeyConsequence(int n){

        setDescription(ev.getResult(n));

        removeButtons();

        ev.pickOption(n);
        loyaltyimage.setIcon(new ImageIcon("Resources/" + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
        loyaltyimage.setBounds(700, 0, 150, 130);


    }

    public void setButtons (){
         if (ev.getOptionNumber() == 4){
                JButton[] d1 = {b_w, b_e, b_n, b_s};

                float size = 20;
                for (JButton b : d1){
                    this.add(b, 3, 0);
                    b.setContentAreaFilled(false);
                    b.setBorderPainted(false);
                    b.setFont(heroname.getFont().deriveFont(size));
                }

                    setDescriptionButton(b_w, ev.getDesc(0));
                    setDescriptionButton(b_e, ev.getDesc(1));
                    setDescriptionButton(b_n, ev.getDesc(2));
                    setDescriptionButton(b_s, ev.getDesc(3));

                    b_w.setBounds(565, 350, 280, 330);
                    b_e.setBounds(1045, 350, 280, 330);
                    b_n.setBounds(820, 80, 280, 330);
                    b_s.setBounds(820, 580, 280, 330);


                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "North");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "North");
                    b_w.getActionMap().put("North", new SelectNorth());
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "South");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "South");
                    b_w.getActionMap().put("South", new SelectSouth());
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "West");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "West");
                    b_w.getActionMap().put("West", new SelectWest());
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "East");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "East");
                    b_w.getActionMap().put("East", new SelectEast());


                }
                else if (ev.getOptionNumber() == 3){

                    JButton[] d1 = {b_w, b_e, b_n};

                    float size = 20;
                    for (JButton b : d1){
                        this.add(b, 3, 0);
                        b.setContentAreaFilled(false);
                        b.setBorderPainted(false);
                        b.setFont(heroname.getFont().deriveFont(size));
                    }

                    setDescriptionButton(b_w, ev.getDesc(0));
                    setDescriptionButton(b_e, ev.getDesc(1));
                    setDescriptionButton(b_n, ev.getDesc(2));

                    b_w.setBounds(565, 350, 280, 330);
                    b_e.setBounds(1045, 350, 280, 330);
                    b_n.setBounds(820, 80, 280, 330);

                    //Key binding KeyEvent.VK_SPACE
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "North");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "North");
                    b_w.getActionMap().put("North", new SelectNorth());
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "South");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "South");
                    b_w.getActionMap().put("South", new SelectSouth());
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "West");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "West");
                    b_w.getActionMap().put("West", new SelectWest());

            }
                else if (ev.getOptionNumber() == 2){

                    JButton[] d1 = {b_w, b_e};

                    float size = 20;
                    for (JButton b : d1){
                        this.add(b, 3, 0);
                        b.setContentAreaFilled(false);
                        b.setBorderPainted(false);
                        b.setFont(heroname.getFont().deriveFont(size));
                    }

                    setDescriptionButton(b_w, ev.getDesc(0));
                    setDescriptionButton(b_e, ev.getDesc(1));

                    b_w.setBounds(565, 350, 280, 330);
                    b_e.setBounds(1045, 350, 280, 330);


                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "North");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "North");
                    b_w.getActionMap().put("North", new SelectNorth());
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "South");
                    b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "South");
                    b_w.getActionMap().put("South", new SelectSouth());
                }
    }

    public void removeButtons(){
        if (ev.getOptionNumber() == 4){
            this.remove(b_w);
            this.remove(b_e);
            this.remove(b_n);
            this.remove(b_s);

        }

        else if (ev.getOptionNumber() ==3){
            this.remove(b_w);
            this.remove(b_e);
            this.remove(b_n);
        }

        else if (ev.getOptionNumber() ==2){
            this.remove(b_w);
            this.remove(b_e);
        }
        repaint();  //to remove components it is better to call it

    }

    //timer for time
    TimerTask timerchange = new TimerTask() {
        @Override
        public void run() {
            time++;
            textheroage.setText("Age: " + String.valueOf(Hero.getAge()));
            textyear.setText("Years of service: " + time);

        }
    };

    //timer so that the event starts after the animation's end
    TimerTask taskevent = new TimerTask() {
        @Override
        public void run() {
            //event image on screen
            eventimage.setIcon(new ImageIcon(path_resources + "e" + ev.getEventNumber() + ".jpg"));
            eventimage.setBounds(760,160,400, 640);

            setDescription(ev.getEventDescription());


        }
    };


    private class SelectWest extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
           if(a == 1){
           KeyConsequence(0);
           a = 2;
           }

        }
    }

    private class SelectEast extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(a == 1){
            KeyConsequence(1);
            a = 2;
            }
        }
    }

    private class SelectNorth extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(a == 1){
            if (ev.getOptionNumber() == 3 || ev.getOptionNumber() == 4){
                KeyConsequence(2);
                a = 2;
            } }

        }
    }

    private class SelectSouth extends AbstractAction{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(a == 1){
            if( ev.getOptionNumber() == 4){
                KeyConsequence(3);
                a = 2;
            }}
        }
    }

    private class Continue extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (a){
                case 0:

                    add(eventimage, 2, 0);
                    add(eventtext, 2,0);

                    setButtons();
                    repaint();

                    setDescription(ev.getEventDescription());
                    a = 1;
                    break;

                case 1:
                    removeButtons();
                    repaint();
                    a = 0;
                break;

                case 2:
                    ev.getEvent();
                    add(eventimage, 2, 0);
                    add(eventtext, 2,0);

                    setButtons();
                    repaint();

                    setDescription(ev.getEventDescription());

                    a = 1;
                    break;
            }
        }
    }


    private class Escape extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    private class GoDown extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            //System.exit(0);
        }
    }
}