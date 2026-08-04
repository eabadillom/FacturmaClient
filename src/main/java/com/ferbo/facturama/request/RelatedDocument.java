package com.ferbo.facturama.request;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.annotations.SerializedName;

/**Documentos Relacionados al Pago<br>
 * <a href="https://www.api.facturama.com.mx/docs/ResourceModel?modelName=RelatedDocument">RelatedDocument</a><br>
 * @author esteban
 *
 */
public class RelatedDocument {
    
    /**Required<br>
     * Matching regular expression pattern: [a-f0-9A-F]{8}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{4}-[a-f0-9A-F]{12}<br>
     */
    @SerializedName(value = "Uuid")
    private String uuid = null;
    
    /**Serie<br>
     */
    @SerializedName(value = "Serie")
    private String serie = null;
    
    /**Folio<br>
     */
    @SerializedName(value = "Folio")
    private String folio = null;
    
    /**Currency<br>
     */
    @SerializedName(value = "Currency")
    private String currency = null;
    
    /**ExchangeRate<br>
     * Matching regular expression pattern: [0-9]{1,18}(.[0-9]{1,6})?<br>
     */
    @SerializedName(value = "ExchangeRate")
    private BigDecimal exchangeRate = null;
    
    /**PaymentMethod<br>
     * Required<br>
     * Matching regular expression pattern: PUE|PIP|PPD<br>
     */
    @SerializedName(value = "PaymentMethod")
    private String paymentMethod = null;
    
    /**PartialityNumber<br>
     * Matching regular expression pattern: [1-9][0-9]{0,2}<br>
     */
    @SerializedName(value = "PartialityNumber")
    private Integer partialityNumber = null;
    
    /**PreviousBalanceAmount<br>
     */
    @SerializedName(value = "PreviousBalanceAmount")
    private BigDecimal previousBalanceAmount = null;
    
    /**ImpSaldoInsoluto<br>
     */
    @SerializedName(value = "ImpSaldoInsoluto")
    private BigDecimal impSaldoInsoluto;
    
    /**TaxObject<br>
     */
    @SerializedName(value = "TaxObject")
    private String taxObject;
    
    /**AmountPaid<br>
     */
    @SerializedName(value = "AmountPaid")
    private BigDecimal amountPaid = null;
    
    /**Taxes
     * Si el atributo TaxObject tiene el valor 02, se debe incluir al menos un nodo Tax indicando el IVA.
     * Para más información, visita el sitio
     * <a href="https://apisandbox.facturama.mx/guias/cfdi40/complementos/complemento-pago-20">Facturama: Complemento de pago 2.0</a>
     * */
    @SerializedName(value = "Taxes")
    private List<Tax> taxes = null;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSerie() {
        return serie;
    }

    public void setSerie(String serie) {
        this.serie = serie;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(BigDecimal exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getPartialityNumber() {
        return partialityNumber;
    }

    public void setPartialityNumber(Integer partialityNumber) {
        this.partialityNumber = partialityNumber;
    }

    public BigDecimal getPreviousBalanceAmount() {
        return previousBalanceAmount;
    }

    public void setPreviousBalanceAmount(BigDecimal previousBalanceAmount) {
        this.previousBalanceAmount = previousBalanceAmount;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(BigDecimal amountPaid) {
        this.amountPaid = amountPaid;
    }
    
    public List<Tax> getTaxes() {
		return taxes;
	}

	public void setTaxes(List<Tax> taxes) {
		this.taxes = taxes;
	}

    public BigDecimal getImpSaldoInsoluto() {
        return impSaldoInsoluto;
    }

    public void setImpSaldoInsoluto(BigDecimal impSaldoInsoluto) {
        this.impSaldoInsoluto = impSaldoInsoluto;
    }

    public String getTaxObject() {
        return taxObject;
    }

    public void setTaxObject(String taxObject) {
        this.taxObject = taxObject;
    }

	@Override
	public String toString() {
		return "{\"" + (uuid != null ? "uuid\":\"" + uuid + "\", \"" : "")
				+ (serie != null ? "serie\":\"" + serie + "\", \"" : "")
				+ (folio != null ? "folio\":\"" + folio + "\", \"" : "")
				+ (currency != null ? "currency\":\"" + currency + "\", \"" : "")
				+ (exchangeRate != null ? "exchangeRate\":\"" + exchangeRate + "\", \"" : "")
				+ (paymentMethod != null ? "paymentMethod\":\"" + paymentMethod + "\", \"" : "")
				+ (partialityNumber != null ? "partialityNumber\":\"" + partialityNumber + "\", \"" : "")
				+ (previousBalanceAmount != null ? "previousBalanceAmount\":\"" + previousBalanceAmount + "\", \"" : "")
				+ (amountPaid != null ? "amountPaid\":\"" + amountPaid + "\", \"" : "")
                                + (impSaldoInsoluto != null ? "impSaldoInsoluto\":\"" + impSaldoInsoluto + "\", \"" : "" )
                                + (taxObject != null ? "taxObject\":\"" + taxObject + "\", \"" : "")
				+ (taxes != null ? "taxes\":\"" + taxes : "") + "\"}";
	}

    
}
