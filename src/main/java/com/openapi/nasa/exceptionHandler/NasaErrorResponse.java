package com.openapi.nasa.exceptionHandler;

/**
 * A standardized error response format for the NASA API application.
 * 
 * <p>This class defines the structure of error responses returned to clients
 * when exceptions occur during API request processing. It includes essential
 * information about the error to help clients understand and address the issue.</p>
 * 
 * <p>Key components of the error response:</p>
 * <ul>
 *   <li><b>status:</b> HTTP status code (e.g., 404 for Not Found)</li>
 *   <li><b>message:</b> Human-readable error description</li>
 *   <li><b>timeStamp:</b> When the error occurred (milliseconds since epoch)</li>
 * </ul>
 * 
 * <p>This class is used by {@link NasaGlobalExceptionHandler} to create consistent
 * error responses across the application.</p>
 * 
 * @see NasaGlobalExceptionHandler
 * @see NasaNotFoundException
 */
public class NasaErrorResponse {
    
    /**
     * HTTP status code for the error.
     * Common values include:
     * <ul>
     *   <li>400 - Bad Request</li>
     *   <li>401 - Unauthorized</li>
     *   <li>403 - Forbidden</li>
     *   <li>404 - Not Found</li>
     *   <li>500 - Internal Server Error</li>
     * </ul>
     */
    private int status;
    
    /**
     * Human-readable error message.
     * This provides details about what went wrong and possibly how to fix it.
     */
    private String message;
    
    /**
     * Timestamp of when the error occurred.
     * Represented as milliseconds since the Unix epoch (January 1, 1970).
     */
    private long timeStamp;

    /**
     * Gets the HTTP status code.
     * 
     * @return the status code
     */
    public int getStatus() {
        return status;
    }

    /**
     * Sets the HTTP status code.
     * 
     * @param status the status code to set
     */
    public void setStatus(int status) {
        this.status = status;
    }

    /**
     * Gets the error message.
     * 
     * @return the error message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Sets the error message.
     * 
     * @param message the error message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * Gets the timestamp of when the error occurred.
     * 
     * @return the timestamp in milliseconds since epoch
     */
    public long getTimeStamp() {
        return timeStamp;
    }

    /**
     * Sets the timestamp of when the error occurred.
     * 
     * @param timeStamp the timestamp to set
     */
    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    /**
     * Constructs a complete error response with all fields.
     * 
     * @param status the HTTP status code
     * @param message the error message
     * @param timeStamp the timestamp
     */
    public NasaErrorResponse(int status, String message, long timeStamp) {
        this.status = status;
        this.message = message;
        this.timeStamp = timeStamp;
    }

    /**
     * No-argument constructor.
     * <p>This is used when creating an error response in stages,
     * setting properties one by one.</p>
     */
    public NasaErrorResponse() {
        // Default constructor
    }
    
    // TODO: Student task - Add additional fields for more detailed error information
    // TODO: Student task - Implement a method to convert the error to a JSON string
}