package com.ferbo.facturama.request;

import com.google.gson.annotations.SerializedName;

/**CSD<br>
 * Certificado Digital
 * https://apisandbox.facturama.mx/docs-multi/api/POST-api-lite-csds<br>
 * Método POST: Facturama no proporciona respuesta después de su ejecución. Sólo devuelve HTTP Status 200.
 * @author esteban
 *
 */
public class Csd {
    
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
         
    public String getCertificate()
    {
         return certificate;
    }
    
    public void setCertificate(String Certificate)
    {
       this.certificate = Certificate;
    }
    
    public String getRfc()
    {
         return rfc;
    }
    
    public void setRfc(String Rfc)
    {
       this.rfc = Rfc;
    }
    
    public String getPrivateKey()
    {
         return privateKey;
    }
    
    public void setPrivateKey(String PrivateKey)
    {
       this.privateKey = PrivateKey;
    }
       
    public String getPrivateKeyPassword ()
    {
         return privateKeyPassword;
    }
    
    public void setPrivateKeyPassword(String PrivateKeyPassword)
    {
       this.privateKeyPassword = PrivateKeyPassword;
    }

	@Override
	public String toString() {
		return "{\"rfc\":\"" + rfc + "\", \"certificate\":\"" + certificate + "\", \"privateKey\":\"" + privateKey
				+ "\", \"privateKeyPassword\":\"" + privateKeyPassword + "\"}";
	}
           
         
     
 }
