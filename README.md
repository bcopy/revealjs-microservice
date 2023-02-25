[![GraalVM Native Image builds](https://github.com/bcopy/revealjs-microservice/actions/workflows/main.yml/badge.svg)](https://github.com/bcopy/revealjs-microservice/actions/workflows/main.yml)

# Reveal.js microservice

This microservice provides a native, self-standing executable to serve Reveal.js presentations over HTTP.

## How to use it

1. Simply download the executable for your given platform (Linux, Windows, Mac OSX are supported).
1. Create a ```www``` folder to contain your presentation 
1. Create a ```resources``` folder to contain your binary and static resources (images, reveal.js plugins etc...)
1. Create an ```index.html``` file in ```www``` to host your reveal.js presentation (Bonus : you can use [Thymeleaf templating](https://www.thymeleaf.org/))
  1. Note that all **reveal.js** resources are available under ```/webjars/reveal.js/4.1.3/```, for instance :
     * ```/webjars/reveal.js/4.1.3/dist/reveal.js```
     * ```/webjars/reveal.js/4.1.3/plugin/notes/notes.js```
     * ```/webjars/reveal.js/4.1.3/dist/reset.css```
1. Start the server with executing ```reveal-microservice``` and it will be available at http://localhost:8080/index.html

🗒 NOTE : Native compilation does not currently support the Webjars locator library, forcing us to use versioned URLs to access Reveal.js resources (c.f. [Spring Framework issue 27619](https://github.com/spring-projects/spring-framework/issues/27619) and [Webjars locator issue 96](https://github.com/webjars/webjars-locator-core/issues/96) )

## Under the hood

This microservice is implemented using Spring Boot 3 and its amazing native image compilation support.
As such, you can configure the microservice using any of the supported [Spring Boot application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html).

All presentation templates are also [Thymeleaf templates](https://www.thymeleaf.org/), which supports the injection of request parameters and other dynamic properties into your presentations.

One custom property available to you is the location of your reveal.js presentation (by default ```./www/```), you can override this by adding a parameter to the microservice execution :

```bash
reveal-microservice --spring.thymeleaf.prefix=./my-custom-folder/
```

Please note that the path **must** end with a forward slash.

Note that your presentation must be called ```index.html``` - To allow other HTML files to be served, you need to list them in the view names property like so (for instance, two files called ``my-presentation.html`` and ``my-other-presentation.html`` located under ```./www/```) :

```bash
reveal-microservice --spring.thymeleaf.view-names=my-presentation.html,my-other-presentation.html
```

Static resources (*e.g.* images) are served from the folder ```./resources/```, you can also adjust this location like so :

```bash
reveal-microservice --spring.web.resources.static-locations=./my-other-resource-location/
```

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
