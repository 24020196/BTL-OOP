package GameManager;

import Entity.User;
import GameDatabase.ScoreDataAccessObject;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.text.Font;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;

public class HighScoresController {

    @FXML private ListView<String> highScoreList;

    private ScoreDataAccessObject scoreDataAccessObject = new ScoreDataAccessObject();

    public void initialize() {
        loadArcadeFont();
        loadHighScores();
    }

    //load font arcade
    private void loadArcadeFont() {
        try (InputStream fontStream = getClass().getResourceAsStream("/fonts/arcade.ttf")) {
            if (fontStream != null) {
                Font arcade = Font.loadFont(fontStream, 18);
                highScoreList.setStyle("-fx-font-family: 'Arcade'; -fx-font-size: 18px; -fx-text-fill: white;");
            } else {
                System.err.println("Không tìm thấy font arcade.ttf");
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi nạp font arcade.ttf:");
            e.printStackTrace();
        }
    }

    private void loadHighScores() {
        //lấy danh sách điểm
        Queue<Pair<String, Integer>> scores = User.getUser().highScores;
        List<Pair<String, Integer>> listScores = new ArrayList<>(scores);
        listScores.sort(Comparator.comparing(Pair::getValue, Comparator.reverseOrder()));


        // Đưa dữ liệu vào ListView
        ObservableList<String> displayScores = FXCollections.observableArrayList();
        for (int i = 0; i < listScores.size(); i++) {
            Pair<String, Integer> s = listScores.get(i);
            displayScores.add(String.format("%2d. %-10s %6d điểm", i + 1, s.getKey(), s.getValue()));
        }

        highScoreList.setItems(displayScores);

        /*for (Pair<String, Integer> temp : User.getUser().highScores) {
            System.out.println(temp.getKey() + " " + temp.getValue());
        }*/
    }

    public void winGame() {
        try {
            String username = User.getUser().getUsername();
            String point = User.getUser().getLevelPoint();
            scoreDataAccessObject.setPoint(username, point);
            System.out.println("Cập nhật điểm thành công cho " + username + ": " + point);
        } catch (Exception e) {
            System.err.println("Lỗi khi lưu điểm:");
            e.printStackTrace();
        }

        //scoreDataAccessObject.setPoint(User.getUser().getUsername(), User.getUser().getLevelPoint());
    }

}