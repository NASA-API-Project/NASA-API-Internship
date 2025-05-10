package com.openapi.nasa.model;

import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Form-backing model for Mars Rover photo requests in the MVC controller.
 * 
 * <p>This class serves as a data transfer object (DTO) that captures user input from
 * the Mars Rover photo selection form. It holds the criteria used to filter Mars Rover
 * photos, including the rover type, camera(s), and Earth date.</p>
 * 
 * <p>Key components:</p>
 * <ul>
 *   <li>Rover type selection (Curiosity, Opportunity, or Spirit)</li>
 *   <li>Camera selection (multiple cameras can be selected)</li>
 *   <li>Earth date for which to retrieve photos</li>
 * </ul>
 * 
 * <p>This model is used by the MVC controller to:</p>
 * <ol>
 *   <li>Bind form data from user input</li>
 *   <li>Validate the input (especially the required date field)</li>
 *   <li>Build the API URL for fetching photos from NASA's API</li>
 * </ol>
 * 
 * <p>The model implements Bean Validation with the {@link NotNull} annotation
 * on required fields to ensure proper form validation.</p>
 * 
 * @see com.openapi.nasa.mvcController.NasaMvcController#marsRover(org.springframework.ui.Model)
 * @see com.openapi.nasa.mvcController.NasaMvcController#showPhotos(RoverPhotoRequests, org.springframework.validation.BindingResult, org.springframework.ui.Model)
 */
public class RoverPhotoRequests {
    
    /**
     * The type of Mars Rover to retrieve photos from.
     * <p>Valid values are "Curiosity", "Opportunity", and "Spirit".</p>
     * <p>This field is populated from a dropdown in the form.</p>
     */
    private String roverType;
    
    /**
     * The list of rover camera codes to filter photos by.
     * <p>Each code represents a specific camera on the rover (e.g., "fhaz", "rhaz", "mast").</p>
     * <p>Multiple cameras can be selected via checkboxes in the form.</p>
     * <p>Valid camera codes include:</p>
     * <ul>
     *   <li>fhaz: Front Hazard Avoidance Camera</li>
     *   <li>rhaz: Rear Hazard Avoidance Camera</li>
     *   <li>mast: Mast Camera</li>
     *   <li>chemcam: Chemistry and Camera Complex</li>
     *   <li>mahli: Mars Hand Lens Imager</li>
     *   <li>mardi: Mars Descent Imager</li>
     *   <li>navcam: Navigation Camera</li>
     *   <li>pancam: Panoramic Camera</li>
     *   <li>minites: Miniature Thermal Emission Spectrometer</li>
     * </ul>
     */
    private List<String> roverCameras;

    /**
     * The Earth date for which to retrieve Mars Rover photos.
     * <p>Format: YYYY-MM-DD (e.g., "2015-06-03")</p>
     * <p>This is a required field, validated with @NotNull annotation.</p>
     * <p>The date must be within the operational range of the selected rover:</p>
     * <ul>
     *   <li>Curiosity: 2012-08-06 to present</li>
     *   <li>Opportunity: 2004-01-25 to 2018-06-10</li>
     *   <li>Spirit: 2004-01-04 to 2010-03-22</li>
     * </ul>
     */
    @NotNull(message = "is required")
    private String earthDate;

    /**
     * Gets the selected rover type.
     * 
     * @return the rover type
     */
    public String getRoverType() {
        return roverType;
    }

    /**
     * Sets the rover type.
     * 
     * @param roverType the rover type to set
     */
    public void setRoverType(String roverType) {
        this.roverType = roverType;
    }

    /**
     * Gets the list of selected rover cameras.
     * 
     * @return the list of camera codes
     */
    public List<String> getRoverCameras() {
        return roverCameras;
    }

    /**
     * Sets the list of rover cameras.
     * 
     * @param roverCameras the list of camera codes to set
     */
    public void setRoverCameras(List<String> roverCameras) {
        this.roverCameras = roverCameras;
    }

    /**
     * Gets the selected Earth date.
     * 
     * @return the Earth date in format YYYY-MM-DD
     */
    public String getEarthDate() {
        return earthDate;
    }

    /**
     * Sets the Earth date.
     * 
     * @param earthDate the Earth date to set
     */
    public void setEarthDate(String earthDate) {
        this.earthDate = earthDate;
    }
    
    // TODO: Student task - Add date validation to ensure the date is in the correct format
    // TODO: Student task - Implement a method to check if the date is within the operational range of the selected rover
}