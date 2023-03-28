package com.ferbo.facturama.response;

import java.util.List;

import com.ferbo.facturama.request.Donat;
import com.google.gson.annotations.SerializedName;

public class Complements {
    
    @SerializedName(value = "Payments")
    private List<Payment> payments = null;
    
    @SerializedName(value = "ForeignTrade")
    private ForeingTrade foreignTrade = null;
    
    @SerializedName(value = "Donation")
    private Donat donation = null;
    
    @SerializedName(value = "Ine")
    private Ine ine = null;

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public ForeingTrade getForeignTrade() {
        return foreignTrade;
    }

    public void setForeignTrade(ForeingTrade foreignTrade) {
        this.foreignTrade = foreignTrade;
    }

    public Donat getDonation() {
        return donation;
    }

    public void setDonation(Donat donation) {
        this.donation = donation;
    }

    public Ine getIne() {
        return ine;
    }

    public void setIne(Ine ine) {
        this.ine = ine;
    }

    @Override
    public String toString() {
        return "{\"payments\":\"" + payments + "\", \"foreignTrade\":\"" + foreignTrade + "\", \"donation\":\""
                + donation + "\", \"ine\":\"" + ine + "\"}";
    }
}
