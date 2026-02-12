package com.gestion_paiements.controllers.accounts_tables;

import com.gestion_paiements.data.Preferences;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.Product;
import com.gestion_paiements.types.PurchasedProduct;
import com.gestion_paiements.types.payments.Payment;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.PaymentFromPlatform;
import com.gestion_paiements.util.Currencies;
import com.gestion_paiements.util.Dates;
import com.gestion_paiements.util.PurchasedProducts;
import com.gestion_paiements.util.Refreshable;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.Month;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/// This table must show this information, that the user can enable or disable through parameters :
/// - id
/// - date sent
/// - date received
/// - amount sent
/// - amount received
/// - sender (client or platform)
/// - bought products (if client)

public class BankAccountTableController implements Refreshable {

    Function<Payment, Void> callBackSelection;

    public void setCallBackSelection(Function<Payment, Void> callBackSelection) {
        this.callBackSelection = callBackSelection;
    }

    private Destination destination;
    private int year;
    private Month month;

    private FilteredList<Payment> filteredPayments;

    public FilteredList<Payment> getFilteredPayments() {
        return filteredPayments;
    }

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
    private TableView<Payment> table;

    // Columns
    private final TableColumn<Payment, Integer> columnID = new TableColumn<>("ID");
    private final TableColumn<Payment, String> columnDateSent = new TableColumn<>("Date d'envoi");
    private final TableColumn<Payment, String> columnDateReceived = new TableColumn<>("Date de réception");
    private final TableColumn<Payment, String> columnAmountSent = new TableColumn<>("Somme envoyée");
    private final TableColumn<Payment, String> columnAmountReceived = new TableColumn<>("Somme reçue");
    private final TableColumn<Payment, String> columnSender = new TableColumn<>("Envoyeur");
    private final TableColumn<Payment, String> columnBought = new TableColumn<>("Produits");
    private final TableColumn<Payment, String> columnCommission = new TableColumn<>("Commission");
    private final TableColumn<Payment, String> columnComment = new TableColumn<>("Commentaire");

    @FXML
    private void initialize () {

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
            // if received from a platform, we display a list of all the purchased products
            else {
                Map<Product, Double> map = ((PaymentFromPlatform) cellData.getValue()).getSentPayments().stream()
                        .flatMap(payment -> payment.getProducts().stream())
                        .collect(Collectors.toMap(
                                PurchasedProduct::getProduct,
                                PurchasedProduct::getQuantity,
                                Double::sum
                        ));

                return new SimpleStringProperty(
                        PurchasedProducts.fromListToString(
                                map.entrySet().stream()
                                        .map((entry) -> new PurchasedProduct(entry.getValue(), entry.getKey()))
                                        .toList()));
            }
        });
        columnCommission.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClass() == PaymentFromPlatform.class ? Currencies.fromAmountToString(((PaymentFromPlatform) cellData.getValue()).getCommission()) : ""));
        columnComment.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getComment()));

        setColumns();
        setItems();
        setListener();

        // Default sorting
        columnDateReceived.setSortType(TableColumn.SortType.DESCENDING);
        table.getSortOrder().add(columnDateReceived);

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
        if (Preferences.ColumnsToShow.BACommission) table.getColumns().add(columnCommission);
        if (Preferences.ColumnsToShow.BAComment) table.getColumns().add(columnComment);
    }

    private void setItems() {
        filteredPayments = new FilteredList<>(Data.instance.getListPayments());
        updateFilter();
        SortedList<Payment> sortedData = new SortedList<>(filteredPayments);
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        table.setItems(sortedData);

    }

    private void setListener () {
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldS, newS) -> callBackSelection.apply(newS));
    }

    private void updateFilter() {
        filteredPayments.setPredicate(p -> {
            boolean matchesDestination = p.getDestination().equals(this.destination);
            boolean matchesDate = p.getDateReceived().getYear() == this.year &&
                    p.getDateReceived().getMonth() == this.month;
            return matchesDestination && matchesDate;
        });
    }

    @Override
    public void refreshElement() {
        setColumns(); // TODO Check this
        table.refresh();
    }
}
