package com.openapi.nasa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing a photo taken by a Mars Rover.
 * 
 * <p>This class is part of a complex object hierarchy that represents the data structure
 * returned by NASA's Mars Rover Photos API. It contains information about a specific
 * photo, including when it was taken (both in Mars sols and Earth date), which camera
 * took it, and a link to the image itself.</p>
 * 
 * <p>The class uses Jackson's {@link JsonProperty} annotations to map JSON fields from
 * the API response to Java object properties. This allows for automatic deserialization
 * of API responses into Java objects.</p>
 * 
 * <p>Object hierarchy:</p>
 * <pre>
 * MarsRoverPhotosResponse
 *   └── List<MarsRoverPhoto>
 *        ├── MarsRoverCamera
 *        └── MarsRover
 *             └── List<MarsRoverCamera>
 * </pre>
 * 
 * @see com.openapi.nasa.model.MarsRover
 * @see com.openapi.nasa.model.MarsRoverCamera
 * @see com.openapi.nasa.response.MarsRoverPhotosResponse
 */
public class MarsRoverPhoto {
    
    /**
     * Unique identifier for the photo.
     * This is a unique ID assigned by NASA to each photo.
     */
    @JsonProperty("id")
    private long id;

    /**
     * Mars sol (day) when the photo was taken.
     * <p>A sol is a Martian day, which is about 24 hours and 39 minutes long.
     * This field represents the number of sols (Martian days) since the rover landed.</p>
     */
    @JsonProperty("sol")
    private long sol;

    /**
     * Camera that took the photo.
     * <p>This is a complex object containing details about the specific
     * camera that captured this image.</p>
     */
    @JsonProperty("camera")
    private MarsRoverCamera marsRoverCamera;

    /**
     * URL to the actual image.
     * <p>This URL can be used to display the image in a browser or download it.</p>
     */
    @JsonProperty("img_src")
    private String imageSource;

    /**
     * Earth date when the photo was taken.
     * <p>This is the Earth calendar date (YYYY-MM-DD) when the photo was captured,
     * which corresponds to the Mars sol.</p>
     */
    @JsonProperty("earth_date")
    private String earthDate;

    /**
     * The Mars rover that took this photo.
     * <p>This is a complex object containing details about the rover,
     * including its name, landing date, status, etc.</p>
     */
    @JsonProperty("rover")
    private MarsRover marsRover;

    /**
     * Gets the unique identifier of this photo.
     * 
     * @return the photo's ID
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the unique identifier of this photo.
     * 
     * @param id the ID to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * Gets the Mars sol when this photo was taken.
     * 
     * @return the sol value
     */
    public long getSol() {
        return sol;
    }

    /**
     * Sets the Mars sol when this photo was taken.
     * 
     * @param sol the sol value to set
     */
    public void setSol(long sol) {
        this.sol = sol;
    }

    /**
     * Gets the camera that took this photo.
     * 
     * @return the MarsRoverCamera object
     */
    public MarsRoverCamera getMarsRoverCamera() {
        return marsRoverCamera;
    }

    /**
     * Sets the camera that took this photo.
     * 
     * @param marsRoverCamera the MarsRoverCamera to set
     */
    public void setMarsRoverCamera(MarsRoverCamera marsRoverCamera) {
        this.marsRoverCamera = marsRoverCamera;
    }

    /**
     * Gets the URL to this photo's image.
     * 
     * @return the image source URL
     */
    public String getImageSource() {
        return imageSource;
    }

    /**
     * Sets the URL to this photo's image.
     * 
     * @param imageSource the image source URL to set
     */
    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }

    /**
     * Gets the Earth date when this photo was taken.
     * 
     * @return the Earth date in format YYYY-MM-DD
     */
    public String getEarthDate() {
        return earthDate;
    }

    /**
     * Sets the Earth date when this photo was taken.
     * 
     * @param earthDate the Earth date to set
     */
    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }

    /**
     * Gets the Mars rover that took this photo.
     * 
     * @return the MarsRover object
     */
    public MarsRover getMarsRover() {
        return marsRover;
    }

    /**
     * Sets the Mars rover that took this photo.
     * 
     * @param marsRover the MarsRover to set
     */
    public void setMarsRover(MarsRover marsRover) {
        this.marsRover = marsRover;
    }

    /**
     * Full constructor with all fields.
     * 
     * @param id the unique identifier
     * @param sol the Mars sol
     * @param marsRoverCamera the camera that took the photo
     * @param imageSource the image URL
     * @param earthDate the Earth date
     * @param marsRover the rover that took the photo
     */
    public MarsRoverPhoto(long id, long sol, MarsRoverCamera marsRoverCamera, String imageSource, String earthDate, MarsRover marsRover) {
        this.id = id;
        this.sol = sol;
        this.marsRoverCamera = marsRoverCamera;
        this.imageSource = imageSource;
        this.earthDate = earthDate;
        this.marsRover = marsRover;
    }

    /**
     * No-argument constructor.
     * <p>Required for proper JSON deserialization by Jackson.</p>
     */
    public MarsRoverPhoto() {
        // Default constructor required for Jackson deserialization
    }
    
    // TODO: Student task - Add a method to calculate the age of the photo in Earth days
    // TODO: Student task - Implement a method to determine if this photo was taken by a specific camera type
}