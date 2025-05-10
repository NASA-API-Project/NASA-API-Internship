package com.openapi.nasa;

/**
 * Getting Started Guide for the NASA API Implementation Project.
 * 
 * <p>This class serves as a comprehensive guide to understanding the architecture,
 * components, and flow of the NASA API Implementation project. It is designed
 * for students who are new to the project and want to understand its structure
 * and functionality.</p>
 * 
 * <h2>Project Overview</h2>
 * 
 * <p>This project is a full-stack web application that utilizes NASA's public APIs
 * to provide users with:
 * <ul>
 *   <li>Astronomy Picture of the Day (APOD) viewing and management</li>
 *   <li>Mars Rover photos browsing with filtering options</li>
 *   <li>RESTful API endpoints for programmatic access</li>
 * </ul>
 * </p>
 * 
 * <h2>Technology Stack</h2>
 * 
 * <ul>
 *   <li><b>Backend:</b> Spring Boot 3, Spring Framework 6, Spring Data JPA, Spring Security</li>
 *   <li><b>Frontend:</b> Thymeleaf, Bootstrap, HTML5, CSS</li>
 *   <li><b>Database:</b> MySQL</li>
 *   <li><b>Security:</b> JWT (JSON Web Tokens), OAuth2 Resource Server</li>
 *   <li><b>API Documentation:</b> OpenAPI/Swagger</li>
 *   <li><b>Deployment:</b> Docker, AWS Elastic Beanstalk</li>
 * </ul>
 * 
 * <h2>Project Architecture</h2>
 * 
 * <p>The project follows a layered architecture pattern:</p>
 * 
 * <pre>
 * ┌───────────────────────┐
 * │     Presentation      │
 * │  (Controllers, Views) │
 * └───────────┬───────────┘
 *             │
 * ┌───────────▼───────────┐
 * │     Service Layer     │
 * │ (Business Logic, API) │
 * └───────────┬───────────┘
 *             │
 * ┌───────────▼───────────┐
 * │      Data Layer       │
 * │  (Repositories, JPA)  │
 * └───────────┬───────────┘
 *             │
 * ┌───────────▼───────────┐
 * │       Database        │
 * └───────────────────────┘
 * </pre>
 * 
 * <h2>Key Components</h2>
 * 
 * <h3>1. Controllers</h3>
 * 
 * <ul>
 *   <li><b>REST Controllers:</b> Handle API requests (NasaApiController)</li>
 *   <li><b>MVC Controllers:</b> Handle web interface requests (NasaMvcController, LoginController)</li>
 * </ul>
 * 
 * <h3>2. Services</h3>
 * 
 * <ul>
 *   <li><b>NasaApiService:</b> Interface defining operations for NASA data</li>
 *   <li><b>NasaApiServiceImpl:</b> Implementation with business logic for NASA APIs</li>
 * </ul>
 * 
 * <h3>3. Data Access</h3>
 * 
 * <ul>
 *   <li><b>NasaRepository:</b> JPA repository for database operations</li>
 *   <li><b>Entity Classes:</b> NasaApod for database mapping</li>
 * </ul>
 * 
 * <h3>4. Models</h3>
 * 
 * <ul>
 *   <li><b>Mars Models:</b> MarsRover, MarsRoverCamera, MarsRoverPhoto</li>
 *   <li><b>Response Models:</b> MarsRoverPhotosResponse</li>
 *   <li><b>Request Models:</b> RoverPhotoRequests</li>
 * </ul>
 * 
 * <h3>5. Security</h3>
 * 
 * <ul>
 *   <li><b>SecurityConfiguration:</b> Security rules and JWT configuration</li>
 *   <li><b>JwtAuthenticationResource:</b> Authentication endpoint</li>
 * </ul>
 * 
 * <h3>6. Exception Handling</h3>
 * 
 * <ul>
 *   <li><b>NasaGlobalExceptionHandler:</b> Global exception handling</li>
 *   <li><b>NasaNotFoundException:</b> Custom exception for "not found" scenarios</li>
 *   <li><b>NasaErrorResponse:</b> Structured error response</li>
 * </ul>
 * 
 * <h2>Application Flow</h2>
 * 
 * <h3>1. APOD Flow</h3>
 * 
 * <pre>
 * 1. User requests APOD page
 * 2. NasaMvcController calls NasaApiService.getAstronomyPictureOfTheDay()
 * 3. NasaApiServiceImpl makes HTTP request to NASA API
 * 4. Data is returned and displayed on apod.html
 * 
 * Admin can also:
 * - Save APOD to database
 * - View all saved APODs
 * - Update APOD details
 * - Delete APOD entries
 * </pre>
 * 
 * <h3>2. Mars Rover Photos Flow</h3>
 * 
 * <pre>
 * 1. User selects rover, cameras, and date
 * 2. Form submitted to NasaMvcController
 * 3. Controller builds API URL and calls NasaApiService.getRoverPhotos()
 * 4. Service makes HTTP request to NASA Mars Rover API
 * 5. Photos are returned and displayed on result.html
 * </pre>
 * 
 * <h3>3. API Flow</h3>
 * 
 * <pre>
 * 1. Client obtains JWT token via /authenticate
 * 2. Client includes token in Authorization header
 * 3. Request goes through SecurityConfiguration validation
 * 4. NasaApiController processes request
 * 5. NasaApiService performs business logic
 * 6. Response is returned as JSON
 * </pre>
 * 
 * <h2>Getting Started with Development</h2>
 * 
 * <h3>Setup</h3>
 * 
 * <ol>
 *   <li>Clone the repository</li>
 *   <li>Configure application.properties with your database and NASA API key</li>
 *   <li>Run the application using Maven or your IDE</li>
 * </ol>
 * 
 * <h3>Example: Fetching APOD from NASA API</h3>
 * 
 * <pre>
 * // Example code to fetch APOD
 * 
 * // 1. In a service class
 * RestTemplate restTemplate = new RestTemplate();
 * String apiKey = "YOUR_NASA_API_KEY";
 * String apiUrl = "https://api.nasa.gov/planetary/apod?api_key={apiKey}";
 * 
 * // 2. Make the request
 * NasaApod apod = restTemplate.getForObject(apiUrl, NasaApod.class, apiKey);
 * 
 * // 3. Use the data
 * System.out.println("Title: " + apod.getTitle());
 * System.out.println("Explanation: " + apod.getExplanation());
 * System.out.println("Image URL: " + apod.getUrl());
 * </pre>
 * 
 * <h3>Example: Saving APOD to Database</h3>
 * 
 * <pre>
 * // Example code to save APOD to database
 * 
 * // 1. Assuming you have a NasaApod object and a repository
 * NasaApod apod = // ... fetch from API or create
 * 
 * // 2. Save to database
 * nasaRepository.save(apod);
 * 
 * // 3. Verify
 * List&lt;NasaApod> allApods = nasaRepository.findAll();
 * System.out.println("Total APODs in database: " + allApods.size());
 * </pre>
 * 
 * <h3>Example: Fetching Mars Rover Photos</h3>
 * 
 * <pre>
 * // Example code to fetch Mars Rover photos
 * 
 * // 1. In a service class
 * RestTemplate restTemplate = new RestTemplate();
 * String apiKey = "YOUR_NASA_API_KEY";
 * String roverType = "curiosity";
 * String earthDate = "2015-06-03";
 * String camera = "fhaz";
 * String apiUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/" 
 *               + roverType + "/photos?earth_date=" + earthDate 
 *               + "&camera=" + camera + "&api_key=" + apiKey;
 * 
 * // 2. Make the request
 * MarsRoverPhotosResponse response = restTemplate.getForObject(
 *     apiUrl, MarsRoverPhotosResponse.class);
 * 
 * // 3. Process photos
 * List&lt;MarsRoverPhoto> photos = response.getMarsRoverPhotosList();
 * for (MarsRoverPhoto photo : photos) {
 *     System.out.println("Photo ID: " + photo.getId());
 *     System.out.println("Image URL: " + photo.getImageSource());
 * }
 * </pre>
 * 
 * <h2>Security Overview</h2>
 * 
 * <p>The application uses Spring Security with JWT for authentication and authorization:</p>
 * 
 * <ul>
 *   <li><b>Form-based authentication</b> for web interface</li>
 *   <li><b>JWT authentication</b> for API access</li>
 *   <li><b>Role-based access control</b> (EMPLOYEE, ADMIN roles)</li>
 *   <li><b>Method-level security</b> with @PreAuthorize annotations</li>
 *   <li><b>URL-based security rules</b> in SecurityConfiguration</li>
 * </ul>
 * 
 * <h3>Example: Obtaining JWT Token</h3>
 * 
 * <pre>
 * // Using curl to get a JWT token
 * curl -X POST http://localhost:5000/authenticate \
 *   -H "Content-Type: application/x-www-form-urlencoded" \
 *   -d "username=admin&password=admin"
 * 
 * // Response will contain a JWT token:
 * {"token":"eyJhbGciOiJSUzI1NiJ9..."}
 * </pre>
 * 
 * <h3>Example: Using JWT Token with API</h3>
 * 
 * <pre>
 * // Using curl to access a protected API endpoint
 * curl -X GET http://localhost:5000/api/apods \
 *   -H "Authorization: Bearer eyJhbGciOiJSUzI1NiJ9..."
 * </pre>
 * 
 * <h2>Database Schema</h2>
 * 
 * <p>The database schema includes the following tables:</p>
 * 
 * <pre>
 * CREATE TABLE apod (
 *     id INT AUTO_INCREMENT PRIMARY KEY,
 *     copyright VARCHAR(255),
 *     date VARCHAR(255) NOT NULL,
 *     explanation TEXT NOT NULL,
 *     hdurl VARCHAR(255),
 *     title VARCHAR(255) NOT NULL,
 *     url VARCHAR(255) NOT NULL
 * );
 * 
 * CREATE TABLE nasa_members (
 *     user_id VARCHAR(50) PRIMARY KEY,
 *     pw VARCHAR(100) NOT NULL,
 *     active BOOLEAN NOT NULL
 * );
 * 
 * CREATE TABLE nasa_roles (
 *     user_id VARCHAR(50) NOT NULL,
 *     role VARCHAR(50) NOT NULL,
 *     PRIMARY KEY (user_id, role),
 *     FOREIGN KEY (user_id) REFERENCES nasa_members(user_id)
 * );
 * </pre>
 * 
 * <h2>NASA API Resources</h2>
 * 
 * <p>The project uses the following NASA API endpoints:</p>
 * 
 * <ul>
 *   <li><b>APOD API:</b> https://api.nasa.gov/planetary/apod</li>
 *   <li><b>Mars Rover Photos API:</b> https://api.nasa.gov/mars-photos/api/v1/rovers</li>
 * </ul>
 * 
 * <p>For more information, see the NASA API documentation at: https://api.nasa.gov/</p>
 * 
 * <h2>Assignment Tasks</h2>
 * 
 * <p>Here are some tasks to help you get familiar with the codebase:</p>
 * 
 * <ol>
 *   <li>Add a method to find APODs by title in NasaRepository</li>
 *   <li>Implement caching for API responses to reduce external calls</li>
 *   <li>Add a new endpoint to get all available photos for a specific Earth date</li>
 *   <li>Create a method to calculate the age of Mars Rover photos in Earth days</li>
 *   <li>Enhance error handling for API communication failures</li>
 * </ol>
 * 
 * <p>Look for TODO comments throughout the codebase for specific tasks.</p>
 */
public class GettingStartedGuide {
    
    /**
     * This is a documentation class only.
     * No implementation is provided.
     * 
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        System.out.println("NASA API Implementation - Getting Started Guide");
        System.out.println("=============================================");
        System.out.println("This is a documentation class. Please read the JavaDoc comments for guidance.");
        System.out.println("Run the NasaApiApplication class to start the application.");
    }
    
    // TODO: Student task - Create a utility class with methods for common NASA API operations
    // TODO: Student task - Implement a dashboard that shows statistics about stored APOD entries
}