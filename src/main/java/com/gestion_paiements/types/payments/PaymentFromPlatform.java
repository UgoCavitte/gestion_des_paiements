package com.gestion_paiements.types.payments;

import com.gestion_paiements.saving_formats.ToBindPayment;
import com.gestion_paiements.types.*;
import com.gestion_paiements.util.IDs;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.math.BigDecimal;
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

    // For binding
    public PaymentFromPlatform (ToBindPayment toBind) {
        super(toBind);
    }

    // Minus commission
    @Override
    public @NotNull Amount getReceivedAmount () {
        BigDecimal total = BigDecimal.valueOf(this.getSentAmount().getAmount());
        BigDecimal comm = BigDecimal.valueOf(commission.getAmount());

        BigDecimal result = total.subtract(comm);

        return new Amount(
                result.doubleValue(),
                super.getDestination().getCurrency()
        );
    }

    // Gross amount
    @Override
    public @NotNull Amount getSentAmount () {
        BigDecimal total = sentPayments.stream()
                            .map(a -> BigDecimal.valueOf(a.getReceivedAmount().getAmount()))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new Amount(
                total.doubleValue(),
                super.getDestination().getCurrency()
        );
    }

    @Override
    public int compareTo(@NotNull Payment other) {
        return this.getDateReceived().compareTo(other.getDateReceived());
    }
}
