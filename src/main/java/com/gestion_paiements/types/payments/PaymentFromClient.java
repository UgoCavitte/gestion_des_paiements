package com.gestion_paiements.types.payments;

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
            StatusPaymentFromClient status,
            @Nullable String comment) {
        super.setSender(sender);
        this.purchasedProducts = purchasedProducts;
        super.setDestination(destination);
        super.setDateSent(dateSent);
        super.setDateReceived(dateReceived);
        super.setSentAmount(sentAmount);
        super.setReceivedAmount(receivedAmount);
        super.setComment(comment);
        this.status = status;

        super.setId(IDs.getAvailableID(Data.instance.getSetPayments()));
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
