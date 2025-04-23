# NASA API Implementation 🚀

![NASA API Banner](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/src/main/resources/static/images/nasa-banner.jpg)

## Introduction
Welcome to the NASA API Implementation project! This full-stack web application utilizes NASA's public APIs to provide users with captivating Astronomy Picture Of The Day (APOD) and Mars Rover Photos. The project seamlessly integrates various technologies to deliver a robust and user-friendly experience.

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Spring Framework](https://img.shields.io/badge/Spring%20Framework-6.0-green.svg)](https://spring.io/)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-orange.svg)](https://www.thymeleaf.org/)
[![Spring Security](https://img.shields.io/badge/Spring%20Security-6.0-red.svg)](https://spring.io/projects/spring-security)
[![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-lightblue.svg)](https://www.openapis.org/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

## Features
- **Astronomy Picture Of The Day (APOD)** - View stunning daily astronomy images with detailed explanations
- **Mars Rover Photos** - Explore photos from NASA's Mars rovers with filtering options:
  - Three different rovers: Curiosity, Opportunity, Spirit
  - Multiple camera options (FHAZ, RHAZ, MAST, etc.)
  - Filter by Earth date or Martian sol
- **Admin Dashboard** - Manage APOD entries with full CRUD functionality
- **Secure Authentication** - Role-based access control with JWT authentication
- **RESTful API** - Well-documented endpoints with Swagger UI

## 🔭 Screenshots

<details>
<summary>View Application Screenshots</summary>

### Home Page
![Home Page](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/home.png)

### APOD Display
![APOD](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/apod.png)

### Mars Rover Photos
![Mars Rover](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/rover.png)

### Admin Interface
![Admin](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/admin.png)

</details>

## 🛠️ Technology Stack

### Backend
- **Spring Boot 3** - Application framework
- **Spring Framework 6** - Core support for dependency injection, MVC, etc.
- **Spring Security** - Authentication and authorization
- **Spring Data JPA** - Data persistence
- **MySQL** - Database
- **JWT** - JSON Web Token for secure API access
- **OpenAPI 3.0** - API documentation

### Frontend
- **Thymeleaf** - Server-side Java template engine
- **HTML5 / CSS3** - Structure and styling
- **Bootstrap** - Responsive design
- **JavaScript** - Client-side functionality

### Tools
- **Maven** - Dependency management
- **JUnit 5** - Testing framework
- **Mockito** - Mocking framework for testing
- **Docker** - Containerization
- **GitHub Actions** - CI/CD

## 🏗️ Architecture Overview

```mermaid
graph TB
    User(User Browser) --> FE(Frontend - Thymeleaf Templates)
    FE --> SC(Spring Controllers)
    SC --> SS(Spring Services)
    SS --> NASA(NASA Public APIs)
    SS --> DR(Data Repositories)
    DR --> DB[(MySQL Database)]
    
    subgraph "Security Layer"
    SEC(Spring Security)
    JWT(JWT Provider)
    end
    
    User --> SEC
    SEC --> JWT
    JWT --> SC
