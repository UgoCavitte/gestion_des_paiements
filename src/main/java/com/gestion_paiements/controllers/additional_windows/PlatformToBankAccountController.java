package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.Main;
import com.gestion_paiements.controllers.elements.SelectablePaymentLineController;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.StatusPaymentFromClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlatformToBankAccountController {

    private Destination platform;

    public void setPlatform(Destination platform) {
        this.platform = platform;
    }

    @FXML
    private Button buttonValidate;

    @FXML
    private VBox vboxPayments;

    List<SelectablePaymentLineController> list;

    @FXML
    private void initialize () {
        List<PaymentFromClient> payments = platform.getTransfers().stream()
                                            .map(p -> (PaymentFromClient) p) // Platform receive payments only from clients
                                            .filter(p -> p.getStatus() == StatusPaymentFromClient.SENT_TO_A_PLATFORM)
                                            .toList();

        // Lines creation
        list = new ArrayList<>();
        vboxPayments.getChildren().clear();

        payments.forEach(p -> System.out.println(p.getId()));

        for (PaymentFromClient p : payments) {
            SelectablePaymentLineController controller = new SelectablePaymentLineController();
            controller.setPayment(p);
            list.add(controller);

            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("elements/selectable-payment-line.fxml"));

                loader.setController(controller);

                AnchorPane pane = loader.load();

                vboxPayments.getChildren().add(pane);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }

    @FXML
    void validate(ActionEvent event) {

    }


}
