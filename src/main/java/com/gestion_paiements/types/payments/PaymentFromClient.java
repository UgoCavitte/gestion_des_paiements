package com.gestion_paiements.types.payments;

import com.gestion_paiements.types.Amount;
import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.PurchasedProduct;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Set;

public final class PaymentFromClient extends Payment {

    // Product(s) (what did the client paid for?)
    private Set<PurchasedProduct> purchasedProducts;

    //////////////////////////////
    /// CONSTRUCTOR
    //////////////////////////////

    public PaymentFromClient(
            int id,
            Client sender,
            Destination destination,
            LocalDate dateSent,
            LocalDate dateReceived,
            Amount sentAmount,
            Amount receivedAmount,
            Set<PurchasedProduct> purchasedProducts,
            @Nullable String comment) {
        super.setSender(sender);
        this.purchasedProducts = purchasedProducts;
        super.setId(id); // TODO Maybe setting the ID automatically would be better?
        super.setDestination(destination);
        super.setDateSent(dateSent);
        super.setDateReceived(dateReceived);
        super.setSentAmount(sentAmount);
        super.setReceivedAmount(receivedAmount);
        super.setComment(comment);
    }

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public Set<PurchasedProduct> getProducts() {
        return purchasedProducts;
    }

    public void setProducts(Set<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    @Override
    public int compareTo(@NotNull Payment other) {
        return this.getDateReceived().compareTo(other.getDateReceived());
    }
}
