package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.data.Preferences;
import com.gestion_paiements.data.RefreshableData;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class ColumnsToShowController {

    @FXML
    private CheckBox BAAmountReceived;

    @FXML
    private CheckBox BAAmountSent;

    @FXML
    private CheckBox BAComment;

    @FXML
    private CheckBox BACommission;

    @FXML
    private CheckBox BADateReceived;

    @FXML
    private CheckBox BADateSent;

    @FXML
    private CheckBox BAId;

    @FXML
    private CheckBox BAProducts;

    @FXML
    private CheckBox BASender;

    @FXML
    private CheckBox PAmountReceived;

    @FXML
    private CheckBox PAmountSent;

    @FXML
    private CheckBox PComment;

    @FXML
    private CheckBox PDateReceived;

    @FXML
    private CheckBox PDateSent;

    @FXML
    private CheckBox PIDEnvoiCompte;

    @FXML
    private CheckBox PId;

    @FXML
    private CheckBox PProducts;

    @FXML
    private CheckBox PSender;

    @FXML CheckBox PSentToBank;

    @FXML
    private void initialize () {

        BAAmountReceived.setSelected(Preferences.ColumnsToShow.BAAmountReceived);
        BAAmountSent.setSelected(Preferences.ColumnsToShow.BAAmountSent);
        BAComment.setSelected(Preferences.ColumnsToShow.BAComment);
        BACommission.setSelected(Preferences.ColumnsToShow.BACommission);
        BADateReceived.setSelected(Preferences.ColumnsToShow.BADateReceived);
        BADateSent.setSelected(Preferences.ColumnsToShow.BADateSent);
        BAId.setSelected(Preferences.ColumnsToShow.BAId);
        BAProducts.setSelected(Preferences.ColumnsToShow.BAProducts);
        BASender.setSelected(Preferences.ColumnsToShow.BASender);

        PAmountReceived.setSelected(Preferences.ColumnsToShow.PAmountReceived);
        PAmountSent.setSelected(Preferences.ColumnsToShow.PAmountSent);
        PComment.setSelected(Preferences.ColumnsToShow.PComment);
        PDateReceived.setSelected(Preferences.ColumnsToShow.PDateReceived);
        PDateSent.setSelected(Preferences.ColumnsToShow.PDateSent);
        PId.setSelected(Preferences.ColumnsToShow.PId);
        PIDEnvoiCompte.setSelected(Preferences.ColumnsToShow.PSentToBankID);
        PProducts.setSelected(Preferences.ColumnsToShow.PProducts);
        PSender.setSelected(Preferences.ColumnsToShow.PSender);
        PSentToBank.setSelected(Preferences.ColumnsToShow.PSentToBank);

    }

    @FXML
    void BAAmountReceived() {
        Preferences.ColumnsToShow.BAAmountReceived = BAAmountReceived.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void BAAmountSent() {
        Preferences.ColumnsToShow.BAAmountSent = BAAmountSent.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void BAComment () {
        Preferences.ColumnsToShow.BAComment = BAComment.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void BACommission () {
        Preferences.ColumnsToShow.BACommission = BACommission.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void BADateReceived() {
        Preferences.ColumnsToShow.BADateReceived = BADateReceived.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void BADateSent() {
        Preferences.ColumnsToShow.BADateSent = BADateSent.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void BAId() {
        Preferences.ColumnsToShow.BAId = BAId.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void BAProducts() {
        Preferences.ColumnsToShow.BAProducts = BAProducts.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void BASender() {
        Preferences.ColumnsToShow.BASender = BASender.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PAmountReceived() {
        Preferences.ColumnsToShow.PAmountReceived = PAmountReceived.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PAmountSent() {
        Preferences.ColumnsToShow.PAmountSent = PAmountSent.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PComment () {
        Preferences.ColumnsToShow.PComment = PComment.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PDateReceived() {
        Preferences.ColumnsToShow.PDateReceived = PDateReceived.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PDateSent() {
        Preferences.ColumnsToShow.PDateSent = PDateSent.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PId() {
        Preferences.ColumnsToShow.PId = PId.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PProducts() {
        Preferences.ColumnsToShow.PProducts = PProducts.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PSender() {
        Preferences.ColumnsToShow.PSender = PSender.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PIDEnvoiCompte() {
        Preferences.ColumnsToShow.PSentToBankID = PIDEnvoiCompte.isSelected();
        RefreshableData.refreshTables();
    }

    @FXML
    void PSentToBank() {
        Preferences.ColumnsToShow.PSentToBank = PSentToBank.isSelected();
        RefreshableData.refreshTables();
    }

}
