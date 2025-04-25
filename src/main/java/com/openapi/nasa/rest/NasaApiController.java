package com.openapi.nasa.rest;

import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.exceptionHandler.NasaNotFoundException;
import com.openapi.nasa.service.NasaApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import com.openapi.nasa.response.MarsRoverPhotosResponse;

import java.util.List;

/**
 * REST Controller for NASA API endpoints.
 * 
 * <p>This controller provides RESTful API endpoints for accessing NASA's
 * Astronomy Picture of the Day (APOD) and Mars Rover photos. It serves as the entry point
 * for API requests and delegates business logic to the service layer.</p>
 * 
 * <p>Key features:</p>
 * <ul>
 *   <li>OpenAPI/Swagger documentation with detailed endpoint descriptions</li>
 *   <li>CRUD operations for APOD entities</li>
 *   <li>Access to Mars Rover photos with filtering options</li>
 *   <li>Consistent error handling</li>
 * </ul>
 * 
 * <p>All endpoints are secured and require appropriate authentication/authorization,
 * as defined in the security configuration.</p>
 * 
 * @see com.openapi.nasa.service.NasaApiService
 * @see com.openapi.nasa.entity.NasaApod
 * @see com.openapi.nasa.security.SecurityConfiguration
 */
@RestController
@RequestMapping("/api")
@Tag(name="Nasa Astronomy Picture Of The Day And Mars Rover Api")
public class NasaApiController {
    
    /**
     * Service for NASA API operations.
     * This is injected by Spring's dependency injection mechanism.
     */
    private NasaApiService nasaApiService;

    /**
     * Constructor with dependency injection.
     * 
     * @param nasaApiService The service to handle NASA API operations
     */
    @Autowired
    public NasaApiController(NasaApiService nasaApiService) {
        this.nasaApiService = nasaApiService;
    }

    // ----- APOD API ENDPOINTS ----- //
    
    /**
     * Retrieves the current Astronomy Picture of the Day.
     * 
     * <p>This endpoint fetches the latest APOD directly from NASA's API
     * without storing it in the local database.</p>
     * 
     * <p>URL: GET /api/apod</p>
     * <p>Required role: EMPLOYEE</p>
     * 
     * @return A NasaApod object containing the latest APOD data
     */
    @Operation(
            description = "Get endpoint for fetching Astronomy Picture Of The Day",
            summary="This endpoint will fetch the latest Astronomy Picture Of The Day",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/apod")
    public NasaApod getApod() {
        return nasaApiService.getAstronomyPictureOfTheDay();
    }

    /**
     * Retrieves all APOD entries stored in the database.
     * 
     * <p>This endpoint returns all stored APOD entries from the database.
     * If no entries are found, a NasaNotFoundException is thrown.</p>
     * 
     * <p>URL: GET /api/apods</p>
     * <p>Required role: EMPLOYEE</p>
     * 
     * @return A list of all NasaApod entities in the database
     * @throws NasaNotFoundException if no entries exist
     */
    @Operation(
            description = "Get endpoint for fetching all Astronomy Pictures Of The Day",
            summary="This endpoint will fetch a list of Astronomy Pictures Of The Day",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping(value = "/apods", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NasaApod> getAllApods() {
        return nasaApiService.fetchAllApods();
    }

    /**
     * Saves the current APOD to the database.
     * 
     * <p>This endpoint fetches the latest APOD from NASA's API and saves it
     * to the local database. It returns a confirmation message.</p>
     * 
     * <p>URL: GET /api/save-apod</p>
     * <p>Required role: EMPLOYEE</p>
     * 
     * @return A string confirmation message
     */
    @Operation(
            description = "Get endpoint for saving Astronomy Picture Of The Day In The Database",
            summary="This endpoint will save the latest Astronomy Picture Of The Day In The Database",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/save-apod")
    public String saveApod() {
        // Fetch the latest APOD from NASA's API
        NasaApod theNasaApod = nasaApiService.getAstronomyPictureOfTheDay();
        
        // Save it to the database
        nasaApiService.save(theNasaApod);
        
        // Return a confirmation message
        return "Successfully Saved" + "\n" + "Title: " + theNasaApod.getTitle() + "\n" + "Date: " + theNasaApod.getDate();
    }

    /**
     * Retrieves an APOD entry by its ID.
     * 
     * <p>This endpoint fetches a specific APOD from the database using its ID.
     * If no entry with the given ID exists, a NasaNotFoundException is thrown.</p>
     * 
     * <p>URL: GET /api/apod/{apodId}</p>
     * <p>Required role: EMPLOYEE</p>
     * 
     * @param apodId The ID of the APOD to retrieve
     * @return The NasaApod entity with the specified ID
     * @throws NasaNotFoundException if no entry with the given ID exists
     */
    @Operation(
            description = "Get endpoint for fetching Astronomy Picture Of The Day Using Id",
            summary="This endpoint will fetch the latest Astronomy Picture Of The Day based on the given Id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @GetMapping("/apod/{apodId}")
    public NasaApod getApodById(@PathVariable Integer apodId) {
        return nasaApiService.findNasaApodById(apodId);
    }

    /**
     * Deletes an APOD entry by its ID.
     * 
     * <p>This endpoint removes a specific APOD from the database using its ID.
     * If no entry with the given ID exists, a NasaNotFoundException is thrown.</p>
     * 
     * <p>URL: DELETE /api/apod/{apodId}</p>
     * <p>Required role: ADMIN</p>
     * 
     * @param apodId The ID of the APOD to delete
     * @return A confirmation message
     * @throws NasaNotFoundException if no entry with the given ID exists
     */
    @Operation(
            description = "Delete endpoint for deleting Astronomy Picture Of The Day By Id",
            summary="This endpoint will delete the Astronomy Picture Of The Day based on the given Id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @DeleteMapping("/apod/{apodId}")
    public String deleteApodById(@PathVariable Integer apodId) {
        nasaApiService.deleteNasaApodById(apodId);
        return "Delete Nasa Apod Id: " + apodId;
    }

    /**
     * Updates an existing APOD entry.
     * 
     * <p>This endpoint updates a specific APOD in the database using its ID.
     * If no entry with the given ID exists, a NasaNotFoundException is thrown.</p>
     * 
     * <p>URL: PUT /api/apod/{apodId}</p>
     * <p>Required role: ADMIN</p>
     * 
     * <p>Note: This implementation only updates the title and explanation fields,
     * preserving other data in the existing record.</p>
     * 
     * @param apodId The ID of the APOD to update
     * @param theNasaApod The APOD data with updated values
     * @return A confirmation message
     * @throws NasaNotFoundException if no entry with the given ID exists
     */
    @Operation(
            description = "Put endpoint for updating Astronomy Picture Of The Day",
            summary="This endpoint will update the Astronomy Picture Of The Day based on the given Id",
            responses = {
                    @ApiResponse(
                            description = "Success",
                            responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Unauthorized/Invalid Token",
                            responseCode = "403"
                    )
            }
    )
    @PutMapping("/apod/{apodId}")
    public String updateApod(@PathVariable Integer apodId, @RequestBody NasaApod theNasaApod) {
        // First check if the APOD exists
        NasaApod existingApod = nasaApiService.findNasaApodById(apodId);
        
        if (existingApod == null) {
            throw new NasaNotFoundException("Apod Not Found With Id: " + apodId);
        } else {
            // Update only selected fields
            existingApod.setTitle(theNasaApod.getTitle());
            existingApod.setExplanation(theNasaApod.getExplanation());
            
            // Save the updated APOD
            nasaApiService.save(existingApod);
            
            return "Updated Nasa Apod Id: " + apodId;
        }
    }

    // ----- MARS ROVER API ENDPOINTS ----- //
    
    /**
     * Flag indicating whether a camera code is valid.
     * Used by the camera validation method.
     */
    private static boolean flag = false;

    /**
     * Validates the camera code against a list of valid rover cameras.
     * 
     * <p>This utility method checks if the provided camera code is one of the
     * valid Mars Rover camera types. It sets the static flag to true if valid,
     * false otherwise.</p>
     * 
     * @param roverCamera The camera code to validate
     */
    public static void roverCameras(String roverCamera) {
        // Array of valid rover camera codes
        String roverCameras[] = {"fhaz", "rhaz", "mast", "chemcam", "mahli", "mardi", "navcam", "pancam", "minites"};
        
        // Check if the provided camera is in the valid list
        for (String itt : roverCameras) {
            if (itt.equalsIgnoreCase(roverCamera)) {
                flag = true;
                break;
            }
        }
    }

    /**
     * Retrieves Mars Rover photos based on specified parameters.
     * 
     * <p>This endpoint fetches photos taken by a specific Mars Rover, using a
     * particular camera, on a given Earth date. It validates the camera type
     * and throws a NasaNotFoundException if the camera is invalid.</p>
     * 
     * <p>URL: GET /api/rover/{roverName}/{earthDate}/{roverCamera}</p>
     * <p>Required role: EMPLOYEE</p>
     * 
     * @param roverName The name of the Mars rover (e.g., "curiosity", "opportunity", "spirit")
     * @param earthDate The Earth date in format YYYY-MM-DD
     * @param roverCamera The camera code (e.g., "fhaz", "rhaz", "mast")
     * @return A response object containing a list of Mars Rover photos
     * @throws NasaNotFoundException if the camera code is invalid
     */
    @Operation(
           description = "This endpoint will fetch the Nasa's Mars Rover Photos.\nDetails are given below( have a look )\n" +
                   "A.) There are 3 Mars Rovers\n"+
                   "    1.) Curiosity\n" +
                   "    2.) Spirit\n" +
                   "    3.) Opportunity\n" +
                   "B.) There are 9 Cameras For These Rovers\n" +
                   "    1.) FHAZ-> Front Hazard Avoidance Camera\n" +
                   "    2.) RHAZ-> Rear Hazard Avoidance Camera\n" +
                   "    3.) MAST-> Mast Camera\n" +
                   "    4.) CHEMCAM-> Chemistry and Camera Complex\n" +
                   "    5.) MAHLI-> Mars Hand Lens Imager\n" +
                   "    6.) MARDI-> Mars Descent Imager\n" +
                   "    7.) NAVCAM-> Navigation Camera\n" +
                   "    8.) PANCAM-> Panoramic Camera\n" +
                   "    9.) MINITES-> Miniature Thermal Emission Spectrometer (Mini-TES)\n" +
                   "C.) Earth Date Is In The Form Of YYYY/MM/DD\n" +
                   "D.) You can fetch Mars photos based on the rovername, earthdate, rovercamera.\n" +
                   "    Ex:- /rover/curiosity/2015-06-03/fhaz",
           summary="Get endpoint for fetching Nasa's Mars Rover Photos",
           responses = {
                   @ApiResponse(
                           description = "Success",
                           responseCode = "200"
                   ),
                   @ApiResponse(
                           description = "Unauthorized/Invalid Token",
                           responseCode = "403"
                   )
           }
    )
    @GetMapping("/rover/{roverName}/{earthDate}/{roverCamera}")
    public MarsRoverPhotosResponse getRoverPhotos(
            @PathVariable("roverName") String roverName,
            @PathVariable("earthDate") String earthDate,
            @PathVariable("roverCamera") String roverCamera) {
        
        // Reset the flag
        flag = false;
        
        // Validate the camera code
        roverCameras(roverCamera);
        
        MarsRoverPhotosResponse theMarsRoverPhotosResponse = null;
        
        if (flag == true) {
            // Camera is valid, fetch the photos
            theMarsRoverPhotosResponse = nasaApiService.getRoverPhotos(
                    roverName.toLowerCase(), earthDate, roverCamera);
            return theMarsRoverPhotosResponse;
        } else {
            // Camera is invalid, throw an exception
            throw new NasaNotFoundException(roverCamera + " Camera Does Not Exist");
        }
    }
    
    // TODO: Student task - Add a new endpoint to get all available photos for a specific Earth date
    // TODO: Student task - Implement an endpoint that returns all unique dates with available photos for a rover
}