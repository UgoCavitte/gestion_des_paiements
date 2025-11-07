package com.gestion_paiements.types.payments;

import com.gestion_paiements.types.*;
import com.gestion_paiements.util.IDs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDate;
import java.util.HashSet;

public final class PaymentFromPlatform extends Payment {

    HashSet<PaymentFromClient> sentPayments;

    Amount commission;

    public HashSet<PaymentFromClient> getSentPayments() {
        return sentPayments;
    }

    public void setSentPayments(HashSet<PaymentFromClient> sentPayments) {
        this.sentPayments = sentPayments;
    }

    public Amount getCommission() {
        return commission;
    }

    public void setCommission(Amount commission) {
        this.commission = commission;
    }

    public PaymentFromPlatform (
            HashSet<PaymentFromClient> payments,
            Destination sender,
            Destination destination,
            LocalDate dateSent,
            LocalDate dateReceived,
            Amount commission,
            @Nullable String comment
    ) {
        this.sentPayments = payments;
        super.setSender(sender);
        super.setDestination(destination);
        super.setDateSent(dateSent);
        super.setDateReceived(dateReceived);
        this.commission = commission;
        super.setComment(comment);

        super.setId(IDs.getAvailableID(Data.instance.getSetPayments()));
    }

    // Minus commission
    @Override
    public Amount getReceivedAmount () {
        return new Amount(
                this.getSentAmount().getAmount() - commission.getAmount(),
                super.getDestination().getCurrency()
        );
    }

    // Gross amount
    @Override
    public Amount getSentAmount () {
        double total = sentPayments.stream()
                            .mapToDouble(a -> a.getReceivedAmount().getAmount())
                            .sum();

        return new Amount(
                total,
                super.getDestination().getCurrency()
        );
    }

    @Override
    public int compareTo(@NotNull Payment other) {
        return this.getDateReceived().compareTo(other.getDateReceived());
    }
}
