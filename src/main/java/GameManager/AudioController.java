package GameManager;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AudioController {

    private static AudioController instance;
    public static AudioController getInstance() {
        if (instance == null) instance = new AudioController();
        return instance;
    }

    private MediaPlayer bgMusicPlayer;
    private double bgmVolume = 0.5;
    private double sfxVolume = 0.5;

    // Thread pool cho các hiệu ứng âm thanh
    private final ExecutorService soundExecutor = Executors.newCachedThreadPool();

    // Load sẵn hiệu ứng
    private final AudioClip brickHit = new AudioClip(getClass().getResource("/audio/brick_hit.wav").toExternalForm());
    private final AudioClip brickBreak = new AudioClip(getClass().getResource("/audio/brick_break.wav").toExternalForm());
    private final AudioClip powerUp = new AudioClip(getClass().getResource("/audio/power_up_paddle.wav").toExternalForm());
    private final AudioClip paddleHit = new AudioClip(getClass().getResource("/audio/paddle_hit.wav").toExternalForm());
    private final AudioClip wallHit = new AudioClip(getClass().getResource("/audio/paddle_hit.wav").toExternalForm());
    private final AudioClip powerUpShoot = new AudioClip(getClass().getResource("/audio/power_up_shoot.wav").toExternalForm());


    // Nhạc nền
    public void playMenuMusic() {
        stopMusic();
        Media sound = new Media(getClass().getResource("/audio/menu.wav").toExternalForm());
        bgMusicPlayer = new MediaPlayer(sound);
        bgMusicPlayer.setVolume(bgmVolume);
        bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgMusicPlayer.play();
    }

    public void playGameMusic() {
        stopMusic();
        Media sound = new Media(getClass().getResource("/audio/game.wav").toExternalForm());
        bgMusicPlayer = new MediaPlayer(sound);
        bgMusicPlayer.setVolume(bgmVolume);
        bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        bgMusicPlayer.play();
    }

    // Các âm thanh hiệu ứng
    public void playBrickHit() { playInThread(brickHit); }
    public void playBrickBreak() { playInThread(brickBreak); }
    public void playPowerUp() { playInThread(powerUp); }
    public void playPaddleHit() { playInThread(paddleHit); }
    public void playWallHit() { playInThread(wallHit); }
    public void playPowerUpShoot() {playInThread(powerUpShoot);}

    private void playInThread(AudioClip clip) {
        soundExecutor.execute(() -> clip.play(sfxVolume));
    }

    public void setBgmVolume(double volume) {
        this.bgmVolume = volume;
        if (bgMusicPlayer != null) bgMusicPlayer.setVolume(volume);
    }

    public void setSfxVolume(double volume) {
        this.sfxVolume = volume;
    }

    public double getBgmVolume() { return bgmVolume; }
    public double getSfxVolume() { return sfxVolume; }

    public void stopMusic() {
        if (bgMusicPlayer != null) bgMusicPlayer.stop();
    }

    public void shutdown() {
        soundExecutor.shutdown();
    }
}
