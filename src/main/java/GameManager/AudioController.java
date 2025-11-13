package GameManager;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Quản lý tất cả các hoạt động liên quan đến âm thanh trong trò chơi.
 * Bao gồm nhạc nền (BGM) và hiệu ứng âm thanh (SFX).
 * Sử dụng mẫu thiết kế Singleton để đảm bảo chỉ có một trình quản lý âm thanh.
 */
public class AudioController {

    private static AudioController instance;

    /**
     * Lấy thể hiện (instance) duy nhất của AudioController.
     *
     * @return Thể hiện AudioController.
     */
    public static AudioController getInstance() {
        if (instance == null) instance = new AudioController();
        return instance;
    }

    private MediaPlayer bgMusicPlayer; // Trình phát nhạc nền
    private double bgmVolume = 0.5; // Âm lượng nhạc nền
    private double sfxVolume = 0.5; // Âm lượng hiệu ứng

    // Sử dụng một Thread Pool (bộ đệm luồng) để phát các hiệu ứng âm thanh (SFX)
    // mà không làm gián đoạn luồng chính của trò chơi (JavaFX Application Thread).
    private final ExecutorService soundExecutor = Executors.newCachedThreadPool();

    // Tải trước các hiệu ứng âm thanh để tránh bị trễ khi phát lần đầu
    private final AudioClip brickHit = new AudioClip(getClass().getResource("/audio/brick_hit.wav").toExternalForm());
    private final AudioClip brickBreak = new AudioClip(getClass().getResource("/audio/brick_break.wav").toExternalForm());
    private final AudioClip powerUp = new AudioClip(getClass().getResource("/audio/power_up_paddle.wav").toExternalForm());
    private final AudioClip paddleHit = new AudioClip(getClass().getResource("/audio/paddle_hit.wav").toExternalForm());
    private final AudioClip wallHit = new AudioClip(getClass().getResource("/audio/paddle_hit.wav").toExternalForm()); // Dùng chung file với paddle_hit
    private final AudioClip powerUpShoot = new AudioClip(getClass().getResource("/audio/power_up_shoot.wav").toExternalForm());
    private final AudioClip ballfailing = new AudioClip(getClass().getResource("/audio/game_lost.wav").toExternalForm());

    /**
     * Phát nhạc nền của màn hình Menu.
     * Tự động dừng nhạc nền cũ nếu đang phát.
     */
    public void playMenuMusic() {
        stopMusic();
        Media sound = new Media(getClass().getResource("/audio/menu.wav").toExternalForm());
        bgMusicPlayer = new MediaPlayer(sound);
        bgMusicPlayer.setVolume(bgmVolume);
        bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
        bgMusicPlayer.play();
    }

    /**
     * Phát nhạc nền trong màn chơi (gameplay).
     * Tự động dừng nhạc nền cũ nếu đang phát.
     */
    public void playGameMusic() {
        stopMusic();
        Media sound = new Media(getClass().getResource("/audio/game.wav").toExternalForm());
        bgMusicPlayer = new MediaPlayer(sound);
        bgMusicPlayer.setVolume(bgmVolume);
        bgMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Lặp vô hạn
        bgMusicPlayer.play();
    }

    // Các phương thức công khai để phát hiệu ứng âm thanh
    public void playBrickHit() { playInThread(brickHit); }
    public void playBrickBreak() { playInThread(brickBreak); }
    public void playPowerUp() { playInThread(powerUp); }
    public void playPaddleHit() { playInThread(paddleHit); }
    public void playWallHit() { playInThread(wallHit); }
    public void playPowerUpShoot() {playInThread(powerUpShoot);}
    public void playBallFailing() {playInThread(ballfailing);}

    /**
     * Phương thức nội bộ để phát một AudioClip trên một luồng riêng
     * sử dụng ExecutorService.
     *
     * @param clip AudioClip cần phát.
     */
    private void playInThread(AudioClip clip) {
        soundExecutor.execute(() -> clip.play(sfxVolume));
    }

    /**
     * Đặt âm lượng cho nhạc nền (BGM).
     *
     * @param volume Giá trị âm lượng (từ 0.0 đến 1.0).
     */
    public void setBgmVolume(double volume) {
        this.bgmVolume = volume;
        if (bgMusicPlayer != null) bgMusicPlayer.setVolume(volume);
    }

    /**
     * Đặt âm lượng cho tất cả các hiệu ứng âm thanh (SFX).
     *
     * @param volume Giá trị âm lượng (từ 0.0 đến 1.0).
     */
    public void setSfxVolume(double volume) {
        this.sfxVolume = volume;
    }

    public double getBgmVolume() { return bgmVolume; }
    public double getSfxVolume() { return sfxVolume; }

    /**
     * Dừng phát nhạc nền hiện tại.
     */
    public void stopMusic() {
        if (bgMusicPlayer != null) bgMusicPlayer.stop();
    }

    /**
     * Tắt ExecutorService (bộ đệm luồng) một cách an toàn.
     * Cần được gọi khi ứng dụng thoát để đảm bảo tất cả luồng được đóng đúng cách.
     */
    public void shutdown() {
        soundExecutor.shutdown();
    }
}