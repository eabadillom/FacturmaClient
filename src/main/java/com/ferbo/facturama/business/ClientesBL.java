package com.ferbo.facturama.business;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.facturama.request.ClientModel;
import com.ferbo.facturama.response.ClientModelRsp;
import com.ferbo.facturama.tools.FacturamaException;
import com.ferbo.facturama.tools.FacturamaInternalException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class ClientesBL extends FacturamaBL {
	private static Logger log = LogManager.getLogger();
	
	public ClientesBL() {
		super();
	}

	public List<ClientModelRsp> get() throws IOException, FacturamaException {
        List<ClientModelRsp> beans = null;
        
        String sURL = null;
        HttpGet request = null;
        CloseableHttpResponse response = null;
        
        Gson prettyJson = null;
        String jsonResponse = null;
        Type listType = null;
        
        int httpStatus = -1;
        
        try {
            sURL = basePath + "/api/Client";
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
            prettyJson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            
            listType = new TypeToken<ArrayList<ClientModelRsp>>(){}.getType();
            beans = prettyJson.fromJson(jsonResponse, listType);
            
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
	
	public ClientModel get(String uuid)throws IOException, FacturamaException {
        ClientModel bean = null;
        
        String sURL = null;
        
        HttpGet request = null;
        CloseableHttpResponse response = null;
        
        Gson prettyJson = null;
        String jsonResponse = null;
        
        int httpStatus = -1;
        
        
        try {
            log.info("Solicitando información a Facturama...");
            sURL = basePath + "/api/Client/%s";
            sURL = String.format(sURL, URLEncoder.encode(uuid,  charset));
            
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
            prettyJson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            bean = prettyJson.fromJson(jsonResponse, ClientModel.class);
            
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
	
	public ClientModelRsp registra(ClientModel bean) throws FacturamaException {
		ClientModelRsp respuesta = null;
		
		String sURL = null;
        HttpPost request = null;
        StringEntity userEntity = null;
        CloseableHttpResponse response = null;
        
        Gson prettyGson   = null;
        String jsonRequest = null;
        String jsonResponse = null;
        
        int httpStatus = -1;
        
        
        
		try {
			log.info("Solicitando información a Facturama...");
			sURL = basePath + "/api/Client";
			
			
			prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
			jsonRequest = prettyGson.toJson(bean);
			log.info("JSON de solicitud: " + jsonRequest);
			
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
            log.info("El registro del certificado se realizó correctamente.");
            
            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            respuesta = prettyGson.fromJson(jsonResponse, ClientModelRsp.class);
			
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
