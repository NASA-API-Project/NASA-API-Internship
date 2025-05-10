package com.openapi.nasa.mvcController;

import com.openapi.nasa.entity.NasaApod;
import com.openapi.nasa.model.RoverPhotoRequests;
import com.openapi.nasa.service.NasaApiService;
import com.openapi.nasa.response.MarsRoverPhotosResponse;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * MVC Controller for the NASA API web interface.
 * 
 * <p>This controller handles web requests for the NASA API application, serving
 * Thymeleaf templates and processing user interactions. It provides access to
 * NASA's Astronomy Picture of the Day (APOD) and Mars Rover Photos through
 * a user-friendly web interface.</p>
 * 
 * <p>Key responsibilities:</p>
 * <ul>
 *   <li>Serving web pages (Thymeleaf templates)</li>
 *   <li>Processing form submissions</li>
 *   <li>Managing APOD entries (view, save, update, delete)</li>
 *   <li>Fetching and displaying Mars Rover photos</li>
 * </ul>
 * 
 * <p>The controller uses method-level security with the @PreAuthorize annotation
 * to restrict access to certain functions based on user roles.</p>
 * 
 * <p>The controller is marked with @Hidden to exclude it from the Swagger/OpenAPI
 * documentation, as it's not part of the API but rather the web interface.</p>
 * 
 * @see com.openapi.nasa.service.NasaApiService
 * @see com.openapi.nasa.entity.NasaApod
 * @see com.openapi.nasa.model.RoverPhotoRequests
 */
@Controller
@RequestMapping("/nasa")
@Validated
@EnableMethodSecurity  // Enables method-level security annotations like @PreAuthorize
@Hidden  // Excludes this controller from Swagger/OpenAPI documentation
public class NasaMvcController {
    
    /**
     * Service for NASA API operations.
     * <p>Injected by Spring's dependency injection mechanism.</p>
     */
    private NasaApiService theNasaApiService;
    
    /**
     * NASA API key from application properties.
     */
    @Value("${nasa.api.key}")
    private String apiKey;
    
    /**
     * URL for NASA's APOD API.
     */
    private String apiApodUrl = "https://api.nasa.gov/planetary/apod?api_key={apiKey}";

    /**
     * Base URL for NASA's Mars Rover Photos API.
     */
    private String baseUrl = "https://api.nasa.gov/mars-photos/api/v1/rovers/";
    
    /**
     * Path component for photo requests.
     */
    private String fullUrl = "/photos?earth_date=";
    
    /**
     * Parameter name for camera filtering.
     */
    private String roverCamera = "&camera=";

    /**
     * Constructor with dependency injection.
     * 
     * @param theNasaApiService The service to handle NASA API operations
     */
    @Autowired
    public NasaMvcController(NasaApiService theNasaApiService) {
        this.theNasaApiService = theNasaApiService;
    }
    
    /**
     * Convenience method for saving APOD to the database.
     * 
     * <p>This endpoint fetches the current APOD from NASA's API, saves it to the
     * database, and redirects to the list of saved APODs.</p>
     * 
     * <p>URL: GET /nasa/save-apod-mvc</p>
     * 
     * @return Redirect to the APOD list page
     */
    @GetMapping("/save-apod-mvc")
    public String saveApod() {
        // Fetch the latest APOD from NASA's API
        NasaApod theNasaApod = theNasaApiService.getAstronomyPictureOfTheDay();
        
        // Save it to the database
        theNasaApiService.save(theNasaApod);
        
        // Redirect to the list of saved APODs
        return "redirect:/nasa/list-apods";
    }

    /**
     * Displays the Astronomy Picture of the Day page.
     * 
     * <p>This endpoint fetches the current APOD from NASA's API and displays it
     * on a dedicated page. Access is restricted to users with the EMPLOYEE role.</p>
     * 
     * <p>URL: GET /nasa/mars-apod</p>
     * <p>Required role: EMPLOYEE</p>
     * <p>Template: nasa/apod.html</p>
     * 
     * @param theModel The Spring MVC Model to add attributes to
     * @return The view name for the APOD page
     */
    @GetMapping("/mars-apod")
    @PreAuthorize("hasRole('EMPLOYEE')")  // Restricts access to users with EMPLOYEE role
    public String aPod(Model theModel) {
        // Fetch the latest APOD from NASA's API
        NasaApod apod = theNasaApiService.getAstronomyPictureOfTheDay();
        
        // Add it to the model for the view
        theModel.addAttribute("apod", apod);
        
        // Return the view name
        return "nasa/apod";
    }

    /**
     * Displays the list of saved APOD entries.
     * 
     * <p>This endpoint fetches all saved APOD entries from the database and displays
     * them in a list. Access is restricted to users with the ADMIN role.</p>
     * 
     * <p>URL: GET /nasa/list-apods</p>
     * <p>Required role: ADMIN</p>
     * <p>Template: nasa/list-apods.html</p>
     * 
     * @param theModel The Spring MVC Model to add attributes to
     * @return The view name for the APOD list page
     */
    @GetMapping("/list-apods")
    @PreAuthorize("hasRole('ADMIN')")  // Restricts access to users with ADMIN role
    public String fetchAllApods(Model theModel) {
        // Fetch all saved APODs from the database
        List<NasaApod> apods = theNasaApiService.fetchAllApodsMVC();
        
        // Add them to the model for the view
        theModel.addAttribute("apods", apods);
        
        // Return the view name
        return "nasa/list-apods";
    }

    /**
     * Displays the form for updating an APOD entry.
     * 
     * <p>This endpoint fetches an APOD entry by ID and displays a form for updating it.
     * The form is pre-populated with the current values.</p>
     * 
     * <p>URL: GET /nasa/showFormToUpdateApod?apodId={id}</p>
     * <p>Template: nasa/update-apod-form.html</p>
     * 
     * @param apodId The ID of the APOD to update
     * @param theModel The Spring MVC Model to add attributes to
     * @return The view name for the update form
     */
    @GetMapping("/showFormToUpdateApod")
    public String showFormToUpdateApod(@RequestParam("apodId") Integer apodId, Model theModel) {
        // Fetch the APOD entry by ID
        NasaApod foundNasaApod = theNasaApiService.findNasaApodById(apodId);
        
        // Add it to the model for the form
        theModel.addAttribute("apod", foundNasaApod);
        
        // Return the view name
        return "nasa/update-apod-form";
    }

    /**
     * Processes the APOD update form submission.
     * 
     * <p>This endpoint validates and saves the updated APOD entry from the form.
     * If validation fails, it returns to the form with error messages.</p>
     * 
     * <p>URL: POST /nasa/saveApodForm</p>
     * 
     * @param theNasaApod The APOD object from the form
     * @param theBindingResult Validation results
     * @return Redirect to the APOD list or back to the form if errors
     */
    @PostMapping("/saveApodForm")
    public String saveApodForm(@Valid @ModelAttribute("apod") NasaApod theNasaApod, BindingResult theBindingResult) {
        // Check for validation errors
        if (theBindingResult.hasErrors()) {
            // Return to the form with error messages
            return "nasa/update-apod-form";
        } else {
            // Save the updated APOD
            theNasaApiService.save(theNasaApod);
            
            // Redirect to the list of saved APODs
            return "redirect:/nasa/list-apods";
        }
    }

    /**
     * Deletes an APOD entry from the database.
     * 
     * <p>This endpoint deletes an APOD entry by ID and redirects to the APOD list.</p>
     * 
     * <p>URL: GET /nasa/deleteApodMVC?apodId={id}</p>
     * 
     * @param apodId The ID of the APOD to delete
     * @return Redirect to the APOD list page
     */
    @GetMapping("/deleteApodMVC")
    public String deleteApodMVC(@RequestParam("apodId") Integer apodId) {
        // Delete the APOD entry by ID
        theNasaApiService.deleteNasaApodById(apodId);
        
        // Redirect to the list of saved APODs
        return "redirect:/nasa/list-apods";
    }

    /**
     * Displays the Mars Rover photo selection form.
     * 
     * <p>This endpoint shows a form for selecting Mars Rover photos by rover type,
     * camera, and date. Access is restricted to users with the EMPLOYEE role.</p>
     * 
     * <p>URL: GET /nasa/mars-rover</p>
     * <p>Required role: EMPLOYEE</p>
     * <p>Template: nasa/index.html</p>
     * 
     * @param theModel The Spring MVC Model to add attributes to
     * @return The view name for the Mars Rover form page
     */
    @GetMapping("/mars-rover")
    @PreAuthorize("hasRole('EMPLOYEE')")  // Restricts access to users with EMPLOYEE role
    public String marsRover(Model theModel) {
        // Create a new photo request object for the form
        RoverPhotoRequests roverPhotoRequest = new RoverPhotoRequests();

        // Define the available rover cameras
        List<String> roverCameras = Arrays.asList("&camera=fhaz", "&camera=rhaz", "&camera=mast", "&camera=chemcam", "&camera=mahli", "&camera=mardi", "&camera=navcam", "&camera=pancam", "&camera=minites");
        List<String> allCameras = Arrays.asList("fhaz", "rhaz", "mast", "chemcam", "mahli", "mardi", "navcam", "pancam", "minites");
        
        // Add the form backing object and camera lists to the model
        theModel.addAttribute("roverPhotoRequest", roverPhotoRequest);
        theModel.addAttribute("roverCameras", roverCameras);
        theModel.addAttribute("allCameras", allCameras);
        
        // Return the view name
        return "nasa/index";
    }

    /**
     * Processes the Mars Rover photo selection form and displays the results.
     * 
     * <p>This endpoint validates the form input, fetches matching photos from
     * NASA's API, and displays them on the result page.</p>
     * 
     * <p>URL: POST /nasa/show-photos</p>
     * <p>Template: nasa/result.html</p>
     * 
     * @param photoRequest The form backing object with selection criteria
     * @param theBindingResult Validation results
     * @param model The Spring MVC Model to add attributes to
     * @return The view name for the results page or back to the form if errors
     */
    @PostMapping("/show-photos")
    public String showPhotos(@ModelAttribute @Valid RoverPhotoRequests photoRequest, BindingResult theBindingResult, Model model) {
        // Check for validation errors
        if (theBindingResult.hasErrors()) {
            // Return to the form with error messages
            return "nasa/index";
        }

        // Build the API URL based on the selected criteria
        String apiUrl = buildApiUrl(photoRequest);

        // Fetch photos from NASA's API
        RestTemplate restTemplate = new RestTemplate();
        MarsRoverPhotosResponse photosResponse = restTemplate.getForObject(apiUrl, MarsRoverPhotosResponse.class);

        // Add the results to the model
        model.addAttribute("roverPhotoRequest", photoRequest);
        model.addAttribute("allCameras", Arrays.asList("FHAZ", "RHAZ", "MAST", "CHEMCAM", "MAHLI", "MARDI", "NAVCAM", "PANCAM", "MINITES"));
        model.addAttribute("photos", photosResponse.getMarsRoverPhotosList());
        model.addAttribute("earthDate", photoRequest.getEarthDate());

        // Return the view name for the results page
        return "nasa/result";
    }

    /**
     * Helper method to build the NASA API URL for Mars Rover photos.
     * 
     * <p>This method constructs the complete URL for the NASA Mars Rover Photos API
     * based on the selected rover type, cameras, and date. It handles multiple
     * selected cameras by joining them with the appropriate URL syntax.</p>
     * 
     * @param photoRequest The photo request with selection criteria
     * @return The complete API URL
     */
    private String buildApiUrl(RoverPhotoRequests photoRequest) {
        // Get the selected cameras
        List<String> selectedCameras = photoRequest.getRoverCameras();

        // Join the camera parameters with "&camera="
        String camerasParam = String.join("&camera=", selectedCameras);

        // Construct and return the complete API URL
        return baseUrl + photoRequest.getRoverType() + fullUrl + photoRequest.getEarthDate() +
                roverCamera + camerasParam + "&api_key=" + apiKey;
    }

    /**
     * Displays the home page of the application.
     * 
     * <p>This endpoint serves the main landing page of the NASA API application,
     * which provides an overview and navigation to various features.</p>
     * 
     * <p>URL: GET /nasa/home-page</p>
     * <p>Template: nasa/home-page.html</p>
     * 
     * @return The view name for the home page
     */
    @GetMapping("/home-page")
    public String homePage() {
        return "nasa/home-page";
    }
    
    // TODO: Student task - Add a dashboard page with APOD and Rover photo statistics
    // TODO: Student task - Implement a search function for finding specific APOD entries
}