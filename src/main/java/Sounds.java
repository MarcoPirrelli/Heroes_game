import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Sounds {

    private Clip soundtrack;
    private Clip death_sound;
    private Clip tap_sound;

    boolean sound_is_on = true;


    public void getSoundtrack(){

        if(sound_is_on){
            try{
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream("src/main/resources/Sounds/TheRealm.wav")));
                soundtrack = AudioSystem.getClip();
                soundtrack.open(inputStream);}
            catch (Exception e){
                // disable sound if something goes wrong
                System.out.println(e.getMessage());
            }
            soundtrack.loop(Clip.LOOP_CONTINUOUSLY);
            soundtrack.start();
        }
        else{
            soundtrack.stop();
        }
    }
    public void getDeathSound(){
        if(sound_is_on){
            try{
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream("src/main/resources/Sounds/Death.wav")));
                death_sound = AudioSystem.getClip();
                death_sound.open(inputStream);}
            catch (Exception e){
                // disable sound if something goes wrong
                System.out.println(e.getMessage());
            }
            death_sound.start();
        }


    }

    public void getTapSound(){
        if(sound_is_on){
            try{
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream("src/main/resources/Sounds/Tap.wav")));
                tap_sound = AudioSystem.getClip();
                tap_sound.open(inputStream);}
            catch (Exception e){
                // disable sound if something goes wrong
                System.out.println(e.getMessage());
            }

            tap_sound.start();
        }
    }

    public void stop(){
        sound_is_on = !sound_is_on;
        getSoundtrack();

    }
}
