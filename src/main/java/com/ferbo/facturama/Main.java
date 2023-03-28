package com.ferbo.facturama;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.ferbo.facturama.business.CertificadosBL;
import com.ferbo.facturama.business.CfdiBL;
import com.ferbo.facturama.request.Csd;
import com.ferbo.facturama.response.CsdRsp;
import com.ferbo.facturama.response.FileViewModel;
import com.ferbo.facturama.tools.FacturamaException;

/**En esta clase se implementan las invocaciones a los respectivos métodos para probar la conectividad con api.facturama.mx
 * @author esteban
 *
 */
public class Main {
	private static Logger log = LogManager.getLogger(Main.class);
	
	public static void main(String args[]) {
		Main main = new Main();
		main.procesarArchivosCFDI();
		
	}
	
	public void procesarArchivosCFDI() {
		CfdiBL cfdiBO = new CfdiBL();
		
		try {
			FileViewModel file = cfdiBO.getFile("pdf", "issuedLite", "V2bnlwZkJgMwgxyWUJHElQ2");
			
			log.info("Respuesta de facturama: {}", file);
			
		} catch(FacturamaException ex) {
			log.error("Problema con Facturama...", ex);
		} catch(Exception ex) {
			log.error("Problema general con Facturama...", ex);
		}
	}
	
	public void procesarSucursales() {
		
	}
	
	public void procesarCertificados() {
		CertificadosBL facturamaBO = new CertificadosBL();
		List<CsdRsp> certificados = null;
		
		Csd certificado = null;
		CsdRsp csdTmp = null;
		
		
		
		try {
			//El ejemplo corresponde a la carga de un certificado digital de un emisor de prueba proporcionado por Facturama.
			certificado = new Csd();
			certificado.setRfc("CACX7605101P8");
			certificado.setCertificate("MIIFjDCCA3SgAwIBAgIUMzAwMDEwMDAwMDA0MDAwMDI0NTEwDQYJKoZIhvcNAQELBQAwggErMQ8wDQYDVQQDDAZBQyBVQVQxLjAsBgNVBAoMJVNFUlZJQ0lPIERFIEFETUlOSVNUUkFDSU9OIFRSSUJVVEFSSUExGjAYBgNVBAsMEVNBVC1JRVMgQXV0aG9yaXR5MSgwJgYJKoZIhvcNAQkBFhlvc2Nhci5tYXJ0aW5lekBzYXQuZ29iLm14MR0wGwYDVQQJDBQzcmEgY2VycmFkYSBkZSBjYWRpejEOMAwGA1UEEQwFMDYzNzAxCzAJBgNVBAYTAk1YMRkwFwYDVQQIDBBDSVVEQUQgREUgTUVYSUNPMREwDwYDVQQHDAhDT1lPQUNBTjERMA8GA1UELRMIMi41LjQuNDUxJTAjBgkqhkiG9w0BCQITFnJlc3BvbnNhYmxlOiBBQ0RNQS1TQVQwHhcNMTkwNjE3MjMyNTQ5WhcNMjMwNjE3MjMyNTQ5WjCBszEdMBsGA1UEAxMUWE9DSElMVCBDQVNBUyBDSEFWRVoxHTAbBgNVBCkTFFhPQ0hJTFQgQ0FTQVMgQ0hBVkVaMR0wGwYDVQQKExRYT0NISUxUIENBU0FTIENIQVZFWjEWMBQGA1UELRMNQ0FDWDc2MDUxMDFQODEbMBkGA1UEBRMSQ0FDWDc2MDUxME1HVFNIQzA0MR8wHQYDVQQLExZYT0NISUxUIENBU0FTIENIQVZFWiAyMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwoZeqXTNnGSt/9LMYKncHwSgab2QzgTw+/oQy8RlD9h57dJdZKYUnY6FX+2qJ5kfAEKR8WXpDdvX9wRCGolt9Mx/t/wWk+9kECMY4s6pikSF1LcvMfrikHoOyKnT6zrUWo/Zcu7IfHfbuf3UTu4Dof2kZG8cMxP2cgh2dltT0YVfxr9ndGIGlbFKM9P2RGmCyz+HXn+gCucdZuuLjo5///ev+GcoECDGU/c1+Dz+2DwRIln2p6Lu3dKS6f2kQbVO+f+vVzdyEMna08Sb92+SBJthR6LfGGP+eNgF4BPUFTXy/f2n8l/RMJ2MCgbrTBgsfCCNGTDkPtth7nieG5YOzwIDAQABox0wGzAMBgNVHRMBAf8EAjAAMAsGA1UdDwQEAwIGwDANBgkqhkiG9w0BAQsFAAOCAgEAEn3sU34HDYbDKFuL2zxdecl360N2wHvL1l/2hAq7TG7TlDE0L5+x+Q8xr9WuyDdhjZhwf89ly7GIVgW74MPvzUyCHE49opIerLaRNbwjiMjWQIn4IJij3smQUzAT9/Gz1pGoMwJpeVCS7ZP/VxiwmRCqemq2DIG+fkbU1HhzUzu3BeeEqvQjGDMKxz/H34Id4O6z/BoB/9Xw9kTIqIhUDxGYQCazTwBnuLoZl9WRCm1PqEhMmlwR/aKV1bPxsoDptlyR9kG9Vg7BOeXlqnC5cmZqge8iiIPSEwH9KCPUm7OrRXa4QomWHVNtXO2cU9KsMCb2aPujjBPdqUgxR2cLpDlMMlYpZkLDt2L18uHKz66PdAHkT3cPhvgUc/hKQCRuTxidug9CdtHWLLSq54hS0e64o5PJ/Xbp6wyXuWR9tWgURZ0+Exy4MJFsua7EDgfKsCxszR1eCFRh29wWvbMRBju0wPushu+asNMamxyvntG7PMBJngOeHBX3VPVbukEAYehNPFF1KPj4VJqPSxaI/Mpyrlxokl9vHttoRArV07zIDm8jIdQRnG/RU8KV3vlGKE2Tru6h6pQZCTCll9Qsnf52U3e2TDq8AQInNgYoraI9vOyuiOGaiouCvf2y2VMJZ06XcwsAomrT2Q3itzESDhyuyuNJyC0Sfh8rv2wWggo=");
			certificado.setPrivateKey("MIIFDjBABgkqhkiG9w0BBQ0wMzAbBgkqhkiG9w0BBQwwDgQIAgEAAoIBAQACAggAMBQGCCqGSIb3DQMHBAgwggS9AgEAMASCBMh4EHl7aNSCaMDA1VlRoXCZ5UUmqErAbucRBAKNQXH8t28sY5cCCAXZLganNn7XR5BPBHtGci/GFR522kP4tUpMUIiM56uK6DUgo/u1+nCnt/tJpQ5vFeNjEmZDURJjale3paZlu3lV4Yw+quu0+gAHEt0glo2P7gtzH/L228ooFQhzfO5E+46QVsF7NMAQG1eGlV8yAIGE9eKO4mu8K+4mVrSzBOr7qwFkVALCF4813J3SjyD4jWtCJX+UmK3Ov4PvkDmccNCR9hl7aXNvZdfIamL1alW5VvgKWC+/ZRCwGbuDVWbudpDnOtmQatg+9inZ5r4qFe1PStJk8SViYbp8FvfvXu1aBXcEF7RSW/YKwrF6MDPG37tFciCT1tnLDn+XPU8sQEOLIpfD6FOwguELLoS3cvJO2TR2HQ2WxwvMf2f6+8doNTOHnVSJIcKR/Ar+CxhwMauxrhzfvDZ7hjlvbsQ+YUXE90HDVnj2w5L7o1bUyWP8PeNEBUqB0TxQ8aEFEBf3f3ksFeWcBTj50LB/t/EV6XWpAtiNvq6UU1H1+O//5iAoaJpnNswTp3MY8gkYdvUVPsWQfmrl31rZAykwLgHhUn2E15mXLLExIKtkKtbvHHxshWSHiefGeMVEi92cNECHvjVKIihQSJGeTHD4KuJKdtrZdysAzzsA7KYJtCm7426oN7Wy+ooNv9WPJLFKaLWX1hpyq9+Qy+DqD2fTgg7fX192DbRc7e5JUhpcQ46BXgxx80BE7Zg7xrOqOLnnEKDRg7lIqZExB0xOTJ9a0hrfmO1A3exgMMr5X62d4rM9OTHLIiKnN9U34+cihCrFv42dbnEfiz75VWwHxnXjmjCQDljL9Hd6fehJVI7bsH0EVqPIVp/koVSNbYtql2Pa44jpJNTrdST9/2wHUUzLdEkHEV60/Gx17+AJoEMyLjEjhym6i4THnwkWatQxrrAjV9gzkY5fcSrnJliIMYbKP2zlXNRPB7uDQkWBcwfPeKqjwgYabZJ60AGC4T8JMWzqGFzrGeww+iWMrCoFrNWi0ipXK2A0NPZXl3wRLP+chNK/Dv8/xjYpSZ1cHlHEBKI8CHavijv2GUoN9hU/jk8kcx1FM/hGeR7/YjJkKSFJmB/oEpAaT1XUe6JxwArMo/A7LOK7k4xmnKN6o1DqF4sVxxZ4DnKlEN/WiOwe1hdefHLSLLa4egWuFfjxaME+4DcdUX7GYToZKhhmzKjKmt6Hs99+8Qzqu0U/pglRbO47KE8C4+l58jBvzlRR0af45BxqVCeylDrvcfLzIcTAJ0kUcf4oaJxQJ1qKxNVMkcq63S4fSwGc3IpUDiFBcVWMVbZecF/pQ9TK3Xio/BzaD83s3LqkKVjymw5iclwjJjhU7A3A8DVSszst737ILPIJxr5SCiJ/pKvPQP684IwsoEsV0xVdOFR6Z7xUlkif7igSNl3RwRDm1o/sIux1gloAtgMKBllMFDs7fgNw3u4U3uX/AnIaBAD/ktQuQJbZdVPew1CIT50ciolZMFIWrWxHDpTmHRWSEJ8ob8VV+2LAlG9eTz8Lq+lyk1qeQvAwklvLae769CSqZqG1wyA83wjcc8CWd95HKp1f6grAXBRQRaAhDi0P0CqyKok=");
			certificado.setPrivateKeyPassword("12345678a");
			
			certificados = facturamaBO.get();
			log.info("Certificados: {}", certificados);
			final String rfc = certificado.getRfc();
			
			List<CsdRsp> collectedCsds = certificados.stream().filter(c -> c.getRfc().equals(rfc))
			.collect(Collectors.toList());
			;
			
			if(collectedCsds.size() > 0)
				csdTmp = collectedCsds.get(0);
			
			facturamaBO.elimina(csdTmp.getRfc());
			
			facturamaBO.registra(certificado);
			log.info("Nuevo certificado: %s", certificado);
			
			certificados = facturamaBO.get();
			log.info("Certificados: {}", certificados);
			log.info("Fin del programa.");
		} catch (IOException ex) {
			log.error("Problema en la consulta con Facturama...", ex);
		} catch(FacturamaException ex) {
			log.error("Problema con Facturama...", ex);
		} catch(Exception ex) {
			log.error("Problema general con Facturama...", ex);
		}
	}
}