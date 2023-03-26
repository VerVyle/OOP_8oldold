module com.vervyle.lw8_oop {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


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
    opens com.vervyle.lw8_oop.drawable to com.fasterxml.jackson.databind;
    exports com.vervyle.lw8_oop.drawable;
    opens com.vervyle.lw8_oop.drawable.leafs to com.fasterxml.jackson.databind;
    exports com.vervyle.lw8_oop.drawable.leafs;
    opens com.vervyle.lw8_oop.drawable.render to com.fasterxml.jackson.databind;
    exports com.vervyle.lw8_oop.drawable.render;
    exports com.vervyle.lw8_oop.drawable.utils;
    opens com.vervyle.lw8_oop.drawable.utils to com.fasterxml.jackson.databind;
}