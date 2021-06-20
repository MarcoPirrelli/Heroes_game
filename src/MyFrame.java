import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.Timer;


public class MyFrame extends JFrame implements ActionListener {

    //remember to change it
    String path_resources = "Resources/";
    Font f = new Font("serif", Font.PLAIN, 10);

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
    private final JButton b_n, b_s, b_w, b_e;

    //Variables
    int a = 0;
    String text = "";
    String name;


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
        eventtext.setHorizontalAlignment(SwingConstants.CENTER);;
        float size = 20;
        eventtext.setFont(eventtext.getFont().deriveFont(size));

        //Event Button
        click = new JButton(); //to click on nowhere
        click.addActionListener(this);

        b_n = new JButton("NORTH");
        b_s = new JButton("SOUTH");
        b_w = new JButton("WEST");
        b_e = new JButton("EAST");

        JButton[] d1 = {b_n, b_s, b_w, b_e};

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

        setContentPane(frame);
        setSize(1920, 1080);
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
            click.setBounds(0, 0, 1920, 1080);

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

                if (ev.getOptionNumber() == 4){
                JButton[] d1 = {b_n, b_s, b_w, b_e};

                float size = 20;
                for (JButton b : d1){
                    this.add(b, 3, 0);
                    b.setContentAreaFilled(false);
                    b.setBorderPainted(false);
                    b.setFont(heroname.getFont().deriveFont(size));
                }

                    setDescriptionButton(b_n, ev.getDesc(0));
                    setDescriptionButton(b_s, ev.getDesc(1));
                    setDescriptionButton(b_w, ev.getDesc(2));
                    setDescriptionButton(b_e, ev.getDesc(3));

                    b_n.setBounds(820, 80, 280, 330);
                    b_s.setBounds(820, 580, 280, 330);
                    b_w.setBounds(565, 350, 280, 330);
                    b_e.setBounds(1045, 350, 280, 330);

                    a = 1;

                }
                else if (ev.getOptionNumber() == 3){

                    JButton[] d1 = {b_n, b_s, b_w};

                    float size = 20;
                    for (JButton b : d1){
                        this.add(b, 3, 0);
                        b.setContentAreaFilled(false);
                        b.setBorderPainted(false);
                        b.setFont(heroname.getFont().deriveFont(size));
                    }

                    setDescriptionButton(b_n, ev.getDesc(1));
                    setDescriptionButton(b_s, ev.getDesc(2));
                    setDescriptionButton(b_w, ev.getDesc(3));

                    b_n.setBounds(820, 80, 280, 330);
                    b_s.setBounds(820, 580, 280, 330);
                    b_w.setBounds(565, 350, 280, 330);
            }
                else if (ev.getOptionNumber() == 2){

                    JButton[] d1 = {b_n, b_s};

                    float size = 20;
                    for (JButton b : d1){
                        this.add(b, 3, 0);
                        b.setContentAreaFilled(false);
                        b.setBorderPainted(false);
                        b.setFont(heroname.getFont().deriveFont(size));
                    }

                    setDescriptionButton(b_n, ev.getDesc(1));
                    setDescriptionButton(b_s, ev.getDesc(2));

                    b_n.setBounds(820, 80, 280, 330);
                    b_s.setBounds(820, 580, 280, 330);
                }


            break;

            case 1:
                this.remove(b_n);
                this.remove(b_s);
                this.remove(b_w);
                this.remove(b_e);

                a = 0;

            break;

            case 2:
                ev.getEvent();
                this.add(eventimage, 2, 0);
                this.add(eventtext, 2,0);
                setDescription(ev.getEventDescription());
                a = 0;


        }

        }

        if (e.getSource() == b_n){

            a = 2;
            this.remove(b_s);
            this.remove(b_w);
            this.remove(b_e);
            this.remove(b_n);

           //Hero.setLoyalty(ev.getDeltaLoyalty(0));
            ev.pickOption(0);
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));

            setDescription(ev.getResult(0));

            //Consequences on statistics?
        }

        if (e.getSource() == b_s){
            a = 2;
            this.remove(b_n);
            this.remove(b_w);
            this.remove(b_e);
            this.remove(b_s);

            setDescription(ev.getResult(1));

            Hero.setLoyalty(ev.getDeltaLoyalty(0));
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
        }

        if (e.getSource() == b_w){
            a = 2;
            this.remove(b_n);
            this.remove(b_s);
            this.remove(b_e);
            this.remove(b_w);

            setDescription(ev.getResult(2));

            Hero.setLoyalty(ev.getDeltaLoyalty(0));
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
        }

        if (e.getSource() == b_e){
            a = 2;

            this.remove(b_n);
            this.remove(b_s);
            this.remove(b_w);
            this.remove(b_e);

            setDescription(ev.getResult(3));

            Hero.setLoyalty(ev.getDeltaLoyalty(0));
            loyaltyimage.setIcon(new ImageIcon(path_resources + "Statistics/Loyalty" + Hero.getLoyalty() + ".png"));
        }

    }

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


    //timer for time
    TimerTask timerchange = new TimerTask() {
        @Override
        public void run() {
            time++;
            textheroage.setText("Age: " + String.valueOf(ev.getAge()));
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



}