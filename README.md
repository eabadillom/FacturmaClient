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

La documentación de Facturama está disponible públicamente en los siguientes sitios:

API web
https://apisandbox.facturama.mx/Docs 

API Multi-emisor
https://apisandbox.facturama.mx/Docs-multi
