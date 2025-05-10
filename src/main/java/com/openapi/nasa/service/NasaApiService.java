package com.openapi.nasa.service;

import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.response.MarsRoverPhotosResponse;

import java.util.List;

/**
 * Service interface defining operations for interacting with NASA's APIs
 * and managing related data.
 * 
 * <p>This interface serves as a contract for service implementations that handle:
 * <ul>
 *   <li>Retrieving data from NASA's Astronomy Picture of the Day (APOD) API</li>
 *   <li>Managing APOD entries in the local database (CRUD operations)</li>
 *   <li>Retrieving data from NASA's Mars Rover Photos API</li>
 * </ul>
 * </p>
 * 
 * <p>The service layer abstracts the data access and external API communication
 * logic from the controllers, allowing for cleaner separation of concerns and
 * easier testing.</p>
 * 
 * <p>This service is implemented by {@link com.openapi.nasa.service.NasaApiServiceImpl}
 * and is used by both the REST controller and MVC controller to handle
 * business logic related to NASA data.</p>
 * 
 * @see com.openapi.nasa.service.NasaApiServiceImpl
 * @see com.openapi.nasa.rest.NasaApiController
 * @see com.openapi.nasa.mvcController.NasaMvcController
 */
public interface NasaApiService {
    
    // ----- APOD API SERVICE METHODS ----- //
    
    /**
     * Retrieves the current Astronomy Picture of the Day from NASA's API.
     * 
     * @return A NasaApod object containing the latest APOD data
     */
    NasaApod getAstronomyPictureOfTheDay();
    
    /**
     * Deletes all APOD entries from the database.
     * Use with caution as this operation cannot be undone.
     */
    void deleteAllApods();

    /**
     * Saves or updates an APOD entry in the database.
     * 
     * <p>If the APOD entity has an ID that exists in the database, it will be updated.
     * Otherwise, a new entry will be created.</p>
     * 
     * @param theNasaApod The APOD entity to save
     */
    void save(NasaApod theNasaApod);
    
    /**
     * Retrieves all APOD entries from the database.
     * 
     * <p>This method is primarily used by the REST API controller and throws
     * a NasaNotFoundException if no entries are found.</p>
     * 
     * @return A list of all NasaApod entities in the database
     * @throws com.openapi.nasa.exceptionHandler.NasaNotFoundException if no entries exist
     */
    List<NasaApod> fetchAllApods();
    
    /**
     * Finds a specific APOD entry by its ID.
     * 
     * @param id The ID of the APOD entry to find
     * @return The NasaApod entity with the specified ID
     * @throws com.openapi.nasa.exceptionHandler.NasaNotFoundException if no entry with the given ID exists
     */
    NasaApod findNasaApodById(Integer id);
    
    /**
     * Deletes an APOD entry by its ID.
     * 
     * @param id The ID of the APOD entry to delete
     * @throws com.openapi.nasa.exceptionHandler.NasaNotFoundException if no entry with the given ID exists
     */
    void deleteNasaApodById(Integer id);
    
    /**
     * Retrieves all APOD entries from the database for MVC controller.
     * 
     * <p>Unlike {@link #fetchAllApods()}, this method does not throw an exception
     * if no entries are found, making it suitable for use in the MVC controller
     * where an empty list can be handled appropriately in the view.</p>
     * 
     * @return A list of all NasaApod entities, or an empty list if none exist
     */
    List<NasaApod> fetchAllApodsMVC();

    // ----- MARS ROVER API SERVICE METHODS ----- //
    
    /**
     * Retrieves Mars Rover photos based on the specified parameters.
     * 
     * <p>This method communicates with NASA's Mars Rover Photos API to fetch
     * images taken by the specified rover, using a particular camera, on the given date.</p>
     * 
     * @param roverType The name of the Mars rover (e.g., "curiosity", "opportunity", "spirit")
     * @param earthDate The Earth date in format YYYY-MM-DD
     * @param camera The camera code (e.g., "fhaz", "rhaz", "mast")
     * @return A response object containing a list of Mars Rover photos
     */
    MarsRoverPhotosResponse getRoverPhotos(String roverType, String earthDate, String camera);
    
    // TODO: Student task - Add a method to get the latest available Mars Rover photos
    // TODO: Student task - Implement a method to find the most active Mars Rover camera
}