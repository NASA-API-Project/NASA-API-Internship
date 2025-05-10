package com.openapi.nasa.mvcController;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Controller for handling authentication-related views and requests.
 * 
 * <p>This controller manages the login page and other authentication-related
 * endpoints for the web interface. It's kept separate from the main application
 * controllers to maintain a clear separation of concerns.</p>
 * 
 * <p>Key responsibilities:</p>
 * <ul>
 *   <li>Serving the custom login page</li>
 *   <li>Handling favicon requests to prevent exceptions</li>
 * </ul>
 * 
 * <p>The controller is marked with @Hidden to exclude it from the Swagger/OpenAPI
 * documentation, as it's not part of the API but rather the web interface.</p>
 * 
 * <p>Note that the actual authentication process is handled by Spring Security
 * based on the configuration in {@link com.openapi.nasa.security.SecurityConfiguration}.</p>
 * 
 * @see com.openapi.nasa.security.SecurityConfiguration
 */
@Controller
@Hidden  // Excludes this controller from Swagger/OpenAPI documentation
public class LoginController {

    /**
     * Serves the custom login page.
     * 
     * <p>This endpoint maps to the URL "/show-login-page" and returns the custom
     * login page template. The login form in this template will submit to
     * "/authenticateTheUser", which is handled by Spring Security.</p>
     * 
     * <p>Key template features:</p>
     * <ul>
     *   <li>Username and password input fields</li>
     *   <li>Error message display for failed login attempts</li>
     *   <li>Logout confirmation message</li>
     *   <li>Space-themed design with video background</li>
     * </ul>
     * 
     * <p>The template path "nasa/custom-login-2" refers to the Thymeleaf template
     * located at src/main/resources/templates/nasa/custom-login-2.html.</p>
     * 
     * @return The view name for the custom login page
     */
    @GetMapping("/show-login-page")
    public String customLogin() {
        return "nasa/custom-login-2";
    }

    /**
     * Handles favicon.ico requests to prevent exceptions.
     * 
     * <p>This method intercepts requests for favicon.ico and returns an empty
     * response to prevent 404 errors in the browser console. Modern browsers
     * automatically request favicon.ico, and this handler ensures those requests
     * don't generate exceptions or error logs.</p>
     * 
     * <p>The @ResponseBody annotation indicates that the return value should be
     * used directly as the response body, rather than being interpreted as a
     * view name. Since the method returns void, no content is sent in the response.</p>
     */
    @GetMapping("favicon.ico")
    @ResponseBody
    public void disableFavicon() {
        // Method is void to avoid browser 404 issue by returning nothing in the response.
    }
    
    // TODO: Student task - Add a registration page for new users
    // TODO: Student task - Implement a "forgot password" functionality
}