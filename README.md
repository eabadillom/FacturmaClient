# FacturmaClient
Cliente no oficial de conexión con la API REST de Facturama para emisión de CFDIs.

Este proyecto no oficial es una implementación de la documentación de la API de Facturama para
la emisión de Comprobantes Fiscales Digitales por Internet.

Para su uso, se debe compilar el proyecto y generar el archivo FacturamaClient___.jar e integrarlo en una
aplicación web.

Para su funcionamiento, se deberá registrar los siguientes valores JNDI en el servidor de aplicaciones
(p. e. Tomcat):

<!--Para el caso de emisión de CFDI's en el ambiente de producción, se deberá utilizar la siguiente
URL: https://api.facturama.mx-->
<Environment name="facturama/api" value="https://apisandbox.facturama.mx" type="java.lang.String"/>
<Environment name="facturama/user" value="user" type="java.lang.String"/>
<Environment name="facturama/password" value="5up3r.sEcRet" type="java.lang.String"/>
<Environment name="facturama/logo" value="http://url.to/logo.png" type="java.lang.String"/>

Para poder realizar el registro de Sucursales con la API multi-emisor, se debe crear
una instancia de la clase com.ferbo.facturama.business.CertificadosBL. Posteriormente
se debe invocar al método registra(CFDIInfo bean) del objeto para enviar el CFDI al
endpoint de Facturama.

La documentación de Facturama está disponible públicamente en los siguientes sitios:

API web
https://apisandbox.facturama.mx/Docs 

API Multi-emisor
https://apisandbox.facturama.mx/Docs-multi

Para la configuración del log4j2, se incluye un archivo .properties, el cual se deberá incluir directamente
en el classpath del proyecto web.

Se incluye un archivo cacerts con el certificado digital del sitio web de facturma.mx, en caso de requerirlo
en el despliegue de proyectos web con servidores Windows. Este archivo se mantiene con la contraseña
por defecto "changeit".