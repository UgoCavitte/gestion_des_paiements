package com.gestion_paiements.controllers.accounts_tables;

import com.gestion_paiements.types.*;
import com.gestion_paiements.types.payments.Payment;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
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

    // Columns
    private TableColumn<Payment, Integer> columnID;
    private TableColumn<Payment, LocalDate> columnDateSent;
    private TableColumn<Payment, LocalDate> columnDateReceived;
    private TableColumn<Payment, Amount> columnAmountSent;
    private TableColumn<Payment, Amount> columnAmountReceived;
    private TableColumn<Payment, Sender> columnSender;
    private TableColumn<Payment, @Nullable Set<PurchasedProduct>> columnBought;

    public void setPayments(Set<Payment> payments) {
        this.payments = payments;
    }

    public void initialize () {

        System.out.println(payments);

        // TODO initialize table

    }

}
