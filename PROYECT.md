# Javematch - Tu Futuro Ideal está a un Match de Distancia

## Introduction

**Javematch** is a social platform designed to connect students at Universidad Javeriana. It aims to facilitate exciting connections through a system of interest-based matching and video calls. The platform prioritizes a safe environment with features like real-time moderation and user reporting/blocking.  This project is developed by JavEngineering.

The backend is built using **Spring Boot**, with a **MySQL** database managed via **Docker Compose**. Continuous integration and deployment to **Azure App Service** are handled using **GitHub Actions**.

---
## Architecture

Javematch follows a layered architecture typical of Spring Boot applications:

* **Model**: Defines the data structures (entities) of the application. Key entities include `Usuario` (User), `Interes` (Interest), `Amistad` (Friendship), `UserMatch`, `Videollamada` (Video Call), `Notificacion` (Notification), `Plan`, and `Juego` (Game). These are mapped to database tables using JPA.
    * Example: `gajudama.javematch.model.Amistad.java`, `gajudama.javematch.model.Interes.java`
* **Data Access Layer (`accesoDatos`)**: Handles database interactions using Spring Data JPA repositories.
    * Example: `gajudama.javematch.accesoDatos.AmistadRepository.java`, `gajudama.javematch.accesoDatos.InteresRepository.java`
* **Logic Layer (`logic`)**: Contains the business logic of the application. Services in this layer coordinate data access and implement core functionalities.
    * Example: `gajudama.javematch.logic.AmistadLogic.java`, `gajudama.javematch.logic.InteresLogic.java`
* **Web Layer (`web`)**: Exposes the application's functionality via a RESTful API using Spring MVC controllers.
    * Example: `gajudama.javematch.web.AmistadController.java`, `gajudama.javematch.web.InteresController.java`
* **Main Application**: `gajudama.javematch.JavematchApplication.java` is the entry point for the Spring Boot application.

The project structure is organized into these respective packages as shown in the provided screenshot.

**Database**: MySQL (intended for production, managed by Docker Compose locally). The provided configuration also shows an H2 in-memory database setup, likely for testing or development profiles.
**Deployment**: Azure App Service via GitHub Actions.

---
## Features

Javematch offers the following key features:

* **User Registration and Login**: Secure system for users to create profiles and access the platform.
* **Profile Personalization**: Users can customize their profiles.
* **Interest-Based Matching**: Connects users based on shared interests.
* **"Tinder-like" Swiping**: Users can like or reject other profiles to find matches.
* **Mutual Match System**: Identifies users who have mutually liked each other ("Tus matches").
* **Friendship Management**: Users can confirm friendships after a match.
* **Video Calls**: Enables matched users to connect via video calls.
* **In-Call Games**: Variety of options including online games within video calls.
* **Notifications**: In-app notifications for new interactions and matches.
* **Safety Features**:
    * Verification of users as Pontificia Universidad Javeriana students.
    * Real-time moderation.
    * Blocking and reporting of problematic users.
* **User Plans**: Different subscription plans for users (e.g., Bronze, Silver, Gold).

---
## Installation

To set up and run the Javematch project locally:

1.  **Prerequisites**:
    * Java Development Kit (JDK) (version compatible with the Spring Boot version used, typically 17+ for recent Spring Boot versions).
    * Apache Maven (for building the project).
    * Docker and Docker Compose (for running the MySQL database).
    * Git (for cloning the repository).
2.  **Clone the Repository**:
    ```bash
    # Replace with your actual repository URL
    git clone [https://github.com/yourusername/javematch.git](https://github.com/yourusername/javematch.git)
    cd javematch
    ```
3.  **Start the Database with Docker Compose**:
    * Ensure you have a `docker-compose.yml` file configured for MySQL.
    * From the project root directory, run:
        ```bash
        docker-compose up -d
        ```
    This will start the MySQL database container in detached mode.
4.  **Configure Application Properties**:
    * Modify `src/main/resources/application.properties` (or `application.yml`) to point to your local MySQL database if it's not already configured to do so by default (the provided snippet shows H2 and commented-out MySQL properties). Ensure the datasource URL, username, and password match your Docker Compose setup.
5.  **Build and Run the Spring Boot Application**:
    * Using Maven Wrapper:
        ```bash
        ./mvnw spring-boot:run
        ```
    * Or, if you have Maven installed globally:
        ```bash
        mvn spring-boot:run
        ```
    The application should start, connect to the database, and be accessible, typically at `http://localhost:8080`.

---
## Usage

Once the application is running locally or accessed via its Azure deployment:

* **Accessing the Web App**: Open your web browser and navigate to `http://localhost:8080` (for local) or the Azure App Service URL.
* **User Interaction**:
    * New users can register for an account.
    * Registered users can log in.
    * Users can view and edit their profiles, add interests, and manage their subscription plan.
    * The main interface allows users to browse other profiles ("Conocer gente"), like or pass on them. [cite: 26]
    * Matched users can be found in the "Tus matches" section. [cite: 26]
    * From a match, users can initiate video calls and potentially play games within the call. [cite: 25]
* **API Endpoints**: The application exposes RESTful APIs for various functionalities. Key base paths include:
    * `/api/amistad` for friendship-related operations.
    * `/api/interes` for interest-related operations.
    * (Other controllers for Usuario, UserMatch, Videollamada, etc., would follow a similar pattern, e.g., `/api/usuario`, `/api/match`).

---
## Configuration

Key configuration settings are managed in `src/main/resources/application.properties` (or `application.yml`).

* **Application Name**:
    ```properties
    spring.application.name=javematch
    ```
* **Database Connection (MySQL - example, often externalized or profile-specific)**:
    ```properties
    #spring.datasource.url=jdbc:mysql://localhost:3306/JaveMatch?serverTimezone=UTC
    #spring.datasource.username=JaveMatch
    #spring.datasource.password=secret
    #spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
    ```
* **Database Connection (H2 - for development/testing)**:
    ```properties
    spring.datasource.url=jdbc:h2:mem:testdb
    spring.datasource.driverClassName=org.h2.Driver
    spring.datasource.username=sa
    spring.datasource.password=password
    spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
    ```
* **JPA & Hibernate**:
    ```properties
    spring.jpa.show-sql=true # Show SQL queries in logs
    spring.jpa.hibernate.ddl-auto=create-drop # Database schema management strategy (use 'update' or 'validate' for production)
    spring.jpa.defer-datasource-initialization=true # Defer data.sql execution until after Hibernate schema generation
    ```
* **SQL Initialization**:
    ```properties
    spring.sql.init.mode=always # Always run schema.sql and data.sql
    spring.sql.init.data-locations=classpath:data.sql # Location of data initialization script
    ```
* **Logging Levels**:
    ```properties
    logging.level.org.springframework.web=DEBUG
    logging.level.gajudama.javematch=DEBUG
    logging.level.org.hibernate.SQL=DEBUG
    ```
The `data.sql` file is used to insert initial data for plans, interests, users, and their relationships.

**GitHub Actions for Azure Deployment**:
Secrets for Azure deployment (e.g., Azure credentials, database connection strings for Azure) are typically configured as secrets in the GitHub repository settings and used by the GitHub Actions workflow.

---
## Dependencies

The project relies on several Spring Boot starters and other libraries, managed via Maven. Key dependencies include:

* **Spring Boot Starters**:
    * `spring-boot-starter-web`: For building web applications, including RESTful APIs with Spring MVC.
    * `spring-boot-starter-data-jpa`: For data access using JPA with Hibernate.
    * `spring-boot-starter-test`: For testing.
* **Database Driver**:
    * `mysql-connector-java` (or `mysql-connector-j` for newer versions): For connecting to MySQL.
    * `h2database`: For the in-memory H2 database.
* **Lombok**: To reduce boilerplate code (e.g., getters, setters, constructors for model classes via annotations like `@Data`).
* **(Potentially)** Spring Boot Starter for Security, WebSockets (for real-time features like notifications/video calls), OAuth2, etc., depending on the full feature set.

Refer to the `pom.xml` file for the complete list of dependencies and their versions.

---
## Examples

### API Usage Example (Amistad - Friendship)

* **Create a new friendship record**:
    `POST /api/amistad`
    Request Body:
    ```json
    {
        "usuarioAmistad": { "user_id": 1 },
        "amigoUsuario": { "user_id": 2 }
    }
    ```
* **Confirm a friendship from a match**:
    `POST /api/amistad/confirmFriendship?matchId=123&isFriend=true`
* **Get all friendships**:
    `GET /api/amistad`
* **Get friendship by ID**:
    `GET /api/amistad/1`

### Model Example (`Interes.java`)

```java
package gajudama.javematch.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "interesId")
public class Interes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long interesId;
    private String nombre;

    @ManyToMany(mappedBy = "intereses")
    @JsonIgnore
    private List<Usuario> usuarios;

    @Override
    public String toString() {
        return "Interes{" +
                "interesId=" + interesId +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}
```


---
## Troubleshooting

* **Database Connection Issues (Local MySQL)**:
    * Ensure the MySQL container is running: `docker ps`.
    * Verify that the port mapping in `docker-compose.yml` matches the `spring.datasource.url` in `application.properties`.
    * Check Docker container logs for MySQL startup errors: `docker logs <mysql_container_name>`.
    * Ensure username and password in `application.properties` match the MySQL credentials.
* **`ddl-auto=create-drop`**: Be aware that this setting drops and recreates the database schema on every application startup. This is useful for development but should be changed to `update`, `validate`, or be managed by migration tools (like Flyway or Liquibase) for production.
* **GitHub Actions Failures**: Check the logs for the specific GitHub Actions workflow run in the "Actions" tab of your GitHub repository. Common issues include incorrect Azure credentials, build failures, or test failures.
* **H2 Console**: If using H2, you might need to configure access to its web console in `application.properties` (e.g., `spring.h2.console.enabled=true`, `spring.h2.console.path=/h2-console`).

---
## Contributors

* Maria Paula Rodríguez Ruiz
* Daniel Felipe Castro Moreno
* Juan Enrique Rozo Tarache
* Gabriel Anibal Riaño

(Pontificia Universidad Javeriana – Department of Systems Engineering)

---
## License

This project is developed for educational purposes as part of the Fundamentals of Software Engineering course. No commercial license is granted.
