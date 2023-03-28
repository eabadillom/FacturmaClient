package com.ferbo.facturama.response;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class CsdRsp {
	
	
	/**RFC del propietario de los certificados<br>
     * Required
     * Matching regular expression pattern: [A-Z,Ñ,&]{3,4}[0-9]{2}[0-1][0-9][0-3][0-9][A-Z,0-9]?[A-Z,0-9]?[0-9,A-Z]?
     */
    @SerializedName(value="Rfc")
    private String rfc;
    
    
    /**Certificado en base64<br>
     * Required
     */
    @SerializedName(value="Certificate")
    private String certificate;
    
    
    /**Llave privada en base64<br>
     * Required
     */
    @SerializedName(value="PrivateKey")
    private String privateKey;
    
    
    /**Contraseña de la Llave privada<br>
     * Required
     */
    @SerializedName(value="PrivateKeyPassword")
    private String privateKeyPassword;
    
    @SerializedName(value="CsdExpirationDate")
    private Date csdExpirationDate;
    
    @SerializedName(value="UploadDate")
    private Date uploadDate;

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPrivateKeyPassword() {
		return privateKeyPassword;
	}

	public void setPrivateKeyPassword(String privateKeyPassword) {
		this.privateKeyPassword = privateKeyPassword;
	}

	public Date getCsdExpirationDate() {
		return csdExpirationDate;
	}

	public void setCsdExpirationDate(Date csdExpirationDate) {
		this.csdExpirationDate = csdExpirationDate;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	@Override
	public String toString() {
		return "{\"rfc\":\"" + rfc + "\", \"certificate\":\"" + certificate + "\", \"privateKey\":\"" + privateKey
				+ "\", \"privateKeyPassword\":\"" + privateKeyPassword + "\", \"csdExpirationDate\":\""
				+ csdExpirationDate + "\", \"uploadDate\":\"" + uploadDate + "\"}";
	}

}
