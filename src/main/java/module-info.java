module com.grawgo.gra_w_go {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires jdk.xml.dom;


    opens com.grawgo.gra_w_go to javafx.fxml;
    exports com.grawgo.gra_w_go;
}