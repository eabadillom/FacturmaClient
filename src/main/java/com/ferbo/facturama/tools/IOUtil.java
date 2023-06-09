package com.ferbo.facturama.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.zip.ZipOutputStream;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;

public class IOUtil {
	
	public static synchronized void close(ZipOutputStream output) {
		
		try {
			if(output != null) {
				output.finish();
				output.close();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			output = null;
		}
	}
	
	public static synchronized void close(OutputStream output) {
		
		try {
			if(output != null) {
				output.close();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			output = null;
		}
	}
	
	public static synchronized void close(Writer writer) {
		try {
			if(writer != null) {
				writer.flush();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			if(writer != null) {
				writer.close();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			writer = null;
		}
	}
	
	public static synchronized void close(InputStream input) {
		
		try {
			if(input != null) {
				input.close();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		} finally {
			input = null;
		}
	}

    public static void close(BufferedReader reader) {
        try {
            if(reader != null)
                reader.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        } finally {
            reader = null;
        }
        
    }
    
    public static void close(CloseableHttpClient client) {
		try {
			if(client != null) {
				client.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			client = null;
		}
    }
}