# Spring Boot Players Microservice

## Project Description

This Spring Boot microservice is crafted to demonstrate the efficient management of player data via RESTful APIs, directly reflecting best practices in scalable web application development. Initially created to expose two primary endpoints based on `player.csv` data:

- **GET /api/players**: Retrieves a list of all players.
- **GET /api/players/{playerID}**: Fetches details of a single player by their unique ID.

The project ambitiously extends beyond these functionalities to explore thorough testing, nuanced handling of edge cases, performance optimizations, and strategic deployment, aiming to cultivate a scalable and robust service.

## Current Project Structure

The application is organized into the following structure:

- **`src/main/java/com/example/playersapp`**:
    - **`modules/player`**:
        - **`controller`**: Manages incoming API requests with mappings to service operations.
        - **`service`**: Encapsulates the core business logic, bridging controllers with repositories.
        - **`repository`**: Data access layer leveraging Spring Data JPA for database interactions.
        - **`model`** & **`repository/entity`**: Comprises domain models and JPA entities for ORM.
        - **`validations`**: Implements custom business validation logic to ensure data integrity.
    - **`utils/csv`**: Utilities dedicated to parsing and importing CSV data.
    - **`utils/health`**: Monitors application health and readiness.
- **`src/main/resources`**: Hosts application configurations and static assets.

## Features Implemented

### RESTful API Endpoints

- **List All Players**: `GET /api/players` endpoint for retrieving a list of all players, with support for pagination and filtering to efficiently manage large datasets.
- **Get Player by ID**: `GET /api/players/{playerID}` endpoint for fetching detailed information about a specific player based on their unique identifier.
- **Handling various HTTP Status Codes** - 404 Not Found, 400 Bad Request, 500 Internal Server Error, etc.
- **Unified Responses For Two Endpoints** - Embed Interceptor concept to wrap the return objects in one component.

### Data Handling

- **CSV Importer**: Utility for parsing and importing player data from CSV files into the application's database, ensuring data is readily available for API responses.

### Error Handling and Validation

- **Global Exception Handling**: Centralized error handling mechanism using `@ControllerAdvice`, providing consistent error responses across the API.
- **Input Validation**: Leveraging `@Valid` and `@Validated` annotations along with Hibernate Validator for robust input validation across DTOs.
- **Business Logic Validation**: Implemented a dedicated class for cross-cutting validations, like playerID existence and throwing *404* Exception in case it does not.

### Database and ORM

- **H2 Database**: In-memory database for development purposes, facilitating quick testing and development cycles.
- **JPA & Hibernate**: Utilized for data persistence and querying, demonstrating best practices in leveraging Spring Data JPA and Hibernate for ORM.

### Testing

- **Unit Testing**: Focused tests for service layer logic using Mockito to mock dependencies.
- **Integration Testing**: Testing of repository interactions and endpoint integrations through `@DataJpaTest` and `@SpringBootTest`.
- **End-to-End Testing**: Comprehensive tests from the controller through to the in-memory database, validating the full application stack.

### Configuration

- **Custom Application Properties**: Configuration management using `application.properties`, including server port customization.

### Additional Utilities and Components

- **Common Module**: Shared utilities for error handling (`GlobalExceptionFilter`), application health checks (`ReadinessFilter`), and consistent response formatting (`ResponseInterceptor`).


## Missing Features and Future Improvements

### Fundamental Improvements
1. **RESTful Characteristic**
    - **HTTP Status Codes**: Incorporating HTTP status codes that enhance API communication precision, such as **201** Created for successful resource creation, **204** No Content for successful requests with no content to return (e.g., DELETE operations), and **409** Conflict indicates a request conflict with the current state of the target resource. Additionally, **401** Unauthorized and **403** Forbidden could be vital for handling authentication and authorization as you add secured endpoints, Lastly **429** Too Many Requests: Implement rate limiting to protect your API from abuse and ensure service reliability.
    - **API Versioning**: Implement API versioning (e.g., through URI path, custom headers) to manage changes in the API without disrupting existing clients.
    - **API Documentation**: Integrate Swagger/OpenAPI to generate dynamic, interactive API documentation, simplifying endpoint exploration and testing.

2. **Caching**
    - Implement strategic caching with Spring's caching abstraction, supported by Redis or EhCache, to reduce database strain and accelerate response times.

3. **Comprehensive Testing**
   - Expand testing across unit, integration, and end-to-end scopes using Mockito, @DataJpaTest, and @SpringBootTest to bolster application reliability.

4. **Database**
    - **Normalizing Database**: Adopting a relational database design with multiple tables and defined relationships will significantly enhance data integrity, query performance, and system scalability. This approach minimizes data redundancy, facilitates maintenance, and provides a flexible foundation for future growth and complexity in the web application's data model.
    - **Migrations**:Implement Liquibase for streamlined database schema evolution, ensuring consistency and automating migrations across environments.
    - **Spring Transaction Management**: Apply Spring's declarative transaction management to ensure atomic execution of database operations.

5. **Observability**
   - **Logging**: Implement structured logging with SLF4J and Logback. Consider using ELK Stack or Splunk for centralized logging.
   - **Tracing**: Utilize OpenTelemetry for distributed tracing, aiding in debugging and monitoring.
   - **Performance Monitoring**: Use Spring Boot Actuator for monitoring application health and metrics.
   
6. **Enhance Input Validations**
    - The Hibernate Validator provides a rich set of constraint annotations for various types of validation, such as @NotNull, @Past, @Future, @Pattern (for regex matching), @Positive, @Negative, @NotEmpty, @NotNull, and many others.

7. **Error Handling**
   - Develop a unified error handling framework with @ControllerAdvice, standardizing error responses for improved API usability.
   
8. **Containerization And CI/CD Piepline**
    - **Containerization**: Improve Dockerize process of the application to streamline deployments and maintain consistency across development, testing, and production environments.
    - **CI/CD**: Establish a CI/CD workflow with Gitlab or Jenkins or GitHub Actions for automated build, test, and deployment cycles, ensuring continuous delivery and high code quality.

9. **Message Queuing for Background Processing**
   - Leverage message queuing (e.g., RabbitMQ, Kafka) for time-consuming operations like CSV processing, offloading them from the main request-processing thread.

10. **Security**
    - **Spring Security**: Integrate Spring Security to manage authentication and authorization, utilizing JWT for stateless authentication.
    - **HTTPS Support**: Ensure application support for HTTPS to secure data in transit.
    - **API Rate Limiting**: Implement rate limiting to protect the API from abuse and ensure fair usage.
    - **Cross-Origin Resource Sharing (CORS)**: Properly configure CORS to allow cross-origin requests from trusted domains.

## How to run the application locally

- docker build -t player-app .
- docker run --publish 8080:12333 player-app

## Conclusion

Players Microservice, powered by Spring Boot is the foundation for a complex web application. This project is not only about fulfilling the basic requirements but also about laying a robust foundation for advanced features. I'm dedicated to crafting this project into an exemplar of modern development practices, prioritizing scalability, ease of maintenance, and readiness for deployment. The README file elaborates on future enhancements and what will be incorporated to make this microservice production-ready.
