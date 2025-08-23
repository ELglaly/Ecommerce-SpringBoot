package com.example.ecommerce.repository;

import com.example.ecommerce.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    /**
     * Finds categories whose names contain the specified string, ordered by name in ascending order.
     *
     * @param category The string to search for within category names.
     * @return A list of categories matching the search criteria.
     */
    <T> Page<T> findByNameContainingOrderByNameAsc(String category, Pageable pageable,Class<T> type);
    /**
     * Checks if a category with the specified name exists.
     *
     * @param name The name of the category to check.
     * @return true if a category with the specified name exists, false otherwise.
     */
    boolean existsByName(String name);
    /**
     * Counts the number of categories with the specified name.
     *
     * @param name The name of the category to count.
     * @return The number of categories with the specified name.
     */
    int countByName( String name);
    /**
     * Finds a category by its ID and maps it to the specified type.
     *
     * @param id   The ID of the category to find.
     * @param type The class type to map the result to.
     * @param <T>  The type of the result.
     * @return The category mapped to the specified type, or null if not found.
     */
    <T> T findById(Long id, Class<T> type);
    /**
     * Finds all categories and maps them to the specified type.
     *
     * @param type     The class type to map the results to.
     * @param pageable The pagination information.
     * @param <T>      The type of the results.
     * @return A page of categories mapped to the specified type.
     */
    <T> Page<T> findBy(Class<T> type, Pageable pageable);

    /**
     * Finds a category by its name and maps it to the specified type.
     *
     * @param name The name of the category to find.
     * @param type The class type to map the result to.
     * @param <T>  The type of the result.
     * @return The category mapped to the specified type, or null if not found.
     */
    <T> T findByName(String name,Class<T> type);

}
