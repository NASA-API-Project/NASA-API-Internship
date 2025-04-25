package com.openapi.nasa.service;

import com.openapi.nasa.daorepo.NasaRepository;
import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.exceptionHandler.NasaNotFoundException;
import com.openapi.nasa.response.MarsRoverPhotosResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the NASA API service interface.
 * 
 * <p>This service class implements the {@link NasaApiService} interface and provides
 * the core business logic for interacting with NASA's APIs and managing related data.</p>
 * 
 * <p>Key responsibilities:</p>
 * <ul>
 *   <li>Communicating with NASA's external APIs (APOD and Mars Rover)</li>
 *   <li>Managing APOD data in the local database</li>
 *   <li>Handling business rules and data transformations</li>
 *   <li>Providing appropriate error handling</li>
 * </ul>
 * 
 * <p>This service is used by both the REST and MVC controllers to process requests
 * and prepare responses.</p>
 * 
 * @see com.openapi.nasa.service.NasaApiService
 * @see com.openapi.nasa.rest.NasaApiController
 * @see com.openapi.nasa.mvcController.NasaMvcController
 */
@Service
public class NasaApiServiceImpl implements NasaApiService {
    
    /**
     * NASA API key injected from application properties.
     * This key is required for all NASA API requests.
     */
    @Value("${nasa.api.key}")
    private String apiKey;
    
    /** URL for NASA's APOD API with parameterized API key placeholder. */
    private String apiApodUrl = "https://api.nasa.gov/planetary/apod?api_key={apiKey}";

    /** Base URL for NASA's Mars Rover Photos API. */
    private String baseUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/";
    
    /** Path segment for photos endpoint with Earth date parameter. */
    private String fullUrl = "/photos?earth_date=";
    
    /** Parameter name for specifying rover camera. */
    private String roverCamera = "&camera=";

    /** Repository for NasaApod entity persistence operations. */
    private NasaRepository theNasaRepository;
    
    /** RestTemplate for making HTTP requests to external APIs. */
    private RestTemplate theRestTemplate;

    /**
     * Constructor with dependency injection.
     * 
     * <p>This constructor uses Spring's @Autowired to inject the required dependencies:
     * <ul>
     *   <li>RestTemplate - For making HTTP requests to NASA's APIs</li>
     *   <li>NasaRepository - For database operations on APOD entities</li>
     * </ul>
     * 
     * @param theRestTemplate RestTemplate for API communication
     * @param theNasaRepository Repository for database operations
     */
    @Autowired
    public NasaApiServiceImpl(RestTemplate theRestTemplate, NasaRepository theNasaRepository) {
        this.theRestTemplate = theRestTemplate;
        this.theNasaRepository = theNasaRepository;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementation details: Makes an HTTP GET request to NASA's APOD API
     * using RestTemplate. The API key is passed as a URL parameter.</p>
     */
    @Override
    public NasaApod getAstronomyPictureOfTheDay() {
        // Using RestTemplate to make a GET request to the NASA APOD API
        // The apiKey is passed as a parameter to replace the placeholder in the URL
        return theRestTemplate.getForObject(apiApodUrl, NasaApod.class, apiKey);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementation details: Calls the deleteAll() method on the repository
     * to remove all APOD entries from the database.</p>
     */
    @Override
    public void deleteAllApods() {
        theNasaRepository.deleteAll();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementation details: Uses the repository's save method within a
     * transactional context to ensure database consistency.</p>
     * 
     * <p>The @Transactional annotation ensures that the operation is atomic -
     * either it completes successfully or rolls back completely in case of errors.</p>
     */
    @Override
    @Transactional
    public void save(NasaApod theNasaApod) {
        theNasaRepository.save(theNasaApod);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementation details: Fetches all APOD entries from the database and
     * throws a NasaNotFoundException if the result is empty. This method is
     * primarily used by the REST API controller.</p>
     * 
     * @throws NasaNotFoundException if no APOD entries exist in the database
     */
    @Override
    public List<NasaApod> fetchAllApods() {
        List<NasaApod> apods = theNasaRepository.findAll();
        
        // Check if the list is empty and throw an exception if so
        if (apods.isEmpty()) {
            throw new NasaNotFoundException("No Apods Found. Try Adding Apod To The Database");
        } else {
            return apods;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementation details: Uses the repository's findById method to locate
     * a specific APOD entry by its ID. Throws a NasaNotFoundException if no
     * entry with the given ID exists.</p>
     * 
     * @throws NasaNotFoundException if no APOD with the given ID exists
     */
    @Override
    public NasaApod findNasaApodById(Integer id) {
        // Use Optional to safely handle potentially null results
        Optional<NasaApod> result = theNasaRepository.findById(id);
        NasaApod foundNasaApod = null;
        
        if (result.isPresent()) {
            foundNasaApod = result.get();
        } else {
            throw new NasaNotFoundException("No Apod Found With Id: " + id);
        }
        return foundNasaApod;
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementation details: First verifies that an APOD with the given ID exists,
     * then uses the repository's deleteById method within a transactional context
     * to remove it from the database.</p>
     * 
     * @throws NasaNotFoundException if no APOD with the given ID exists
     */
    @Override
    @Transactional
    public void deleteNasaApodById(Integer id) {
        // First check if the APOD exists
        Optional<NasaApod> result = theNasaRepository.findById(id);
        NasaApod foundNasaApod = null;
        
        if (result.isPresent()) {
            foundNasaApod = result.get();
        } else {
            throw new NasaNotFoundException("No Apod Found With Id: " + id);
        }
        
        // If we reach here, the APOD exists, so delete it
        theNasaRepository.deleteById(id);
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementation details: Simply returns the result of the repository's
     * findAll method without checking if the result is empty. This method is
     * primarily used by the MVC controller, which can handle an empty list
     * in the view.</p>
     */
    @Override
    public List<NasaApod> fetchAllApodsMVC() {
        return theNasaRepository.findAll();
    }

    /**
     * {@inheritDoc}
     * 
     * <p>Implementation details: Constructs the API URL with the provided parameters,
     * then makes an HTTP GET request to NASA's Mars Rover Photos API. The response
     * is automatically deserialized into a MarsRoverPhotosResponse object.</p>
     */
    @Override
    public MarsRoverPhotosResponse getRoverPhotos(String roverType, String earthDate, String camera) {
        // Construct the complete API URL with all parameters
        String apiUrl = baseUrl + roverType + fullUrl + earthDate + roverCamera + camera + "&api_key=" + apiKey;
        
        // Make the API request and deserialize the response
        return theRestTemplate.getForObject(apiUrl, MarsRoverPhotosResponse.class);
    }
    
    // TODO: Student task - Implement caching for API responses to reduce external calls
    // TODO: Student task - Add error handling for API communication failures
}