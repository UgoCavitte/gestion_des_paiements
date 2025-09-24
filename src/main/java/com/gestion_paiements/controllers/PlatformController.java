package com.gestion_paiements.controllers;

import com.gestion_paiements.Main;
import com.gestion_paiements.controllers.accounts_tables.BankAccountTableController;
import com.gestion_paiements.controllers.accounts_tables.PlatformTableController;
import com.gestion_paiements.data.RefreshableData;
import com.gestion_paiements.types.Destination;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.time.Instant;
import java.time.Month;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/// The tab controlled by this controller must contain these pieces of information and functionalities :
/// - a menu to show the month to show
/// - a table with entering transfers for each month with all the information about these transfers (maybe make this customizable so the user does not have to see what he does not want to see)
/// - a button to add an entering transfer ;
/// - a button to add a transfer to the bank account.

public class PlatformController {

    @FXML
    private ComboBox<Month> boxMonth;

    @FXML
    private ComboBox<Integer> boxYear;

    @FXML
    private AnchorPane paneTable;

    private PlatformTableController controller;

    private final HashMap<Integer, Set<Month>> monthsByYears = new HashMap<>();

    private Destination platform;

    public void setPlatform(Destination platform) {
        this.platform = platform;
    }

    // Used to set the columns
    public void initialize () {
        // On commence par les comboBoxes
        final Set<Integer> availableYears = platform.getTransfers().stream().map(p -> p.getDateReceived().getYear()).collect(Collectors.toSet());

        if (platform.getTransfers().isEmpty()) {
            int currentYear = Instant.now().atZone(ZoneId.systemDefault()).getYear();
            Month currentMonth = Instant.now().atZone(ZoneId.systemDefault()).getMonth();

            boxYear.setItems(FXCollections.observableArrayList(List.of(currentYear)));

            monthsByYears.put(currentYear, Set.of(currentMonth));
        }

        else {
            for (Integer year : availableYears) {
                final Set<Month> monthsForThisYear = platform.getTransfers()
                        .stream().filter(p -> p.getDateReceived().getYear() == year)
                        .map(p -> p.getDateReceived().getMonth())
                        .collect(Collectors.toSet());

                if (year == Instant.now().atZone(ZoneId.systemDefault()).getYear())
                    monthsForThisYear.add(Instant.now().atZone(ZoneId.systemDefault()).getMonth());

                monthsByYears.put(year, monthsForThisYear);
            }

            boxYear.setItems(FXCollections.observableList(monthsByYears.keySet().stream().toList()));
        }

        // Add the table
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("accounts_tables/empty-table.fxml"));
            paneTable.getChildren().clear();
            paneTable.getChildren().add(loader.load());
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    void addNewTransfer() {

    }

    @FXML
    void transferToBankAccount () {

    }

    @FXML
    void selectionChanged() {
        boxMonth.setItems(FXCollections.observableList(monthsByYears.get(boxYear.getValue()).stream().toList()));

        if (boxMonth.getValue() != null) {
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("accounts_tables/table-platform.fxml"));

                RefreshableData.getToRefresh().remove(controller); // This is to prevent memory overload when user reloads tables too much
                controller = new PlatformTableController();
                RefreshableData.getToRefresh().add(controller); // Replacing the removed element instead of duplicating it
                controller.setPayments(
                        platform.getTransfers().stream()
                                .filter(p -> p.getDateReceived().getYear() == boxYear.getValue()
                                        && p.getDateReceived().getMonth() == boxMonth.getValue())
                                .collect(Collectors.toSet()));

                loader.setController(controller);

                paneTable.getChildren().clear();
                paneTable.getChildren().add(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
