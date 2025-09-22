package com.gestion_paiements.types.payments;

import com.gestion_paiements.types.Amount;
import com.gestion_paiements.types.Client;
import com.gestion_paiements.types.Destination;
import com.gestion_paiements.types.PurchasedProduct;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.Set;

public final class PaymentFromClient extends Payment {

    // Sending client
    private Client sender;

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
        this.sender = sender;
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

    public Client getSender() {
        return sender;
    }

    public void setSender(Client sender) {
        this.sender = sender;
    }

    public Set<PurchasedProduct> getProducts() {
        return purchasedProducts;
    }

    public void setProducts(Set<PurchasedProduct> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

}
