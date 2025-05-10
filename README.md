# NASA API Implementation

A full-stack web application that leverages NASA's open APIs to provide access to Astronomy Picture of the Day (APOD) and Mars Rover photos through a user-friendly interface and RESTful services.

![NASA API Implementation Banner](https://api.nasa.gov/assets/img/favicons/favicon-192.png)

## Overview

This project is an advanced Spring Boot application that demonstrates integration with NASA's public APIs while implementing modern web development practices including security, database persistence, and responsive UI design.

### Key Features

- **Astronomy Picture of the Day (APOD)**: View and manage NASA's daily astronomy images
- **Mars Rover Photos**: Browse photos from Mars rovers with filtering by rover, camera, and date
- **Dual Interface**: Both web UI (using Thymeleaf) and REST API access
- **Security**: JWT-based authentication with role-based access control
- **Database Storage**: Persistent storage of APOD entries
- **API Documentation**: Comprehensive OpenAPI/Swagger documentation
- **Exception Handling**: Robust error handling for a better user experience

## Technology Stack

### Backend
- **Spring Boot 3** and **Spring Framework 6**
- **Spring Security** with JWT and OAuth2 resource server
- **Spring Data JPA** for database operations
- **MySQL** database for persistence
- **OpenAPI/Swagger** for API documentation

### Frontend
- **Thymeleaf** for server-side templating
- **Bootstrap** for responsive UI design
- **HTML5** and **CSS3** for modern web standards

### Deployment
- **Docker** for containerization
- **AWS Elastic Beanstalk** for cloud hosting

## Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- MySQL 8.0+
- NASA API Key (get one at [https://api.nasa.gov/](https://api.nasa.gov/))

### Setup

1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/nasa-api-implementation.git
   cd nasa-api-implementation
   ```

2. Configure the database and API key in `application.properties`

3. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```

4. Access the application:
   - Web UI: [http://localhost:5000/nasa/home-page](http://localhost:5000/nasa/home-page)
   - Swagger Documentation: [http://localhost:5000/swaggerdoc.html](http://localhost:5000/swaggerdoc.html)

For detailed setup instructions, see the [Setup Guide](docs/SETUP.md).

## Screenshots

### Home Page
![Home Page](https://placeholder-for-home-page-screenshot.png)

### APOD Display
![APOD Display](https://placeholder-for-apod-screenshot.png)

### Mars Rover Photos
![Mars Rover Photos](https://placeholder-for-mars-rover-screenshot.png)

## API Endpoints

The application provides the following RESTful endpoints:

### APOD Endpoints
- `GET /api/apod`: Fetch the current Astronomy Picture of the Day
- `GET /api/apods`: Fetch all stored APOD entries
- `GET /api/apod/{id}`: Fetch a specific APOD by ID
- `PUT /api/apod/{id}`: Update an APOD entry (Admin role required)
- `DELETE /api/apod/{id}`: Delete an APOD entry (Admin role required)

### Mars Rover Endpoints
- `GET /api/rover/{roverName}/{earthDate}/{roverCamera}`: Fetch Mars Rover photos based on criteria

### Authentication Endpoints
- `POST /authenticate`: Generate a JWT token for API access

## Security

This application implements a comprehensive security model:

- **Form-based authentication** for web interface
- **JWT authentication** for REST API
- **Role-based access control**:
  - `EMPLOYEE` role: Can view APOD and Mars Rover photos
  - `ADMIN` role: Can additionally manage APOD entries (update, delete)
- **SecurityConfiguration** with URL-based security rules
- **Method-level security** with `@PreAuthorize` annotations

## Project Structure

The project follows a layered architecture pattern:

- **Controller Layer**: Handles incoming HTTP requests (MVC and REST)
- **Service Layer**: Contains business logic and interacts with external APIs
- **Repository Layer**: Manages database operations
- **Model Layer**: Defines entity and data transfer objects
- **Security Layer**: Manages authentication and authorization
- **Exception Handling**: Provides global exception handling

For a detailed explanation of project structure, see the [Contributing Guide](docs/CONTRIBUTING.md).

## Deployment

### Docker

```bash
docker build -t nasa-api-implementation .
docker run -p 5000:5000 nasa-api-implementation
```

### AWS Elastic Beanstalk

The application is configured for easy deployment to AWS Elastic Beanstalk. See the [Setup Guide](docs/SETUP.md) for details.

## Contributing

Contributions are welcome! Please see our [Contributing Guide](docs/CONTRIBUTING.md) for details on how to get started.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- [NASA Open APIs](https://api.nasa.gov/) for providing the data
- [Spring Boot](https://spring.io/projects/spring-boot) for the framework
- [Bootstrap](https://getbootstrap.com/) for the UI components
- All contributors who have helped improve this project

## Contact

For questions or suggestions, please contact [your-email@example.com](mailto:your-email@example.com).