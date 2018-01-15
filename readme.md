## Project for getting currency rates
==================================

## Routes
If you un app in http://localhost:8080

    ROUTE                               TYPE                DESCRIPTION  
    
 - /initial-db/fill-tables                PUT                 start preparation DB with pre-clearing
 - /initial-db/clear-db                   DELETE              clear DB
 - /currencies                            GET                 currency list
 - /currencies/new                        POST                add new currency
 - /rate/{currFrom}/{currTo}              GET                 get rate for pair currFrom-currTo
 - /rate/{currFrom}/{currTo}/{dateTo}     GET                 get rate for pair currFrom-currTo by date
 - /rate/{currFrom}/{currTo}/{dateTo}/{timeTo} GET            get rate for pair currFrom-currTo by date and time
 - /schedule/start                        GET                 start scheduler 2 times per day 
 - /schedule/start                        GET                 start scheduler 2 times per day 
 - /schedule/start?timesPerDay=10         GET                 start scheduler 10 times per day 
 - /rate-sources/                         GET                 return list of all resource sources
 - /rate-sources/new                      POST                add new resource source
 - /rate-sources/set-active?id=sourceID&active=true PUT       set active(true/false) mark for source, 
                                                              if will be true, then scheduler will get info from source url 
   
## Build and Run
For run application in Docker containers need to prepare the environment: 
- Java 8
- Maven 3
- Docker 
- MySql 

### Maven Build
Make sure you have `Maven` installed. Execute the following maven command from the directory of the 
parent project, `currency-exchange-service`:
```
$ mvn clean install
```
It will create the Spring Boot executable JAR,`currency-exchange-service-0.0.1-SNAPSHOT.jar` in folder `currency-exchange-service/target`.

### Run
To run the created JAR file from the command line:
```
$ java -jar currency-exchange-service-0.0.1-SNAPSHOT.jar
```
This should start up the example application at port `8080`.
Start page of application can be accessed at `http://localhost:8080`
For correct work you need prepare connection to MySql Db. 


### Docker Build
Before you build the Docker image, make sure Docker is available in your environment.
Execute the following maven command from the directory of the parent project, `currency-exchange-service`:
```
$ mvn clean package docker:build
```
This should build a Docker image named `currency-exchange-service`.

Check your new image via docker command: 
```
$ docker images
```
In the list of images, you should see new `currency-exchange-service` name

### Docker Run
Run the newly created Docker image, `currency-exchange-service`, by executing the 
[`docker run`](https://docs.docker.com/engine/reference/run/) command from the terminal:
```
docker run --rm -p 8080:8080 --name=ces currency-exchange-service
```
It may takes couple of minutes.

Run the [`docker ps`](https://docs.docker.com/v1.11/engine/reference/commandline/ps/) to list all the containers.
Among containers should be find `currency-exchange-service`


#### Configurations
Open the `application.properties` file and set your own configurations for the
database connection.

For docker container mode you should prepare access for `currency-exchange-service` container to MySQL.  
If you want use docker container for MySQL, as an example,you should create new docker network

```
$ docker network create my-network-name
```
and run your containers in common network 
```
docker run --rm -p 8080:8080 --network=my-network-name --name=ces currency-exchange-service
```
or connect them to network
```
docker network connect my-network-name ces
```