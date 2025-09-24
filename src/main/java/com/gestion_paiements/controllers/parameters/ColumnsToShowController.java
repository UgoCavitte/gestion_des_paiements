package com.gestion_paiements.controllers.parameters;

import com.gestion_paiements.data.Preferences;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class ColumnsToShowController {

    @FXML
    private CheckBox BAAmountReceived;

    @FXML
    private CheckBox BAAmountSent;

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

    public void initialize() {
        BAAmountReceived.setSelected(Preferences.ColumnsToShow.BAAmountReceived);
        BAAmountSent.setSelected(Preferences.ColumnsToShow.BAAmountSent);
        BADateReceived.setSelected(Preferences.ColumnsToShow.BADateReceived);
        BADateSent.setSelected(Preferences.ColumnsToShow.BADateSent);
        BAId.setSelected(Preferences.ColumnsToShow.BAId);
        BAProducts.setSelected(Preferences.ColumnsToShow.BAProducts);
        BASender.setSelected(Preferences.ColumnsToShow.BASender);
    }

    @FXML
    void BAAmountReceived() {
        Preferences.ColumnsToShow.BAAmountReceived = BAAmountReceived.isSelected();
    }

    @FXML
    void BAAmountSent() {
        Preferences.ColumnsToShow.BAAmountSent = BAAmountSent.isSelected();
    }

    @FXML
    void BADateReceived() {
        Preferences.ColumnsToShow.BADateReceived = BADateReceived.isSelected();
    }

    @FXML
    void BADateSent() {
        Preferences.ColumnsToShow.BADateSent = BADateSent.isSelected();
    }

    @FXML
    void BAId() {
        Preferences.ColumnsToShow.BAId = BAId.isSelected();
    }

    @FXML
    void BAProducts() {
        Preferences.ColumnsToShow.BAProducts = BAProducts.isSelected();
    }

    @FXML
    void BASender() {
        Preferences.ColumnsToShow.BASender = BASender.isSelected();
    }

}
