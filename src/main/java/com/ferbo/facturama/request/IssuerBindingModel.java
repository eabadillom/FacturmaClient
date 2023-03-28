package com.ferbo.facturama.request;

import com.google.gson.annotations.SerializedName;

/**https://apisandbox.facturama.mx/docs/ResourceModel?modelName=IssuerBindingModel<br>
 * Datos del Emisor<br>
 * @author esteban
 *
 */
public class IssuerBindingModel {
	
	/**Regimenes Fiscales<br>
	 * Required
	 */
	@SerializedName(value="FiscalRegime")
	private String fiscalRegime = null;
	
	/**Atributo requerido para precisar la Clave del Registro Federal de Contribuyentes correspondiente al contribuyente<br>
	 * Required
	 * Matching regular expression pattern: [A-ZÑ]{3,4}[0-9]{2}(0[1-9]|1[012])(0[1-9]|[12][0-9]|3[01])[A-Z0-9]{2}[0-9A]?
	 * 
	 */
	@SerializedName(value = "Rfc")
	private String rfc = null;
	
	/**Modelo del tipo TaxName Requerido
	 * Required
	 * Matching regular expression pattern: [^|]{1,100}
	 * String length: inclusive between 1 and 100
	 */
	@SerializedName(value = "Name")
	private String name = null;
	
	@SerializedName(value = "Address")
	private Address address = null;

	public String getFiscalRegime() {
		return fiscalRegime;
	}

	public void setFiscalRegime(String fiscalRegime) {
		this.fiscalRegime = fiscalRegime;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "{\"fiscalRegime\":\"" + fiscalRegime + "\", \"rfc\":\"" + rfc + "\", \"name\":\"" + name
				+ "\", \"address\":\"" + address + "\"}";
	}
}
