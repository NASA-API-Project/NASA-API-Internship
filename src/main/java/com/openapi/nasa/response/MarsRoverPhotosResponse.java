package com.openapi.nasa.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openapi.nasa.model.MarsRoverPhoto;

import java.util.List;

/**
 * Response class for Mars Rover photos API.
 * 
 * <p>This class serves as a wrapper for the Mars Rover photos returned by NASA's API.
 * It maps to the top-level structure of the API response, which contains a "photos"
 * array with the list of Mars Rover photos.</p>
 * 
 * <p>The class uses Jackson's {@link JsonProperty} annotation to map the "photos"
 * field from the JSON response to the {@code marsRoverPhotosList} field in this class.
 * This allows for automatic deserialization of API responses into Java objects.</p>
 * 
 * <p>Example JSON structure from NASA's API:</p>
 * <pre>
 * {
 *   "photos": [
 *     {
 *       "id": 102693,
 *       "sol": 1000,
 *       "camera": { ... },
 *       "img_src": "http://mars.jpl.nasa.gov/...",
 *       "earth_date": "2015-05-30",
 *       "rover": { ... }
 *     },
 *     ...
 *   ]
 * }
 * </pre>
 * 
 * <p>This response object is used by both the service layer and controllers to
 * process and present Mars Rover photos to users. It serves as an intermediate
 * object between the raw API response and the application's business logic.</p>
 * 
 * @see com.openapi.nasa.model.MarsRoverPhoto
 * @see com.openapi.nasa.service.NasaApiService#getRoverPhotos(String, String, String)
 */
public class MarsRoverPhotosResponse {
    
    /**
     * List of Mars Rover photos from the API response.
     * 
     * <p>This field is mapped to the "photos" array in the JSON response using
     * the {@link JsonProperty} annotation. Each element in the array is deserialized
     * into a {@link MarsRoverPhoto} object.</p>
     * 
     * <p>The list may be empty if no photos match the specified criteria
     * (rover type, camera, date).</p>
     */
    @JsonProperty("photos")
    private List<MarsRoverPhoto> marsRoverPhotosList;
    
    /**
     * Gets the list of Mars Rover photos.
     * 
     * @return the list of MarsRoverPhoto objects
     */
    public List<MarsRoverPhoto> getMarsRoverPhotosList() {
        return marsRoverPhotosList;
    }

    /**
     * Sets the list of Mars Rover photos.
     * 
     * @param marsRoverPhotosList the list of photos to set
     */
    public void setMarsRoverPhotosList(List<MarsRoverPhoto> marsRoverPhotosList) {
        this.marsRoverPhotosList = marsRoverPhotosList;
    }

    /**
     * Constructor with list of Mars Rover photos.
     * 
     * @param marsRoverPhotosList the list of photos to initialize with
     */
    public MarsRoverPhotosResponse(List<MarsRoverPhoto> marsRoverPhotosList) {
        this.marsRoverPhotosList = marsRoverPhotosList;
    }

    /**
     * No-argument constructor.
     * <p>Required for proper JSON deserialization by Jackson.</p>
     */
    public MarsRoverPhotosResponse() {
        // Default constructor required for Jackson deserialization
    }
    
    // TODO: Student task - Add method to filter photos by camera type
    // TODO: Student task - Implement pagination support for large photo sets
}