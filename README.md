# Calculator DevOps

**CSE 816 — Software Production Engineering Mini-Project**

An end-to-end CI/CD project that builds, tests, containerizes, and deploys a Calculator REST API using Java 21, Spring Boot, Maven, Jenkins, Docker, Docker Compose, Nginx, and GitHub.

## Application Overview

The application provides REST endpoints for addition, subtraction, multiplication, and division. It supports input validation, structured error responses, and application health monitoring through Spring Boot Actuator.

## API Endpoints

| Operation | Endpoint |
|---|---|
| Add | `GET /api/v1/add?a=2&b=3` |
| Subtract | `GET /api/v1/subtract?a=5&b=3` |
| Multiply | `GET /api/v1/multiply?a=4&b=3` |
| Divide | `GET /api/v1/divide?a=6&b=3` |
| Health | `GET /actuator/health` |

Successful requests return JSON containing the operation, input values, and result. Invalid or missing parameters and division by zero return HTTP `400 Bad Request` with a structured JSON error.

## Build and Test

The project includes the Maven Wrapper.

```bash
./mvnw clean package
./mvnw test
```

Tests include JUnit 5 unit tests and Spring MVC integration tests using MockMvc. JUnit XML reports are generated in `target/surefire-reports/` and published by Jenkins.

## Run Locally

```bash
./mvnw spring-boot:run
```

The application runs on port `8081` by default.

Example request:

```bash
curl "http://localhost:8081/api/v1/add?a=2&b=3"
curl "http://localhost:8081/actuator/health"
```

## Docker

Build and run the application:

```bash
docker build -t calculator:dev .
docker run --rm -p 8083:8080 -e SERVER_PORT=8080 calculator:dev
```

The multi-stage Dockerfile uses Maven and JDK 21 for building, and a Java 21 JRE for runtime. Only the generated JAR is copied into the runtime image, where the application runs as a non-root user.

## Docker Compose and Nginx

Docker Compose runs two services:

- **Calculator:** Spring Boot application on internal port `8080`.
- **Nginx:** Reverse proxy exposed on host port `8082`.

Start and check the services:

```bash
docker compose up -d
docker compose ps
```

Test the API through Nginx:

```bash
curl "http://localhost:8082/api/v1/add?a=2&b=3"
curl "http://localhost:8082/actuator/health"
```

Stop the services:

```bash
docker compose down
```

The Compose configuration includes a bridge network, restart policies, and an Actuator-based healthcheck for the calculator service.

## CI/CD Pipeline

The Jenkins pipeline is defined in the `Jenkinsfile`:

**Checkout → Build → Test → Docker Build → Docker Push → Deploy → Health Check**

The pipeline:
1. Checks out the source code from GitHub.
2. Builds the application using Maven.
3. Runs tests and publishes JUnit reports.
4. Builds a Docker image tagged with the Jenkins build number.
5. Pushes the image to Docker Hub.
6. Deploys the build-specific image using Docker Compose.
7. Verifies application health and sends success or failure email notifications.

If tests fail, subsequent Docker build, push, and deployment stages are skipped.

### Jenkins Trigger

A GitHub webhook triggers Jenkins when changes are pushed to the repository. The Jenkins job builds the `main` branch.

### Docker Hub Image

Images are published as:

```text
vishnu933/calculator-devops:<build-number>
vishnu933/calculator-devops:latest
```

The build-number tag is used for deployment to identify the exact image associated with each Jenkins build.

## Project Structure

```text
calculator-devops/
├── .mvn/
├── src/
│   ├── main/
│   └── test/
├── nginx/
│   └── nginx.conf
├── .dockerignore
├── .gitignore
├── Dockerfile
├── docker-compose.yml
├── Jenkinsfile
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```