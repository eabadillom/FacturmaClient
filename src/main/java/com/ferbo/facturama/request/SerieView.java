package com.ferbo.facturama.request;

import com.google.gson.annotations.SerializedName;

/**Clase con los atributos necesarios para la creación de sucursales (branch office) con Facturama.<br>
 * https://apisandbox.facturama.mx/docs/api/POST-serie-idBranchOffice<br>
 * <br>
 * Para realizar el registro de una serie, se debe tener primero registrada la sucursal a la cual se quiere asociar la serie.<br>
 * Se debe invocar mediante HTTP POST a la URL<br>
 * https://apisandbox.facturama.mx/serie/{idBranchOffice}<br>
 * <br>
 * Como resultado, se obtiene un objeto de la clase SerieViewModel<br>
 * https://apisandbox.facturama.mx/docs/ResourceModel?modelName=SerieViewModel<br>
 * @author esteban
 *
 */
public class SerieView {
	
	
	/**Id de la Sucursal a la que pertenece la Serie<br>
	 * Required<br>
	 * Data type: Text<br>
	 * String length: inclusive between 23 and 23<br>
	 */
	@SerializedName(value = "IdBranchOffice")
	String idBranchOffice = null;
	
	/**Nombre de la serie<br>
	 * Required<br>
	 * Matching regular expression pattern: [a-zA-Z0-9]{1,10}<br>
	 * String length: inclusive between 1 and 10<br>
	 */
	@SerializedName(value = "Name")
	String name = null;
	
	/**Descripción de la serie<br>
	 * String length: inclusive between 1 and 50<br>
	 * 
	 */
	@SerializedName(value = "Description")
	String description = null;
	
	/**Folio inicial para la serie
	 */
	@SerializedName(value = "Folio")
	Integer folio = null;

	public String getIdBranchOffice() {
		return idBranchOffice;
	}

	public void setIdBranchOffice(String idBranchOffice) {
		this.idBranchOffice = idBranchOffice;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getFolio() {
		return folio;
	}

	public void setFolio(Integer folio) {
		this.folio = folio;
	}

	@Override
	public String toString() {
		return "{\"idBranchOffice\":\"" + idBranchOffice + "\", \"name\":\"" + name + "\", \"description\":\""
				+ description + "\", \"folio\":\"" + folio + "\"}";
	}

}
