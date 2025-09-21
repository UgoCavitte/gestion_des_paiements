module com.gestion_paiements {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires javafx.base;
    requires com.gestion_paiements;

    exports com.gestion_paiements;
    exports com.gestion_paiements.controllers;
    exports com.gestion_paiements.controllers.parameters;
    opens com.gestion_paiements to javafx.fxml;
    opens com.gestion_paiements.controllers to javafx.fxml;
    opens com.gestion_paiements.controllers.parameters to javafx.fxml;
}