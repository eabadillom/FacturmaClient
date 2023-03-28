package com.ferbo.facturama.business;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;

import org.apache.hc.client5.http.classic.methods.HttpDelete;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.facturama.response.ModelException;
import com.ferbo.facturama.tools.DataSourceManager;
import com.google.gson.Gson;

public abstract class FacturamaBL {
    private static Logger log = LogManager.getLogger();
    
    protected String basePath = null;
    protected String user = null;
    protected String password = null;
    
    protected String charset = "UTF-8";
    protected String auth = null;
    protected byte[] encodedAuth = null;
    protected String authHeaderValue = null;
    
    protected CloseableHttpClient httpClient = null;
    
    public FacturamaBL() {
        File cacertsFile = null;
        log.info("Estableciendo archivo trustStore...");
        cacertsFile = new File( getClass().getResource("/cacerts").getFile() );
        if(cacertsFile.exists() == false)
            log.warn("El archivo " + cacertsFile.getAbsolutePath() + " no se encuentra.");
        
        System.setProperty("javax.net.ssl.trustStore",cacertsFile.getPath());
        System.setProperty("javax.net.ssl.trustStorePassword","changeit");
        this.infoTrustStorePath();
        
        basePath = DataSourceManager.getJndiParameter("facturama/api");
        user = DataSourceManager.getJndiParameter("facturama/user");
        password = DataSourceManager.getJndiParameter("facturama/password");
        log.info("Cargando basePath: {}", basePath);
        
        auth = String.format("%s:%s", user, password);
        encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        authHeaderValue = "Basic " + new String(encodedAuth);
        
        httpClient = HttpClients.createDefault();
    }
    
    protected void infoTrustStorePath() {
        log.info("java.net.ssl.trustStore: " + System.getProperty("javax.net.ssl.trustStore"));
    }
    
    protected String getResponseBody(CloseableHttpResponse response) {
    	String bodyResponse = null;
        HttpEntity entity = null;
        String resultContent;
        
		try {
			entity = response.getEntity();
			resultContent = EntityUtils.toString(entity);
			bodyResponse = new String(resultContent.getBytes(), "UTF-8");
		} catch (ParseException | IOException e) {
			log.error("Problema para obtener la respueta de facturama...", e);
		}
        
        return bodyResponse;
    }
    
    protected String getErrorMessage(CloseableHttpResponse response) {
    	Gson                  gson = null;
    	String                jsonResponse = null;
    	ModelException        failMessage = null;
		Map<String, String[]> mpModelState = null;
		StringBuilder         sbMessage = new StringBuilder();
		
		jsonResponse = this.getResponseBody(response);
		log.error("Error en facturama: {}",jsonResponse);
        gson = new Gson();
        if(jsonResponse == null || "".equalsIgnoreCase(jsonResponse.trim()))
        	return "No hay mensaje de respuesta de facturama.";
        
        failMessage = gson.fromJson(jsonResponse, ModelException.class);
        if(failMessage == null)
        	return "No hay mensaje de respuesta de Facturama.";
        
        sbMessage.append(failMessage.getMessage());
        sbMessage.append("\r\n");
        mpModelState = failMessage.getModelState();
        
        for(Map.Entry<String, String[]> entry : mpModelState.entrySet()) {
        	String[] value = entry.getValue();
        	for(String msg : value) {
        		sbMessage.append(msg);
        		sbMessage.append("\r\n");
        	}
        }
		return sbMessage.toString();
    }
    
    protected HttpGet createGetRequest(String url) {
    	HttpGet request = null;
    	request = new HttpGet(url);
        request.addHeader("Accept-Charset", charset);
        request.addHeader("Authorization", authHeaderValue);
    	return request;
    }
    
    protected HttpPost createPostRequest(String url) {
    	HttpPost request = null;
    	request = new HttpPost(url);
		request.addHeader("Accept-Charset", charset);
		request.addHeader("Authorization", authHeaderValue);
    	return request;
    }
    
    protected HttpDelete createDeleteRequest(String url) {
    	HttpDelete request = null;
    	request = new HttpDelete(url);
        request.addHeader("Accept-Charset", charset);
        request.addHeader("Authorization", authHeaderValue);
        return request;
    }
}
