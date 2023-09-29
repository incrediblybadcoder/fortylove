# fortylove

[![Build Status](https://dev.azure.com/fortylove/fortylove/_apis/build/status%2FBuild%20and%20Test?branchName=refs%2Fpull%2F270%2Fmerge)](https://dev.azure.com/fortylove/fortylove/_build/latest?definitionId=9&branchName=refs%2Fpull%2F270%2Fmerge) 
![Static Badge](https://img.shields.io/badge/Spring%20Boot-3.1.0-brightgreen) 
![Static Badge](https://img.shields.io/badge/Vaadin%20Flow-24.2.0-blue)

Fortylove is a tennis club management system built with Java on Spring Boot with the web framework Vaadin. For persistence postgresql is used.

## Table of Contents
- [Prerequisites](#prerequisites)
- [Getting Started](#getting-started)
  - [Building](#building)
  - [Running](#running)
  - [Environment Variables](#environment-variables)
  - [Profiles](#profiles)
- [Database](#database)
- [IntelliJ Run-Configurations](#intellij-run-configurations)

## Prerequisites

The following dependencies need to be installed before running the application.

- [Java JDK](https://www.oracle.com/java/technologies/javase-downloads.html) (version 17 or higher)

To run the application with a live database, a postgresql database is needed. To run the application in development mode, an in-memory database can be used. See [Database](#database) for more information.

## Getting Started

### Building

Check out the repository and run
```sh
./gradlew build
```

### Running

Run
```sh
./gradlew bootRun
```

### Environment Variables

In order to run the application, the following environment variables need to be set up

| Environment Variable | Description | Sample Development Configuration |
| ------ | ------ | ------ |
| SPRING_PROFILE | The spring profile configures the application to run in different modes. The available profiles are described in section [Profiles](#profiles) | develop |
| SPRING_DATASOURCE_URL | Url of the database | jdbc:h2:mem:fortylovedb |
| SPRING_DATASOURCE_USERNAME | Username to access the database | sa |
| SPRING_DATASOURCE_PASSWORD | Password to access the database |  |
| MAIL_USERNAME | Username of the mail service for mail functionality | _Setup develop email service and use credentials (for example [mailtrap](https://mailtrap.io/))_ |
| MAIL_PASSWORD | Password of the mail service for mail functionality | _Setup develop email service and use credentials (for example [mailtrap](https://mailtrap.io/))_ |
| BASE_URL | Base url (root) of the application | http://localhost:8080/ |

> Note: `MAIL_USERNAME / MAIL_PASSWORD / BASE_URL` can be omitted. In this case the mail functionality is not supported.

### Profiles

The following spring profiles exist

| Profile | Description |
| ------ | ------ |
| production | The live postgresql database is used and no demo data is generated at application start for development purposes |
| develop | A in-memory database (h2) is used and demo data is generated at application start for development purposes |
| sql-generation | Same as the production profile, but configured to use spring-hibernate database management and **create-drop** all existing tables. Use this profile to let hibernate generate databse tables from entity classes. Sql statements can then be generated through a DBMS and used for database migration tool flyway. |
| test | To run application tests |

## Database

To use a live postgresql database, the postgresql server needs to have an empty database with name `fortylovedb`. On first start, this database will be set up by the application.
<br>
To run the application in development mode, an in-memory database can be used. Simply use the `develop` profile and specify the spring environment-variables for the h2 in-memory database as described in the sample development configuration in [Environment Variables](#environment-variables).

## IntelliJ Run-Configurations

Template IntelliJ run-configurations are checked in to this repository. All non-critical environment variables are predefined.
