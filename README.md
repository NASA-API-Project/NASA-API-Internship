# NASA API Implementation 🚀

<div align="center">
  <img src="https://raw.githubusercontent.com/username/NASA-API-Implementation/main/src/main/resources/static/images/nasa-banner.jpg" alt="NASA API Banner" width="800"/>
  
  <p>A comprehensive full-stack application integrating NASA's public APIs</p>
  
  [![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-brightgreen.svg)](https://spring.io/projects/spring-boot)
  [![Spring Framework](https://img.shields.io/badge/Spring%20Framework-6.0-green.svg)](https://spring.io/)
  [![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg)](https://www.mysql.com/)
  [![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1-orange.svg)](https://www.thymeleaf.org/)
  [![Spring Security](https://img.shields.io/badge/Spring%20Security-6.0-red.svg)](https://spring.io/projects/spring-security)
  [![OpenAPI](https://img.shields.io/badge/OpenAPI-3.0-lightblue.svg)](https://www.openapis.org/)
  [![JUnit](https://img.shields.io/badge/JUnit-5.9-yellow.svg)](https://junit.org/junit5/)
  [![Docker](https://img.shields.io/badge/Docker-Support-blue.svg)](https://www.docker.com/)
  [![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
</div>

---

## Table of Contents

- [Introduction](#introduction)
- [Key Features](#key-features)
- [Screenshots](#screenshots)
- [Technology Stack](#technology-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Installation & Setup](#installation--setup)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Security Implementation](#security-implementation)
- [Testing Strategy](#testing-strategy)
- [Performance Optimizations](#performance-optimizations)
- [Deployment Guidelines](#deployment-guidelines)
- [Contributing](#contributing)
- [Troubleshooting](#troubleshooting)
- [License](#license)
- [Acknowledgements](#acknowledgements)

---

## Introduction

The NASA API Implementation project is a sophisticated full-stack web application that serves as a comprehensive interface to NASA's public data services. This project showcases advanced integration techniques, security implementation, and modern web development practices.

The application provides users with access to two primary NASA data sources:

1. **Astronomy Picture of the Day (APOD)** - A daily-updated image or photograph of our universe with detailed explanations written by professional astronomers.

2. **Mars Rover Photos** - An extensive collection of images captured by NASA's Mars rovers - Curiosity, Opportunity, and Spirit, with advanced filtering capabilities.

Beyond simple API integration, this application demonstrates:
- Robust authentication and authorization mechanisms
- Comprehensive data caching and persistence
- Responsive user interface design
- RESTful API implementation with thorough documentation
- Administrative capabilities for content management

This project is ideal for educational purposes, showcasing how to build production-ready applications that integrate with third-party APIs.

---

## Key Features

### APOD (Astronomy Picture of the Day)
- **Daily Updates**: Automatic fetching of the latest astronomy picture
- **Historical Access**: Browse APOD entries from any past date
- **Detailed Information**: View comprehensive descriptions written by astronomers
- **HD Image Support**: Toggle between standard and high-definition images
- **Favorites**: Save favorite APOD entries to the database (admin feature)
- **Content Management**: Admin interface for managing saved APOD entries

### Mars Rover Photos
- **Multiple Rover Support**: 
  - **Curiosity**: Active since 2012, equipped with 9 cameras
  - **Opportunity**: Mission from 2004-2018, equipped with 9 cameras
  - **Spirit**: Mission from 2004-2010, equipped with 9 cameras

- **Advanced Filtering Options**:
  - By rover type
  - By camera type:
    - FHAZ (Front Hazard Avoidance Camera)
    - RHAZ (Rear Hazard Avoidance Camera)
    - MAST (Mast Camera)
    - CHEMCAM (Chemistry and Camera Complex)
    - MAHLI (Mars Hand Lens Imager)
    - MARDI (Mars Descent Imager)
    - NAVCAM (Navigation Camera)
    - PANCAM (Panoramic Camera)
    - MINITES (Miniature Thermal Emission Spectrometer)
  - By Earth date (YYYY-MM-DD format)
  - By Martian sol (day on Mars)

- **Gallery View**: Responsive image gallery with loading optimization
- **Detail View**: Enlarged view with detailed photo information
- **Pagination**: Efficient handling of large result sets

### Security & User Management
- **Role-Based Access Control**: Different capabilities for regular users vs. administrators
- **JWT Authentication**: Secure API access via JSON Web Tokens
- **Protected Routes**: Admin-only sections for content management
- **Custom Login**: Personalized login experience
- **Remember Me**: Extended session functionality

### Administrative Features
- **APOD Management**: CRUD operations for astronomy pictures
- **User Management**: Admin interface for managing user accounts
- **Content Moderation**: Review and manage saved content
- **System Monitoring**: Basic metrics on system usage

### API Features
- **RESTful Endpoints**: Well-designed API following REST principles
- **Swagger Documentation**: Interactive API documentation
- **Error Handling**: Comprehensive error responses
- **Rate Limiting**: Basic protection against abuse
- **Versioning**: Support for API versioning

---

## Screenshots

<details>
<summary><strong>Click to view application screenshots</strong></summary>

### Home Page
![Home Page](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/home.png)
*The main landing page featuring Mars imagery and navigation options.*

### Astronomy Picture of the Day
![APOD Page](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/apod.png)
*APOD display showing Epsilon Tauri with detailed astronomical information.*

### Mars Rover Photo Selection
![Mars Rover Selection](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/rover.png)
*Interface for selecting Mars Rover photos with multiple filtering options.*

### Admin Dashboard
![Admin Dashboard](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/admin.png)
*Administrative interface showing the APOD directory with management options.*

### Login Screen
![Login Screen](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/login.png)
*Secure authentication screen with Mars background.*

### Swagger API Documentation
![Swagger Documentation](https://raw.githubusercontent.com/username/NASA-API-Implementation/main/screenshots/swagger.png)
*Interactive API documentation using Swagger UI.*

</details>

---

## Technology Stack

### Backend Framework
- **Spring Boot 3.0**: Application framework providing configuration, web server, dependency injection
- **Spring Framework 6.0**: Core Spring functionality
  - **Spring MVC**: Web framework for building the application's controllers
  - **Spring Data JPA**: Data persistence and repository abstraction
  - **Spring Security**: Authentication and authorization framework

### Database & Persistence
- **MySQL 8.0**: Primary relational database
- **Hibernate ORM**: Object-relational mapping implementation
- **Hikari CP**: High-performance JDBC connection pool

### Frontend Technologies
- **Thymeleaf 3.1**: Server-side Java template engine
- **HTML5/CSS3**: Standard web technologies
- **Bootstrap 5**: Responsive design framework
- **JavaScript**: Client-side scripting
- **jQuery**: JavaScript library for DOM manipulation (minimal usage)

### Security Implementation
- **Spring Security**: Framework for authentication and authorization
- **JWT (JSON Web Tokens)**: For stateless authentication
- **OAuth2 Resource Server**: For JWT validation
- **BCrypt**: Password hashing algorithm

### API Integration & Documentation
- **RestTemplate**: HTTP client for consuming NASA APIs
- **Jackson**: JSON serialization/deserialization
- **OpenAPI 3.0**: API specification format
- **Swagger UI**: Interactive API documentation
- **Springdoc**: OpenAPI integration for Spring Boot

### Testing Frameworks
- **JUnit 5**: Unit testing framework
- **Mockito**: Mocking framework for unit tests
- **Spring Test**: Integration testing support
- **H2 Database**: In-memory database for testing
- **Testcontainers**: Integration testing with Docker containers

### Build & Deployment
- **Maven**: Dependency management and build tool
- **Docker**: Containerization support
- **GitHub Actions**: CI/CD pipeline integration
- **AWS Elastic Beanstalk**: Deployment platform (optional)

### Developer Tools
- **Project Lombok**: Reducing boilerplate code
- **Dev Tools**: Spring Boot development utilities
- **Actuator**: Production monitoring and management

---

## Architecture

The NASA API Implementation follows a layered architecture with clear separation of concerns, ensuring maintainability and testability.

### High-Level Architecture Diagram

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
