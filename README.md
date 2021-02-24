# dremio-firebird-connector
Dremio Firebird Connector Plugin

Not fully tested.

<h2>Building and Installation</h2>
<ol>
<li>In root directory with the pom.xml file run <code>mvn clean install</code></li>
<li>Take the resulting .jar file in the target folder and put it in the \dremio\jars folder in Dremio</li>
<li>Take the Firebird JDBC driver from your .m2 folder (or check https://github.com/FirebirdSQL/jaybird/releases/download/v4.0.0/jaybird-4.0.0.java8.zip) and put in in the \dremio\jars\3rdparty folder</li>
<li>Download connector-api-1.5.jar from https://mvnrepository.com/artifact/javax.resource/connector-api/1.5 and put in in the \dremio\jars\3rdparty folder</li>
<li>Restart Dremio</li>
</ol>
