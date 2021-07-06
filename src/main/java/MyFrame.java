import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;


public class MyFrame extends JFrame implements ActionListener {

    //remember to change it
    String path_resources = "src/main/resources/";
    //Font f = new Font("serif", Font.PLAIN, 10);


    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    int width = screenSize.width;
    int height = screenSize.height;

    //int width = 960;
    // int height = 540;

    int widthbs = width / 3;
    int heightbs = height / 10;

    int widthshield = width * 100 / 685;
    int heightshield = height * 100 / 327;

    EventManager ev = new EventManager();

    //Hero
    JLabel textyear;
    JLabel textheroage;
    Timer timertime = new Timer();
    Timer timerevent = new Timer();
    JLabel heroname;
    JLabel heroimage;
    JPanel heropanel;


    //Bg and Event
    JPanel layout;
    JLabel backgroundimage;
    JLabel eventimage;
    JLabel eventtext;


    //Statistics
    JLabel healthimage;
    JLabel fameimage;
    JLabel moneyimage;
    JLabel loyaltyimage;
    JLabel manaimage;
    JPanel statisticspanel;
    GridBagConstraints con1;

    //Artifacts
    JLabel art0;
    JLabel art1;
    JLabel art2;
    JLabel art3;
    JLabel art;
    JPanel artifactspanel;
    GridBagConstraints con3;

    //JButtons
    private final JButton b_newgame, b_load, b_settings, b_exit;
    private final JButton b_back, b_exitgame;
    private final JButton click;
    private final JButton b_w, b_e, b_n, b_s;
    JButton[] menubutton;
    JPanel buttonspanel;
    private final JButton b_slot1, b_slot2, b_slot3;
    JPanel slotpanel;
    private final JButton b_d_newgame, b_d_exit;
    JPanel deathpanel;
    JLabel death;


    //Variables
    int a = 0;
    int b = 0;
    int s = 1;
    int p = 0;
    String name;
    JLabel overwrite;

    final JOptionPane optionPane;

    Boolean isloading = true;


    MyFrame() {

        layout = new JPanel(new GridBagLayout());

        JLayeredPane frame = new JLayeredPane();


        JPanel back = new JPanel(new BorderLayout());

        //Background Image
        backgroundimage = new JLabel(new ImageIcon());
        backgroundimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "b0.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        back.add(backgroundimage);


        //Menu Buttons
        b_newgame = new JButton("New Game");
        b_load = new JButton("Load");
        b_settings = new JButton("Settings");
        b_exit = new JButton("Exit");

        menubutton = new JButton[]{b_newgame, b_load, b_settings, b_exit};

        for (JButton b : menubutton) {
            b.addActionListener(this);
            b.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonstart.png").getImage().getScaledInstance(widthbs, heightbs, Image.SCALE_DEFAULT)));
            b.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
            b.setVerticalTextPosition(JButton.CENTER);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
        }
        b_newgame.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonstartsel.png").getImage().getScaledInstance(widthbs, heightbs, Image.SCALE_DEFAULT)));


        buttonspanel = new JPanel(new GridBagLayout());
        GridBagConstraints con = new GridBagConstraints();

        Insets i = new Insets(0, 0, height / 45, 0);
        con.gridx = 2;
        con.gridy = 0;
        con.weightx = 2;
        con.insets = i;
        buttonspanel.add(b_newgame, con);

        con.gridx = 2;
        con.gridy = 1;
        buttonspanel.add(b_load, con);

        con.gridx = 2;
        con.gridy = 2;
        buttonspanel.add(b_settings, con);

        con.gridx = 2;
        con.gridy = 3;
        buttonspanel.add(b_exit, con);

        JLabel none = new JLabel();
        con.gridx = 0;
        con.gridy = 0;
        buttonspanel.add(none, con);

        JLabel none1 = new JLabel();
        con.gridx = 1;
        con.gridy = 0;
        buttonspanel.add(none1, con);

        buttonspanel.setOpaque(false);

        //Settings Button


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

        for (JButton b : d1) {
            b.addActionListener(this);
            b.setIcon((new ImageIcon(new ImageIcon(path_resources + "scudo1.png").getImage().getScaledInstance(widthshield, heightshield, Image.SCALE_DEFAULT))));
            b.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
            b.setVerticalTextPosition(JButton.CENTER);
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
        }


        //Statistics
        healthimage = new JLabel(new ImageIcon());
        fameimage = new JLabel(new ImageIcon());
        moneyimage = new JLabel(new ImageIcon());
        loyaltyimage = new JLabel(new ImageIcon());
        manaimage = new JLabel(new ImageIcon());

        statisticspanel = new JPanel(new GridBagLayout());
        con1 = new GridBagConstraints();

        Insets i1 = new Insets(0, height / 108, 0, height / 108);

        con1.gridx = 0;
        con1.gridy = 0;
        con1.insets = i1;
        statisticspanel.add(healthimage, con1);

        con1.gridx = 1;
        con1.gridy = 0;
        statisticspanel.add(fameimage, con1);

        con1.gridx = 2;
        con1.gridy = 0;
        statisticspanel.add(loyaltyimage, con1);

        con1.gridx = 3;
        con1.gridy = 0;
        statisticspanel.add(moneyimage, con1);

        statisticspanel.setOpaque(false);


        //Timer
        textheroage = new JLabel(String.valueOf(Hero.getAge()));
        textyear = new JLabel(String.valueOf(Hero.getYearsOfService()));

        //Hero
        heroname = new JLabel(name);
        heroimage = new JLabel(new ImageIcon());

        heropanel = new JPanel(new GridBagLayout());
        GridBagConstraints con2 = new GridBagConstraints();
        //Insets i2 = new Insets(0,0,10,0);

        con2.gridx = 0;
        con2.gridy = 0;
        con2.insets = i1;
        heropanel.add(heroname, con2);

        con2.gridx = 0;
        con2.gridy = 1;
        con2.gridheight = 1;
        heropanel.add(textheroage, con2);

        con2.gridx = 0;
        con2.gridy = 2;
        con2.gridheight = 2;
        heropanel.add(heroimage, con2);

        heropanel.setOpaque(false);

        //Artifacts

        art0 = new JLabel();
        art1 = new JLabel();
        art2 = new JLabel();
        art3 = new JLabel();
        art = new JLabel();

        art0.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts/Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_DEFAULT)));
        art1.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts/Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_DEFAULT)));
        art2.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts/Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_DEFAULT)));
        art3.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts/Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_DEFAULT)));
        art.setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts.png").getImage().getScaledInstance(width / 5, height * 10 / 63, Image.SCALE_DEFAULT)));

        artifactspanel = new JPanel(new GridBagLayout());
        con3 = new GridBagConstraints();

        Insets i3 = new Insets(0, width / 256, 0, width / 256);
        con3.gridx = 0;
        con3.gridy = 1;
        con3.insets = i3;
        artifactspanel.add(art0, con3);

        con3.gridx = 1;
        con3.gridy = 1;
        artifactspanel.add(art1, con3);

        con3.gridx = 2;
        con3.gridy = 1;
        artifactspanel.add(art2, con3);

        con3.gridx = 3;
        con3.gridy = 1;
        artifactspanel.add(art3, con3);

        artifactspanel.setOpaque(false);

        //LOAD
        overwrite = new JLabel("The slots are full, which saving do you want to overwrite?");

        b_slot1 = new JButton();
        b_slot2 = new JButton();
        b_slot3 = new JButton();


        JButton[] slotbutton = {b_slot1, b_slot2, b_slot3};
        for (JButton b : slotbutton) {
            b.addActionListener(this);
            b.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
            b.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
            b.setVerticalTextPosition(JButton.CENTER);
            float size2 = width / 90;
            b.setFont(b.getFont().deriveFont(size2));
            b.setContentAreaFilled(false);
            b.setBorderPainted(false);
        }

        slotpanel = new JPanel(new GridBagLayout());//Mah

        GridBagConstraints con5 = new GridBagConstraints();
        Insets i4 = new Insets(height / 12, 0, 0, 0);

        con5.gridx = 1;
        con5.gridy = 1;
        con5.insets = i4;
        slotpanel.add(b_slot1, con5);

        Insets i5 = new Insets(height / 21, 0, 0, 0);
        con5.gridx = 1;
        con5.gridy = 2;
        con5.insets = i5;
        slotpanel.add(b_slot2, con5);

        con5.gridx = 1;
        con5.gridy = 3;
        slotpanel.add(b_slot3, con5);

        slotpanel.setOpaque(false);


        frame.add(back, 0, 0);
        frame.add(buttonspanel, 3, 0);
        back.setBounds(0, 0, width, height);
        buttonspanel.setBounds(0, 0, width, 4 * height / 5);

        //Button exit TOP RIGHT
        b_back = new JButton();
        b_back.addActionListener(this);
        b_back.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonback.png").getImage().getScaledInstance(width / 10, height / 15, Image.SCALE_DEFAULT)));
        b_back.setContentAreaFilled(false);
        b_back.setBorderPainted(false);
        b_back.setOpaque(false);

        b_exitgame = new JButton();
        b_exitgame.addActionListener(this);
        b_exitgame.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonback.png").getImage().getScaledInstance(width / 10, height / 15, Image.SCALE_DEFAULT)));
        b_exitgame.setContentAreaFilled(false);
        b_exitgame.setBorderPainted(false);
        b_exitgame.setOpaque(false);

        //death
        b_d_newgame = new JButton();
        b_d_exit = new JButton();

        float size2 = width / 90;

        b_d_newgame.addActionListener(this);
        b_d_newgame.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
        b_d_newgame.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
        b_d_newgame.setVerticalTextPosition(JButton.CENTER);
        b_d_newgame.setContentAreaFilled(false);
        b_d_newgame.setBorderPainted(false);
        b_d_newgame.setFont(b_d_newgame.getFont().deriveFont(size2));
        b_d_newgame.setText("New Game");

        b_d_exit.addActionListener(this);
        b_d_exit.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
        b_d_exit.setHorizontalTextPosition(JButton.CENTER); //to set the text on the center of the picture, if not the bg moves it
        b_d_exit.setVerticalTextPosition(JButton.CENTER);
        b_d_exit.setContentAreaFilled(false);
        b_d_exit.setBorderPainted(false);
        b_d_exit.setFont(b_d_exit.getFont().deriveFont(size2));
        b_d_exit.setText("Exit");

        death = new JLabel("You Died.");
        float size0 = width / 75;
        death.setForeground(Color.WHITE);
        death.setFont(death.getFont().deriveFont(size0));


        deathpanel = new JPanel(new GridBagLayout());

        GridBagConstraints con6 = new GridBagConstraints();
        Insets i6 = new Insets(height / 12, 0, 0, 0);

        con6.gridx = 1;
        con6.gridy = 1;
        con6.insets = i6;
        deathpanel.add(death, con6);

        con6.gridx = 1;
        con6.gridy = 2;
        deathpanel.add(b_d_newgame, con6);

        con6.gridx = 1;
        con6.gridy = 3;
        deathpanel.add(b_d_exit, con6);

        deathpanel.setOpaque(false);


        /*GridBagConstraints con3 = new GridBagConstraints();

        Insets i3 = new Insets(0,0,0,0);
        con3.gridx = 0;
        con3.gridy = 0;
        con3.weightx = 2;
        con3.anchor = GridBagConstraints.NORTH;
        con3.insets = i3;
        layout.add(heropanel, con3);

        con3.gridx = 1;
        con3.gridy = 0;
        layout.add(statisticspanel, con3);

        con3.gridx = 1;
        con3.gridy = 1;
        layout.add(eventimage, con3);

        con3.gridx = 1;
        con3.gridy = 2;
        layout.add(eventtext, con3);

        layout.setOpaque(false);*/
        //Are you sure?
        optionPane = new JOptionPane(
                "Are you sure?",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION);
        //optionPane.addActionListener(this);

        //KEY
        frame.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
        frame.getActionMap().put("Escape", new Escape());

        buttonspanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "Down");
        buttonspanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "Down");
        buttonspanel.getActionMap().put("Down", new GoDown());

        buttonspanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "Up");
        buttonspanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "Up");
        buttonspanel.getActionMap().put("Up", new GoUp());

        buttonspanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Continue");
        buttonspanel.getActionMap().put("Continue", new Continue());


        setContentPane(frame);
        //setExtendedState(JFrame.MAXIMIZED_BOTH);
        setSize(width, height);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == b_newgame) {
            ev.newGame();
            searchSlot();
        }

        if (e.getSource() == b_load) {
            load();
        }

        if (e.getSource() == b_settings) {
            settings();
        }

        if (e.getSource() == b_exit) {
            // add(optionPane, 4, 0);
            //optionPane.setBounds(100, 100, 400, 500);
            System.exit(0);
        }


        if (e.getSource() == click) {

            //clicking makes the shields to appear and disappear
            switch (a) {
                case 0:
                    setShields();
                    a = 1;

                    break;

                case 1:

                    removeShields();
                    a = 0;

                    break;

                case 2:

                    boolean died = false;
                    for (int i = 0; i < 5; i++) {  //i<4 o i <5?
                        if (Hero.stats[i] == 100 || Hero.stats[i] == 0) {
                            heroDied(i);
                            a = 3;
                            died = true;
                            break;
                        }
                    }
                    if (!died) {
                        ev.getEvent();
                        //this.add(eventimage, 2, 0);
                        //this.add(eventtext, 2,0);
                        setDescription(ev.getEventDescription());
                        a = 0;
                    }
                    break;

                case 3:
                    afterDeath();

                    break;


            }

        }

        if (e.getSource() == b_w) {

            removeShields();
            OptionConsequence(0);
            a = 2;
        }

        if (e.getSource() == b_e) {

            OptionConsequence(1);
            a = 2;
        }

        if (e.getSource() == b_n) {

            OptionConsequence(2);
            a = 2;
        }

        if (e.getSource() == b_s) {

            removeShields();
            OptionConsequence(3);
            a = 2;
        }

        if (e.getSource() == b_exitgame) {
            System.exit(0);
        }

        if (e.getSource() == b_back) {
            this.remove(slotpanel);
            this.remove(b_back);
            this.add(buttonspanel, 3, 0);
            backgroundimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "b0.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        }


        if (e.getSource() == b_slot1) {

            remove(b_back);
            remove(slotpanel);
            revalidate();
            repaint();

            if (isloading) {
                ev.load(1);
            } else {
                ev.newGame();
            }

            newGame(1);
        }

        if (e.getSource() == b_slot2) {

            remove(b_back);
            remove(slotpanel);
            revalidate();
            repaint();

            if (isloading) {
                ev.load(2);
            } else {
                ev.newGame();
            }

            newGame(2);
        }

        if (e.getSource() == b_slot3) {

            remove(b_back);
            remove(slotpanel);
            revalidate();
            repaint();

            if (isloading) {
                ev.load(3);
            } else {
                ev.newGame();
            }

            newGame(3);
        }

        if (e.getSource() == b_d_newgame) {

            //int currentSlot = ev.currentSlot)
            //ev.deleteSave( currentSlot);
            //ev.newGame(currentSlot));
            a = 0;

        }

        if (e.getSource() == b_d_exit) {
            System.exit(0);
        }


    }

    /**
     * search free slot :)
     */
    public void searchSlot() {
        if (ev.firstEmptySlot() == 0) {
            //chiedi agli utenti di scegliere che slot sovraccaricare
            this.remove(buttonspanel);
            backgroundimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "b1.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
            b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));

            ev.load(1);
            b_slot1.setText(Hero.getHeroName() + ",     Years of Service: " + Hero.getYearsOfService() + ",     Completed Events: " + ev.completedEvents);
            ev.load(2);
            b_slot2.setText(Hero.getHeroName() + ",     Years of Service: " + Hero.getYearsOfService() + ",     Completed Events: " + ev.completedEvents);
            ev.load(3);
            b_slot3.setText(Hero.getHeroName() + ",     Years of Service: " + Hero.getYearsOfService() + ",     Completed Events: " + ev.completedEvents);

            float size0 = width / 75;
            overwrite.setForeground(Color.WHITE);
            overwrite.setFont(overwrite.getFont().deriveFont(size0));
            this.add(overwrite, 4, 0);
            overwrite.setBounds(width / 3, height / 20, 1000, 160);

            this.add(slotpanel, 3, 0);
            slotpanel.setBounds(0, 0, width, height * 4 / 5);
            isloading = false;
            revalidate();
            repaint();

            slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "DownLoad");
            slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "DownLoad");
            slotpanel.getActionMap().put("DownLoad", new GoDownLoad());

            slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "UpLoad");
            slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "UpLoad");
            slotpanel.getActionMap().put("UpLoad", new GoUpLoad());

            slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "LoadNewGame");
            slotpanel.getActionMap().put("LoadNewGame", new LoadNewGame());
        } else {
            newGame(ev.firstEmptySlot());
        }
    }

    public void newGame(int slot) {

        ev.setSaveSlot(slot);
        ev.getEvent();

        //remove menubuttons
        this.remove(buttonspanel);
        this.remove(overwrite);

        //Add and setting things
        backgroundimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "arazzogif.gif").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        add(backgroundimage, 1, 0);
        b_exitgame.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonexit.png").getImage().getScaledInstance(width / 10, height / 15, Image.SCALE_DEFAULT)));
        this.add(b_exitgame, 4, 0);
        b_exitgame.setBounds(width * 100 / 113, height / 30, width / 10, height / 15);
        //click
        this.add(click, 3, 0);
        click.setOpaque(false);
        click.setContentAreaFilled(false);
        click.setBorderPainted(false);
        click.setBounds(0, 0, width, height);

        //hero
        float size0 = width / 85;
        heroname.setForeground(Color.WHITE);
        heroname.setFont(heroname.getFont().deriveFont(size0));
        heroname.setText(Hero.getHeroName());
        heroimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "Heroes/" + Hero.getHeroName() + ".jpg").getImage().getScaledInstance(width / 12, height / 7, Image.SCALE_DEFAULT)));
        float size1 = width / 100;
        textheroage.setForeground(Color.WHITE);
        textheroage.setFont(textheroage.getFont().deriveFont(size1));
        textheroage.setText("Age: " + Hero.getAge());

        this.add(heropanel, 2, 0);
        heropanel.setBounds(0, 0, width / 6, height / 4);


       /*this.add(layout,2 ,0);
        layout.setBounds(0,0,width,height);*/

        //event

        this.add(eventimage, 2, 0);
        this.add(eventtext, 2, 0);
        eventtext.setFont(eventtext.getFont().deriveFont(size1));
        timerevent.schedule(taskevent, 700); //delay for the animation

        //year
        this.add(textyear, 2, 0);
        textyear.setBounds(width / 38, (height - height / 6), width / 9, height / 5);
        float size2 = width / 90;
        textyear.setFont(textyear.getFont().deriveFont(size2));
        textyear.setForeground(Color.WHITE);
        timertime.schedule(timerchange, 0, 5000); //the age changes every 5 sec

        //statistics
        healthimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Health" + (int) Hero.getHealth() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));
        fameimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Fame" + (int) Hero.getFame() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));
        moneyimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Money" + (int) Hero.getMoney() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));
        loyaltyimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + (int) Hero.getLoyalty() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));
        manaimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Mana" + (int) Hero.getMana() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));

        this.add(statisticspanel, 2, 0);
        int widthstat = width * 100 / 355;
        statisticspanel.setBounds((width - widthstat) / 2, height / 91, widthstat, height / 10);

        this.add(artifactspanel, 3, 0);
        artifactspanel.setBounds(width * 10 / 14, height * 10 / 12, width / 5, height * 10 / 63);
        this.add(art, 2, 0);
        art.setBounds(width * 10 / 14, height * 1000 / 1244, width / 5, height * 10 / 63);

        a = 0;

        click.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "Continue");
        click.getActionMap().put("Continue", new Continue());

    }

    /**
     * LOAD
     */
    public void load() {

        this.remove(buttonspanel);

        this.add(b_back, 3, 0);
        b_back.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonbackload.png").getImage().getScaledInstance(width / 10, height / 15, Image.SCALE_DEFAULT)));
        b_back.setBounds(width * 100 / 113, height / 30, width / 10, height / 15);

        backgroundimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "b2.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));

        ev.load(1);
        b_slot1.setText(Hero.getHeroName() + ",     Years of Service: " + Hero.getYearsOfService() + ",     Completed Events: " + ev.completedEvents);
        ev.load(2);
        b_slot2.setText(Hero.getHeroName() + ",     Years of Service: " + Hero.getYearsOfService() + ",     Completed Events: " + ev.completedEvents);
        ev.load(3);
        b_slot3.setText(Hero.getHeroName() + ",     Years of Service: " + Hero.getYearsOfService() + ",     Completed Events: " + ev.completedEvents);

        this.add(slotpanel, 3, 0);
        slotpanel.setBounds(0, 0, width, height * 4 / 5);

        isloading = true;
        revalidate();
        repaint();

        //KEY
        slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "DownLoad");
        slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "DownLoad");
        slotpanel.getActionMap().put("DownLoad", new GoDownLoad());

        slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "UpLoad");
        slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "UpLoad");
        slotpanel.getActionMap().put("UpLoad", new GoUpLoad());

        slotpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "LoadNewGame");
        slotpanel.getActionMap().put("LoadNewGame", new LoadNewGame());
    }

    /**
     * SETTINGS
     */
    public void settings() {

        this.remove(buttonspanel);

        this.add(b_back, 3, 0);
        b_back.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonbacksettings.png").getImage().getScaledInstance(width / 10, height / 15, Image.SCALE_DEFAULT)));
        b_back.setBounds(width * 100 / 113, height / 30, width / 10, height / 15);
        backgroundimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "b3.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));

    }


    public void heroDied(int stat) {

        String descDeath = "";
        switch (stat) {
            case 0:
                if (Hero.stats[0] == 0) {
                    descDeath = "You have no energy to continue living, so you let yourself die under a tree";
                } else {
                    descDeath = "You feel so good and energetic and life is good. But Steve, a cripple, is envious of your good health and poison you.";
                }
                break;
            case 1:
                if (Hero.stats[1] == 0) {
                    descDeath = "Nobody recognize you anymore. You fell too lonely to continue to live.";
                } else {
                    descDeath = "One of your fan tries to marry you, but at your rejection he kills you.";
                }
                break;
            case 2:
                if (Hero.stats[2] == 0) {
                    descDeath = "The king doesn't like your unwillingness to obey. So he sentence you to an horrible death.";
                } else {
                    descDeath = "You are so loyal to the king that people start calling you 'the loyal dog'. Some rebels decide to kill you to damage the king.";
                }
                break;
            case 3:
                if (Hero.stats[3] == 0) {
                    descDeath = "You are hungry, but there is no more money left. You die miserably.";
                } else {
                    descDeath = "You are now one of the richest man in town. Some thieves want to steal from your home and in the process they kill you.";
                }
                break;
            case 4:
                if (Hero.stats[4] == 0) {
                    descDeath = "You try to light a fire, but the spell is too strong and take away your life.";
                } else {
                    descDeath = "The mana inside you is too much, you explode.";
                }
                break;
        }

        setDeath(descDeath, stat, Hero.stats[stat]);


    }

    public void setDeath(String description, int stat, int valuestat) {

        eventimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "Death/d" + stat + valuestat + ".png").getImage().getScaledInstance(width * 10 / 48, height * 100 / 168, Image.SCALE_DEFAULT)));
        eventimage.setBounds(width * 100 / 252, height * 100 / 677, width * 10 / 48, height * 100 / 168);

        //text event on screen so that every line isn't interrupted
        if (description.length() <= 40) {
            eventtext.setText(description);
        } else {
            int c = 40;
            while (description.charAt(c) != ' ') {
                c--;
            }

            int d = description.length() - c;

            if (d <= 40) {
                eventtext.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1, description.length()) + "</div><html>");
            } else {
                int e = c + 40;
                while (description.charAt(e) != ' ') {
                    e--;
                }

                int f = description.length() - c - e;
                if (f <= 40) {
                    eventtext.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1, e) + "<br>" + description.substring(e + 1) + "</div><html>");
                } else {
                    int g = c + e + 40;
                    while (description.charAt(g) != ' ') {
                        g--;
                    }

                    eventtext.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1, e) + "<br>" + description.substring(e + 1, g) + "<br>" + description.substring(g + 1) + "</div><html>");
                }


            }

        }

        eventtext.setBounds(width * 100 / 266, height * 10 / 13, width / 4, height * 10 / 72);


    }

    public void afterDeath() {
        backgroundimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "b4.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
        add(backgroundimage, 4, 0);
        death.setText("You died. You did " + Hero.getYearsOfService() + " years of service and you completed " + ev.completedEvents + " events.");
        add(deathpanel, 5, 0);
        deathpanel.setBounds(0, 0, width, height);
        revalidate();
        repaint();

        //KEY
        deathpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "ButtonDeath");
        deathpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "ButtonDeath");
        deathpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "ButtonDeath");
        deathpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "ButtonDeath");
        deathpanel.getActionMap().put("ButtonDeath", new ChangeButtonDeath());

        deathpanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), "AfterDeath");
        deathpanel.getActionMap().put("AfterDeath", new AfterDeath());

    }

    /**
     * Put the shields (options) on screen based on how many options there are
     */
    public void setShields() {
        if (ev.getOptionNumber() == 4) {
            JButton[] d1 = {b_w, b_e, b_n, b_s};

            float size = 20;
            for (JButton b : d1) {
                this.add(b, 3, 0);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                b.setFont(heroname.getFont().deriveFont(size));
            }

            setDescriptionShields(b_w, ev.getDesc(0));
            setDescriptionShields(b_e, ev.getDesc(1));
            setDescriptionShields(b_n, ev.getDesc(2));
            setDescriptionShields(b_s, ev.getDesc(3));


            b_w.setBounds(width * 100 / 339, height / 3, widthshield, heightshield);
            b_e.setBounds(width * 100 / 179, height / 3, widthshield, heightshield);
            b_n.setBounds(width * 100 / 234, height * 10 / 135, widthshield, heightshield);
            b_s.setBounds(width * 100 / 234, height * 100 / 186, widthshield, heightshield);
                   /* b_w.setBounds(565, 350, 280, 330);
                    b_e.setBounds(, 350, 280, 330);
                    b_n.setBounds(820, 80, 280, 330);
                    b_s.setBounds(820, 580, 280, 330);
*/

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


        } else if (ev.getOptionNumber() == 3) {

            JButton[] d1 = {b_w, b_e, b_n};

            float size = 20;
            for (JButton b : d1) {
                this.add(b, 3, 0);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                b.setFont(heroname.getFont().deriveFont(size));
            }

            setDescriptionShields(b_w, ev.getDesc(0));
            setDescriptionShields(b_e, ev.getDesc(1));
            setDescriptionShields(b_n, ev.getDesc(2));

            b_w.setBounds(width * 100 / 339, height / 3, widthshield, heightshield);
            b_e.setBounds(width * 100 / 179, height / 3, widthshield, heightshield);
            b_n.setBounds(width * 100 / 234, height * 10 / 135, widthshield, heightshield);

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

        } else if (ev.getOptionNumber() == 2) {

            JButton[] d1 = {b_w, b_e};

            float size = 20;
            for (JButton b : d1) {
                this.add(b, 3, 0);
                b.setContentAreaFilled(false);
                b.setBorderPainted(false);
                b.setFont(heroname.getFont().deriveFont(size));
            }

            setDescriptionShields(b_w, ev.getDesc(0));
            setDescriptionShields(b_e, ev.getDesc(1));

            b_w.setBounds(width * 100 / 339, height / 3, widthshield, heightshield);
            b_e.setBounds(width * 100 / 179, height / 3, widthshield, heightshield);


            b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "North");
            b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "North");
            b_w.getActionMap().put("North", new SelectNorth());
            b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "South");
            b_w.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "South");
            b_w.getActionMap().put("South", new SelectSouth());
        }
    }

    /**
     * remove buttons on click
     */
    public void removeShields() {
        if (ev.getOptionNumber() == 4) {
            this.remove(b_w);
            this.remove(b_e);
            this.remove(b_n);
            this.remove(b_s);

        } else if (ev.getOptionNumber() == 3) {
            this.remove(b_w);
            this.remove(b_e);
            this.remove(b_n);
        } else if (ev.getOptionNumber() == 2) {
            this.remove(b_w);
            this.remove(b_e);
        }
        repaint();  //to remove components it is better to call it

    }


    /**
     * set description of the options
     *
     * @param
     * @param textbutton
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
                b.setText("<html><div style='text-align: center;'>" + textbutton.substring(0, c) + "<br>" + textbutton.substring(c + 1, textbutton.length()) + "</div><html>");
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
     *
     * @param description
     */
    public void setDescription(String description) {
        if (ev.isCrowed())
            eventimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "Events/eCrows.png").getImage().getScaledInstance(width * 10 / 48, height * 100 / 168, Image.SCALE_DEFAULT)));
        else
            eventimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "Events/e" + ev.getEventNumber() + ".png").getImage().getScaledInstance(width * 10 / 48, height * 100 / 168, Image.SCALE_DEFAULT)));
        eventimage.setBounds(width * 100 / 252, height * 100 / 677, width * 10 / 48, height * 100 / 168);

        //text event on screen so that every line isn't interrupted
        if (description.length() <= 40) {
            eventtext.setText(description);
        } else {
            int c = 40;
            while (description.charAt(c) != ' ') {
                c--;
            }

            int d = description.length() - c;

            if (d <= 40) {
                eventtext.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1, description.length()) + "</div><html>");
            } else {
                int e = c + 40;
                while (description.charAt(e) != ' ') {
                    e--;
                }

                int f = description.length() - c - e;
                if (f <= 40) {
                    eventtext.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1, e) + "<br>" + description.substring(e + 1) + "</div><html>");
                } else {
                    int g = c + e + 40;
                    while (description.charAt(g) != ' ') {
                        g--;
                    }

                    eventtext.setText("<html><div style='text-align: center;'>" + description.substring(0, c) + "<br>" + description.substring(c + 1, e) + "<br>" + description.substring(e + 1, g) + "<br>" + description.substring(g + 1) + "</div><html>");
                }


            }

        }

        eventtext.setBounds(width * 100 / 266, height * 10 / 13, width / 4, height * 10 / 72);
    }

    public void OptionConsequence(int n) {

        setDescription(ev.getResult(n));

        removeShields();

        ev.pickOption(n);

        JLabel[] artifactslabel = {art0, art1, art2, art3};

        for (int i = 0; i < Hero.artefacts.length; i++) {
            if (Hero.artefacts[i]) {
                artifactslabel[i].setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts/" + i + ".png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_DEFAULT)));
            } else {
                artifactslabel[i].setIcon(new ImageIcon(new ImageIcon(path_resources + "Artifacts/Null.png").getImage().getScaledInstance(width * 10 / 256, height * 10 / 144, Image.SCALE_DEFAULT)));
            }

        }

        if (Hero.hasWand()) {
            con1.gridx = 4;
            con1.gridy = 0;
            statisticspanel.add(manaimage, con1);
            int widthstat = width * 100 / 250;
            statisticspanel.setBounds((width - widthstat) / 2, height / 91, widthstat, height / 10);
        } else {
            statisticspanel.remove(manaimage);
            int widthstat = width * 100 / 355;
            statisticspanel.setBounds((width - widthstat) / 2, height / 91, widthstat, height / 10);

        }


        healthimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Health" + (int) Hero.getHealth() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));
        fameimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Fame" + (int) Hero.getFame() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));
        moneyimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Money" + (int) Hero.getMoney() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));
        loyaltyimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + (int) Hero.getLoyalty() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));
        manaimage.setIcon((new ImageIcon(new ImageIcon(path_resources + "Statistics/Mana" + (int) Hero.getMana() / 10 + ".png").getImage().getScaledInstance(width / 16, height / 10, Image.SCALE_DEFAULT))));


    }


    private void changeFocus(JButton button) {
        button.requestFocus();

        for (JButton b : menubutton) {
            if (b == button) {
                b.setIcon((new ImageIcon(new ImageIcon(path_resources + "buttonstartsel.png").getImage().getScaledInstance(widthbs, heightbs, Image.SCALE_DEFAULT))));
            } else {
                b.setIcon((new ImageIcon(new ImageIcon(path_resources + "buttonstart.png").getImage().getScaledInstance(widthbs, heightbs, Image.SCALE_DEFAULT))));
            }
        }
    }

    //timer for time
    TimerTask timerchange = new TimerTask() {
        @Override
        public void run() {
            Hero.age++;
            Hero.yearsOfService++;
            textheroage.setText("Age: " + String.valueOf(Hero.getAge()));
            textyear.setText("Years of service: " + Hero.getYearsOfService());

        }
    };

    //timer so that the event starts after the animation's end
    TimerTask taskevent = new TimerTask() {
        @Override
        public void run() {
            setDescription(ev.getEventDescription());

        }
    };


    private class SelectWest extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (a == 1) {
                OptionConsequence(0);
                a = 2;
            }

        }
    }

    private class SelectEast extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (a == 1) {
                OptionConsequence(1);
                a = 2;
            }
        }
    }

    private class SelectNorth extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (a == 1) {
                if (ev.getOptionNumber() == 3 || ev.getOptionNumber() == 4) {
                    OptionConsequence(2);
                    a = 2;
                }
            }

        }
    }

    private class SelectSouth extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (a == 1) {
                if (ev.getOptionNumber() == 4) {
                    OptionConsequence(3);
                    a = 2;
                }
            }
        }
    }

    private class Continue extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            switch (a) {
                case 0:

                    setShields();
                    revalidate();
                    repaint();

                    //setDescription(ev.getEventDescription());
                    a = 1;
                    break;

                case 1:
                    removeShields();
                    revalidate();
                    repaint();
                    a = 0;
                    break;

                case 2:

                    for (int i = 0; i < 5; i++) {  //i<4 o i <5?
                        if (Hero.stats[i] == 100 || Hero.stats[i] == 0) {
                            heroDied(i);
                            a = 3;
                            break;
                        } else {
                            ev.getEvent();
                            //this.add(eventimage, 2, 0);
                            //this.add(eventtext, 2,0);
                            setDescription(ev.getEventDescription());
                            a = 0;
                        }
                    }
                    break;

                case 3:
                    afterDeath();
                  /*  backgroundimage.setIcon(new ImageIcon(new ImageIcon(path_resources + "b4.png").getImage().getScaledInstance(width, height, Image.SCALE_DEFAULT)));
                    death.setText("You died. You did " + Hero.getYearsOfService() + " years of service and you completed " + ev.completedEvents + " events.");
                    add(deathpanel, 5, 0);
                    deathpanel.setBounds(0,0, width,height);
                    revalidate();
                    repaint();*/
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

    /**
     * the space key select the focused button; so for the menu --> change the focus
     * (if not the space triggers the new game and I can't stop it;
     * in game this problem is being taken care of in a different way that here it didnt work)
     */
    private class GoDown extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (b < 3) {
                b++;
            } else {
                b = 0;
            }

            if (b == 0) {
                changeFocus(b_newgame);
            }
            if (b == 1) {
                changeFocus(b_load);
            }
            if (b == 2) {
                changeFocus(b_settings);
            }
            if (b == 3) {
                changeFocus(b_exit);
            }


        }
    }

    private class GoUp extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {

            if (b > 0) {
                b--;
            } else {
                b = 3;
            }

            if (b == 0) {
                changeFocus(b_newgame);
            }
            if (b == 1) {
                changeFocus(b_load);
            }
            if (b == 2) {
                changeFocus(b_settings);
            }
            if (b == 3) {
                changeFocus(b_exit);
            }


        }
    }

    private class GoDownLoad extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (s < 3) {
                s++;
            } else {
                s = 1;
            }

            switch (s) {
                case 1:
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    break;
                case 2:
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    break;
                case 3:
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    break;


            }

        }
    }

    private class GoUpLoad extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (s < 1) {
                s--;
            } else {
                s = 3;
            }

            switch (s) {
                case 1:
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    break;
                case 2:
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    break;
                case 3:
                    b_slot3.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot1.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    b_slot2.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                    break;


            }

        }
    }

    private class LoadNewGame extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            switch (s) {
                case 1:
                    remove(b_back);
                    remove(slotpanel);
                    revalidate();
                    repaint();

                    if (isloading) {
                        ev.load(1);
                    } else {
                        ev.newGame();
                    }

                    newGame(1);
                    break;
                case 2:
                    remove(b_back);
                    remove(slotpanel);
                    revalidate();
                    repaint();

                    if (isloading) {
                        ev.load(2);
                    } else {
                        ev.newGame();
                    }

                    newGame(2);
                    break;
                case 3:

                    remove(b_back);
                    remove(slotpanel);
                    revalidate();
                    repaint();

                    if (isloading) {
                        ev.load(3);
                    } else {
                        ev.newGame();
                    }

                    newGame(3);
                    break;
            }
        }
    }


    private class ChangeButtonDeath extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (p == 0) {
                b_d_newgame.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                b_d_exit.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                p = 1;
            } else {
                b_d_newgame.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonload.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                b_d_exit.setIcon(new ImageIcon(new ImageIcon(path_resources + "buttonloadsel.png").getImage().getScaledInstance(width / 2, height * 10 / 72, Image.SCALE_DEFAULT)));
                p = 0;
            }

        }

    }


    private class AfterDeath extends AbstractAction {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (p == 0) {
                System.exit(0);
            } else {
                //new game
            }
        }

    }

}