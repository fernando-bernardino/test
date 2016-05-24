Generate WAR:
-------------
mvn clean install

WAR path:
---------
./target/createAPI.war


Application context for REST webservice:
----------------------------------------
/createAPI/add/


Example of request:
------------------------
POST /createAPI/add/ HTTP/1.1
 HOST: localhost:8080
 content-type: application/json;charset=UTF-8
 content-length: 32
 
 {"id":"1","operations":[[1.0, 2.0],[2.0,3.0]]}


Setting properties file
-----------------------
Copy ./config/createapi.properties into the application
container classpath


Setting log4j
-------------
Copy ./config/log4jconfig.properties into the application 
container classpath

Database settings
-----------------
To create database execute ./db/db_install.sql
For production purpose copy ./config/db-empty.properties to
the classpath and rename to db.properties
Change values in file for valid database connection

For testing change ./config/db-test.properties to valid database
connection
