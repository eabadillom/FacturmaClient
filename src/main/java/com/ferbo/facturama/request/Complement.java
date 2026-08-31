package com.ferbo.facturama.request;

import com.google.gson.annotations.SerializedName;
import java.util.List;

/**Complemento del CFDI<br>
 * https://www.api.facturama.com.mx/docs/ResourceModel?modelName=Complement<br>
 * @author esteban
 *
 */
public class Complement {
    /**Complemento de Pago<br>
     */
    @SerializedName(value = "Payments")
    private List<PaymentBindingModel> Payments = null;
    
    /**Complemento Detallista<br>
     * 
     */
    @SerializedName(value = "Donation")
    private Donat Donation = null;
    
    /**Complemento de Comercio Exterior<br>
     */
    @SerializedName(value = "ForeignTrade")
    private ForeignTrade ForeignTrade = null;
    
    /**Complemento de Nómina
     */
    @SerializedName(value = "Payroll")
    private Payroll Payroll = null;
    
    /**Complemento de Leyendas Fiscales<br>
     */
    @SerializedName(value = "TaxLegends")
    private TaxLegends TaxLegends = null;
    
    /**Complemento de carta porte 2.0
     * 
     */
    @SerializedName(value = "CartaPorte20")
    private ComplementoCartaPorte20 CartaPorte20 = null;

    public List<PaymentBindingModel> getPayments() {
        return Payments;
    }

    public void setPayments(List<PaymentBindingModel> Payments) {
        this.Payments = Payments;
    }

    public Donat getDonation() {
        return Donation;
    }

    public void setDonation(Donat Donation) {
        this.Donation = Donation;
    }

    public ForeignTrade getForeignTrade() {
        return ForeignTrade;
    }

    public void setForeignTrade(ForeignTrade ForeignTrade) {
        this.ForeignTrade = ForeignTrade;
    }

    public Payroll getPayroll() {
        return Payroll;
    }

    public void setPayroll(Payroll Payroll) {
        this.Payroll = Payroll;
    }

    public TaxLegends getTaxLegends() {
        return TaxLegends;
    }

    public void setTaxLegends(TaxLegends TaxLegends) {
        this.TaxLegends = TaxLegends;
    }

    public ComplementoCartaPorte20 getCartaPorte20() {
        return CartaPorte20;
    }

    public void setCartaPorte20(ComplementoCartaPorte20 CartaPorte20) {
        this.CartaPorte20 = CartaPorte20;
    }
    
}
