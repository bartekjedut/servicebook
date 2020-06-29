module servicebook {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;

    exports pl.bartek.servicebook.app to javafx.graphics;
    opens pl.bartek.servicebook.controller to javafx.fxml;
    opens pl.bartek.servicebook.model to javafx.base;
}