import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Main Frame
 * List of functions:
 * Constructor, game listener, action performed (buttons), Menu, searchSlot, newGame, load, settings, setDeath, afterDeath, setShields
 * removeShields, setDescriptionShields, setDescription, optionConsequences, setStatistics, removeGameObject, changeFocus, Timer (time and event),
 * override for keyboard for all buttons on game
 */
public class MyFrame extends JFrame implements ActionListener, GameListener {

    String path_resources = "src" + File.separator + "main" + File.separator + "resources" + File.separator;
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

    //some dimensions
    private final int width = screenSize.width;
    private final int height = screenSize.height;
    // int width = 1280;
    // int height = 720;

    private final int width_shield = width * 100 / 685;
    private final int height_shield = height * 100 / 327;

    //Hero
    private final JLabel text_year, hero_age, hero_name, hero_image;
    private Timer timer_event = new Timer();
    private final JPanel hero_panel;


    //Bg and Event
    private final JLabel background_image, event_image, event_text;

    //Statistics
    private final JLabel health_image, fame_image, money_image, loyalty_image, mana_image;
    private final JPanel statistics_panel;
    private final GridBagConstraints con1;

    //Artifacts
    private final JLabel art0, art1, art2, art3, art;
    private final JPanel artifacts_panel;
    private final GridBagConstraints con3;

    //Death
    private final JLabel death;
    private final JPanel death_panel;

    //Overwrite
    private final JLabel overwrite;

    //Achievement
    private final JLabel achie;
    private Timer timer_achievement = new Timer();


    //JButtons
    private final JButton b_newGame, b_load, b_tutorial, b_exit;
    private final JButton b_back;
    private final JButton click;
    private final JButton b_w, b_e, b_n, b_s;
    private final JButton b_slot1, b_slot2, b_slot3;
    private final JButton b_d_newGame, b_d_exit;
    private final JButton[] start_button;
    private final JPanel start_panel;
    private final JPanel slot_panel;


    //State variables
    private int state_game = 0;
    private int state_menuButton = 0;
    private int state_loadButton = 1;
    private int state_deathButton = 0;
    private boolean is_gaming = false;
    private boolean is_loading = true;
    private boolean menu = true;

    Sounds sound = new Sounds();

    MyFrame() {
        EventManager.addGameListener(this);
        JLayeredPane frame = new JLayeredPane();

        //Background Image
        JPanel back = new JPanel(new BorderLayout());
        background_image = new JLabel(new ImageIcon());
        background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "b0.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        back.add(background_image);


        //Menu Buttons
        b_newGame = new JButton("New Game");
        b_load = new JButton("Load");
        b_tutorial = new JButton("Tutorial");
        b_exit = new JButton("Exit");
        float size_start = width / 96;

        start_button = new JButton[]{b_newGame, b_load, b_tutorial, b_exit};
        for (JButton b : start_button) {
            b.addActionListener(this);
            b.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_start.png").getImage().getScaledInstance(width / 3, height / 10, Image.SCALE_SMOOTH)));
            b.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
            b.setVerticalTextPosition(JButton.CENTER);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
            b.setFont(b.getFont().deriveFont(size_start));
        }
        b_newGame.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_start_sel.png").getImage().getScaledInstance(width / 3, height / 10, Image.SCALE_SMOOTH)));

        start_panel = new JPanel(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();

        Insets i = new Insets(0, 0, height / 45, 0);
        con.gridx = 2;
        con.gridy = 0;
        con.weightx = 2;
        con.insets = i;
        start_panel.add(b_newGame, con);

        con.gridx = 2;
        con.gridy = 1;
        start_panel.add(b_load, con);

        con.gridx = 2;
        con.gridy = 2;
        start_panel.add(b_tutorial, con);

        con.gridx = 2;
        con.gridy = 3;
        start_panel.add(b_exit, con);

        JLabel none = new JLabel();
        con.gridx = 0;
        con.gridy = 0;
        start_panel.add(none, con);

        JLabel none1 = new JLabel();
        con.gridx = 1;
        con.gridy = 0;
        start_panel.add(none1, con);

        start_panel.setOpaque(false);

        //Event Image and Description
        event_image = new JLabel(new ImageIcon());
        event_text = new JLabel("This is the event");
        event_text.setHorizontalAlignment(SwingConstants.CENTER);
        float size_event = width / 100;
        event_text.setFont(event_text.getFont().deriveFont(size_event));

        //Event Button
        click = new JButton(); //to click on nowhere
        click.addActionListener(this);
        click.setOpaque(false);
        click.setContentAreaFilled(false);
        click.setBorderPainted(false);

        b_w = new JButton("WEST");
        b_e = new JButton("EAST");
        b_n = new JButton("NORTH");
        b_s = new JButton("SOUTH");

        JButton[] d1 = {b_w, b_e, b_n, b_s};
        for (JButton b : d1) {
            b.addActionListener(this);
            b.setIcon((new ImageIcon(new ImageIcon(path_resources + "shield.png").getImage().getScaledInstance(width_shield, height_shield, Image.SCALE_SMOOTH))));
            b.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
            b.setVerticalTextPosition(JButton.CENTER);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
        }

        //Statistics
        health_image = new JLabel(new ImageIcon());
        fame_image = new JLabel(new ImageIcon());
        money_image = new JLabel(new ImageIcon());
        loyalty_image = new JLabel(new ImageIcon());
        mana_image = new JLabel(new ImageIcon());
        float size_s = width / 110;

        JLabel[] stat_label = {health_image, fame_image, money_image, loyalty_image, mana_image};

        for (JLabel s : stat_label) {
            s.setHorizontalTextPosition(JLabel.CENTER);
            s.setVerticalTextPosition(JLabel.CENTER);
            s.setForeground(Color.BLACK);
            s.setFont(s.getFont().deriveFont(size_s));
        }

        statistics_panel = new JPanel(new GridBagLayout());
        con1 = new GridBagConstraints();

        Insets i1 = new Insets(0, height / 108, 0, height / 108);

        con1.gridx = 0;
        con1.gridy = 0;
        con1.insets = i1;
        statistics_panel.add(health_image, con1);

        con1.gridx = 1;
        con1.gridy = 0;
        statistics_panel.add(fame_image, con1);

        con1.gridx = 2;
        con1.gridy = 0;
        statistics_panel.add(loyalty_image, con1);

        con1.gridx = 3;
        con1.gridy = 0;
        statistics_panel.add(money_image, con1);

        con1.gridx = 4;
        con1.gridy = 0;
        statistics_panel.add(mana_image, con1);

        statistics_panel.setOpaque(false);

        //achievement
        achie = new JLabel();

        //Hero
        hero_name = new JLabel();
        hero_image = new JLabel(new ImageIcon());
        hero_age = new JLabel(String.valueOf(Hero.getAge()));
        text_year = new JLabel(String.valueOf(Hero.getYearsOfService()));

        float size_name = width / 85;
        hero_name.setForeground(Color.WHITE);
        hero_name.setFont(hero_name.getFont().deriveFont(size_name));
        float size_age = width / 100;
        hero_age.setForeground(Color.WHITE);
        hero_age.setFont(hero_age.getFont().deriveFont(size_age));
        float size_year = width / 90;
        text_year.setFont(text_year.getFont().deriveFont(size_year));
        text_year.setForeground(Color.WHITE);


        hero_panel = new JPanel(new GridBagLayout());
        GridBagConstraints con2 = new GridBagConstraints();

        con2.gridx = 0;
        con2.gridy = 0;
        con2.insets = i1;
        hero_panel.add(hero_name, con2);

        con2.gridx = 0;
        con2.gridy = 1;
        con2.gridheight = 1;
        hero_panel.add(hero_age, con2);

        con2.gridx = 0;
        con2.gridy = 2;
        con2.gridheight = 2;
        hero_panel.add(hero_image, con2);

        hero_panel.setOpaque(false);

        //Artifacts
        art0 = new JLabel();
        art1 = new JLabel();
        art2 = new JLabel();
        art3 = new JLabel();
        art = new JLabel();

        art0.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts" + File.separator + "Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_SMOOTH)));
        art1.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts" + File.separator + "Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_SMOOTH)));
        art2.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts" + File.separator + "Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_SMOOTH)));
        art3.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts" + File.separator + "Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_SMOOTH)));
        art.setIcon(new ImageIcon(new ImageIcon(path_resources + "artifacts.png").getImage().getScaledInstance(width / 5, height * 10 / 63, Image.SCALE_SMOOTH)));

        artifacts_panel = new JPanel(new GridBagLayout());
        con3 = new GridBagConstraints();

        Insets i3 = new Insets(0, width / 256, 0, width / 256);
        con3.gridx = 0;
        con3.gridy = 1;
        con3.insets = i3;
        artifacts_panel.add(art0, con3);

        con3.gridx = 1;
        con3.gridy = 1;
        artifacts_panel.add(art1, con3);

        con3.gridx = 2;
        con3.gridy = 1;
        artifacts_panel.add(art2, con3);

        con3.gridx = 3;
        con3.gridy = 1;
        artifacts_panel.add(art3, con3);

        artifacts_panel.setOpaque(false);

        //Overwrite and Load
        overwrite = new JLabel("The slots are full, which save do you want to overwrite?");
        float size_overwrite = width / 75;
        overwrite.setForeground(Color.WHITE);
        overwrite.setFont(overwrite.getFont().deriveFont(size_overwrite));

        b_slot1 = new JButton();
        b_slot2 = new JButton();
        b_slot3 = new JButton();

        JButton[] slot_button = {b_slot1, b_slot2, b_slot3};
        for (JButton b : slot_button) {
            b.addActionListener(this);
            b.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
            b.setHorizontalTextPosition(JButton.CENTER);
            b.setVerticalTextPosition(JButton.CENTER);
            float size_load = width / 90;
            b.setFont(b.getFont().deriveFont(size_load));
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
        }

        slot_panel = new JPanel(new GridBagLayout());

        GridBagConstraints con5 = new GridBagConstraints();
        Insets i4 = new Insets(height / 12, 0, 0, 0);

        con5.gridx = 1;
        con5.gridy = 1;
        con5.insets = i4;
        slot_panel.add(b_slot1, con5);

        Insets i5 = new Insets(height / 21, 0, 0, 0);
        con5.gridx = 1;
        con5.gridy = 2;
        con5.insets = i5;
        slot_panel.add(b_slot2, con5);

        con5.gridx = 1;
        con5.gridy = 3;
        slot_panel.add(b_slot3, con5);

        slot_panel.setOpaque(false);

        //Button exit TOP RIGHT
        b_back = new JButton();
        b_back.addActionListener(this);
        b_back.setContentAreaFilled(false);
        b_back.setBorderPainted(false);
        b_back.setOpaque(false);


        //death
        b_d_newGame = new JButton();
        b_d_exit = new JButton();

        float size2 = width / 90;

        b_d_newGame.addActionListener(this);
        b_d_newGame.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
        b_d_newGame.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
        b_d_newGame.setVerticalTextPosition(JButton.CENTER);
        b_d_newGame.setContentAreaFilled(false);
        b_d_newGame.setBorderPainted(false);
        b_d_newGame.setFont(b_d_newGame.getFont().deriveFont(size2));
        b_d_newGame.setText("New Game");

        b_d_exit.addActionListener(this);
        b_d_exit.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
        b_d_exit.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
        b_d_exit.setVerticalTextPosition(JButton.CENTER);
        b_d_exit.setContentAreaFilled(false);
        b_d_exit.setBorderPainted(false);
        b_d_exit.setFont(b_d_exit.getFont().deriveFont(size2));
        b_d_exit.setText("Menu");

        death = new JLabel("You Died.");
        float size0 = width / 75;
        death.setForeground(Color.WHITE);
        death.setFont(death.getFont().deriveFont(size0));

        death_panel = new JPanel(new GridBagLayout());

        GridBagConstraints con6 = new GridBagConstraints();
        Insets i6 = new Insets(height / 12, 0, 0, 0);

        con6.gridx = 1;
        con6.gridy = 1;
        con6.insets = i6;
        death_panel.add(death, con6);

        con6.gridx = 1;
        con6.gridy = 2;
        death_panel.add(b_d_newGame, con6);

        con6.gridx = 1;
        con6.gridy = 3;
        death_panel.add(b_d_exit, con6);

        death_panel.setOpaque(false);

        //KEYBOARD!
        frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        frame.getActionMap().put("Escape", new Escape());
        back.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("P"), "StopSound");
        back.getActionMap().put("StopSound", new StopSound());

        start_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "Down");
        start_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "Down");
        start_panel.getActionMap().put("Down", new GoDown());
        start_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "Up");
        start_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "Up");
        start_panel.getActionMap().put("Up", new GoUp());
        start_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Continue");
        start_panel.getActionMap().put("Continue", new Continue());

        slot_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "DownLoad");
        slot_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "DownLoad");
        slot_panel.getActionMap().put("DownLoad", new GoDownLoad());
        slot_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "UpLoad");
        slot_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "UpLoad");
        slot_panel.getActionMap().put("UpLoad", new GoUpLoad());
        slot_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "LoadNewGame");
        slot_panel.getActionMap().put("LoadNewGame", new LoadNewGame());

        death_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "ButtonDeath");
        death_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "ButtonDeath");
        death_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "ButtonDeath");
        death_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "ButtonDeath");
        death_panel.getActionMap().put("ButtonDeath", new ChangeButtonDeath());
        death_panel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "AfterDeath");
        death_panel.getActionMap().put("AfterDeath", new AfterDeath());

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


        //SOUND
        sound.getSoundtrack();

        //Main Container and Start Menu
        frame.add(back, 0, 0);
        frame.add(start_panel, 3, 0);
        back.setBounds(0, 0, width, height);
        start_panel.setBounds(0, 0, width, 4 * height / 5);

        setContentPane(frame);
        setSize(width, height);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }

    /**
     *  override of Game listener methods
     *  here the achievement is displayed for 5 secs before being removed by a timer
     *
     * @param achievement String with the name of the achievement.
     */
    @Override
    public void achievementObtained(String achievement) {
        System.out.println(achievement);
        achie.setIcon(new ImageIcon(new ImageIcon(path_resources + "Achievements" + File.separator + achievement + ".png").getImage().getScaledInstance(width * 10 / 34, height * 10 / 54, Image.SCALE_SMOOTH)));
        this.add(achie, 4, 0);
        achie.setBounds(width * 100 / 142, height * 100 / 127, width * 10 / 34, height * 10 / 54);
        rt_achie();
    }

    /**
     * override of Game listener methods
     * here the hero's age and years of service are changed
     */
    @Override
    public void heroAged() {
        hero_age.setText("Age: " + Hero.getAge());
        text_year.setText("Years of service: " + Hero.getYearsOfService());
    }

    /**
     * override of Game listener methods
     * here when the hero take an artifact, a sound is displayed accordingly
     */
    @Override
    public void artifactObtained(int artefact) {
        sound.getArtifactSound(artefact);
    }

    /**
     * override for clicking on buttons
     *
     * @param e = buttons
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        sound.getTapSound();

        if (e.getSource() == b_newGame) {
            searchSlot();
        }

        if (e.getSource() == b_load) {
            load();
        }

        if (e.getSource() == b_tutorial) {
            tutorial();
        }

        if (e.getSource() == b_exit) {
            System.exit(0);
        }


        if (e.getSource() == click) {

            //clicking makes the shields to appear and disappear
            if(is_gaming) {
            switch (state_game) {
                case 0:

                    setShields();
                    state_game = 1;

                    break;

                case 1:

                    removeShields();
                    state_game = 0;

                    break;

                case 2:

                    boolean died = false;
                    for (int i = 0; i < 5; i++) {
                        if (Hero.stats[i] == 100 || Hero.stats[i] == 0) {
                            setDeath(i);
                            state_game = 3;
                            died = true;
                            break;
                        }
                    }
                    if (!died) {
                        EventManager.newEvent();
                        setDescription(EventManager.getEventDescription(), 0);
                        state_game = 0;
                    }
                    break;

                case 3:
                    afterDeath();
                    break;

            }
            }
        }

        if (e.getSource() == b_w) {

            removeShields();
            OptionConsequence(0);
            state_game = 2;
        }

        if (e.getSource() == b_e) {

            removeShields();
            OptionConsequence(1);
            state_game = 2;
        }

        if (e.getSource() == b_n) {

            removeShields();
            OptionConsequence(2);
            state_game = 2;
        }

        if (e.getSource() == b_s) {

            removeShields();
            OptionConsequence(3);
            state_game = 2;
        }


        if (e.getSource() == b_back) {
            Menu();
        }

        if (e.getSource() == b_slot1) {

            remove(b_back);
            remove(slot_panel);
            revalidate();
            repaint();

            if (is_loading) {
                DBManager.load(1);
            } else {
                EventManager.newGame();
            }

            newGame(1);
        }

        if (e.getSource() == b_slot2) {

            remove(b_back);
            remove(slot_panel);
            revalidate();
            repaint();

            if (is_loading) {
                DBManager.load(2);
            } else {
                EventManager.newGame();
            }

            newGame(2);
        }

        if (e.getSource() == b_slot3) {

            remove(b_back);
            remove(slot_panel);
            revalidate();
            repaint();

            if (is_loading) {
                DBManager.load(3);
            } else {
                EventManager.newGame();
            }

            newGame(3);
        }

        if (e.getSource() == b_d_newGame) {

            int currentSlot = DBManager.getSaveSlot();
            newGame(currentSlot);
            EventManager.newEvent();
            state_game = 0;

        }

        if (e.getSource() == b_d_exit) {

            menu = true;
            this.remove(slot_panel);
            removeGameObject();
            this.remove(death_panel);

            this.add(start_panel, 3, 0);
            background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "b0.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        }


    }

    /**
     *  START MENU
     *  remove all other panels and labels
     */
    public void Menu(){
        timer_event.cancel();

        menu = true;
        is_loading = false;

        this.remove(slot_panel);
        this.remove(b_back);
        this.remove(overwrite);
        this.remove(death_panel);
        removeGameObject();

        this.add(start_panel, 3, 0);
        background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "b0.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));

        b_newGame.requestFocus();

        revalidate();
        repaint();

    }

    /**
     * search free slot
     * if there aren't free slots, the user have to choose which slot to overwrite
     */
    public void searchSlot() {
        menu = false;
        is_gaming = false;

        if (DBManager.firstEmptySlot() == 0) {
            //the user has to chose which slot to overwrite
            this.remove(start_panel);
            background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "b1.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
            b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));

            b_slot1.setText(DBManager.getHeroName(1) + ",     Years of Service: " + DBManager.getService(1) + ",     Completed Events: " + DBManager.getCompleted(1));
            b_slot2.setText(DBManager.getHeroName(2) + ",     Years of Service: " + DBManager.getService(2) + ",     Completed Events: " + DBManager.getCompleted(2));
            b_slot3.setText(DBManager.getHeroName(3) + ",     Years of Service: " + DBManager.getService(3) + ",     Completed Events: " + DBManager.getCompleted(3));

            this.add(overwrite, 4, 0);
            overwrite.setBounds(width / 3, height / 20, width * 10/19, height * 10/67);

            this.add(slot_panel, 3, 0);
            slot_panel.setBounds(0, 0, width, height * 4 / 5);
            is_loading = false;
            revalidate();
            repaint();


        } else {
            EventManager.newGame();
            newGame(DBManager.firstEmptySlot());
        }
    }

    /**
     * NEW GAME
     * ev methods are called to pick the saving slot and get the first event
     * remove all useless component
     * component in order: bg and exit, click, hero, event, time, statistics, artifacts, keyboard
     */
    public void newGame(int slot) {
        menu = false;
        is_loading = false;
        is_gaming = true;

        DBManager.setSaveSlot(slot);

        //remove menu_buttons
        this.remove(start_panel);
        this.remove(overwrite);
        this.remove(death_panel);

        //Bg and exit
        background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "arazzogif.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        add(background_image, 1, 0);
        b_back.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_game_back.png").getImage().getScaledInstance(width / 10, height / 15, Image.SCALE_SMOOTH)));
        this.add(b_back, 4, 0);
        b_back.setBounds(width * 100 / 113, height / 30, width / 10, height / 15);

        //click
        this.add(click, 3, 0);
        click.setBounds(0, 0, width, height);

        //hero
        hero_name.setText(Hero.getHeroName());
        hero_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "heroes" + File.separator + Hero.getHeroName() + ".png").getImage().getScaledInstance(width / 12, height / 7, Image.SCALE_SMOOTH)));
        hero_age.setText("Age: " + Hero.getAge());
        this.add(hero_panel, 2, 0);
        hero_panel.setBounds(0, 0, width / 6, height / 4);
        this.add(text_year, 2, 0);
        text_year.setBounds(width / 38, (height - height / 6), width / 9, height / 5);


        //event timer
        resumetimer();

        //statistics
        if (Hero.hasWand()) {
            con1.gridx = 4;
            con1.gridy = 0;
            statistics_panel.add(mana_image, con1);
            int width_stat = width * 100 / 250;
            statistics_panel.setBounds((width - width_stat) / 2, height / 91, width_stat, height / 10);
        } else {
            statistics_panel.remove(mana_image);
            int width_stat = width * 100 / 355;
            statistics_panel.setBounds((width - width_stat) / 2, height / 91, width_stat, height / 10);

        }


        setStatistic();
        this.add(statistics_panel, 2, 0);

        //artifacts
        JLabel[] artifacts_label = {art0, art1, art2, art3};

        for (int i = 0; i < Hero.artefacts.length; i++) {
            if (Hero.artefacts[i]) {
                artifacts_label[i].setIcon(new ImageIcon(new ImageIcon(path_resources + "artifacts" + File.separator + +i + ".png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_SMOOTH)));
            } else {
                artifacts_label[i].setIcon(new ImageIcon(new ImageIcon(path_resources + "artifacts" + File.separator + "Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_SMOOTH)));
            }
        }

        this.add(artifacts_panel, 3, 0);
        artifacts_panel.setBounds(width * 10 / 13, height * 10 / 12, width / 5, height * 10 / 63);
        this.add(art, 2, 0);
        art.setBounds(width * 10 / 13, height * 1000 / 1244, width / 5, height * 10 / 63);

        state_game = 0;

        click.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Continue");
        click.getActionMap().put("Continue", new Continue());

        revalidate();
        repaint();
    }

    /**
     * LOAD
     * remove useless components
     * show three possible slot to load
     */
    public void load() {

        menu = false;
        is_gaming = false;
        is_loading = true;

        this.remove(start_panel);

        this.add(b_back, 3, 0);
        b_back.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_back.png").getImage().getScaledInstance(width / 10, height / 15, Image.SCALE_SMOOTH)));
        b_back.setBounds(width * 100 / 113, height / 30, width / 10, height / 15);

        background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "b2.png").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));

        if (DBManager.hasSave(1))
            b_slot1.setText(DBManager.getHeroName(1) + ",     Years of Service: " + DBManager.getService(1) + ",     Completed Events: " + DBManager.getCompleted(1));
        else
            b_slot1.setText("Empty");
        if (DBManager.hasSave(2))
            b_slot2.setText(DBManager.getHeroName(2) + ",     Years of Service: " + DBManager.getService(2) + ",     Completed Events: " + DBManager.getCompleted(2));
        else
            b_slot2.setText("Empty");
        if (DBManager.hasSave(3))
            b_slot3.setText(DBManager.getHeroName(3) + ",     Years of Service: " + DBManager.getService(3) + ",     Completed Events: " + DBManager.getCompleted(3));
        else
            b_slot3.setText("Empty");

        this.add(slot_panel, 3, 0);
        slot_panel.setBounds(0, 0, width, height * 4 / 5);

        revalidate();
        repaint();

    }

    /**
     * TUTORIAL
     */
    public void tutorial() {
        menu = false;
        is_loading = false;
        is_gaming = false;

        this.remove(start_panel);

        this.add(b_back, 3, 0);
        b_back.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_tuto_back.png").getImage().getScaledInstance(width / 10, height / 15, Image.SCALE_SMOOTH)));
        b_back.setBounds(width * 100 / 113, height / 30, width / 10, height / 15);
        background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "tutorial.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));

    }

    /**
     * set the correct phrase based on which stat lead to death
     *
     * @param stat statistic
     */
    public void setDeath(int stat) {

        is_loading = false;
        menu = false;
        DBManager.deleteSave(DBManager.getSaveSlot());
        sound.getDeathSound();

        String descDeath = EventManager.getDeath(stat);

        event_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "events" + File.separator + "eDeath.png").getImage().getScaledInstance(width * 10 / 48, height * 100 / 168, Image.SCALE_SMOOTH)));
        event_image.setBounds(width * 100 / 252, height * 100 / 677, width * 10 / 48, height * 100 / 168);

        //text event on screen so that every line isn't interrupted
        if (descDeath.length() <= 40) {
            event_text.setText(descDeath);
        } else {
            int c = 40;
            while (descDeath.charAt(c) != ' ') {
                c--;
            }

            int d = descDeath.length() - c;

            if (d <= 40) {
                event_text.setText("<html><div style='text-align: center;'>" + descDeath.substring(0, c) + "<br>" + descDeath.substring(c + 1) + "</div><html>");
            } else {
                int e = c + 40;
                while (descDeath.charAt(e) != ' ') {
                    e--;
                }

                int f = descDeath.length() - c - e;
                if (f <= 40) {
                    event_text.setText("<html><div style='text-align: center;'>" + descDeath.substring(0, c) + "<br>" + descDeath.substring(c + 1, e) + "<br>" + descDeath.substring(e + 1) + "</div><html>");
                } else {
                    int g = c + e + 40;
                    while (descDeath.charAt(g) != ' ') {
                        g--;
                    }

                    event_text.setText("<html><div style='text-align: center;'>" + descDeath.substring(0, c) + "<br>" + descDeath.substring(c + 1, e) + "<br>" + descDeath.substring(e + 1, g) + "<br>" + descDeath.substring(g + 1) + "</div><html>");
                }


            }

        }

        event_text.setBounds(width * 100 / 266, height * 10 / 13, width / 4, height * 10 / 72);

    }

    /**
     * The statistics of the game are displayed
     * The user has to choose if starting a new game or exit
     */
    public void afterDeath() {

        removeGameObject();

        background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "b4.png").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
        death.setText("You died. You did " + Hero.getYearsOfService() + " years of service and you completed " + Hero.completedEvents + " events.");
        add(death_panel, 5, 0);
        death_panel.setBounds(0, 0, width, height);

        revalidate();
        repaint();

        EventManager.newGame();


    }

    /**
     * Put the shields (options) on screen based on how many options there are
     */
    public void setShields() {
        if (EventManager.getOptionNumber() == 4) {
            JButton[] d1 = {b_w, b_e, b_n, b_s};

            float size = 20;
            for (JButton b : d1) {
                this.add(b, 3, 0);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                b.setFont(hero_name.getFont().deriveFont(size));
            }

            setDescriptionShields(b_w, EventManager.getOptionDescription(0));
            setDescriptionShields(b_e, EventManager.getOptionDescription(1));
            setDescriptionShields(b_n, EventManager.getOptionDescription(2));
            setDescriptionShields(b_s, EventManager.getOptionDescription(3));


            b_w.setBounds(width * 100 / 339, height / 3, width_shield, height_shield);
            b_e.setBounds(width * 100 / 179, height / 3, width_shield, height_shield);
            b_n.setBounds(width * 100 / 234, height * 10 / 135, width_shield, height_shield);
            b_s.setBounds(width * 100 / 234, height * 100 / 186, width_shield, height_shield);
                   /* b_w.setBounds(565, 350, 280, 330);
                    b_e.setBounds(, 350, 280, 330);
                    b_n.setBounds(820, 80, 280, 330);
                    b_s.setBounds(820, 580, 280, 330);
*/
        } else if (EventManager.getOptionNumber() == 3) {

            JButton[] d1 = {b_w, b_e, b_n};

            float size = 20;
            for (JButton b : d1) {
                this.add(b, 3, 0);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                b.setFont(hero_name.getFont().deriveFont(size));
            }

            setDescriptionShields(b_w, EventManager.getOptionDescription(0));
            setDescriptionShields(b_e, EventManager.getOptionDescription(1));
            setDescriptionShields(b_n, EventManager.getOptionDescription(2));

            b_w.setBounds(width * 100 / 339, height / 3, width_shield, height_shield);
            b_e.setBounds(width * 100 / 179, height / 3, width_shield, height_shield);
            b_n.setBounds(width * 100 / 234, height * 10 / 135, width_shield, height_shield);


        } else if (EventManager.getOptionNumber() == 2) {

            JButton[] d1 = {b_w, b_e};

            float size = 20;
            for (JButton b : d1) {
                this.add(b, 3, 0);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                b.setFont(hero_name.getFont().deriveFont(size));
            }

            setDescriptionShields(b_w, EventManager.getOptionDescription(0));
            setDescriptionShields(b_e, EventManager.getOptionDescription(1));

            b_w.setBounds(width * 100 / 339, height / 3, width_shield, height_shield);
            b_e.setBounds(width * 100 / 179, height / 3, width_shield, height_shield);

        }
    }

    /**
     * remove buttons on click
     */
    public void removeShields() {

        if (is_gaming) {
            if (EventManager.getOptionNumber() == 4) {
                this.remove(b_w);
                this.remove(b_e);
                this.remove(b_n);
                this.remove(b_s);

            } else if (EventManager.getOptionNumber() == 3) {
                this.remove(b_w);
                this.remove(b_e);
                this.remove(b_n);
            } else if (EventManager.getOptionNumber() == 2) {
                this.remove(b_w);
                this.remove(b_e);
            }

            repaint();
        }
    }


    /**
     * set description of the options
     * text button on screen so that every line isn't interrupted
     *
     * @param b          shield button
     * @param textbutton text on the shield
     */
    public void setDescriptionShields(JButton b, String textbutton) {

        float size1 = width / 100;
        b.setFont(b.getFont().deriveFont(size1));

        if (textbutton.length() <= 20) {
            b.setText(textbutton);
        } else {
            int c = 20;
            while (textbutton.charAt(c) != ' ') {
                c--;
            }

            int d = textbutton.length() - c;

            if (d <= 20) {
                b.setText("<html><div style='text-align: center;'>" + textbutton.substring(0, c) + "<br>" + textbutton.substring(c + 1) + "</div><html>");
            } else {
                int e = c + 20;
                while (textbutton.charAt(e) != ' ') {
                    e--;
                }

                b.setText("<html><div style='text-align: center;'>" + textbutton.substring(0, c) + "<br>" + textbutton.substring(c + 1, e) + "<br>" + textbutton.substring(e + 1) + "</div><html>");

            }

        }

    }


    /**
     * set description of the event/consequences
     * text event on screen so that every line isn't interrupted
     *
     * @param description desc of the event/death
     * @param type        0=initial description, 1=result
     */
    public void setDescription(String description, int type) {

        add(event_image, 2, 0);
        add(event_text, 2, 0);;

        if (type == 0) {
            if (Hero.isCrowed()) {
                event_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "events" + File.separator + "eCrows.png").getImage().getScaledInstance(width * 10 / 48, height * 100 / 168, Image.SCALE_SMOOTH)));
                sound.getCrowSound();
            } else
                event_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "events" + File.separator + "e" + EventManager.getEventNumber() + ".png").getImage().getScaledInstance(width * 10 / 48, height * 100 / 168, Image.SCALE_SMOOTH)));
            event_image.setBounds(width * 100 / 252, height * 100 / 677, width * 10 / 48, height * 100 / 168);
        }
        if (description.length() <= 40) {
            event_text.setText(description);
        } else {
            int c = 40;
            while (description.charAt(c) != ' ') {
                c--;
            }

            int d = description.length() - c;

            if (d <= 40) {
                event_text.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1) + "</div><html>");
            } else {
                int e = c + 40;
                while (description.charAt(e) != ' ') {
                    e--;
                }

                int f = description.length() - c - e;
                if (f <= 40) {
                    event_text.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1, e) + "<br>" + description.substring(e + 1) + "</div><html>");
                } else {
                    int g = c + e + 40;
                    while (description.charAt(g) != ' ') {
                        g--;
                    }

                    event_text.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1, e) + "<br>" + description.substring(e + 1, g) + "<br>" + description.substring(g + 1) + "</div><html>");
                }


            }

        }

        event_text.setBounds(width * 100 / 266, height * 10 / 13, width / 4, height * 10 / 72);
    }

    /**
     * set the consequences for the option selected
     */
    public void OptionConsequence(int n) {

        EventManager.pickOption(n);
        setDescription(EventManager.getResult(n), 1);
        removeShields();

        JLabel[] artifacts_label = {art0, art1, art2, art3};

        for (int i = 0; i < Hero.artefacts.length; i++) {
            if (Hero.artefacts[i]) {
                artifacts_label[i].setIcon(new ImageIcon(new ImageIcon(path_resources + "artifacts" + File.separator + i + ".png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_SMOOTH)));
            } else {
                artifacts_label[i].setIcon(new ImageIcon(new ImageIcon(path_resources + "artifacts" + File.separator + "Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_SMOOTH)));
            }

        }

        if (Hero.hasWand()) {
            con1.gridx = 4;
            con1.gridy = 0;
            statistics_panel.add(mana_image, con1);
            int width_stat = width * 100 / 250;
            statistics_panel.setBounds((width - width_stat) / 2, height / 91, width_stat, height / 10);
        } else {
            statistics_panel.remove(mana_image);
            int width_stat = width * 100 / 355;
            statistics_panel.setBounds((width - width_stat) / 2, height / 91, width_stat, height / 10);

        }

        setStatistic();

    }

    public void setStatistic() {

        if (Hero.getHealth() < 10 && Hero.getHealth() != 0) {
            health_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Health" + ((Hero.getHealth() / 10) + 1) + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        } else {
            health_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics" + File.separator + "Health" + Hero.getHealth() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        }

        if (Hero.getFame() < 10 && Hero.getFame() != 0) {
            fame_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Fame" + ((Hero.getFame() / 10) + 1) + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        } else {
            fame_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Fame" + Hero.getFame() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        }

        if (Hero.getMoney() < 10 && Hero.getMoney() != 0) {
            money_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Money" + ((Hero.getMoney() / 10) + 1) + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        } else {
            money_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Money" + Hero.getMoney() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        }
        if (Hero.getLoyalty() < 10 && Hero.getLoyalty() != 0) {
            loyalty_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Loyalty" + ((Hero.getLoyalty() / 10) + 1) + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        } else {
            loyalty_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Loyalty" + Hero.getLoyalty() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        }

        if (Hero.getMana() < 10 && Hero.getMana() != 0) {
            mana_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Mana" + ((Hero.getMana() / 10) + 1) + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        } else {
            mana_image.setIcon((new ImageIcon(new ImageIcon(path_resources + "statistics" + File.separator + "Mana" + Hero.getMana() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_SMOOTH))));
        }

        health_image.setText(String.valueOf(Hero.getHealth()));
        fame_image.setText(' ' + String.valueOf(Hero.getFame()));
        money_image.setText(String.valueOf(Hero.getMoney()) + ' ');
        loyalty_image.setText(String.valueOf(Hero.getLoyalty()));
        mana_image.setText(String.valueOf(Hero.getMana()));

    }

    public void removeGameObject() {
        this.remove(hero_panel);
        event_image.setBounds(0, 0, 0, 0);
        event_text.setBounds(0, 0, 0, 0);
        this.remove(event_image);
        this.remove(event_text);
        this.remove(statistics_panel);
        this.remove(artifacts_panel);
        this.remove(text_year);
        this.remove(art);
        this.remove(click);
        this.removeShields();
        this.remove(b_back);
        is_gaming = false;
    }

    /**
     * change focus and image of the correct button
     *
     * @param button which menu button has focus
     */
    public void changeFocus(JButton button) {
        button.requestFocus();

        for (JButton b : start_button) {
            if (b == button) {
                b.setIcon((new ImageIcon(new ImageIcon(path_resources + "b_start_sel.png").getImage().getScaledInstance(width / 3, height / 10, Image.SCALE_SMOOTH))));
            } else {
                b.setIcon((new ImageIcon(new ImageIcon(path_resources + "b_start.png").getImage().getScaledInstance(width / 3, height / 10, Image.SCALE_SMOOTH))));
            }
        }
    }

    /**
     * Timers work only once,
     * so every new game the timers needs to be recreated
     * override run()TimerTask to put the image and text after the animation
     * and to change years of service and age
     */
    public void resumetimer() {

        timertask taskevent = new timertask();
        timer_event = new Timer();
        timer_event.schedule(taskevent, 700);

    }

    public class timertask extends TimerTask {
        @Override
        public void run() {
            background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "b1_ara.png").getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH)));
            setDescription(EventManager.getEventDescription(), 0);

        }
    }


    public void rt_achie() {
        timer_achievement.cancel();
        timer_achievement = new Timer();
        timer_achie t_achie = new timer_achie();
        timer_achievement.schedule(t_achie, 5000);

    }

    public class timer_achie extends TimerTask {
        @Override
        public void run() {
            remove(achie);
            revalidate();
            repaint();
        }
    }


    /**
     * override for keyboard
     * selection for the shields/option
     */
    private class SelectWest extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (state_game == 1) {
                sound.getTapSound();
                OptionConsequence(0);
                state_game = 2;
            }

        }
    }

    private class SelectEast extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (state_game == 1) {
                sound.getTapSound();
                OptionConsequence(1);
                state_game = 2;
            }
        }
    }

    private class SelectNorth extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (state_game == 1) {
                if (EventManager.getOptionNumber() == 3 || EventManager.getOptionNumber() == 4) {
                    sound.getTapSound();
                    OptionConsequence(2);
                    state_game = 2;
                }
            }

        }
    }

    private class SelectSouth extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (state_game == 1) {
                if (EventManager.getOptionNumber() == 4) {
                    sound.getTapSound();
                    OptionConsequence(3);
                    state_game = 2;
                }
            }
        }
    }

    /**
     * override for keyboard
     * case 0 = the user can see the events
     * case 1 = the user can see the shields and select and option or press space again
     * if space is pressed, the shield disappear
     * case 2 = check if hero is dead, if yes set the current function,
     * if not, pick a new event
     */
    private class Continue extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            if(is_gaming){
            sound.getTapSound();
            switch (state_game) {
                case 0:
                    setShields();
                    revalidate();
                    repaint();
                    state_game = 1;
                    break;

                case 1:
                    removeShields();
                    revalidate();
                    repaint();
                    state_game = 0;
                    break;

                case 2:
                    boolean died = false;
                    for (int i = 0; i < 5; i++) {
                        if (Hero.stats[i] == 100 || Hero.stats[i] == 0) {
                            setDeath(i);
                            state_game = 3;
                            died = true;
                            break;
                        }
                    }
                    if (!died) {
                        EventManager.newEvent();
                        setDescription(EventManager.getEventDescription(), 0);
                        state_game = 0;
                    }
                    break;

                case 3:
                    afterDeath();
                    break;
            }
        }}
    }

    /**
     * Override esc
     */
    private class Escape extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (menu) {
                System.exit(0);
            } else {
                Menu();
            }


        }
    }

    /**
     * override for keyboard
     * go up and down in the menu buttons
     * the focus is changed so space triggers the correct button
     */
    private class GoDown extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (state_menuButton < 3) {
                state_menuButton++;
            } else {
                state_menuButton = 0;
            }

            if (state_menuButton == 0) {
                changeFocus(b_newGame);
            }
            if (state_menuButton == 1) {
                changeFocus(b_load);
            }
            if (state_menuButton == 2) {
                changeFocus(b_tutorial);
            }
            if (state_menuButton == 3) {
                changeFocus(b_exit);
            }


        }
    }


    private class GoUp extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (state_menuButton > 0) {
                state_menuButton--;
            } else {
                state_menuButton = 3;
            }

            if (state_menuButton == 0) {
                changeFocus(b_newGame);
            }
            if (state_menuButton == 1) {
                changeFocus(b_load);
            }
            if (state_menuButton == 2) {
                changeFocus(b_tutorial);
            }
            if (state_menuButton == 3) {
                changeFocus(b_exit);
            }


        }
    }

    /**
     * override for keyboard
     * in load and overwrite to go down and up the buttons
     * the image is change accordingly
     */
    private class GoDownLoad extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (state_loadButton < 3) {
                state_loadButton++;
            } else {
                state_loadButton = 1;
            }

            switch (state_loadButton) {
                case 1:
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    break;
                case 2:
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    break;
                case 3:
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    break;


            }

        }
    }

    private class GoUpLoad extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (state_loadButton > 1) {
                state_loadButton--;
            } else {
                state_loadButton = 3;
            }

            switch (state_loadButton) {
                case 1:
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    break;
                case 2:
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    break;
                case 3:
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                    break;


            }

        }
    }

    /**
     * override for keyboard
     * load or delete a slot
     */
    private class LoadNewGame extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            sound.getTapSound();
            switch (state_loadButton) {
                case 1:
                    remove(b_back);
                    remove(slot_panel);
                    revalidate();
                    repaint();

                    if (is_loading) {
                        DBManager.load(1);
                    } else {
                        EventManager.newGame();
                    }

                    newGame(1);
                    break;
                case 2:
                    remove(b_back);
                    remove(slot_panel);
                    revalidate();
                    repaint();

                    if (is_loading) {
                        DBManager.load(2);
                    } else {
                        EventManager.newGame();
                    }

                    newGame(2);
                    break;
                case 3:

                    remove(b_back);
                    remove(slot_panel);
                    revalidate();
                    repaint();

                    if (is_loading) {
                        DBManager.load(3);
                    } else {
                        EventManager.newGame();
                    }

                    newGame(3);
                    break;
            }
        }
    }

    /**
     * override for keyboard
     * change the button during death hero
     */
    private class ChangeButtonDeath extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (state_deathButton == 0) {
                b_d_newGame.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                b_d_exit.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                state_deathButton = 1;
            } else {
                b_d_newGame.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                b_d_exit.setIcon(new ImageIcon(new ImageIcon(path_resources + "b_load_sel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_SMOOTH)));
                state_deathButton = 0;
            }

        }

    }

    /**
     * override fo keyboard
     * can create a new game or exit accordingly with the current button selected
     */
    private class AfterDeath extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            sound.getTapSound();
            if (state_deathButton == 0) {
                menu = true;
                //remove(slot_panel);
                removeGameObject();
                remove(death_panel);

                add(start_panel, 3, 0);
                background_image.setIcon(new ImageIcon(new ImageIcon(path_resources + "b0.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
            } else {
                int currentSlot = DBManager.getSaveSlot();
                newGame(currentSlot);
                EventManager.newEvent();
                state_game = 0;
            }
        }


    }


    private class StopSound extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            sound.stop();
        }

    }
}
