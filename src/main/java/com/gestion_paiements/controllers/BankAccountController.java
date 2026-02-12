package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.controllers.accounts_tables.BankAccountTableController;
import com.gestion_paiements.controllers.additional_windows.AddNewTransferController;
import com.gestion_paiements.controllers.additional_windows.ModifyPaymentController;
import com.gestion_paiements.controllers.additional_windows.ModifyPaymentFromPlatformController;
import com.gestion_paiements.data.RefreshableData;
import com.gestion_paiements.types.Data;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.payments.Payment;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.PaymentFromPlatform;
import com.gestion_paiements.types.payments.StatusPaymentFromClient;
import com.gestion_paiements.util.Refreshable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/// The tab controlled by this controller must contain these pieces of information and functionalities :
/// - a menu to show the month to show
/// - a table with entering transfers for each month with all the information about these transfers (maybe make this customizable so the user does not have to see what he does not want to see)
/// - a button to add an entering transfer.

public class BankAccountController implements Refreshable {

    @FXML
    private ComboBox<Month> boxMonth;

    @FXML
    private ComboBox<Integer> boxYear;

    @FXML
    private StackPane paneTable;

    @FXML
    private Label labelAverage;

    @FXML
    private Label labelCount;

    @FXML
    private Label labelTotal;

    private BankAccountTableController controller;

    private final HashMap<Integer, Set<Month>> monthsByYears = new HashMap<>();

    private Destination account;

    public void setAccount(Destination account) {
        this.account = account;
    }

    @FXML
    private Button buttonDelete;

    @FXML
    private Button buttonModify;

    private Payment selectedPayment;

    // Used to set the columns
    @FXML
    private void initialize () {
        ZonedDateTime now = Instant.now().atZone(ZoneId.systemDefault());

        // On commence par les comboBoxes
        final Set<Integer> availableYears = account.getTransfers().stream().map(p -> p.getDateReceived().getYear()).collect(Collectors.toSet());

        if (account.getTransfers().isEmpty()) {
            int currentYear = now.getYear();
            Month currentMonth = now.getMonth();

            boxYear.setItems(FXCollections.observableArrayList(List.of(currentYear)));

            monthsByYears.put(currentYear, Set.of(currentMonth));
        }

        else {
            for (Integer year : availableYears) {
                final Set<Month> monthsForThisYear = account.getTransfers()
                        .stream().filter(p -> p.getDateReceived().getYear() == year)
                        .map(p -> p.getDateReceived().getMonth())
                        .collect(Collectors.toSet());

                if (year == now.getYear())
                    monthsForThisYear.add(Instant.now().atZone(ZoneId.systemDefault()).getMonth());

                monthsByYears.put(year, monthsForThisYear);
            }

            // If there are payment from last year but not this year, it crashes without this
            int currentYear = now.getYear();
            if (!monthsByYears.containsKey(currentYear)) {
                monthsByYears.put(currentYear, new HashSet<>(Set.of(now.getMonth())));
            } else {
                // If the year exists, still make sure the current month is an option
                monthsByYears.get(currentYear).add(now.getMonth());
            }

            boxYear.setItems(FXCollections.observableList(monthsByYears.keySet().stream().toList()));
        }

        // Select the current year and month
        boxYear.setValue(now.getYear());
        boxMonth.setItems(FXCollections.observableList(monthsByYears.get(now.getYear()).stream().toList())); // Otherwise the box is empty
        boxMonth.setValue(now.getMonth());

        // Add the table
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("accounts_tables/table-bank-account.fxml"));

            RefreshableData.getToRefresh().remove(controller); // This is to prevent memory overload when user reloads tables too much
            controller = new BankAccountTableController();
            RefreshableData.getToRefresh().add(controller); // Replacing the removed element instead of duplicating it
            controller.setDestination(account);
            controller.setYear(boxYear.getValue());
            controller.setMonth(boxMonth.getValue());

            // Selection listener callback
            controller.setCallBackSelection(this::selectionCallBack);
            loader.setController(controller);

            paneTable.getChildren().clear();
            paneTable.getChildren().add(loader.load());

            // Binds the labels to the list
            FilteredList<Payment> list = controller.getFilteredPayments();
            list.addListener((ListChangeListener<Payment>) c -> {
                setLabels();
            });
            setLabels();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void addNewTransfer() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("additional_windows/add-new-transfer.fxml"));

            AddNewTransferController controller = new AddNewTransferController();
            controller.setDestination(account);
            loader.setController(controller);

            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Nouveau paiement");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(parent);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void setLabels() {
        List<Payment> list = Data.instance.getSetPayments().stream()
                .filter(p -> p.getDestination() == account)
                .filter(p -> p.getDateReceived().getYear() == boxYear.getValue() && p.getDateReceived().getMonth() == boxMonth.getValue())
                .toList();

        int count = list.size();
        BigDecimal total = list.stream().map(p -> BigDecimal.valueOf(p.getReceivedAmount().getAmount())).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal average = BigDecimal.valueOf(0);
        if (count != 0) average = total.divide(BigDecimal.valueOf(count), 2, RoundingMode.DOWN);

        labelCount.setText(String.valueOf(count));
        labelTotal.setText(String.valueOf(total));
        labelAverage.setText(String.format("%.2f", average));
    }

    @FXML
    void selectionChanged() {
        boxMonth.setItems(FXCollections.observableList(monthsByYears.get(boxYear.getValue()).stream().toList()));

        if (boxMonth.getValue() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("accounts_tables/table-bank-account.fxml"));

                RefreshableData.getToRefresh().remove(controller); // This is to prevent memory overload when user reloads tables too much
                controller = new BankAccountTableController();
                RefreshableData.getToRefresh().add(controller); // Replacing the removed element instead of duplicating it
                controller.setDestination(account);
                controller.setYear(boxYear.getValue());
                controller.setMonth(boxMonth.getValue());

                loader.setController(controller);

                // Selection listener callback
                controller.setCallBackSelection(this::selectionCallBack);
                loader.setController(controller);

                paneTable.getChildren().clear();
                paneTable.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            setLabels();

        }
    }

    private Void selectionCallBack(Payment p) {

        if (p == null) {
            buttonDelete.setDisable(true);
            buttonModify.setDisable(true);
            return null;
        }

        buttonModify.setDisable(false);
        buttonDelete.setDisable(false);

        selectedPayment = p;
        return null;
    }

    @FXML
    void modify () {

        if (selectedPayment.getClass() == PaymentFromClient.class) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("additional_windows/modify-payment.fxml"));

                ModifyPaymentController controller = new ModifyPaymentController();
                controller.setSelectedPayment((PaymentFromClient) selectedPayment);
                fxmlLoader.setController(controller);

                Parent parent = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setTitle("Modifier un paiement");
                stage.initModality(Modality.WINDOW_MODAL);

                Scene scene = new Scene(parent);
                stage.setScene(scene);

                stage.setResizable(false);

                stage.showAndWait();

            } catch (IOException e) {
                throw new RuntimeException();
            }
        }

        else {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("additional_windows/modify-payment-from-platform.fxml"));

                ModifyPaymentFromPlatformController controller = new ModifyPaymentFromPlatformController();
                controller.setPayment((PaymentFromPlatform) selectedPayment);
                fxmlLoader.setController(controller);

                Parent parent = fxmlLoader.load();

                Stage stage = new Stage();
                stage.setTitle("Modifier un paiement");
                stage.initModality(Modality.WINDOW_MODAL);

                Scene scene = new Scene(parent);
                stage.setScene(scene);

                stage.setResizable(false);

                stage.showAndWait();

            } catch (IOException e) {
                throw new RuntimeException();
            }
        }



    }

    // Works differently whether the payment was sent by a client or from a platform
    @FXML
    void delete () {

        // Sent by a client -> simple removal
        if (selectedPayment.getClass() == PaymentFromClient.class) {
            PaymentFromClient toRemove = (PaymentFromClient) selectedPayment;
            toRemove.getDestination().getTransfers().remove(toRemove);
            toRemove.getSender().getPayments().remove(toRemove);
            Data.instance.getSetPayments().remove(toRemove);
        }

        // Sent by a platform
        else {
            PaymentFromPlatform toRemove = (PaymentFromPlatform) selectedPayment;

            // Start by marking all the sent payments in the group as kept on the platform
            toRemove.getSentPayments().forEach(p -> p.setStatus(StatusPaymentFromClient.SENT_TO_A_PLATFORM));

            // Then remove from the bank account
            toRemove.getDestination().getTransfers().remove(toRemove);

            // And from the overall set
            Data.instance.getSetPayments().remove(toRemove);
        }

        RefreshableData.refreshTables();

        // TODO Write to memory
    }

    @Override
    public void refreshElement() {
        setLabels();
    }

    /// Lets user select and unselect payments for the given period and check sums, averages...
    @FXML
    void createSynthesis() {
        // TODO Implement this (and for Platforms too)
    }
}
