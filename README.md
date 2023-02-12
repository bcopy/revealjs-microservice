# Reveal.js microservice

This microservice provides a native, self-standing executable to serve Reveal.js presentations over HTTP.

## How to use it

1. Simply download the executable for your given platform (Linux, Windows, Mac OSX are supported).
1. Create a ```www`` folder to contain your presentation and resources (images, reveal.js plugins etc...)
1. Create an ```index.html``` file in ```www``` to host your reveal.js presentation
  1. Note that all **reveal.js** resources are available under ```/revealjs```, for instance :
     * ```/revealjs/dist/reveal.js```
     * ```/revealjs/plugin/notes/notes.js```
     * ```/revealjs/dist/reset.css```
1. Start the server with executing ```reveal-microservice``` and it will be available at http://localhost:8080/index.html

## Under the hood

This microservice is implemented using Spring Boot 3 and its amazing native image compilation support.
As such, you can configure the microservice using any of the supported Spring Boot application properties.

One custom property available to you is the location of your static contents (by default ```./www/```), you can override this by adding a parameter to the microservice execution :

```reveal-microservice --static.path=./my-custom-folder/```

Please note that the path **must** end with a forward slash.

## How to develop

### Pre-requisites

* Java 17 GraalVM 22.3+
* GraalVM Native image plugin

## Building the Spring Boot application

Using the provided Maven wrapper, simply call :

```./mvnw package```

You can then run the service with :

```./mvnw spring-boot:run```

To compile the native image, please refer to [HELP.md](./HELP.md).