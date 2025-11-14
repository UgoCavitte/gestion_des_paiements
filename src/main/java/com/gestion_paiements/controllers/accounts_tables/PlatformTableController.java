package com.gestion_paiements.controllers.accounts_tables;

import com.gestion_paiements.data.Preferences;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.PaymentFromPlatform;
import com.gestion_paiements.types.payments.StatusPaymentFromClient;
import com.gestion_paiements.util.Currencies;
import com.gestion_paiements.util.Dates;
import com.gestion_paiements.util.PurchasedProducts;
import com.gestion_paiements.util.Refreshable;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.Month;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/// This table must show this information, that the user can enable or disable through parameters :
/// - payment sent to bank or not
/// - id
/// - date sent
/// - date received
/// - amount sent
/// - amount received
/// - sender
/// - bought products

public class PlatformTableController implements Refreshable {

    Function<PaymentFromClient, Void> callBackSelection;

    public void setCallBackSelection(Function<PaymentFromClient, Void> callBackSelection) {
        this.callBackSelection = callBackSelection;
    }

    private Destination destination;
    private int year;
    private Month month;

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(Month month) {
        this.month = month;
    }

    @FXML
    private TableView<PaymentFromClient> table;

    // Columns
    private final TableColumn<PaymentFromClient, String> columnSentToBank = new TableColumn<>("Envoyé ?");
    private final TableColumn<PaymentFromClient, Integer> columnID = new TableColumn<>("ID");
    private final TableColumn<PaymentFromClient, String> columnDateSent = new TableColumn<>("Date d'envoi");
    private final TableColumn<PaymentFromClient, String> columnDateReceived = new TableColumn<>("Date de réception");
    private final TableColumn<PaymentFromClient, String> columnAmountSent = new TableColumn<>("Somme envoyée");
    private final TableColumn<PaymentFromClient, String> columnAmountReceived = new TableColumn<>("Somme reçue");
    private final TableColumn<PaymentFromClient, String> columnSender = new TableColumn<>("Envoyeur");
    private final TableColumn<PaymentFromClient, String> columnBought = new TableColumn<>("Produits");
    private final TableColumn<PaymentFromClient, String> columnComment = new TableColumn<>("Commentaire");
    private final TableColumn<PaymentFromClient, String> columnSentToBankID = new TableColumn<>("ID d'envoi sur le compte");

    @FXML
    private void initialize() {

        columnSentToBank.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus() == StatusPaymentFromClient.SENT_TO_THE_BANK ? "V" : ""));
        columnID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        columnDateSent.setCellValueFactory(cellData -> new SimpleStringProperty(Dates.fromDateToString(cellData.getValue().getDateSent())));
        columnDateReceived.setCellValueFactory(cellData -> new SimpleStringProperty(Dates.fromDateToString(cellData.getValue().getDateReceived())));
        columnAmountSent.setCellValueFactory(cellData -> new SimpleStringProperty(Currencies.fromAmountToString(cellData.getValue().getSentAmount())));
        columnAmountReceived.setCellValueFactory(cellData -> new SimpleStringProperty(Currencies.fromAmountToString(cellData.getValue().getReceivedAmount())));
        columnSender.setCellValueFactory(cellData  -> new SimpleStringProperty(cellData.getValue().getSender().getName()));
        columnBought.setCellValueFactory(cellData -> {
            if (cellData.getValue() instanceof PaymentFromClient pfc) {
                return new SimpleStringProperty(PurchasedProducts.fromListToString(pfc.getProducts()));
            }
            else {
                return new SimpleStringProperty("");
            }
        });
        columnComment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));
        columnSentToBankID.setCellValueFactory(cellData -> {
            Set<PaymentFromPlatform> payments = Data.instance.getSetPayments().stream().filter(p -> p.getClass() == PaymentFromPlatform.class).map(p -> (PaymentFromPlatform) p).collect(Collectors.toSet());

            for (PaymentFromPlatform p : payments) {
                if (p.getSentPayments().contains(cellData.getValue())) {
                    return new SimpleStringProperty(String.valueOf(p.getId()));
                }
            }

            return new SimpleStringProperty("Error");

        });

        setColumns();
        setItems();
        setListener();
    }

    private void setColumns () {
        table.getColumns().clear();
        if (Preferences.ColumnsToShow.PSentToBank) table.getColumns().add(columnSentToBank);
        if (Preferences.ColumnsToShow.PId) table.getColumns().add(columnID);
        if (Preferences.ColumnsToShow.PSender) table.getColumns().add(columnSender);
        if (Preferences.ColumnsToShow.PDateSent) table.getColumns().add(columnDateSent);
        if (Preferences.ColumnsToShow.PDateReceived) table.getColumns().add(columnDateReceived);
        if (Preferences.ColumnsToShow.PAmountSent) table.getColumns().add(columnAmountSent);
        if (Preferences.ColumnsToShow.PAmountReceived) table.getColumns().add(columnAmountReceived);
        if (Preferences.ColumnsToShow.PProducts) table.getColumns().add(columnBought);
        if (Preferences.ColumnsToShow.PComment) table.getColumns().add(columnComment);
        if (Preferences.ColumnsToShow.PSentToBankID) table.getColumns().add(columnSentToBankID);
    }

    private void setItems () {
        table.getItems().clear();
        Set<PaymentFromClient> payments = destination
                .getTransfers().stream()
                .filter(p -> p.getDateReceived().getYear() == year && p.getDateReceived().getMonth() == month)
                .map(p -> (PaymentFromClient) p)
                .collect(Collectors.toSet());
        table.setItems(FXCollections.observableArrayList(payments));
    }

    private void setListener () {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldS, newS) -> callBackSelection.apply(newS));
    }

    @Override
    public void refreshElement() {
        setItems();
        setColumns();
        table.refresh();
    }
}
