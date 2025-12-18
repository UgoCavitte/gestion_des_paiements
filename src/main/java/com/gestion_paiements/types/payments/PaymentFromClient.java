package com.gestion_paiements.types.payments;

import com.gestion_paiements.saving_formats.ToBindPayment;
import com.gestion_paiements.types.*;
import com.gestion_paiements.util.IDs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.List;

public final class PaymentFromClient extends Payment {

    // Product(s) (what did the client paid for?)
    private List<PurchasedProduct> purchasedProducts;

    private StatusPaymentFromClient status;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public PaymentFromClient(
            Client sender,
            Destination destination,
            LocalDate dateSent,
            LocalDate dateReceived,
            Amount sentAmount,
            Amount receivedAmount,
            List<PurchasedProduct> purchasedProducts,
            @Nullable String comment) {
        super();
        super.setSender(sender);
        this.purchasedProducts = purchasedProducts;
        super.setDestination(destination);
        super.setDateSent(dateSent);
        super.setDateReceived(dateReceived);
        super.setSentAmount(sentAmount);
        super.setReceivedAmount(receivedAmount);
        super.setComment(comment);

        super.setId(IDs.getAvailableID(Data.instance.getSetPayments()));

        // Bank account or Platform?
        if (destination.getDestinationType() == DestinationType.bankAccount) this.status = StatusPaymentFromClient.NOT_ON_A_PLATFORM;
        else this.status = StatusPaymentFromClient.SENT_TO_A_PLATFORM;
    }

    // For binding
    public PaymentFromClient (ToBindPayment toBind) {
        super(toBind);
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public List<PurchasedProduct> getProducts() {
        return purchasedProducts;
    }

    public void setProducts(List<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public StatusPaymentFromClient getStatus() {
        return status;
    }

    public void setStatus(StatusPaymentFromClient status) {
        this.status = status;
    }

    @Override
    public Client getSender() {
        return (Client) super.getSender();
    }

    @Override
    public int compareTo(@NotNull Payment other) {
        return this.getDateReceived().compareTo(other.getDateReceived());
    }
}
