module com.example.generatorewolucyjny {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;


    opens ge to javafx.fxml;
    exports ge;
}