module com.gestion_paiements {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires javafx.base;
    requires javafx.graphics;

    exports com.gestion_paiements;
    exports com.gestion_paiements.data;
    exports com.gestion_paiements.types;
    exports com.gestion_paiements.types.payments;
    exports com.gestion_paiements.util;
    exports com.gestion_paiements.controllers;
    exports com.gestion_paiements.controllers.accounts_tables;
    exports com.gestion_paiements.controllers.parameters;
    opens com.gestion_paiements to javafx.fxml;
    opens com.gestion_paiements.controllers to javafx.fxml;
    opens com.gestion_paiements.controllers.accounts_tables to javafx.fxml;
    opens com.gestion_paiements.controllers.parameters to javafx.fxml;
}