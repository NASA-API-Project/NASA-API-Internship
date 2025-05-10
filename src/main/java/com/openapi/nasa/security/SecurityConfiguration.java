package com.openapi.nasa.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.sql.DataSource;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

/**
 * Spring Security configuration for the NASA API application.
 * 
 * <p>This class provides comprehensive security configuration including:</p>
 * <ul>
 *   <li>Authentication via JDBC with custom user schema</li>
 *   <li>Form-based login for web interface</li>
 *   <li>JWT-based authentication for REST API</li>
 *   <li>URL-based security rules</li>
 *   <li>Custom login and logout handling</li>
 * </ul>
 * 
 * <p>The configuration implements a dual security model:</p>
 * <ol>
 *   <li><b>Form-based authentication</b> for users accessing the web interface</li>
 *   <li><b>JWT-based authentication</b> for applications accessing the REST API</li>
 * </ol>
 * 
 * <p>This allows both human users and programmatic clients to securely
 * interact with the application in their preferred ways.</p>
 * 
 * @see JwtAuthenticationResource
 */
@Configuration
public class SecurityConfiguration {

    /**
     * Configures a JDBC-based UserDetailsManager for authentication.
     * 
     * <p>This method creates a JdbcUserDetailsManager that uses a custom database
     * schema for user authentication and authorization. Instead of the default
     * Spring Security tables, it uses custom SQL queries to find:</p>
     * <ul>
     *   <li>Users by username (from nasa_members table)</li>
     *   <li>Authorities by username (from nasa_roles table)</li>
     * </ul>
     * 
     * <p>Database schema used:</p>
     * <pre>
     * CREATE TABLE nasa_members (
     *     user_id VARCHAR(50) PRIMARY KEY,
     *     pw VARCHAR(100) NOT NULL,
     *     active BOOLEAN NOT NULL
     * );
     * 
     * CREATE TABLE nasa_roles (
     *     user_id VARCHAR(50) NOT NULL,
     *     role VARCHAR(50) NOT NULL,
     *     PRIMARY KEY (user_id, role),
     *     FOREIGN KEY (user_id) REFERENCES nasa_members(user_id)
     * );
     * </pre>
     * 
     * @param dataSource The DataSource for database connection
     * @return A configured UserDetailsManager
     */
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        // Create a JDBC-based user details manager with the provided data source
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
        
        // Set custom SQL query to find users by username
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from nasa_members where user_id=?");
        
        // Set custom SQL query to find authorities (roles) by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from nasa_roles where user_id=?");
        
        return jdbcUserDetailsManager;
    }

    /**
     * Configures the security filter chain for the application.
     * 
     * <p>This method defines security rules including:</p>
     * <ul>
     *   <li>URL-based access control</li>
     *   <li>Authentication requirements</li>
     *   <li>Login/logout behavior</li>
     *   <li>JWT token validation</li>
     * </ul>
     * 
     * <p>The configuration uses a combination of:</p>
     * <ul>
     *   <li>Form-based login for web interface</li>
     *   <li>JWT-based authentication for API</li>
     *   <li>Role-based access control</li>
     * </ul>
     * 
     * @param http HttpSecurity to configure
     * @return The configured SecurityFilterChain
     * @throws Exception If configuration fails
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(config ->
                        config
                                // Allow access to static resources without authentication
                                .requestMatchers("/images/**", "/resources/**", "/static/**", "/public/**", "/webui/**").permitAll()
                                
                                // Allow access to authentication endpoints
                                .requestMatchers(HttpMethod.POST, "/authenticate").permitAll()
                                //.requestMatchers(HttpMethod.GET, "/get-token").permitAll()
                                
                                // Allow access to API documentation
                                .requestMatchers("/v3/api-docs/**").permitAll()
                                .requestMatchers("/swagger-ui/**").permitAll()
                                .requestMatchers("/swagger-resources/**").permitAll()
                                .requestMatchers("/swagger-ui.html").permitAll()
                                .requestMatchers("/swaggerdoc.html").permitAll()
                                .requestMatchers("/webjars/**").permitAll()
                                
                                // Restrict access to home page to users with EMPLOYEE role
                                .requestMatchers(HttpMethod.GET, "/nasa/home-page").hasAnyAuthority("SCOPE_ROLE_EMPLOYEE", "ROLE_EMPLOYEE")
                                
                                // Restrict access to APOD API endpoints to users with EMPLOYEE role
                                .requestMatchers(HttpMethod.GET, "/api/apod").hasAnyAuthority("SCOPE_ROLE_EMPLOYEE", "ROLE_EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/api/apods").hasAnyAuthority("SCOPE_ROLE_EMPLOYEE", "ROLE_EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/api/save-apod").hasAnyAuthority("SCOPE_ROLE_EMPLOYEE", "ROLE_EMPLOYEE")
                                .requestMatchers(HttpMethod.GET, "/api/apod/**").hasAnyAuthority("SCOPE_ROLE_EMPLOYEE", "ROLE_EMPLOYEE")
                                
                                // Restrict modification operations to users with ADMIN role
                                .requestMatchers(HttpMethod.DELETE, "/api/apod/**").hasAnyAuthority("SCOPE_ROLE_ADMIN", "ROLE_ADMIN")
                                .requestMatchers(HttpMethod.PUT, "/api/apod/**").hasAnyAuthority("SCOPE_ROLE_ADMIN", "ROLE_ADMIN")
                                
                                // Require authentication for all other requests
                                .anyRequest().authenticated()
                
                // Configure form login
                ).formLogin(form -> form
                        .loginPage("/show-login-page")  // Custom login page URL
                        .loginProcessingUrl("/authenticateTheUser")  // Form submission URL
                        .permitAll())  // Allow anyone to see the login page
                
                // Configure logout
                .logout(logout -> logout.permitAll()
                        .invalidateHttpSession(true)  // End the session
                        .deleteCookies("JSESSIONID")  // Clear JSESSIONID cookie
                        .clearAuthentication(true)  // Clear current authentication
                        .addLogoutHandler((request, response, authentication) -> {
                            // Additional custom logout handling, if needed
                        }))
                
                // Disable caching for login and logout pages
                .headers(headers -> headers
                        .cacheControl().disable()
                        .frameOptions().disable())
                
                // Configure HTTP Basic authentication and JWT
                .httpBasic(Customizer.withDefaults())
                //.csrf(csrf->csrf.disable())  // CSRF protection is enabled by default
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);  // Enable JWT validation

        return http.build();
    }

    /**
     * Generates an RSA key pair for JWT signing and validation.
     * 
     * <p>This method creates a new 2048-bit RSA key pair each time the
     * application starts. In a production environment, you would typically
     * use a persistent key pair stored securely.</p>
     * 
     * @return An RSA key pair
     */
    @Bean
    public KeyPair keyPair() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);  // Use 2048-bit key for security
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to generate RSA key pair", e);
        }
    }

    /**
     * Creates an RSA key for JWT operations.
     * 
     * <p>This method wraps the RSA key pair in a Nimbus JOSE+JWT RSAKey object,
     * which provides convenient methods for JWT operations.</p>
     * 
     * @param keyPair The RSA key pair to use
     * @return An RSAKey object
     */
    @Bean
    public RSAKey rsaKey(KeyPair keyPair) {
        return new RSAKey
                .Builder((RSAPublicKey) keyPair.getPublic())  // Use public key for verification
                .privateKey(keyPair.getPrivate())  // Use private key for signing
                .keyID(UUID.randomUUID().toString())  // Generate a unique key ID
                .build();
    }

    /**
     * Creates a JWK source for the JWT encoder and decoder.
     * 
     * <p>This method wraps the RSA key in a JWK set and creates a source that
     * can be used by the JWT encoder and decoder.</p>
     * 
     * @param rsaKey The RSA key to use
     * @return A JWK source
     */
    @Bean
    public JWKSource<SecurityContext> jwkSource(RSAKey rsaKey) {
        // Create a JWK set with the RSA key
        var jwkSet = new JWKSet(rsaKey);
        
        // Return a JWK source that always returns the JWK set
        return (jwkSelector, context) -> jwkSelector.select(jwkSet);
    }

    /**
     * Creates a JWT decoder for validating JWTs.
     * 
     * <p>This method creates a JWT decoder that uses the public key from the RSA key
     * to validate JWT signatures.</p>
     * 
     * @param rsaKey The RSA key to use
     * @return A JWT decoder
     * @throws JOSEException If there's an error creating the decoder
     */
    @Bean
    public JwtDecoder jwtDecoder(RSAKey rsaKey) throws JOSEException {
        // Create a JWT decoder that uses the public key from the RSA key
        return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey())
                .build();
    }

    /**
     * Creates a JWT encoder for creating JWTs.
     * 
     * <p>This method creates a JWT encoder that uses the JWK source to sign JWTs.</p>
     * 
     * @param jwkSource The JWK source to use
     * @return A JWT encoder
     */
    @Bean
    public JwtEncoder jwtEncoder(JWKSource<SecurityContext> jwkSource) {
        // Create a JWT encoder that uses the JWK source
        return new NimbusJwtEncoder(jwkSource);
    }
    
    // TODO: Student task - Implement remember-me functionality for web interface
    // TODO: Student task - Add password encoding for enhanced security
}