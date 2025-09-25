package com.gestion_paiements.controllers.accounts_tables;

import com.gestion_paiements.data.Preferences;
import com.gestion_paiements.types.payments.Payment;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.util.Currencies;
import com.gestion_paiements.util.Dates;
import com.gestion_paiements.util.PurchasedProducts;
import com.gestion_paiements.util.Refreshable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

public class BankAccountTableController implements Refreshable {

    private Set<Payment> payments;

    @FXML
    private TableView<Payment> table;

    // Columns
    private final TableColumn<Payment, Integer> columnID = new TableColumn<>("ID");
    private final TableColumn<Payment, String> columnDateSent = new TableColumn<>("Date d'envoi");
    private final TableColumn<Payment, String> columnDateReceived = new TableColumn<>("Date de réception");
    private final TableColumn<Payment, String> columnAmountSent = new TableColumn<>("Somme envoyée");
    private final TableColumn<Payment, String> columnAmountReceived = new TableColumn<>("Somme reçue");
    private final TableColumn<Payment, String> columnSender = new TableColumn<>("Envoyeur");
    private final TableColumn<Payment, String> columnBought = new TableColumn<>("Produits");
    private final TableColumn<Payment, String> columnComment = new TableColumn<>("Commentaire");

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    @FXML
    private void initialize () {

        ObservableList<Payment> paymentsList = FXCollections.observableArrayList(payments);

        columnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnDateSent.setCellValueFactory(cellData -> new SimpleStringProperty(Dates.fromDateToString(cellData.getValue().getDateSent())));
        columnDateReceived.setCellValueFactory(cellData -> new SimpleStringProperty(Dates.fromDateToString(cellData.getValue().getDateReceived())));
        columnAmountSent.setCellValueFactory(cellData -> new SimpleStringProperty(Currencies.fromAmountToString(cellData.getValue().getSentAmount())));
        columnAmountReceived.setCellValueFactory(cellData -> new SimpleStringProperty(Currencies.fromAmountToString(cellData.getValue().getReceivedAmount())));
        columnSender.setCellValueFactory(cellData  -> new SimpleStringProperty(cellData.getValue().getSender().getName()));
        columnBought.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof PaymentFromClient pfc) {
                return new SimpleStringProperty(PurchasedProducts.fromSetToString(pfc.getProducts()));
            }
            else {
                return new SimpleStringProperty("");
            }
        });
        columnComment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));

        setColumns();

        table.setItems(paymentsList);

    }

    private void setColumns() {
        table.getColumns().clear();
        if (Preferences.ColumnsToShow.BAId) table.getColumns().add(columnID);
        if (Preferences.ColumnsToShow.BASender) table.getColumns().add(columnSender);
        if (Preferences.ColumnsToShow.BADateSent) table.getColumns().add(columnDateSent);
        if (Preferences.ColumnsToShow.BADateReceived) table.getColumns().add(columnDateReceived);
        if (Preferences.ColumnsToShow.BAAmountSent) table.getColumns().add(columnAmountSent);
        if (Preferences.ColumnsToShow.BAAmountReceived) table.getColumns().add(columnAmountReceived);
        if (Preferences.ColumnsToShow.BAProducts) table.getColumns().add(columnBought);
        if (Preferences.ColumnsToShow.BAComment) table.getColumns().add(columnComment);
    }

    @Override
    public void refreshElement() {
        setColumns();
        table.refresh();
    }
}
