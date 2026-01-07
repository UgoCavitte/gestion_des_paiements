package com.gestion_paiements.controllers.additional_windows;

import com.gestion_paiements.Main;
import com.gestion_paiements.controllers.elements.SelectablePaymentLineController;
import com.gestion_paiements.data.RefreshableData;
import com.gestion_paiements.types.Amount;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.DestinationType;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.PaymentFromPlatform;
import com.gestion_paiements.types.payments.StatusPaymentFromClient;
import com.gestion_paiements.util.Dates;
import com.gestion_paiements.util.Destinations;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class PlatformToBankAccountController {

    private Destination platform;

    public void setPlatform(Destination platform) {
        this.platform = platform;
    }

    @FXML
    private TextArea areaComment;

    @FXML
    private Button buttonValidate;

    @FXML
    private VBox vboxPayments;

    @FXML
    private DatePicker dateSent;

    @FXML
    private DatePicker dateReceived;

    @FXML
    private TextField fieldCommission;

    @FXML
    private Label labelCurrency;

    @FXML
    private Label labelError;

    @FXML
    private ComboBox<String> boxBankAccount;

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

        // Setting other elements
        List<Destination> rawAccounts = platform
                                            .getCountry()
                                            .getAccountsAndPlatforms()
                                            .values().stream()
                                            .filter(p -> p.getDestinationType() == DestinationType.bankAccount)
                                            .toList();

        ObservableList<String> accounts = FXCollections.observableList(
                        rawAccounts.stream()
                        .map(Destination::getName)
                        .toList());
        boxBankAccount.setItems(accounts);
        boxBankAccount.setValue(accounts.getFirst());

        dateSent.setConverter(Dates.dateStringConverter);
        dateReceived.setConverter(Dates.dateStringConverter);

        fieldCommission.setText("0");
        labelCurrency.setText(rawAccounts.getFirst().getCurrency().getName());

    }

    @FXML
    void dateSentSelected(ActionEvent event) {
        if (dateSent.getValue() == null)  {
            return;
        }
        dateReceived.setValue(dateSent.getValue());
    }

    @FXML
    void validate(ActionEvent event) {

        labelError.setText("");

        // CHECKING FOR NULL VALUES
        if (dateSent.getValue() == null) {
            labelError.setText("Veuillez sélectionner une date d'envoi.");
            return;
        }

        if (dateReceived.getValue() == null) {
            labelError.setText("Veuillez sélectionner une date de réception.");
            return;
        }

        double commissionParsed;
        try {
            commissionParsed = Double.parseDouble(fieldCommission.getText());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
            labelError.setText("Veuillez entrer une commission valable.");
            return;
        }

        Destination account = Destinations.fromStringToDestination(boxBankAccount.getValue());
        if (account == null) {
            labelError.setText("Veuillez sélectionner un compte en banque destinataire.");
            return;
        }


        // CREATING THE LIST AND SENDING IT

        HashSet<PaymentFromClient> toSend = list.stream().
                filter(SelectablePaymentLineController::isSelected).
                map(SelectablePaymentLineController::getPayment).collect(Collectors.toCollection(HashSet::new));

        // Do not forget to mark the payments as sent from the platform
        toSend.forEach(p -> p.setStatus(StatusPaymentFromClient.SENT_TO_THE_BANK));

        PaymentFromPlatform sent = new PaymentFromPlatform(
                toSend,
                platform,
                account,
                dateSent.getValue(),
                dateReceived.getValue(),
                new Amount(commissionParsed, platform.getCurrency()),
                areaComment.getText()

        );

        account.getTransfers().add(sent);
        Data.instance.getSetPayments().add(sent);

        // TODO Write to memory

        // TODO Payment doesn't appear on the LCL table
        RefreshableData.refreshTables();

        // Close window
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }


}
