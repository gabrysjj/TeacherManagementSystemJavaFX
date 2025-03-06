module org.example.projekt3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens org.example.projekt3 to javafx.fxml;
    exports org.example.projekt3;
}