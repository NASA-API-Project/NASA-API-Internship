package com.openapi.nasa.daorepo;

import com.openapi.nasa.entity.NasaApod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Repository interface for accessing and managing {@link NasaApod} entities in the database.
 * 
 * <p>This interface extends Spring Data JPA's JpaRepository, which provides a complete set of CRUD 
 * operations for the NasaApod entity. The primary key type is Integer, matching the NasaApod's ID field.</p>
 * 
 * <p>Key features:</p>
 * <ul>
 *   <li>Built-in methods for CRUD operations (findAll, findById, save, delete, etc.)</li>
 *   <li>Custom query methods for specific data retrieval needs</li>
 *   <li>Integration with JPA for database operations</li>
 * </ul>
 * 
 * <p>This repository is used by service layer components to perform database operations
 * without knowledge of the underlying data storage implementation.</p>
 * 
 * @see com.openapi.nasa.entity.NasaApod
 * @see com.openapi.nasa.service.NasaApiServiceImpl
 */
public interface NasaRepository extends JpaRepository<NasaApod, Integer> {
    // JpaRepository provides basic CRUD operations without requiring explicit implementation:
    // - save(entity): saves an entity to the database
    // - findById(id): finds an entity by its ID
    // - findAll(): finds all entities
    // - deleteById(id): deletes an entity by its ID
    // - delete(entity): deletes an entity
    // - count(): returns the count of entities
    
    /**
     * Finds all APOD entries that match the given date.
     * 
     * <p>This method uses JPQL (Java Persistence Query Language) to find APOD entries
     * with a specific date. The ?1 parameter represents the first method parameter.</p>
     * 
     * @param date The date string to search for (in format YYYY-MM-DD)
     * @return A list of NasaApod objects matching the given date
     */
    @Query("select n from NasaApod n where n.date=?1")
    List<NasaApod> findByDate(String date);

    /**
     * Finds an APOD entry by its copyright information.
     * 
     * <p>This method demonstrates a named parameter approach using the @Param annotation.
     * This is an alternative to positional parameters (?1, ?2, etc.) and can be more
     * readable when multiple parameters are used.</p>
     * 
     * @param copyright The copyright string to search for
     * @return The NasaApod object with the matching copyright, or null if not found
     */
    @Query("select n from NasaApod n where n.copyright=:theData")
    NasaApod findApodByCopyright(@Param("theData") String copyright);

    // TODO: Student task - Implement additional query methods for finding APODs by title
    // TODO: Student task - Add a method to find APODs with explanation containing specific keywords
}