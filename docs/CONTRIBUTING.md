# Contributing to NASA API Implementation

Thank you for your interest in contributing to the NASA API Implementation project! This document provides guidelines and instructions for contributing to the project.

## Table of Contents

1. [Code of Conduct](#code-of-conduct)
2. [Project Structure](#project-structure)
3. [Development Workflow](#development-workflow)
4. [Coding Standards](#coding-standards)
5. [Submitting Changes](#submitting-changes)
6. [Testing Guidelines](#testing-guidelines)
7. [Documentation Guidelines](#documentation-guidelines)
8. [Feature Requests and Bug Reports](#feature-requests-and-bug-reports)
9. [Code Review Process](#code-review-process)
10. [Learning Resources](#learning-resources)

## Code of Conduct

Please be respectful and considerate of others when contributing to this project. We expect all contributors to:

- Be respectful of differing viewpoints and experiences
- Accept constructive criticism gracefully
- Focus on what is best for the community
- Show empathy towards other community members

## Project Structure

Understanding the project structure is essential for effective contributions:

```
nasa-api-implementation/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── openapi/
│   │   │           └── nasa/
│   │   │               ├── daorepo/            # Data access repositories
│   │   │               ├── entity/             # JPA entities
│   │   │               ├── exceptionHandler/   # Global exception handling
│   │   │               ├── model/              # Model classes for Mars Rover
│   │   │               ├── mvcController/      # MVC controllers for web interface
│   │   │               ├── response/           # API response models
│   │   │               ├── rest/               # REST controllers for API
│   │   │               ├── security/           # Security configuration
│   │   │               ├── service/            # Service layer
│   │   │               └── NasaApiApplication.java  # Main application class
│   │   └── resources/
│   │       ├── static/                 # Static resources (CSS, JS, images)
│   │       ├── templates/              # Thymeleaf templates
│   │       │   └── nasa/               # NASA-specific templates
│   │       └── application.properties  # Application configuration
│   └── test/                           # Test classes
├── docs/                               # Documentation
├── pom.xml                             # Maven configuration
└── README.md                           # Project overview
```

## Development Workflow

1. **Fork the repository** to your GitHub account
2. **Clone your fork** to your local machine
3. **Create a new branch** for your feature or bug fix
4. **Make your changes**, following the coding standards
5. **Run tests** to ensure your changes don't break existing functionality
6. **Commit your changes** with clear, descriptive commit messages
7. **Push your branch** to your fork on GitHub
8. **Create a pull request** from your branch to the main repository

### Branch Naming Convention

- Feature branches: `feature/short-description`
- Bug fix branches: `fix/issue-description`
- Documentation branches: `docs/what-is-changed`

## Coding Standards

This project follows standard Java coding conventions with some specific rules:

### Java Code

- Use 4 spaces for indentation (no tabs)
- Follow Java naming conventions:
  - CamelCase for class names (`NasaApiService`)
  - camelCase for method and variable names (`getAstronomyPictureOfTheDay()`)
  - ALL_CAPS for constants (`API_KEY`)
- Keep methods short and focused on a single responsibility
- Write comprehensive JavaDoc comments for all public methods and classes
- Include meaningful TODO comments for incomplete features

### HTML/Thymeleaf Templates

- Use 2 spaces for indentation
- Use semantic HTML elements where appropriate
- Include descriptive comments for complex sections
- Separate structure (HTML), presentation (CSS), and behavior (JavaScript)

### Database

- Use lowercase for table names with underscores for multi-word names
- Use singular form for table names (e.g., `apod` not `apods`)

## Submitting Changes

1. Ensure your code passes all tests
2. Update documentation if your changes affect it
3. Create a pull request with a clear title and description
4. Reference any related issues using GitHub issue references
5. Wait for code review and address any feedback

## Testing Guidelines

All new features and bug fixes should include appropriate tests:

- Write unit tests for services and utilities
- Add integration tests for controllers and repositories
- Ensure tests are independent and don't rely on specific states
- Mock external dependencies (NASA API) for deterministic tests
- Target a minimum of 80% code coverage

### Running Tests

```bash
# Run all tests
mvn test

# Run a specific test class
mvn test -Dtest=NasaApiServiceImplTest

# Run with coverage report
mvn test jacoco:report
```

## Documentation Guidelines

Good documentation is crucial for this project:

- Update the JavaDoc comments for any modified code
- Keep README.md and other documentation up to date
- Document API endpoints with OpenAPI annotations
- Add explanatory comments for complex logic
- Create or update user guides for new features

### JavaDoc Style

```java
/**
 * Method description - what it does, not how it does it.
 * 
 * <p>Additional details if needed, including implementation notes,
 * usage examples, or important considerations.</p>
 * 
 * @param paramName Description of the parameter
 * @return Description of the return value
 * @throws ExceptionType Description of when this exception is thrown
 * @see RelatedClass
 */
```

## Feature Requests and Bug Reports

We use GitHub Issues for tracking bugs and feature requests:

- **Bug Reports**: Include detailed steps to reproduce, expected vs. actual behavior, and your environment details
- **Feature Requests**: Describe the feature, its benefits, and how it should work
- **Security Issues**: For security vulnerabilities, please report privately to the project maintainers

## Code Review Process

All pull requests undergo code review before merging:

1. Automated checks (build, tests, linting)
2. Manual review by at least one maintainer
3. Any issues must be addressed before merging
4. Large changes may require multiple reviewers

## Learning Resources

New to some of the technologies used in this project? Here are some resources to help:

- **Spring Boot**: [Spring Boot Reference Documentation](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/)
- **Spring Security**: [Spring Security Reference](https://docs.spring.io/spring-security/reference/index.html)
- **JWT Authentication**: [JWT Introduction](https://jwt.io/introduction)
- **Thymeleaf**: [Thymeleaf Tutorial](https://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html)
- **NASA APIs**: [NASA API Documentation](https://api.nasa.gov/)
- **OpenAPI/Swagger**: [OpenAPI Specification](https://swagger.io/specification/)

## Additional Resources

- Join the discussion on our [project discussions board](https://github.com/your-username/nasa-api-implementation/discussions)
- Check out the [GettingStartedGuide](../src/main/java/com/openapi/nasa/GettingStartedGuide.java) in the project

Thank you for contributing to the NASA API Implementation project! Your efforts help make space science more accessible to everyone.