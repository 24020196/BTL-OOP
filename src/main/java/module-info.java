module org.BTL {
    requires javafx.controls;
    requires javafx.fxml;


    opens Entity to javafx.fxml;
    exports Entity;
    exports GameManager;
    opens GameManager to javafx.fxml;
    exports RenderView;
    opens RenderView to javafx.fxml;
}