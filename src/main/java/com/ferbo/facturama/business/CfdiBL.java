package com.ferbo.facturama.business;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.facturama.request.CFDIInfo;
import com.ferbo.facturama.response.CfdiInfoModel;
import com.ferbo.facturama.response.FileViewModel;
import com.ferbo.facturama.tools.FacturamaException;
import com.ferbo.facturama.tools.FacturamaInternalException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**Clase para facturación en Facturama API Multi-emisor
 * @author esteban
 *
 */
public class CfdiBL extends FacturamaBL {
	
	private static Logger log = LogManager.getLogger();
	
	public CfdiBL() {
		super();
	}
	
	public CfdiInfoModel registra(CFDIInfo bean) throws FacturamaException {
		CfdiInfoModel respuesta = null;
		
		String sURL = null;
		HttpPost request = null;
        StringEntity userEntity = null;
        CloseableHttpResponse response = null;
        
        Gson prettyGson   = null;
        String jsonRequest = null;
        String jsonResponse = null;
        
        int httpStatus = -1;
        
        try {
			sURL = basePath + "/api-lite/3/cfdis";
			
			prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
			jsonRequest = prettyGson.toJson(bean);
			log.info("Solicitando información a Facturama (): {}", sURL);
			log.info("Solicitud JSON: " + jsonRequest);
			
			userEntity = new StringEntity(jsonRequest, ContentType.APPLICATION_JSON );
			request = this.createPostRequest(sURL);
            request.setEntity(userEntity);
            response = httpClient.execute(request);
            httpStatus = response.getCode();
            
            //¿La solicitud está fuera del rango 200?
            if(httpStatus < 200 || httpStatus >= 300)
            	throw new FacturamaInternalException("Respuesta no satisfactoria de Facturama.");
            
            //La solicitud si está en el rango 200
            jsonResponse = this.getResponseBody(response);
            log.info("El registro del certificado se realizó correctamente.");
            
            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            respuesta = prettyGson.fromJson(jsonResponse, CfdiInfoModel.class);
			
		} catch(FacturamaInternalException ex) {
        	log.error("Se presentó un problema en la comunicación con Facturama...", ex);
        	String message = this.getErrorMessage(response);
            throw new FacturamaException(message);
		} catch(IOException ex) {
			log.error("Se presentó un problema en la comunicación con Facturama...", ex);
			throw new FacturamaException(ex);
		}
		
		return respuesta;
	}
	
	/**Obtiene el archivo de la factura en una sucesión de caracteres base64 en el formato deseado.<br>
     * https://www.api.facturama.com.mx/api/Cfdi/{format}/{type}/{id}<br>
     * @param format Formato del archivo a obtener: ( pdf | html | xml ) (requerido)<br>
     * @param type Tipo de comprbante a obtener, puede ser: para facturas de API normal( payroll | received | issued ) y para API Multiemisor ( issuedLite ) (requerido)<br>
     * @param id Identificador unico de la factura (requerido)<br>
     * @return FileViewModel representación del archivo mediante el objeto FileViewModel de Facturama.
     * @throws IOException 
     */
    public FileViewModel getFile(String format, String type, String id) throws FacturamaException {
    	FileViewModel respuesta = null;
    	String sURL = null;
    	
		HttpGet request = null;
        CloseableHttpResponse response = null;
        
        Gson prettyGson   = null;
        String jsonResponse = null;
        
        int httpStatus = -1;
        
        try {
			sURL = basePath + "/api/Cfdi/%s/%s/%s";
			sURL = String.format(sURL, URLEncoder.encode(format,  charset), URLEncoder.encode(type, charset), URLEncoder.encode(id, charset));
			log.info("Solicitando información a Facturama (GET): {}", sURL);
			
			//prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
			
			request = this.createGetRequest(sURL);
            response = httpClient.execute(request);
            httpStatus = response.getCode();
            
            //¿La solicitud está fuera del rango 200?
            if(httpStatus < 200 || httpStatus >= 300)
            	throw new FacturamaInternalException("Respuesta no satisfactoria de Facturama.");
            
            //La solicitud si está en el rango 200
            jsonResponse = this.getResponseBody(response);
            log.info("El registro del certificado se realizó correctamente.");
            
            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            respuesta = prettyGson.fromJson(jsonResponse, FileViewModel.class);
			
		} catch(FacturamaInternalException ex) {
        	log.error("Se presentó un problema en la comunicación con Facturama...", ex);
        	String message = this.getErrorMessage(response);
            throw new FacturamaException(message);
		} catch(IOException ex) {
			log.error("Se presentó un problema en la comunicación con Facturama...", ex);
			throw new FacturamaException(ex);
		}
    	
    	return respuesta;
    }

}
