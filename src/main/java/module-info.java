module org {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

//    requires org.controlsfx.controls;
//    requires com.dlsc.formsfx;
//    requires org.kordamp.bootstrapfx.core;
//    requires com.almasb.fxgl.all;
    requires transitive javafx.graphics;
    requires javafx.media;

    opens Entity to javafx.fxml;
    exports Entity;
    exports GameManager;
    opens GameManager to javafx.fxml;
    exports RenderView;
    opens RenderView to javafx.fxml;

    exports GameDatabase;
    opens GameDatabase to javafx.fxml;
}