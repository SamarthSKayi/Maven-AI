module com.example.firstfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires java.net.http;
    requires org.json;
//    requires com.gluonhq.charm.glisten;
    requires java.desktop;
    requires pdfbox.app;


    opens com.example.firstfx to javafx.fxml;
    exports com.example.firstfx;

//    opens com.example.firstfx.ChoiceBox to javafx.fxml;
    exports com.example.firstfx.controller;
    opens com.example.firstfx.controller to javafx.fxml;
    exports com.example.firstfx.Elements;
    opens com.example.firstfx.Elements to javafx.fxml;
//    exports com.example.firstfx.start;
//    opens com.example.firstfx.start to javafx.fxml;
}