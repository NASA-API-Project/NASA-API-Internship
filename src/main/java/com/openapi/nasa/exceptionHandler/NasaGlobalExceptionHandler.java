package com.openapi.nasa.exceptionHandler;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

/**
 * Global exception handler for the NASA API application.
 * 
 * <p>This class provides centralized exception handling across all controllers
 * in the application. It uses Spring's {@link ControllerAdvice} to intercept
 * exceptions thrown during request processing and respond with appropriate error
 * messages and status codes.</p>
 * 
 * <p>Key responsibilities:</p>
 * <ul>
 *   <li>Catching exceptions from all controllers</li>
 *   <li>Converting exceptions to standardized error responses</li>
 *   <li>Setting appropriate HTTP status codes</li>
 *   <li>Handling specific exception types with custom logic</li>
 * </ul>
 * 
 * <p>This global approach ensures consistent error handling throughout the
 * application without duplicating error handling code in each controller.</p>
 * 
 * @see NasaErrorResponse
 * @see NasaNotFoundException
 */
@ControllerAdvice
public class NasaGlobalExceptionHandler {

    /**
     * Handles NasaNotFoundException.
     * 
     * <p>This exception is thrown when a requested resource (like an APOD entry)
     * is not found. It returns a 404 NOT_FOUND response with details about
     * what was not found.</p>
     * 
     * @param exec The caught NasaNotFoundException
     * @return ResponseEntity with error details and 404 status code
     */
    @ExceptionHandler
    public ResponseEntity<NasaErrorResponse> handle(NasaNotFoundException exec) {
        // Create a new error response
        NasaErrorResponse response = new NasaErrorResponse();
        
        // Set status code to 404 NOT_FOUND
        response.setStatus(HttpStatus.NOT_FOUND.value());
        
        // Include the current timestamp
        response.setTimeStamp(System.currentTimeMillis());
        
        // Use the exception's message
        response.setMessage(exec.getMessage());
        
        // Return the response with appropriate HTTP status
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    /**
     * Generic exception handler for all other exceptions.
     * 
     * <p>This is a fallback handler that catches any exceptions not handled
     * by more specific handlers. It returns a 400 BAD_REQUEST response with
     * details from the exception.</p>
     * 
     * @param exec The caught Exception
     * @return ResponseEntity with error details and 400 status code
     */
    @ExceptionHandler
    public ResponseEntity<NasaErrorResponse> handle(Exception exec) {
        // Create a new error response
        NasaErrorResponse response = new NasaErrorResponse();
        
        // Set status code to 400 BAD_REQUEST
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        
        // Include the current timestamp
        response.setTimeStamp(System.currentTimeMillis());
        
        // Use the exception's message
        response.setMessage(exec.getMessage());
        
        // Return the response with appropriate HTTP status
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles access denied scenarios for the web interface.
     * 
     * <p>When a user tries to access a resource they don't have permission for,
     * this handler redirects them to a custom access-denied page instead of
     * returning a JSON error response.</p>
     * 
     * @param exec The caught AccessDeniedException
     * @param request The current HttpServletRequest
     * @return String view name for the access-denied page
     */
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException exec, HttpServletRequest request) {
        // Redirect to a custom access-denied page
        return "nasa/access-denied";
    }

    /**
     * Handles missing resource exceptions during session management.
     * 
     * <p>This handler prevents application crashes when static resources are not found
     * during session management operations. It redirects to the login page.</p>
     * 
     * @param exec The caught NoResourceFoundException
     * @param request The current HttpServletRequest
     * @return String view name for the login page
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public String handleNoResourceFoundException(NoResourceFoundException exec, HttpServletRequest request) {
        // Redirect to the login page
        return "nasa/custom-login-2";
    }

    /**
     * Handles exceptions related to HTTP message conversion.
     * 
     * <p>This is a special handler for cases where Spring cannot write the response
     * body. Instead of failing with an error, it returns an empty response with
     * appropriate headers.</p>
     * 
     * @param ex The caught HttpMessageNotWritableException
     * @return Empty ResponseEntity with appropriate headers and 500 status code
     */
    @ExceptionHandler(HttpMessageNotWritableException.class)
    public ResponseEntity<Void> handleHttpMessageNotWritableException(HttpMessageNotWritableException ex) {
        // Create headers with JSON content type
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        
        // Return empty response with headers and 500 status
        return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    // TODO: Student task - Add a handler for validation exceptions (MethodArgumentNotValidException)
    // TODO: Student task - Implement a handler for API connection failures (RestClientException)
}