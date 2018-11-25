package uet.oop.bomberman.music;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;
import uet.oop.bomberman.level.FileLevelLoader;

public final class GameSound {
    public static GameSound instance;

    public static final String MENU = "Title Screen.wav";
    public static final String PLAYGAME = "Stage Theme.wav";
    public static final String BOMB = "Newbomb.wav";
    public static final String BOMBER_DIE = "Bomber Die.wav";
    public static final String MONSTER_DIE = "monster_die.wav";
    public static final String BONG_BANG = "Bomb Bang.wav";
    public static final String ITEM = "Find item.wav";
    public static final String WIN = "Win.wav";
    public static final String GAME_OVER = "Game Over.wav";
    public static final String WALK = "walk6.wav";
    private HashMap<String, AudioClip> audioMap;

    public GameSound() {
        audioMap = new HashMap<>();
        loadAllAudio();
    }

    public static GameSound getIstance(){
        if (instance == null) {
            instance = new GameSound();
        }

        return instance;
    }

    public void loadAllAudio(){
        putAudio(MENU);
        putAudio(PLAYGAME);
        putAudio(BOMB);
        putAudio(MONSTER_DIE);
        putAudio(BOMBER_DIE);
        putAudio(BONG_BANG);
        putAudio(ITEM);
        putAudio(WIN);
        putAudio(GAME_OVER);
        putAudio(WALK);
    }

    public void stop() {
        getAudio(MENU).stop();;
        getAudio(PLAYGAME).stop();
        getAudio(BOMB).stop();
        getAudio(BONG_BANG).stop();
        getAudio(WIN).stop();
        getAudio(GAME_OVER).stop();
    }

    public void putAudio(String name){
        String path = "music/" + name;
        URL absPath = FileLevelLoader.class.getResource("/" + path);
        AudioClip auClip = Applet.newAudioClip(absPath);
        audioMap.put(name, auClip);
    }

    public AudioClip getAudio(String name) {
        return audioMap.get(name);
    }
}