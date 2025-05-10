# NASA API Implementation - Setup Guide

This document provides comprehensive instructions for setting up and running the NASA API Implementation project.

## Table of Contents

1. [Prerequisites](#prerequisites)
2. [Project Overview](#project-overview)
3. [Local Development Setup](#local-development-setup)
   - [Clone the Repository](#clone-the-repository)
   - [Configure Database](#configure-database)
   - [Configure NASA API Key](#configure-nasa-api-key)
   - [Building the Project](#building-the-project)
   - [Running the Application](#running-the-application)
4. [Testing](#testing)
5. [Accessing the Application](#accessing-the-application)
6. [Docker Deployment](#docker-deployment)
7. [AWS Deployment](#aws-deployment)
8. [Troubleshooting](#troubleshooting)

## Prerequisites

Before setting up the project, ensure you have the following installed:

- Java Development Kit (JDK) 17 or newer
- Maven 3.8+ for dependency management and building
- MySQL 8.0 or newer for the database
- Git for version control
- A NASA API key (obtainable from [https://api.nasa.gov/](https://api.nasa.gov/))
- Docker (optional, for containerization)
- AWS CLI (optional, for AWS deployment)

## Project Overview

The NASA API Implementation is a Spring Boot application that provides access to NASA's Astronomy Picture of the Day (APOD) and Mars Rover photos. It includes:

- Web interface built with Thymeleaf, Bootstrap, and HTML5
- RESTful API endpoints for programmatic access
- Database storage for APOD entries
- JWT-based security with role-based access control
- Swagger/OpenAPI documentation

## Local Development Setup

### Clone the Repository

```bash
git clone https://github.com/your-username/nasa-api-implementation.git
cd nasa-api-implementation
```

### Configure Database

1. Create a MySQL database:

```sql
CREATE DATABASE nasa_api;
CREATE USER 'nasauser'@'localhost' IDENTIFIED BY 'nasapassword';
GRANT ALL PRIVILEGES ON nasa_api.* TO 'nasauser'@'localhost';
FLUSH PRIVILEGES;
```

2. Set up the authentication tables:

```sql
USE nasa_api;

CREATE TABLE nasa_members (
    user_id VARCHAR(50) PRIMARY KEY,
    pw VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL
);

CREATE TABLE nasa_roles (
    user_id VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL,
    PRIMARY KEY (user_id, role),
    FOREIGN KEY (user_id) REFERENCES nasa_members(user_id)
);

-- Insert default users
-- Note: In a production environment, use encrypted passwords
INSERT INTO nasa_members (user_id, pw, active) VALUES 
('employee', '{noop}employee123', true),
('admin', '{noop}admin123', true);

INSERT INTO nasa_roles (user_id, role) VALUES 
('employee', 'ROLE_EMPLOYEE'),
('admin', 'ROLE_EMPLOYEE'),
('admin', 'ROLE_ADMIN');
```

3. Create the APOD table:

```sql
CREATE TABLE apod (
    id INT AUTO_INCREMENT PRIMARY KEY,
    copyright VARCHAR(255),
    date VARCHAR(255) NOT NULL,
    explanation TEXT NOT NULL,
    hdurl VARCHAR(255),
    title VARCHAR(255) NOT NULL,
    url VARCHAR(255) NOT NULL
);
```

### Configure NASA API Key

1. Get your NASA API key from [https://api.nasa.gov/](https://api.nasa.gov/)

2. Create or edit `src/main/resources/application.properties`:

```properties
# Database configuration
spring.datasource.url=jdbc:mysql://localhost:3306/nasa_api
spring.datasource.username=nasauser
spring.datasource.password=nasapassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA/Hibernate configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

# NASA API key
nasa.api.key=YOUR_NASA_API_KEY

# Server port
server.port=5000

# Log level
logging.level.org.springframework=INFO
logging.level.com.openapi.nasa=DEBUG
```

Replace `YOUR_NASA_API_KEY` with your actual NASA API key.

### Building the Project

```bash
mvn clean install
```

### Running the Application

```bash
mvn spring-boot:run
```

Or run the generated JAR file:

```bash
java -jar target/nasa-api-0.0.1-SNAPSHOT.jar
```

## Testing

To run the tests:

```bash
mvn test
```

## Accessing the Application

After starting the application, you can access:

- **Web Interface**: http://localhost:5000/nasa/home-page
- **Login Page**: http://localhost:5000/show-login-page
- **API Documentation**: http://localhost:5000/swaggerdoc.html

### Default Users

- **Employee User**:
  - Username: `employee`
  - Password: `employee123`
  - Access: View APOD and Mars Rover photos

- **Admin User**:
  - Username: `admin`
  - Password: `admin123`
  - Access: All features including APOD management

## Docker Deployment

1. Build the Docker image:

```bash
docker build -t nasa-api-implementation .
```

2. Run the container:

```bash
docker run -p 5000:5000 --name nasa-api \
  -e SPRING_DATASOURCE_URL=jdbc:mysql://host.docker.internal:3306/nasa_api \
  -e SPRING_DATASOURCE_USERNAME=nasauser \
  -e SPRING_DATASOURCE_PASSWORD=nasapassword \
  -e NASA_API_KEY=YOUR_NASA_API_KEY \
  nasa-api-implementation
```

## AWS Deployment

The application can be deployed to AWS Elastic Beanstalk:

1. Install the EB CLI:

```bash
pip install awsebcli
```

2. Configure EB CLI:

```bash
eb init
```

3. Create an environment:

```bash
eb create nasa-api-env
```

4. Configure environment variables:

```bash
eb setenv NASA_API_KEY=YOUR_NASA_API_KEY
```

5. Deploy the application:

```bash
eb deploy
```

## Troubleshooting

### Database Connection Issues

- Verify database credentials in `application.properties`
- Ensure the MySQL service is running
- Check if the database and tables exist

### Application Startup Failures

- Verify the JDK version (must be 17+)
- Check if all required dependencies are resolved
- Ensure the port 5000 is not in use by another application

### API Access Issues

- Verify that your NASA API key is valid
- Check network connectivity to api.nasa.gov
- Look for rate limiting issues in the API responses

### Authentication Problems

- Ensure the database contains the correct user credentials
- Check that the roles are properly assigned
- Verify security configuration in SecurityConfiguration.java

For more assistance, please file an issue in the project repository with detailed error logs and reproduction steps.