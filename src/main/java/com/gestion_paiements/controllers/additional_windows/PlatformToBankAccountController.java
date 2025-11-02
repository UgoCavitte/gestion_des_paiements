package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.StatusPaymentFromClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;

import java.util.List;

public class PlatformToBankAccountController {

    private Destination platform;

    public void setPlatform(Destination platform) {
        this.platform = platform;
    }

    @FXML
    private Button buttonValidate;

    @FXML
    private ListView<HBox> listViewPayments;

    @FXML
    private void initialize () {
        List<PaymentFromClient> payments = platform.getTransfers().stream()
                                            .map(p -> (PaymentFromClient) p) // Platform receive payments only from clients
                                            .filter(p -> p.getStatus() == StatusPaymentFromClient.SENT_TO_A_PLATFORM)
                                            .toList();

        // Lines creation
        // TODO Do it with FXML injection

    }

    @FXML
    void validate(ActionEvent event) {

    }


}
