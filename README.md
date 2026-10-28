# Rugendo Online Bus Ticket System — Backend

## Project Information

- **Project Name:** RUGENDO
- **Project Developer:** Rwagapfizi Igor, 27329
- **Project Description:** The project is an online Booking system for tickets that will be usable and accessible by customers who want to book tickets for bus trips online.

## System Description

The Rugendo Online Bus Ticket System backend is a REST API built with Spring Boot, Spring Data JPA, and PostgreSQL. It manages routes, buses, drivers, passengers, bookings, and tickets.

The API supports CRUD operations, derived database queries, JPQL queries, booking business rules, ticket management, booking reports, bulk updates, a dashboard summary, and paginated booking results.

## Technologies Used

- Java
- Spring Boot
- Spring Web
- Spring Data JPA
- PostgreSQL
- Maven
- Postman

## Prerequisites

Before running the application, install:

- Java version compatible with the project
- PostgreSQL
- Maven, or use the included Maven Wrapper if available
- Postman (for API testing)

## Database Setup

1. Start PostgreSQL.
2. Create a database for the project, for example:

   ```sql
   CREATE DATABASE rugendo_db;
   ```

3. Open `src/main/resources/application.properties`.
4. Configure the database connection using your local PostgreSQL database name, username, and password.

Example configuration:

```properties
spring.datasource.url=${DB_URL:jdbc:postgresql://localhost:5432/rugendo_db}
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.open-in-view=true
```

Set `DB_USERNAME` and `DB_PASSWORD` in your environment, or configure your local credentials securely. Do not commit real database passwords to GitHub.

## How to Run the API

1. Clone the repository:

   ```bash
   git clone https://github.com/Rwagapfizi/rugendo-online-bus-ticket-system-backend.git
   ```

2. Enter the project directory:

   ```bash
   cd rugendo-online-bus-ticket-system-backend
   ```

3. Configure PostgreSQL as described above.
4. Run the application using Maven:

   ```bash
   mvn spring-boot:run
   ```

   Alternatively, on Windows, if the Maven Wrapper is included:

   ```bash
   .\mvnw.cmd spring-boot:run
   ```

   On Linux or macOS, use:

   ```bash
   ./mvnw spring-boot:run
   ```

5. When the application starts successfully, the API is available at:

   `http://localhost:8080`

## API Testing with Postman

Import the following collection into Postman:

`rugendo-online-bus-ticket-system-midterm.postman_collection.json`

Run the requests to test the implemented requirements. Requests are named using their assignment requirement IDs, such as B1, C4, and D2.

For requests that require existing records, use valid IDs and data from the database. Run prerequisite requests first where necessary—for example, create a booking before testing booking operations, and use a PAID booking when creating a ticket.

## Main Features

- Routes, buses, drivers, passengers, and booking management
- Search and filter operations using Spring Data derived queries
- JPQL queries involving joins and aggregate functions
- Booking date and status validation
- Driver route and bus assignment
- Ticket creation, history, and deletion
- Booking counts grouped by status
- Bulk cancellation of eligible bookings for a selected date
- Dashboard summary
- Paginated and sorted booking results

## Repository Contents

- `src/` — application source code
- `pom.xml` — Maven dependencies and build configuration
- `src/main/resources/application.properties` — application configuration
- `rugendo-online-bus-ticket-system-midterm.postman_collection.json` — Postman API test collection

## Notes

- Start PostgreSQL before starting the API.
- Ensure the configured database credentials are correct.
- Use the correct request parameters and valid database IDs when testing.
- Do not commit passwords, API secrets, or other sensitive credentials.