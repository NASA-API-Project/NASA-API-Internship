package com.openapi.nasa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model class representing a camera on a NASA Mars Rover.
 * 
 * <p>This class contains information about a specific camera mounted on a Mars Rover.
 * Each rover is equipped with multiple cameras, each with different capabilities
 * and purposes. This model maps to the camera data returned by NASA's Mars Rover 
 * Photos API.</p>
 * 
 * <p>The cameras on Mars rovers are used for various purposes, including:</p>
 * <ul>
 *   <li>Navigation and hazard avoidance</li>
 *   <li>Scientific observations and research</li>
 *   <li>Detailed surface imaging</li>
 *   <li>Atmospheric studies</li>
 * </ul>
 * 
 * <p>Common camera types include:</p>
 * <ul>
 *   <li>FHAZ: Front Hazard Avoidance Camera</li>
 *   <li>RHAZ: Rear Hazard Avoidance Camera</li>
 *   <li>MAST: Mast Camera</li>
 *   <li>CHEMCAM: Chemistry and Camera Complex</li>
 *   <li>MAHLI: Mars Hand Lens Imager</li>
 *   <li>MARDI: Mars Descent Imager</li>
 *   <li>NAVCAM: Navigation Camera</li>
 *   <li>PANCAM: Panoramic Camera</li>
 *   <li>MINITES: Miniature Thermal Emission Spectrometer</li>
 * </ul>
 * 
 * <p>The class uses Jackson's {@link JsonProperty} annotations to map JSON fields from
 * the API response to Java object properties.</p>
 * 
 * @see MarsRover
 * @see MarsRoverPhoto
 */
public class MarsRoverCamera {
    
    /**
     * Unique identifier for the camera.
     * <p>This ID is assigned by NASA and is consistent across API calls.</p>
     */
    @JsonProperty("id")
    private int id;

    /**
     * Short name/code for the camera type.
     * <p>Examples: "FHAZ", "RHAZ", "MAST", etc.</p>
     */
    @JsonProperty("name")
    private String cameraType;

    /**
     * ID of the rover this camera is mounted on.
     * <p>This ID corresponds to the {@link MarsRover#getId()} value.</p>
     */
    @JsonProperty("rover_id")
    private int roverId;

    /**
     * Full descriptive name of the camera.
     * <p>Examples: "Front Hazard Avoidance Camera", "Chemistry and Camera Complex", etc.</p>
     */
    @JsonProperty("full_name")
    private String cameraFullName;

    /**
     * Gets the unique identifier of the camera.
     * 
     * @return the camera's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the camera.
     * 
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the short name/code of the camera type.
     * 
     * @return the camera type code (e.g., "FHAZ", "MAST")
     */
    public String getCameraType() {
        return cameraType;
    }

    /**
     * Sets the short name/code of the camera type.
     * 
     * @param cameraType the camera type code to set
     */
    public void setCameraType(String cameraType) {
        this.cameraType = cameraType;
    }

    /**
     * Gets the ID of the rover this camera is mounted on.
     * 
     * @return the rover ID
     */
    public int getRoverId() {
        return roverId;
    }

    /**
     * Sets the ID of the rover this camera is mounted on.
     * 
     * @param roverId the rover ID to set
     */
    public void setRoverId(int roverId) {
        this.roverId = roverId;
    }

    /**
     * Gets the full descriptive name of the camera.
     * 
     * @return the camera's full name
     */
    public String getCameraFullName() {
        return cameraFullName;
    }

    /**
     * Sets the full descriptive name of the camera.
     * 
     * @param cameraFullName the full name to set
     */
    public void setCameraFullName(String cameraFullName) {
        this.cameraFullName = cameraFullName;
    }

    /**
     * Constructor with all fields.
     * 
     * @param id the unique identifier
     * @param cameraType the camera type code
     * @param roverId the ID of the rover this camera is mounted on
     * @param cameraFullName the full descriptive name of the camera
     */
    public MarsRoverCamera(int id, String cameraType, int roverId, String cameraFullName) {
        this.id = id;
        this.cameraType = cameraType;
        this.roverId = roverId;
        this.cameraFullName = cameraFullName;
    }
    
    /**
     * No-argument constructor.
     * <p>Required for proper JSON deserialization by Jackson.</p>
     * This constructor is not explicitly defined in the code but is implicitly
     * available from the default constructor.
     */
    
    // TODO: Student task - Implement a method to get a description of the camera's purpose
    // TODO: Student task - Add a method to determine if this camera is available on a specific rover type
}