package com.openapi.nasa.security;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.stream.Collectors;

/**
 * REST controller for JWT authentication.
 * 
 * <p>This controller provides endpoints for generating JWT tokens that can be
 * used to authenticate API requests. It leverages Spring Security's OAuth2
 * and JWT support to create signed tokens with appropriate claims.</p>
 * 
 * <p>The controller is marked with the @Hidden annotation to exclude it from
 * the Swagger/OpenAPI documentation, as it's a security implementation detail
 * rather than a business API endpoint.</p>
 * 
 * <p>Key features of the JWT tokens:</p>
 * <ul>
 *   <li>Signed using RSA key pair for security</li>
 *   <li>30-minute expiration time</li>
 *   <li>Includes user authorities as "scope" claim</li>
 *   <li>Uses username as the subject claim</li>
 * </ul>
 * 
 * @see com.openapi.nasa.security.SecurityConfiguration
 */
@RestController
public class JwtAuthenticationResource {
    
    /**
     * JWT encoder for creating signed tokens.
     * <p>Injected by Spring's dependency injection mechanism.</p>
     */
    private JwtEncoder jwtEncoder;

    /**
     * Constructor with dependency injection.
     * 
     * @param jwtEncoder The JWT encoder to use for creating tokens
     */
    @Autowired
    public JwtAuthenticationResource(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }

    /**
     * Endpoint for JWT token generation.
     * 
     * <p>This endpoint accepts POST requests to "/authenticate" and returns a
     * JWT token based on the authenticated user's credentials. The authentication
     * is handled by Spring Security before this method is called.</p>
     * 
     * <p>The token is returned in a JSON response containing a single "token" field.</p>
     * 
     * <p>Example usage with curl:</p>
     * <pre>
     * curl -X POST http://localhost:5000/authenticate \
     *   -H "Content-Type: application/x-www-form-urlencoded" \
     *   -d "username=admin&password=admin"
     * </pre>
     * 
     * @param authentication The authenticated user (provided by Spring Security)
     * @return A JWTResponse containing the JWT token
     */
    @Hidden
    @PostMapping("/authenticate")
    public JWTResponse authenticate(Authentication authentication) {
        return new JWTResponse(createToken(authentication));
    }
    
    /**
     * Alternative endpoint for JWT token generation.
     * 
     * <p>This endpoint accepts GET requests to "/get-token" and returns a
     * JWT token as a plain string. It serves the same purpose as the authenticate
     * endpoint but with a different format.</p>
     * 
     * <p>Note: This endpoint is commented out in the security configuration.</p>
     * 
     * @param authentication The authenticated user (provided by Spring Security)
     * @return The JWT token as a string
     */
    @Hidden
    @GetMapping("/get-token")
    public String getToken(Authentication authentication) {
        return createToken(authentication);
    }

    /**
     * Creates a JWT token for the given authentication.
     * 
     * <p>This helper method builds a JWT token with the following claims:</p>
     * <ul>
     *   <li>Issuer: "self"</li>
     *   <li>Issued at: current time</li>
     *   <li>Expires at: current time + 30 minutes</li>
     *   <li>Subject: authenticated username</li>
     *   <li>Scope: space-separated list of authorities</li>
     * </ul>
     * 
     * <p>The token is signed using the configured JwtEncoder.</p>
     * 
     * @param authentication The authenticated user
     * @return The JWT token as a string
     */
    private String createToken(Authentication authentication) {
        // Build JWT claims
        var claims = JwtClaimsSet.builder()
                .issuer("self")  // Token issuer
                .issuedAt(Instant.now())  // Token issuance time
                .expiresAt(Instant.now().plusSeconds(60 * 30))  // Token expiration (30 minutes)
                .subject(authentication.getName())  // User identifier
                .claim("scope", createScope(authentication))  // User authorities
                .build();

        // Encode and return the token
        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    /**
     * Creates a space-separated list of authorities from the authentication.
     * 
     * <p>This helper method extracts the granted authorities from the authentication
     * and joins them into a single string with spaces between each authority.</p>
     * 
     * <p>Example: "ROLE_ADMIN ROLE_USER"</p>
     * 
     * @param authentication The authenticated user
     * @return A space-separated list of authorities
     */
    private String createScope(Authentication authentication) {
        // Extract authorities and join with spaces
        return authentication.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.joining(" "));
    }
    
    // TODO: Student task - Add token refresh functionality
    // TODO: Student task - Implement token revocation mechanism
}

/**
 * Simple record for JWT response.
 * 
 * <p>This record represents the response structure for the authenticate endpoint.
 * It contains a single field for the JWT token.</p>
 * 
 * <p>Using a Java record provides a concise way to define an immutable data class
 * with automatic getters, constructors, equals, hashCode, and toString methods.</p>
 * 
 * <p>Example JSON response:</p>
 * <pre>
 * {
 *   "token": "eyJhbGciOiJSUzI1NiJ9..."
 * }
 * </pre>
 */
record JWTResponse(String token) {}