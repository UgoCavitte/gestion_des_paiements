package com.gestion_paiements.controllers.accounts_tables;

import com.gestion_paiements.data.Preferences;
import com.gestion_paiements.types.payments.Payment;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.util.Currencies;
import com.gestion_paiements.util.Dates;
import com.gestion_paiements.util.PurchasedProducts;
import com.gestion_paiements.util.Refreshable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
/// - sender
/// - bought products

public class PlatformTableController implements Refreshable {

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
    private final TableColumn<Payment, List<String>> columnBought = new TableColumn<>("Produits");
    private final TableColumn<Payment, String> columnSentToBankID = new TableColumn<>("ID d'envoi sur le compte");

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void initialize() {
        System.out.println("Initializing");

        // A mutable ObservableList to hold the countries for the ListView
        ObservableList<Payment> paymentsList = FXCollections.observableArrayList(payments);

        columnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnDateSent.setCellValueFactory(cellData -> new SimpleStringProperty(Dates.fromDateToString(cellData.getValue().getDateSent())));
        columnDateReceived.setCellValueFactory(cellData -> new SimpleStringProperty(Dates.fromDateToString(cellData.getValue().getDateReceived())));
        columnAmountSent.setCellValueFactory(cellData -> new SimpleStringProperty(Currencies.fromAmountToString(cellData.getValue().getSentAmount())));
        columnAmountReceived.setCellValueFactory(cellData -> new SimpleStringProperty(Currencies.fromAmountToString(cellData.getValue().getReceivedAmount())));
        columnSender.setCellValueFactory(cellData  -> new SimpleStringProperty(cellData.getValue().getSender().getName()));
        columnBought.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof PaymentFromClient pfc) {
                return new SimpleObjectProperty<>(PurchasedProducts.fromSetToStrings(pfc.getProducts()));
            }
            else {
                return null;
            }
        });
        columnSentToBankID.setCellValueFactory(cellData -> new SimpleStringProperty("Pas implémenté")); // TODO Implement this

        setColumns();

        table.setItems(paymentsList);
    }

    private void setColumns () {
        table.getColumns().clear();
        if (Preferences.ColumnsToShow.PId) table.getColumns().add(columnID);
        if (Preferences.ColumnsToShow.PSender) table.getColumns().add(columnSender);
        if (Preferences.ColumnsToShow.PDateSent) table.getColumns().add(columnDateSent);
        if (Preferences.ColumnsToShow.PDateReceived) table.getColumns().add(columnDateReceived);
        if (Preferences.ColumnsToShow.PAmountSent) table.getColumns().add(columnAmountSent);
        if (Preferences.ColumnsToShow.PAmountReceived) table.getColumns().add(columnAmountReceived);
        if (Preferences.ColumnsToShow.PProducts) table.getColumns().add(columnBought);
        if (Preferences.ColumnsToShow.PSentToBank) table.getColumns().add(columnSentToBankID);
    }

    @Override
    public void refreshElement() {
        setColumns();
        table.refresh();
    }
}
