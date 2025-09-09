package com.gestion_paiements.gestion_des_paiements.types.payments;

import com.gestion_paiements.gestion_des_paiements.types.Destination;

public class PaymentFromPlatform extends Payment {

    // Sending platform
    private Destination sendingPlatform;

    //////////////////////////////
    /// GETTERS AND SETTERS
    //////////////////////////////

    public Destination getSendingPlatform() {
        return sendingPlatform;
    }

    public void setSendingPlatform(Destination sendingPlatform) {
        this.sendingPlatform = sendingPlatform;
    }
}
