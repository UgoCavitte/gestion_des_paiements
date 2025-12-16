package com.gestion_paiements.saving_formats;

import com.gestion_paiements.data.Memory;
import com.gestion_paiements.types.payments.Payment;
import com.gestion_paiements.types.payments.PaymentFromClient;
import com.gestion_paiements.types.payments.PaymentFromPlatform;

import java.util.Set;
import java.util.stream.Collectors;

/// Unbound class for [Payment] elements

public class ToBindPayment {

    private int id;

    private String dateSent;

    private String dateReceived;

    private double amountSent;

    private int currencySent;

    private double amountReceived;

    private int currencyReceived;

    private int destination;

    private int senderType;

    private int senderID; // ID is usable only after checking sender type (platform, client)

    private String comment;

    // More complex parameters for PaymentFromPlatform
    private double commissionAmount;

    private int commissionCurrency;

    private Set<Integer> sentPayments;

    // More complex parameters for PaymentFromClient
    private Set<Integer> purchasedProducts;

    private int statusPaymentFromClient;

    // For deserialization
    public ToBindPayment() {
    }

    // For me
    public ToBindPayment(Payment payment) {
        this.id = payment.getId();
        this.dateSent = payment.getDateSent().toString();
        this.dateReceived = payment.getDateReceived().toString();
        this.amountSent = payment.getSentAmount().getAmount();
        this.currencySent = payment.getSentAmount().getCurrency().getId();
        this.amountReceived = payment.getReceivedAmount().getAmount();
        this.currencyReceived = payment.getReceivedAmount().getCurrency().getId();
        this.destination = payment.getDestination().getId();
        this.senderType = Memory.mapFromSenderTypeToInteger.get(payment.getSender().getClass());
        this.senderID = payment.getSender().getId();
        this.comment = payment.getComment();

        if (payment instanceof PaymentFromPlatform) {
            this.commissionAmount = ((PaymentFromPlatform) payment).getCommission().getAmount();
            this.commissionCurrency = ((PaymentFromPlatform) payment).getCommission().getCurrency().getId();
            this.sentPayments = ((PaymentFromPlatform) payment).getSentPayments().stream().map(Payment::getId).collect(Collectors.toSet());
        }

        else if (payment instanceof PaymentFromClient) {
            //
        }

        else {
            throw new RuntimeException("Non-supported subtype of Payment");
        }

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateSent() {
        return dateSent;
    }

    public void setDateSent(String dateSent) {
        this.dateSent = dateSent;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public double getAmountSent() {
        return amountSent;
    }

    public void setAmountSent(double amountSent) {
        this.amountSent = amountSent;
    }

    public int getCurrencySent() {
        return currencySent;
    }

    public void setCurrencySent(int currencySent) {
        this.currencySent = currencySent;
    }

    public double getAmountReceived() {
        return amountReceived;
    }

    public void setAmountReceived(double amountReceived) {
        this.amountReceived = amountReceived;
    }

    public int getCurrencyReceived() {
        return currencyReceived;
    }

    public void setCurrencyReceived(int currencyReceived) {
        this.currencyReceived = currencyReceived;
    }

    public int getDestination() {
        return destination;
    }

    public void setDestination(int destination) {
        this.destination = destination;
    }

    public int getSenderType() {
        return senderType;
    }

    public void setSenderType(int senderType) {
        this.senderType = senderType;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getCommissionAmount() {
        return commissionAmount;
    }

    public void setCommissionAmount(double commissionAmount) {
        this.commissionAmount = commissionAmount;
    }

    public int getCommissionCurrency() {
        return commissionCurrency;
    }

    public void setCommissionCurrency(int commissionCurrency) {
        this.commissionCurrency = commissionCurrency;
    }

    public Set<Integer> getSentPayments() {
        return sentPayments;
    }

    public void setSentPayments(Set<Integer> sentPayments) {
        this.sentPayments = sentPayments;
    }

    public Set<Integer> getPurchasedProducts() {
        return purchasedProducts;
    }

    public void setPurchasedProducts(Set<Integer> purchasedProducts) {
        this.purchasedProducts = purchasedProducts;
    }

    public int getStatusPaymentFromClient() {
        return statusPaymentFromClient;
    }

    public void setStatusPaymentFromClient(int statusPaymentFromClient) {
        this.statusPaymentFromClient = statusPaymentFromClient;
    }
}
