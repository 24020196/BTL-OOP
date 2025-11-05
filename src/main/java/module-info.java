module org {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires transitive javafx.graphics;
    requires java.sql;

    opens Entity to javafx.fxml;
    exports Entity;
    exports GameManager;
    opens GameManager to javafx.fxml;
    exports RenderView;
    opens RenderView to javafx.fxml;
}