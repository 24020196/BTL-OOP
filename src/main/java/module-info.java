module org.example.btl {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens Entity to javafx.fxml;
    exports Entity;
    exports GameManager;
    opens GameManager to javafx.fxml;
}