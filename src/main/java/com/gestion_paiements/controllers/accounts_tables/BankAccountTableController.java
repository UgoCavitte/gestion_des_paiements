package com.gestion_paiements.controllers.accounts_tables;

import com.gestion_paiements.types.*;
import com.gestion_paiements.types.payments.Payment;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.util.Currencies;
import com.gestion_paiements.util.Dates;
import com.gestion_paiements.util.PurchasedProducts;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/// This table must show this information, that the user can enable or disable through parameters :
/// - id
/// - date sent
/// - date received
/// - amount sent
/// - amount received
/// - sender (client or platform)
/// - bought products (if client)

public class BankAccountTableController {

    private Set<Payment> payments;

    @FXML
    private TableView table;

    // A mutable ObservableList to hold the countries for the ListView
    private ObservableList<Payment> paymentsList;

    // Columns
    private TableColumn<Payment, Integer> columnID;
    private TableColumn<Payment, String> columnDateSent;
    private TableColumn<Payment, String> columnDateReceived;
    private TableColumn<Payment, String> columnAmountSent;
    private TableColumn<Payment, String> columnAmountReceived;
    private TableColumn<Payment, String> columnSender;
    private TableColumn<Payment, List<String>> columnBought;

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void initialize () {

        System.out.println(payments);

        // TODO initialize table
        paymentsList = FXCollections.observableArrayList(payments);

        columnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnDateSent.setCellValueFactory(cellData -> new SimpleStringProperty(Dates.fromDateToString(cellData.getValue().getDateSent())));
        columnDateReceived.setCellValueFactory(cellData -> new SimpleStringProperty(Dates.fromDateToString(cellData.getValue().getDateReceived())));
        columnAmountSent.setCellValueFactory(cellData -> new SimpleStringProperty(Currencies.fromAmountToString(cellData.getValue().getSentAmount())));
        columnAmountReceived.setCellValueFactory(cellData -> new SimpleStringProperty(Currencies.fromAmountToString(cellData.getValue().getReceivedAmount())));
        columnSender.setCellValueFactory(cellData  -> new SimpleStringProperty(cellData.getValue().getSender().getName()));
        columnBought.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof PaymentFromClient) {
                PaymentFromClient pfc = (PaymentFromClient) cellData.getValue();
                return new SimpleObjectProperty<>(PurchasedProducts.fromSetToStrings(pfc.getProducts()));
            }
            else {
                return null;
            }
        });

        table.getColumns().clear();
        table.getColumns().addAll();

    }

}
