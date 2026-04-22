# College Circular Mailing System

A Spring Boot application for managing students and sending circular emails to a college mailing list. The system stores student and circular data in MongoDB, sends email through Gmail SMTP, and serves a simple web dashboard from Spring Boot static resources.

## Features

- Add, list, and delete students
- Send circular emails to all students or filter recipients by department and semester
- Attach a file to a circular when needed
- View circular history
- Clear circular history
- Static web UI served directly by Spring Boot

## Tech Stack

- Java 17
- Spring Boot 3.2.3
- Maven
- Spring Web
- Spring Data MongoDB
- Spring Mail
- Lombok
- MongoDB
- Gmail SMTP

## Project Structure

- `src/main/java/com/college/mailsystem/MailsystemApplication.java` - application entry point
- `src/main/java/com/college/mailsystem/controller/AdminController.java` - REST API endpoints
- `src/main/java/com/college/mailsystem/service/` - student filtering and email delivery logic
- `src/main/java/com/college/mailsystem/model/` - MongoDB entities
- `src/main/java/com/college/mailsystem/repository/` - Spring Data repositories
- `src/main/resources/static/` - frontend HTML, CSS, and JavaScript
- `src/main/resources/application.properties` - local configuration

## Prerequisites

Before running the application, make sure you have:

- Java 17 installed
- Maven installed
- MongoDB running on `localhost:27017`
- A valid Gmail SMTP account or app password configured for mail delivery

## Configuration

The application reads its local configuration from `src/main/resources/application.properties`.

Update the following values for your environment:

- MongoDB host, port, and database name
- Gmail SMTP username and password

> Note: Do not commit real credentials to a shared repository. Use local or environment-specific configuration for secrets.

## Run Locally

Start the application with Maven:

```bash
mvn spring-boot:run
```

Then open the web UI in your browser.

## Build

Package the application with Maven:

```bash
mvn clean package
```

To run the packaged JAR:

```bash
java -jar target/mailsystem-0.0.1-SNAPSHOT.jar
```

## API Overview

All REST endpoints are available under `/api/admin`.

### Students

- `POST /api/admin/students` - add a student
- `GET /api/admin/students` - list all students
- `DELETE /api/admin/students/{id}` - remove a student

### Circulars

- `POST /api/admin/send-circular` - send a circular with optional attachment
- `GET /api/admin/history` - list circular history
- `DELETE /api/admin/history` - clear circular history

## Frontend

The user interface is served from `src/main/resources/static/index.html` and uses `src/main/resources/static/app.js` to call the backend API.

## Notes

- The application currently exposes its admin API without authentication.
- Email sending depends on valid SMTP configuration.
- Attachment uploads are optional and handled through the circular form.

