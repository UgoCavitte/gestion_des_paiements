package com.gestion_paiements.gestion_des_paiements.types.payments;

import com.gestion_paiements.gestion_des_paiements.types.Amount;
import com.gestion_paiements.gestion_des_paiements.types.Destination;
import org.jetbrains.annotations.Nullable;

import java.util.Date;

public abstract class Payment {

    // Used to store data
    private int id;

    private Date dateSent;

    private Date dateReceived;

    // Amount sent by the client or the platform
    private Amount sentAmount;

    // Amount received on the account or on the platform
    // The currency can be different from the sent one
    private Amount receivedAmount;

    // Destination (account, platform)
    private Destination destination;

    // Any other information if needed
    private @Nullable String comment;

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateSent() {
        return dateSent;
    }

    public void setDateSent(Date dateSent) {
        this.dateSent = dateSent;
    }

    public Date getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(Date dateReceived) {
        this.dateReceived = dateReceived;
    }

    public Amount getSentAmount() {
        return sentAmount;
    }

    public void setSentAmount(Amount sentAmount) {
        this.sentAmount = sentAmount;
    }

    public Amount getReceivedAmount() {
        return receivedAmount;
    }

    public void setReceivedAmount(Amount receivedAmount) {
        this.receivedAmount = receivedAmount;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public @Nullable String getComment() {
        return comment;
    }

    public void setComment(@Nullable String comment) {
        this.comment = comment;
    }
}
