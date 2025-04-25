package com.openapi.nasa.exceptionHandler;

/**
 * Custom exception for handling "not found" scenarios in the NASA API application.
 * 
 * <p>This exception is thrown when a requested resource (such as an APOD entry or
 * a Mars Rover camera) cannot be found. It extends RuntimeException, making it an
 * unchecked exception that doesn't require explicit handling in calling code.</p>
 * 
 * <p>Key use cases include:</p>
 * <ul>
 *   <li>When an APOD entry with a specified ID doesn't exist in the database</li>
 *   <li>When no APOD entries exist in the database when trying to list all</li>
 *   <li>When an invalid Mars Rover camera type is specified</li>
 * </ul>
 * 
 * <p>This exception is caught and handled by {@link NasaGlobalExceptionHandler},
 * which converts it to a structured error response with a 404 NOT_FOUND status code.</p>
 * 
 * @see NasaGlobalExceptionHandler
 * @see NasaErrorResponse
 */
public class NasaNotFoundException extends RuntimeException {
    
    /**
     * Default constructor.
     * <p>Creates an exception with no message or cause.</p>
     */
    public NasaNotFoundException() {
        // Default constructor with no message
    }

    /**
     * Constructor with message.
     * <p>Creates an exception with a detailed message but no cause.</p>
     * 
     * @param message The detail message explaining what was not found
     */
    public NasaNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with message and cause.
     * <p>Creates an exception with both a detailed message and a cause.</p>
     * 
     * @param message The detail message explaining what was not found
     * @param cause The exception that caused this one
     */
    public NasaNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor with cause.
     * <p>Creates an exception with a cause but no detailed message.</p>
     * 
     * @param cause The exception that caused this one
     */
    public NasaNotFoundException(Throwable cause) {
        super(cause);
    }
    
    // TODO: Student task - Add a static factory method for creating common "not found" scenarios
    // TODO: Student task - Implement exception serialization for better network transport
}