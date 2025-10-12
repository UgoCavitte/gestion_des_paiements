package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.controllers.accounts_tables.BankAccountTableController;
import com.gestion_paiements.data.RefreshableData;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.WorkingCountry;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

/// The tab controlled by this controller must contain these pieces of information and functionalities :
/// - a menu to show the month to show
/// - a table with entering transfers for each month with all the information about these transfers (maybe make this customizable so the user does not have to see what he does not want to see)
/// - a button to add an entering transfer.

// TODO Make the columns customizable in the parameters menu

public class BankAccountController {

    @FXML
    private ComboBox<Month> boxMonth;

    @FXML
    private ComboBox<Integer> boxYear;

    @FXML
    private AnchorPane paneTable;

    private BankAccountTableController controller;

    private final HashMap<Integer, Set<Month>> monthsByYears = new HashMap<>();

    private Destination account;

    public void setAccount(Destination account) {
        this.account = account;
    }

    private WorkingCountry country;

    public void setCountry(WorkingCountry country) {
        this.country = country;
    }

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
            controller.setCountry(country);
            controller.setDestination(account);
            controller.setYear(boxYear.getValue());
            controller.setMonth(boxMonth.getValue());

            loader.setController(controller);

            paneTable.getChildren().clear();
            paneTable.getChildren().add(loader.load());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void addNewTransfer() {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("additional_windows/add-new-transfer.fxml"));
            Parent parent = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Paramètres");
            stage.initModality(Modality.WINDOW_MODAL);

            Scene scene = new Scene(parent);
            stage.setScene(scene);

            stage.setResizable(false);

            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
                controller.setCountry(country);
                controller.setDestination(account);
                controller.setYear(boxYear.getValue());
                controller.setMonth(boxMonth.getValue());

                loader.setController(controller);

                paneTable.getChildren().clear();
                paneTable.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
