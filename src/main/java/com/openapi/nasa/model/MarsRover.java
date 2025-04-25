package com.openapi.nasa.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Model class representing a NASA Mars Rover.
 * 
 * <p>This class models the Mars Rover data returned by NASA's Mars Rover Photos API.
 * It contains information about the rover itself, including its identification,
 * mission details, status, and equipped cameras.</p>
 * 
 * <p>Key information includes:</p>
 * <ul>
 *   <li>Basic rover identification (ID, name)</li>
 *   <li>Mission timing (launch and landing dates)</li>
 *   <li>Current status (active or inactive)</li>
 *   <li>Mission statistics (sol count, photo count)</li>
 *   <li>Available cameras</li>
 * </ul>
 * 
 * <p>The class uses Jackson's {@link JsonProperty} annotations to map JSON fields from
 * the API response to Java object properties. This allows for automatic deserialization
 * of API responses into Java objects.</p>
 * 
 * <p>NASA currently has data for three Mars rovers:</p>
 * <ul>
 *   <li>Curiosity (landed August 6, 2012)</li>
 *   <li>Opportunity (landed January 25, 2004; mission ended June 10, 2018)</li>
 *   <li>Spirit (landed January 4, 2004; mission ended March 22, 2010)</li>
 * </ul>
 * 
 * @see MarsRoverCamera
 * @see MarsRoverPhoto
 */
public class MarsRover {
    /**
     * Unique identifier for the rover.
     * <p>This ID is assigned by NASA and is consistent across API calls.</p>
     */
    @JsonProperty("id")
    private int id;

    /**
     * Name of the Mars rover.
     * <p>One of: "Curiosity", "Opportunity", or "Spirit".</p>
     */
    @JsonProperty("name")
    private String roverName;

    /**
     * Date when the rover landed on Mars.
     * <p>Format: YYYY-MM-DD (e.g., "2012-08-06" for Curiosity)</p>
     */
    @JsonProperty("landing_date")
    private String roverLandingDate;

    /**
     * Date when the rover was launched from Earth.
     * <p>Format: YYYY-MM-DD (e.g., "2011-11-26" for Curiosity)</p>
     */
    @JsonProperty("launch_date")
    private String roverLaunchDate;

    /**
     * Current mission status of the rover.
     * <p>Either "active" or "complete".</p>
     */
    @JsonProperty("status")
    private String roverStatus;

    /**
     * Maximum sol (Mars day) for which the rover has data.
     * <p>A sol is a Martian solar day, which is about 24 hours and 39 minutes long.
     * This value represents the latest sol for which the rover has transmitted data.</p>
     */
    @JsonProperty("max_sol")
    private long maxSol;

    /**
     * Maximum Earth date for which the rover has data.
     * <p>Format: YYYY-MM-DD</p>
     * <p>This represents the latest Earth date for which the rover has transmitted data.</p>
     */
    @JsonProperty("max_date")
    private String maxDate;

    /**
     * Total number of photos taken by the rover.
     * <p>This is the count of all photos taken by all cameras on the rover
     * that have been transmitted back to Earth and processed.</p>
     */
    @JsonProperty("total_photos")
    private long totalPhotos;

    /**
     * List of cameras mounted on the rover.
     * <p>Each rover has a different set of cameras with various capabilities.
     * For example, Curiosity has 9 cameras including MAST (mast camera) and
     * CHEMCAM (chemistry and camera complex).</p>
     */
    @JsonProperty("cameras")
    private List<MarsRoverCamera> roverCameras;

    /**
     * Gets the unique identifier of the rover.
     * 
     * @return the rover's ID
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the unique identifier of the rover.
     * 
     * @param id the ID to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the name of the rover.
     * 
     * @return the rover's name
     */
    public String getRoverName() {
        return roverName;
    }

    /**
     * Sets the name of the rover.
     * 
     * @param roverName the name to set
     */
    public void setRoverName(String roverName) {
        this.roverName = roverName;
    }

    /**
     * Gets the landing date of the rover.
     * 
     * @return the landing date (YYYY-MM-DD)
     */
    public String getRoverLandingDate() {
        return roverLandingDate;
    }

    /**
     * Sets the landing date of the rover.
     * 
     * @param roverLandingDate the landing date to set
     */
    public void setRoverLandingDate(String roverLandingDate) {
        this.roverLandingDate = roverLandingDate;
    }

    /**
     * Gets the launch date of the rover.
     * 
     * @return the launch date (YYYY-MM-DD)
     */
    public String getRoverLaunchDate() {
        return roverLaunchDate;
    }

    /**
     * Sets the launch date of the rover.
     * 
     * @param roverLaunchDate the launch date to set
     */
    public void setRoverLaunchDate(String roverLaunchDate) {
        this.roverLaunchDate = roverLaunchDate;
    }

    /**
     * Gets the mission status of the rover.
     * 
     * @return the rover's status ("active" or "complete")
     */
    public String getRoverStatus() {
        return roverStatus;
    }

    /**
     * Sets the mission status of the rover.
     * 
     * @param roverStatus the status to set
     */
    public void setRoverStatus(String roverStatus) {
        this.roverStatus = roverStatus;
    }

    /**
     * Gets the maximum sol (Mars day) for which the rover has data.
     * 
     * @return the maximum sol
     */
    public long getMaxSol() {
        return maxSol;
    }

    /**
     * Sets the maximum sol (Mars day) for which the rover has data.
     * 
     * @param maxSol the maximum sol to set
     */
    public void setMaxSol(long maxSol) {
        this.maxSol = maxSol;
    }

    /**
     * Gets the maximum Earth date for which the rover has data.
     * 
     * @return the maximum Earth date (YYYY-MM-DD)
     */
    public String getMaxDate() {
        return maxDate;
    }

    /**
     * Sets the maximum Earth date for which the rover has data.
     * 
     * @param maxDate the maximum date to set
     */
    public void setMaxDate(String maxDate) {
        this.maxDate = maxDate;
    }

    /**
     * Gets the total number of photos taken by the rover.
     * 
     * @return the total photo count
     */
    public long getTotalPhotos() {
        return totalPhotos;
    }

    /**
     * Sets the total number of photos taken by the rover.
     * 
     * @param totalPhotos the total photo count to set
     */
    public void setTotalPhotos(long totalPhotos) {
        this.totalPhotos = totalPhotos;
    }

    /**
     * Gets the list of cameras mounted on the rover.
     * 
     * @return the list of rover cameras
     */
    public List<MarsRoverCamera> getRoverCameras() {
        return roverCameras;
    }

    /**
     * Sets the list of cameras mounted on the rover.
     * 
     * @param roverCameras the list of cameras to set
     */
    public void setRoverCameras(List<MarsRoverCamera> roverCameras) {
        this.roverCameras = roverCameras;
    }

    /**
     * Full constructor with all fields.
     * 
     * @param id the unique identifier
     * @param roverName the rover name
     * @param roverLandingDate the landing date
     * @param roverLaunchDate the launch date
     * @param roverStatus the mission status
     * @param maxSol the maximum sol
     * @param maxDate the maximum Earth date
     * @param totalPhotos the total photo count
     * @param roverCameras the list of cameras
     */
    public MarsRover(int id, String roverName, String roverLandingDate, String roverLaunchDate, String roverStatus, long maxSol, String maxDate, long totalPhotos, List<MarsRoverCamera> roverCameras) {
        this.id = id;
        this.roverName = roverName;
        this.roverLandingDate = roverLandingDate;
        this.roverLaunchDate = roverLaunchDate;
        this.roverStatus = roverStatus;
        this.maxSol = maxSol;
        this.maxDate = maxDate;
        this.totalPhotos = totalPhotos;
        this.roverCameras = roverCameras;
    }

    /**
     * No-argument constructor.
     * <p>Required for proper JSON deserialization by Jackson.</p>
     */
    public MarsRover() {
        // Default constructor required for Jackson
    }
    
    // TODO: Student task - Add a method to calculate the rover's mission duration in Earth days
    // TODO: Student task - Implement a method to find a specific camera by its name
}