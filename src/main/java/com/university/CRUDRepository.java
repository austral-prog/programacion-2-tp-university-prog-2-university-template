package com.university;

public interface CRUDRepository<T extends Entity> {
    /**
     * Creates a new entity in the repository.
     *
     * @param entity the entity to be created
     */
    void create(T entity);

    /**
     * Reads or retrieves an entity from the repository based on its ID.
     *
     * @param id the unique identifier of the entity to be read
     * @return the entity found with the given ID, or null if not found
     */
    T read(int id);

    /**
     * Updates an existing entity in the repository.
     *
     * @param id the unique identifier of the entity to be updated
     * @param entity the updated entity information
     */
    void update(int id, T entity);

    /**
     * Deletes an entity from the repository based on its ID.
     *
     * @param id the unique identifier of the entity to be deleted
     */
    void delete(int id);

    /**
     * Returns a string identifier that represents the type of entity handled by this CRUD interface.
     * This can be used to differentiate between different entity types (e.g., "Person", "Product").
     *
     * @return a string representing the identifier of the entity type
     */
    String getIdentifier();

    Class<T> getEntityClass();
}
