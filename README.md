[![GraalVM Native Image builds](https://github.com/bcopy/revealjs-microservice/actions/workflows/main.yml/badge.svg)](https://github.com/bcopy/revealjs-microservice/actions/workflows/main.yml)
[![reveal.js](https://img.shields.io/badge/dynamic/xml?url=https%3A%2F%2Fraw.githubusercontent.com%2Fbcopy%2Frevealjs-microservice%2Fmain%2Fpom.xml&query=%2F%2F%2A%5Blocal-name%28%29%3D%27project%27%5D%2F%2A%5Blocal-name%28%29%3D%27dependencies%27%5D%2F%2A%5Blocal-name%28%29%3D%27dependency%27%5D%5B%2A%5Blocal-name%28%29%3D%27artifactId%27%5D%3D%27reveal.js%27%5D%2F%2A%5Blocal-name%28%29%3D%27version%27%5D&label=reveal.js)](https://search.maven.org/artifact/org.webjars.npm/reveal.js)

# Reveal.js microservice

This microservice provides a native, self-standing executable to serve Reveal.js presentations over HTTP.

## How to use it

1. Simply download the executable for your given platform (Linux, Windows, Mac OSX are supported).
1. Create a ```www``` folder to contain your presentation 
1. Create a ```resources``` folder to contain your binary and static resources (images, reveal.js plugins etc...)
1. Create an ```index.html``` file in ```www``` to host your reveal.js presentation (Bonus : you can use [Thymeleaf templating](https://www.thymeleaf.org/))
  1. Note that all **reveal.js** resources are available under ```/webjars/reveal.js/```, for instance :
     * ```/webjars/reveal.js/dist/reveal.js```
     * ```/webjars/reveal.js/plugin/notes/notes.js```
     * ```/webjars/reveal.js/dist/reset.css```
1. Start the server with executing ```reveal-microservice``` and it will be available at http://localhost:8080/index.html

## Under the hood

This microservice is implemented using Spring Boot 3 and its amazing native image compilation support.
As such, you can configure the microservice using any of the supported [Spring Boot application properties](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html).

All presentation templates are also [Thymeleaf templates](https://www.thymeleaf.org/), which supports the injection of request parameters and other dynamic properties into your presentations.

One custom property available to you is the location of your reveal.js presentation (by default ```./www/```), you can override this by adding a parameter to the microservice execution (for instance if your files are stored in ```./my-custom-folder```) :

```bash
reveal-microservice --spring.thymeleaf.prefix=file:./my-custom-folder/
```

Please note that the path **must** end with a forward slash.

<!--
Note that your presentation must be called ```index.html``` - To allow other HTML files to be served, you need to list them in the view names property like so (for instance, two files called ``my-presentation.html`` and ``my-other-presentation.html`` located under ```./www/```) :

```bash
reveal-microservice --spring.thymeleaf.view-names=my-presentation.html,my-other-presentation.html
```
-->

Static resources (*e.g.* images) are served from the folder ```./resources/```, you can also adjust this location like so :

```bash
reveal-microservice --spring.web.resources.static-locations=./my-other-resource-location/
```

## How to develop

### Pre-requisites

* Java 17 GraalVM 23.0+ (normally ships with the ``native`` component out of the box)

## Building the Spring Boot application

Using the provided Maven wrapper, simply call :

```./mvnw package```

You can then run the service with :

```./mvnw spring-boot:run```

You can compile a Native image with :

```./mvnw -q -B native:compile -Pnative```

For more details on how to compile the native image, please refer to [HELP.md](./HELP.md).

## Releasing a new version

To release a new version, invoke the gitflow plugin like so :

```./mvnw gitflow:release```

It will prompt for the new version number, and increment all related Maven information, before pushing all updates.
