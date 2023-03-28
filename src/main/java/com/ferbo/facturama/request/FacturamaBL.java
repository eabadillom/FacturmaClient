package com.ferbo.facturama.request;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ContentType;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.facturama.response.CfdiInfoModel;
import com.ferbo.facturama.response.ClientModelRsp;
import com.ferbo.facturama.response.FileViewModel;
import com.ferbo.facturama.response.ModelException;
import com.ferbo.facturama.response.ProductRsp;
import com.ferbo.facturama.tools.DataSourceManager;
import com.ferbo.facturama.tools.FacturamaException;
import com.ferbo.facturama.tools.FacturamaInternalException;
import com.ferbo.facturama.tools.IOUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FacturamaBL {
    private static Logger log = LogManager.getLogger();
    
    private String basePath = null;
    private String user = null;
    private String password = null;
    
    private String charset = "UTF-8";
    private String auth = null;
    private byte[] encodedAuth = null;
    private String authHeaderValue = null;
    
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
        
        auth = String.format("%s:%s", user, password);
        encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
        authHeaderValue = "Basic " + new String(encodedAuth);
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
    
    
    
    
    
    public ProductRsp registra(Product sf) {
        ProductRsp respuesta = null;
        List<ProductTax> alTaxes = null;
        ProductTax tax = null;
        
        Gson prettyGson   = null;
        String json = null;
        
        String user = null;
        String password = null;
        String basePath = null;
        String sURL = null;
        String auth = null;
        byte[] encodedAuth = null;
        String authHeaderValue = null;
        
        URL url = null;
        HttpURLConnection httpConn = null;
        OutputStream output = null;
        byte[] bytes = null;
        
        InputStream input = null;
        
        Connection conn = null;
        String jndiName = null;
        
        try {
            this.infoTrustStorePath();
            log.info("Solicitando información a Facturama...");
            basePath = DataSourceManager.getJndiParameter("facturama/api");
            user = DataSourceManager.getJndiParameter("facturama/user");
            password = DataSourceManager.getJndiParameter("facturama/password");
            sURL = basePath + "/api/Product";
            
            jndiName = DataSourceManager.getJniName("");
            conn = DataSourceManager.getConnection(jndiName);
            
            
            /*--------------------------------------------------*/
            
            prettyGson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setPrettyPrinting().create();
            json = prettyGson.toJson(sf);
            
            
            log.info("JSON Producto / Servicio Facturama: " + json);
            
            
            auth = String.format("%s:%s", user, password);
            encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            authHeaderValue = "Basic " + new String(encodedAuth);
            
            url = new URL(sURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.setRequestProperty("Authorization", authHeaderValue);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            bytes = json.getBytes("utf-8");
            output = httpConn.getOutputStream();
            output.write(bytes);
            output.close();
            
            input = new BufferedInputStream(httpConn.getInputStream());
            String result = IOUtils.toString(input, "UTF-8");
            log.info("Respuesta de la API Facturama:\n" + result);
            Gson gson = new Gson();
            respuesta = gson.fromJson(result,  ProductRsp.class);
            
            /*--------------------------------------------------*/
            
            
        } catch(Exception ex) {
            log.error("Problema para registrar producto o servicio en Facturama...", ex);
        } finally {
            DataSourceManager.close(conn);
            IOUtil.close(input);
            IOUtil.close(output);
            httpConn.disconnect();
            
        }
        
        return respuesta;
    }
    
    public CfdiInfoModel registra(CFDIInfo cfdi) throws IOException {
        CfdiInfoModel respuesta = null;
        
        Gson prettyGson   = null;
        String json = null;
        
        String user = null;
        String password = null;
        String basePath = null;
        String sURL = null;
        String auth = null;
        byte[] encodedAuth = null;
        String authHeaderValue = null;
        
        URL url = null;
        HttpURLConnection httpConn = null;
        OutputStream output = null;
        byte[] bytes = null;
        
        InputStream input = null;
        
        try {
            this.infoTrustStorePath();
            log.info("Solicitando información a Facturama...");
            basePath = DataSourceManager.getJndiParameter("facturama/api");
            user = DataSourceManager.getJndiParameter("facturama/user");
            password = DataSourceManager.getJndiParameter("facturama/password");
            sURL = basePath + "/2/cfdis";
            
            prettyGson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setPrettyPrinting().create();
            json = prettyGson.toJson(cfdi);
            
            log.info("JSON CFDI (Factura) Facturama: " + json);
            auth = String.format("%s:%s", user, password);
            encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            authHeaderValue = "Basic " + new String(encodedAuth);
            
            url = new URL(sURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            httpConn.setRequestProperty("Accept", "application/json");
            httpConn.setRequestProperty("Authorization", authHeaderValue);
            httpConn.setRequestMethod("POST");
            httpConn.setDoOutput(true);
            httpConn.setDoInput(true);
            bytes = json.getBytes("utf-8");
            output = httpConn.getOutputStream();
            output.write(bytes);
            output.close();
            
            input = new BufferedInputStream(httpConn.getInputStream());
            String jsonResponse = IOUtils.toString(input, "UTF-8");
            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            Gson gson = new Gson();
            respuesta = gson.fromJson(jsonResponse,  CfdiInfoModel.class);
            
        } catch(Exception ex) {
            input = httpConn.getErrorStream();
            String responseMessage = httpConn.getResponseMessage();
            String errorMessage = String.format("Problema para registrar producto o servicio en Facturama (%s)...", responseMessage);
            log.error(errorMessage, ex);
        } finally {
            IOUtil.close(input);
            IOUtil.close(output);
            httpConn.disconnect();
        }
        
        return respuesta;
    }
    
//    public boolean existenConceptosNoSincronizados() {
//        boolean respuesta = false;
//        Servicio[] servicios = null;
//        List<Servicio> alServicios = null;
//        List<Servicio> alServiciosNoSync = null;
//        ServicioBusinessLogic servicioBO = null;
//        String jndiName = null;
//        
//        jndiName = DataSourceManager.getJniName("");
//        servicioBO = new ServicioBusinessLogic(jndiName);
//        servicios = servicioBO.get();
//        
//        alServicios = Arrays.asList(servicios);
//        
//        alServiciosNoSync = alServicios.stream()
//                .filter(s -> (s.getUUID() == null || "".equalsIgnoreCase(s.getUUID())))
//                .collect(Collectors.toList());
//        
//        if(alServiciosNoSync == null || alServiciosNoSync.size() == 0) {
//            respuesta = false;
//        } else {
//            log.info("Servicios no sincronizados: " + alServiciosNoSync);
//            respuesta = true;
//        }
//        
//        return respuesta;
//    }
    
    /**Obtiene el archivo de la factura en una sucesión de caracteres base64 en el formato deseado.<br>
     * https://www.api.facturama.com.mx/api/Cfdi/{format}/{type}/{id}<br>
     * @param format Formato del archivo a obtener: ( pdf | html | xml ) (requerido)<br>
     * @param type Tipo de comprbante a obtener, puede ser: para facturas de API normal( payroll | received | issued ) y para API Multiemisor ( issuedLite ) (requerido)<br>
     * @param id Identificador unico de la factura (requerido)<br>
     * @return FileViewModel representación del archivo mediante el objeto FileViewModel de Facturama.
     * @throws IOException 
     */
    public FileViewModel getFile(String format, String type, String id) throws IOException {
        FileViewModel respuesta = null;
        
        String charset = "UTF-8";
        String basePath = null;
        String user = null;
        String password = null;
        String auth = null;
        byte[] encodedAuth = null;
        String authHeaderValue = null;
        String sURL = null;
        
        URL url = null;
        HttpURLConnection httpConn = null;
        OutputStream output = null;
        InputStream input = null;
        
        try {
            this.infoTrustStorePath();
            log.info("Solicitando información a Facturama...");
            basePath = DataSourceManager.getJndiParameter("facturama/api");
            user = DataSourceManager.getJndiParameter("facturama/user");
            password = DataSourceManager.getJndiParameter("facturama/password");
            
            //URL original: https://www.api.facturama.com.mx/api/Cfdi/{format}/{type}/{id}
            sURL = basePath + "/api/Cfdi/%s/%s/%s";
            
            auth = String.format("%s:%s", user, password);
            encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            authHeaderValue = "Basic " + new String(encodedAuth);
            
            sURL = String.format(sURL, URLEncoder.encode(format,  charset), URLEncoder.encode(type, charset), URLEncoder.encode(id, charset));
            
            url = new URL(sURL);
            httpConn = (HttpURLConnection) url.openConnection();
            log.info("URL solicitada para archivos: " + sURL);
            httpConn.setRequestProperty("Accept-Charset", charset);
            httpConn.setRequestProperty("Authorization", authHeaderValue);
            httpConn.setRequestMethod("GET");
            httpConn.setDoOutput(false);
            httpConn.setDoInput(true);
            
            input = new BufferedInputStream(httpConn.getInputStream());
            
            String jsonResponse = IOUtils.toString(input, "UTF-8");
            
            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            
            Gson gson = new Gson();
            respuesta = gson.fromJson(jsonResponse, FileViewModel.class);
        } finally {
            IOUtil.close(input);
            IOUtil.close(output);
            httpConn.disconnect();
        }
        
        return respuesta;
    }
    
    
    
//    public ClientModelRsp getByRFC(String rfc) {
//        List<ClientModelRsp> alClientes = null;
//        List<ClientModelRsp> alTmp = null;
//        ClientModelRsp respuesta = null;
//        
//        try {
//            alClientes = this.getClientes();
//            
//            alTmp = alClientes.stream()
//                    .filter(c -> c.getRfc().equalsIgnoreCase(rfc))
//                    .collect(Collectors.toList());
//            
//            if(alTmp.size() > 0)
//                respuesta = alTmp.get(0);
//            
//        } catch(Exception ex) {
//            log.error("Problema para obtener el cliente con RFC " + rfc, ex);
//        }
//        
//        return respuesta;
//    }
    
    
    
    public List<Product> getProductos() throws IOException, FacturamaException {
        List<Product> beans = null;
        String charset = "UTF-8";
        String auth = null;
        byte[] encodedAuth = null;
        String authHeaderValue = null;
        String sURL = null;
        
        
        CloseableHttpClient httpclient = null;
        HttpGet request = null;
        CloseableHttpResponse response = null;
        
        Gson gson = null;
        String jsonResponse = null;
        Type listType = null;
        
        int httpStatus = -1;
        
        
        
        try {
            log.info("Solicitando información a Facturama...");
            
            auth = String.format("%s:%s", user, password);
            encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.UTF_8));
            authHeaderValue = "Basic " + new String(encodedAuth);
            
            sURL = basePath + "/api/Product";
            sURL = String.format(sURL);
            
            request = new HttpGet(sURL);
            request.addHeader("Accept-Charset", charset);
            request.addHeader("Authorization", authHeaderValue);
            
            
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(request);
            
            httpStatus = response.getCode();
            
            //¿La solicitud está fuera del rango 200?
            if(httpStatus < 200 || httpStatus >= 300)
            	throw new Exception("Respuesta no satisfactoria de Facturama.");
        	
            jsonResponse = this.getResponseBody(response);
            
            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            
            gson = new Gson();
            listType = new TypeToken<ArrayList<Product>>(){}.getType();
            beans = gson.fromJson(jsonResponse, listType);
            
        } catch(Exception ex) {
			ModelException failMessage = null;
			jsonResponse = this.getResponseBody(response);
			log.error("Error en facturama: {}",jsonResponse);
            gson = new Gson();
            failMessage = gson.fromJson(jsonResponse, ModelException.class);
            throw new FacturamaException(failMessage.getMessage());
		}
        
        return beans;
    }
    
    
    
	
	/**Método para registrar un documento CFDI Tipo I (Factura) en Facturama - API Multiemisor.
	 * @param cfdi Ver documentación @see <a href="https://apisandbox.facturama.mx/docs-multi/api/POST-api-2-cfdis">Facturama CFDI Multiemisor</a>
	 * @return
	 * @throws FacturamaException 
	 */
	public CfdiInfoModel registraME(CFDIInfo cfdi) throws FacturamaException {
		CfdiInfoModel respuesta = null;
        
        Gson prettyGson   = null;
        String json = null;
        String jsonResponse = null;
        
        String sURL = null;
        URL url = null;
        CloseableHttpClient httpClient = null;
        HttpPost request = null;
        StringEntity userEntity = null;
        CloseableHttpResponse response = null;
        int httpStatus = -1;

        try {
            this.infoTrustStorePath();
            log.info("Solicitando información a Facturama...");
            sURL = basePath + "/2/cfdis";
            
            prettyGson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .setPrettyPrinting().create();
            json = prettyGson.toJson(cfdi);
            
            url = new URL(sURL);
            
            request = new HttpPost(sURL);
            request.addHeader("Accept-Charset", charset);
            request.addHeader("Authorization", authHeaderValue);
            
            prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").setPrettyPrinting().create();
            json = prettyGson.toJson(cfdi);
            
            userEntity = new StringEntity(json, ContentType.APPLICATION_JSON );
            request.setEntity(userEntity);
            
            httpClient = HttpClients.createDefault();
            response = httpClient.execute(request);
            
            httpStatus = response.getCode();
            
            //¿La solicitud está fuera del rango 200?
            if(httpStatus < 200 || httpStatus >= 300)
            	throw new FacturamaInternalException("Respuesta no satisfactoria de Facturama.");
            
            //La solicitud si está en el rango 200
            log.info("El registro del certificado se realizó correctamente.");
            
            
            //La solicitud si está en el rango 200
            jsonResponse = this.getResponseBody(response);

            log.info("Respuesta de la API Facturama:\n" + jsonResponse);
            prettyGson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'hh:mm:ss").create();
            respuesta = prettyGson.fromJson(jsonResponse, CfdiInfoModel.class);
            
        } catch(Exception ex) {
        	String message = this.getErrorMessage(response);
        	throw new FacturamaException(message);
        } finally {
        	IOUtil.close(httpClient);
        }
        
		return respuesta;
	}
}
