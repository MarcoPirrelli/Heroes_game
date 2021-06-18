import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.Timer;
import java.util.concurrent.TimeUnit;



public class MyFrame extends JFrame implements ActionListener {

    //remember to change it
    String path_resources = "D:/Altro/ProgettoEsame/Resources/";
    Font f = new Font("serif", Font.PLAIN, 10);

    EventManager ev = new EventManager();

    //Hero Time
    int time;
    JLabel textyear;
    Timer timertime = new Timer();
    Timer timerevent = new Timer();

    //JLabel
    JLabel backgroundimage;
    JLabel eventimage;
    JLabel eventtext;
    JLabel nametext;

    //JButtons
    private final JButton b_newgame, b_load, b_settings, b_exit;
    private final JButton b_back;
    private final JButton click;
    private final JButton b_n, b_s, b_w, b_e;

    //Variables
    int a = 0;
    int event = 0;
    String text = "";
    String name;
    String[] menubuttons = new String[4];


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

        //Timer
        time = 15;
        textyear = new JLabel(String.valueOf(time));

        //Hero Name
        nametext = new JLabel(name);

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

            event = ev.getEventNumber();

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

            this.add(nametext, 2,0);
            float size0 = 25;
            nametext.setBounds(50,30, 160,50);
            nametext.setForeground(Color.WHITE);
            nametext.setFont(nametext.getFont().deriveFont(size0));
            nametext.setText(Hero.getHeroName()); //the hero name is generated on EventManager


            this.add(eventimage, 2, 0);
            this.add(eventtext, 2,0);
            timerevent.schedule(taskevent, 2000); //delay for the animation

            this.add(textyear,2,0);
            float size1 = 30;
            textyear.setBounds(50, 900, 200, 200);
            textyear.setFont(textyear.getFont().deriveFont(size1));
            textyear.setForeground(Color.WHITE);
            timertime.schedule(timerchange, 5000, 5000); //the time changes every 5 sec

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
            if (a == 0) {

                JButton[] d1 = {b_n, b_s, b_w, b_e};

                float size = 15;
                for (JButton b : d1){
                    this.add(b, 3, 0);
                    b.setContentAreaFilled(false);
                    b.setBorderPainted(false);
                    b.setFont(nametext.getFont().deriveFont(size));
                }



                String text_n = ev.getDescN();
                String[] descr =  {ev.getDescN(), ev.getDescS(), ev.getDescW(), ev.getDescE()};

                int i =  0;
                for (String de : descr){

              /*   if (de.length() <= 25) {
                    if(i == 0){
                        b_n.setText(de);
                }
                    else if(i == 1){
                        b_s.setText(de);
                    }
                    else if(i == 2){
                        b_w.setText(de);
                    }
                    else if(i == 3){
                        b_e.setText(de);
                    }
                }

               /* else if (de.length() > 25 && de.length() <= 50){
                    int c = 24;
                    while (de.charAt(c) != '.'&& de.charAt(c) != ' ' && de.charAt(c) != ','){
                        c++;
                    }

                       b_n.setText("<html>"+ de.substring(0, c + 1)+ "<br>" + text.substring(c + 1, text.length()) + "<html>");

                }
                i++;
                } */
                    b_n.setText(ev.getDescN());
                    b_s.setText(ev.getDescS());
                    b_w.setText(ev.getDescW());
                    b_e.setText(ev.getDescE());

                    b_n.setBounds(820, 80, 280, 330);
                    b_s.setBounds(820, 580, 280, 330);
                    b_w.setBounds(565, 350, 280, 330);
                    b_e.setBounds(1045, 350, 280, 330);

                    a = 1;

                }
            }

            else if (a == 1) {
                this.remove(b_n);
                this.remove(b_s);
                this.remove(b_w);
                this.remove(b_e);

                a = 0;

            }
        }

        if (e.getSource() == b_n){

            a = 0;
            this.remove(b_s);
            this.remove(b_w);
            this.remove(b_e);
            this.remove(b_n);

            text = ev.getResultN();
            setDescription();
            //Consequences on statistics?
        }

        if (e.getSource() == b_s){
            a = 0;
            this.remove(b_n);
            this.remove(b_w);
            this.remove(b_e);
            this.remove(b_s);

            text = ev.getResultS();
            setDescription();
        }

        if (e.getSource() == b_w){
            a = 0;
            this.remove(b_n);
            this.remove(b_s);
            this.remove(b_e);
            this.remove(b_w);

            text = ev.getResultW();
            setDescription();
        }

        if (e.getSource() == b_e){
            a = 0;

            this.remove(b_n);
            this.remove(b_s);
            this.remove(b_w);
            this.remove(b_e);

            text = ev.getResultE();
            setDescription();
        }

    }

    public void setDescription(){

        eventimage.setIcon(new ImageIcon(path_resources + "e" + event + ".jpg"));
        eventimage.setBounds(760,160,400, 640);

        //text event on screen so that every line isn't interrupted
        int length = text.length();
        if (text.length() <= 40) {
            eventtext.setText(text);
        }

        else if (text.length() > 40 && text.length() <= 80 ){
            int   c = 35;
            while (text.charAt(c) != '.'&& text.charAt(c) != ' ' && text.charAt(c) != ','){
                c++;
            }
            eventtext.setText("<html>"+text.substring(0, c + 1)+ "<br>" + text.substring(c + 1, text.length()) + "<html>");
        }

        else if (text.length() > 80 ){
            int   c = 35;
            int   d = 75;
            while (text.charAt(c) != '.'&& text.charAt(c) != ' ' && text.charAt(c) != ','){
                c++;
            }
            while (text.charAt(d) != '.'&& text.charAt(d) != ' ' && text.charAt(d) != ','){
                d++;
            }
            eventtext.setText("<html>"+text.substring(0, c + 1)+ "<br>"+text.substring(c + 1, d)+ "<br>" + text.substring(d + 1, text.length()) + "<html>");

        }
        eventtext.setBounds(720,825, 470, 130);
    }


    //timer for time
    TimerTask timerchange = new TimerTask() {
        @Override
        public void run() {
            time++;
            textyear.setText(String.valueOf(time));

        }
    };

    //timer so that the event starts after the animation's end
    TimerTask taskevent = new TimerTask() {
        @Override
        public void run() {
            //event image on screen
            text = ev.getEventDescription();
            setDescription();

        }
    };


}