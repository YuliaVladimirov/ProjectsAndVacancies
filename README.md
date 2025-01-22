
# Projects and Vacancies App

__Projects and Vacancies__ is a backend web application for project and vacancy management. This application implements the basic functionality of a REST API web service.

This project is a test assignment for an internship application at the TROOD company.

## General Description

The application provides basic CRUD functionality for projects and for vacancies related to these projects.

- The structure of the database tables is shown in [Database Structure](DB.md).
- All API requests (endpoints and their fields) and responses are documented and exposed via [Swagger](https://projects-and-vacancies.azurewebsites.net/swagger-ui/index.html#/).
- To simplify running and testing the application on a developer's machine, a minimal Docker Compose file was created.

## Usage and Testing

### Application Deployed on Azure

The base URL of the deployed backend application is: `https://projects-and-vacancies.azurewebsites.net`

The application exposes two endpoints:

[/projects](http://projects-and-vacancies.azurewebsites.net/projects)

[/projects/{id}/vacancies](http://projects-and-vacancies.azurewebsites.net/projects/1/vacancies)

The easiest way to explore the API is through the built-in [Swagger UI](https://projects-and-vacancies.azurewebsites.net/swagger-ui/index.html#/). It has both the full list of API endpoints and the detailed description with examples. The API requests can be made directly through Swagger UI.

### Running the Application Locally

The contents of this GitHub repository can be directly imported into your IDE of choice (I used IntelliJ IDEA for development). Building and running the application depends on the IDE and is rather obvious.

For convenience, I have included a [Docker Compose file](compose.yml) which can be used on a machine without the necessary technical stack installed - only Docker (Docker Compose) is required. To run, simply issue the following command in the root directory of the project:

```docker-compose up```

After that you can access the Swagger UI on this URL - http://localhost:8080/swagger-ui/index.html#/


## Technology Stack

The project is written in Kotlin using the Spring Boot framework and built with Gradle. PostgereSQL is used as the database. As requested in the assignment, the application and the database are deployed on Azure App Services and Azure Database for PostgreSQL.

- Kotlin 1.9.25
- Spring Boot 3.4.1
- Postgres 14 
