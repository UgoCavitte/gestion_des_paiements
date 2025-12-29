module com.gestion_paiements {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires javafx.base;
    requires javafx.graphics;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    exports com.gestion_paiements;
    exports com.gestion_paiements.data;
    exports com.gestion_paiements.types;
    exports com.gestion_paiements.types.payments;
    exports com.gestion_paiements.util;
    exports com.gestion_paiements.controllers;
    exports com.gestion_paiements.controllers.accounts_tables;
    exports com.gestion_paiements.controllers.parameters;
    exports com.gestion_paiements.controllers.warnings;
    exports com.gestion_paiements.controllers.elements;

    exports com.gestion_paiements.saving_formats to com.fasterxml.jackson.databind;

    opens com.gestion_paiements to javafx.graphics, javafx.fxml;
    opens com.gestion_paiements.controllers to javafx.fxml;
    opens com.gestion_paiements.controllers.accounts_tables to javafx.fxml;
    opens com.gestion_paiements.controllers.parameters to javafx.fxml;
    opens com.gestion_paiements.controllers.additional_windows to javafx.fxml;
    opens com.gestion_paiements.controllers.warnings to javafx.fxml;
    opens com.gestion_paiements.controllers.elements to javafx.fxml;
}