module com.gestion_paiements.gestion_des_paiements {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;
    requires com.gestion_paiements.gestion_des_paiements;


    opens com.gestion_paiements.gestion_des_paiements to javafx.fxml;
    exports com.gestion_paiements.gestion_des_paiements;
    exports com.gestion_paiements.gestion_des_paiements.controllers;
    opens com.gestion_paiements.gestion_des_paiements.controllers to javafx.fxml;
}