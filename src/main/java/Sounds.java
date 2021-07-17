import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Sounds {

    String path_sounds = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "Sounds" + File.separator;
    private Clip soundtrack;
    private Clip death_sound;
    private Clip tap_sound;
    private Clip magic_sound;
    private Clip curse_sound;
    private Clip scale_sound;
    private Clip crow_sound;

    boolean sound_is_on = true;


    public void getSoundtrack(){

        if(sound_is_on){
            try{
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path_sounds + "TheRealm.wav")));
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
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path_sounds + "Death.wav")));
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
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path_sounds + "Tap.wav")));
                tap_sound = AudioSystem.getClip();
                tap_sound.open(inputStream);}
            catch (Exception e){
                // disable sound if something goes wrong
                System.out.println(e.getMessage());
            }

            tap_sound.start();
        }
    }

    public void getMagicSound(){
        if(sound_is_on){
            try{
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path_sounds + "Magic.wav")));
                magic_sound = AudioSystem.getClip();
                magic_sound.open(inputStream);}
            catch (Exception e){
                // disable sound if something goes wrong
                System.out.println(e.getMessage());
            }

            magic_sound.start();
        }
    }

    public void getCurseSound(){
        if(sound_is_on){
            try{
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path_sounds + "Curse.wav")));
                curse_sound = AudioSystem.getClip();
                curse_sound.open(inputStream);}
            catch (Exception e){
                // disable sound if something goes wrong
                System.out.println(e.getMessage());
            }

            curse_sound.start();
        }
    }

    public void getScaleSound(){
        if(sound_is_on){
            try{
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path_sounds + "Scale.wav")));
                scale_sound = AudioSystem.getClip();
                scale_sound.open(inputStream);}
            catch (Exception e){
                // disable sound if something goes wrong
                System.out.println(e.getMessage());
            }

            scale_sound.start();
        }
    }

    public void getCrowSound(){
        if(sound_is_on){
            try{
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(path_sounds + "Crow.wav")));
                crow_sound = AudioSystem.getClip();
                crow_sound.open(inputStream);}
            catch (Exception e){
                // disable sound if something goes wrong
                System.out.println(e.getMessage());
            }

            crow_sound.start();
        }
    }

    public void getArtifactSound(){
        if(Hero.artefacts[0]){
            getMagicSound();
        }
        else if (Hero.artefacts[1]){
            getCurseSound();
        }
        else if (Hero.artefacts[1]){
            getScaleSound();
        }
        else if (Hero.artefacts[1]){
            getCrowSound();
        }
    }
    public void stop(){
        sound_is_on = !sound_is_on;
        getSoundtrack();

    }
}
