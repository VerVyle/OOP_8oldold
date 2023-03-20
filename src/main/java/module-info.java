module com.vervyle.lw8_oop {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.vervyle.lw8_oop to javafx.fxml;
    exports com.vervyle.lw8_oop;
    exports com.vervyle.lw8_oop.controllers;
    opens com.vervyle.lw8_oop.controllers to javafx.fxml;
    exports com.vervyle.lw8_oop.controllers.states;
    opens com.vervyle.lw8_oop.controllers.states to javafx.fxml;
    exports com.vervyle.lw8_oop.containers;
    opens com.vervyle.lw8_oop.containers to javafx.fxml;
    exports com.vervyle.lw8_oop.factories;
    opens com.vervyle.lw8_oop.factories to javafx.fxml;
}