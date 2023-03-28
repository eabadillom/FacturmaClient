package com.ferbo.facturama.business;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.facturama.request.SerieView;
import com.ferbo.facturama.response.SerieViewModel;
import com.ferbo.facturama.tools.FacturamaException;
import com.ferbo.facturama.tools.FacturamaInternalException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class SeriesBL extends FacturamaBL {
	private static Logger log = LogManager.getLogger();
	
	public SeriesBL() {
		super();
	}

	/**Obtiene el listado de series, de acuerdo al uuid de la sucursal proporcionada.
	 * @param uuid Identificador de Facturama de la sucursal.
	 * @return Listado de series de la sucursal.
	 * @throws FacturamaException
	 */
	public List<SerieViewModel> get(String uuid) throws FacturamaException {
		List<SerieViewModel> beans = null;
        
        String sURL = null;
        HttpGet request = null;
        CloseableHttpResponse response = null;
        
        Gson gson = null;
        String jsonResponse = null;
        Type listType = null;
        
        int httpStatus = -1;
        
        
        
        try {
            sURL = basePath + String.format("/serie/%s", uuid) ;
            sURL = String.format(sURL);
            log.info("Solicitando información a Facturama (GET): {}", sURL);
            
            request = this.createGetRequest(sURL);
            response = httpClient.execute(request);
            httpStatus = response.getCode();
            
            log.info("Código de respuesta HTTP: {}", httpStatus);
            
            //¿La solicitud está fuera del rango 200?
            if(httpStatus < 200 || httpStatus >= 300)
            	throw new FacturamaInternalException("Respuesta no satisfactoria de Facturama.");
            
            //La solicitud si está en el rango 200
            jsonResponse = this.getResponseBody(response);

            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            listType = new TypeToken<ArrayList<SerieViewModel>>(){}.getType();
            beans = gson.fromJson(jsonResponse, listType);
            
        } catch(FacturamaInternalException ex) {
        	log.error("Se presentó un problema en la comunicación con Facturama...", ex);
        	String message = this.getErrorMessage(response);
            throw new FacturamaException(message);
		} catch(IOException ex) {
			log.error("Se presentó un problema en la comunicación con Facturama...", ex);
			throw new FacturamaException(ex);
		}
        
        return beans;
	}
	
	/**Obtiene un Serie a partir de su Nombre<br>
	 * @param uuid Identificador de Facturama de la sucursal.
	 * @param name Nombre de la serie.
	 * @return
	 * @throws FacturamaException
	 */
	public SerieViewModel get(String uuid, String name) throws FacturamaException {
		SerieViewModel bean = null;
		
		String sURL = null;
        HttpGet request = null;
        CloseableHttpResponse response = null;
        
        Gson gson = null;
        String jsonResponse = null;
        
        int httpStatus = -1;
        
        
        
        try {
            
            sURL = basePath + String.format("/serie/%s/%s", uuid, name) ;
            sURL = String.format(sURL);
            log.info("Solicitando información a Facturama (GET): {}", sURL);
            
            request = this.createGetRequest(sURL);
            response = httpClient.execute(request);
            httpStatus = response.getCode();
            
            log.info("Código de respuesta HTTP: {}", httpStatus);
            
            //¿La solicitud está fuera del rango 200?
            if(httpStatus < 200 || httpStatus >= 300)
            	throw new FacturamaInternalException("Respuesta no satisfactoria de Facturama.");
            
            //La solicitud si está en el rango 200
            jsonResponse = this.getResponseBody(response);

            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            bean = gson.fromJson(jsonResponse, SerieViewModel.class);
            
        } catch(FacturamaInternalException ex) {
        	log.error("Se presentó un problema en la comunicación con Facturama...", ex);
        	String message = this.getErrorMessage(response);
            throw new FacturamaException(message);
		} catch(IOException ex) {
			log.error("Se presentó un problema en la comunicación con Facturama...", ex);
			throw new FacturamaException(ex);
		}
        
        return bean;
	}
	
	public SerieViewModel registra(SerieView serie) throws FacturamaException {
		SerieViewModel respuesta = null;
		String sURL = null;
        
        HttpPost request = null;
        StringEntity userEntity = null;
        CloseableHttpResponse response = null;
        
        Gson prettyGson   = null;
        String jsonRequest = null;
        String jsonResponse = null;
        
        int httpStatus = -1;
        
        try {
            
            sURL = basePath + "/serie/%s";
            sURL = String.format(sURL, serie.getIdBranchOffice());
            log.info("Solicitando información a Facturama (POST): {}", sURL);
            
            prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
            jsonRequest = prettyGson.toJson(serie);
            log.info("Solicitud JSON: {}", jsonRequest);
            
            userEntity = new StringEntity(jsonRequest, ContentType.APPLICATION_JSON );
            request = this.createPostRequest(sURL);
            request.setEntity(userEntity);
            
            response = httpClient.execute(request);
            httpStatus = response.getCode();
            
            log.info("Código de respuesta HTTP: {}", httpStatus);
            
            //¿La solicitud está fuera del rango 200?
            if(httpStatus < 200 || httpStatus >= 300)
            	throw new FacturamaInternalException("Respuesta no satisfactoria de Facturama.");
            
            //La solicitud si está en el rango 200
            jsonResponse = this.getResponseBody(response);

            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            respuesta = prettyGson.fromJson(jsonResponse, SerieViewModel.class);
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
