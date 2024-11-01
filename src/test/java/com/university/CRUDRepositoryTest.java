package com.university;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.*;

class TestEntity implements Entity {
    private int id;
    private String name;

    public TestEntity(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestEntity{id=" + id + ", name='" + name + '\'' + '}';
    }
}

class CRUDRepositoryTest {

    private ServiceLoader<CRUDRepository> serviceLoader;

    @BeforeEach
    void setUp() {
        // Load all available CRUDRepository implementations using ServiceLoader
        serviceLoader = ServiceLoader.load(CRUDRepository.class);
    }

    @Test
    void testServiceLoaderLoadsAllCRUDRepositorys() {
        // Ensure that at least one CRUDRepository implementation is found
        boolean found = serviceLoader.iterator().hasNext();
        assertTrue(found, "No CRUDRepository implementations found");
    }

    @Test
    void testCRUDOperationsOnAllRepositories() {
        for (CRUDRepository crudRepository : serviceLoader) {
            assertNotNull(crudRepository, "CRUDRepository should not be null");

            // Print the entity type being tested
            System.out.println("Testing CRUDRepository for entity type: " + crudRepository.getIdentifier());

            // Create an entity
            Entity testEntity = createTestEntity(crudRepository);
            assertNotNull(testEntity, "Created entity should not be null");
            crudRepository.create(testEntity);

            // Read the entity (assuming an ID of 1 for simplicity)
            Object readEntity = crudRepository.read(1);
            assertNotNull(readEntity, "Entity should be found");
            assertEquals(testEntity.toString(), readEntity.toString(), "Read entity should match created entity");

            // Update the entity (assuming an ID of 1)
            Entity updatedEntity = updateTestEntity(crudRepository);
            crudRepository.update(1, updatedEntity);
            Object readUpdatedEntity = crudRepository.read(1);
            assertEquals(updatedEntity.toString(), readUpdatedEntity.toString(), "Updated entity should match");

            // Delete the entity (assuming an ID of 1)
            crudRepository.delete(1);
            Object deletedEntity = crudRepository.read(1);
            assertNull(deletedEntity, "Deleted entity should no longer exist");
        }
    }

    private Entity createTestEntity(CRUDRepository<? extends Entity> crudRepository) {
        try {
            // Get the entity class from the CRUD repository
            Class<? extends Entity> entityClass = crudRepository.getEntityClass();

            // Use reflection to create a new instance of the entity class
            Constructor<? extends Entity> constructor = entityClass.getDeclaredConstructor();
            Entity entity = constructor.newInstance();

            // Set the ID and name (or other fields as needed)
            entity.setId(1);  // Set the ID, you can adjust this logic
            if (entity instanceof TestEntity) {
                ((TestEntity) entity).setName("Test Entity");
            }

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private Entity updateTestEntity(CRUDRepository<? extends Entity> crudRepository) {
        try {
            // Get the entity class from the CRUD repository
            Class<? extends Entity> entityClass = crudRepository.getEntityClass();

            // Use reflection to create a new instance of the entity class
            Constructor<? extends Entity> constructor = entityClass.getDeclaredConstructor();
            Entity entity = constructor.newInstance();

            // Set the ID and updated name (or other fields as needed)
            entity.setId(1);  // ID remains the same for update
            if (entity instanceof TestEntity) {
                ((TestEntity) entity).setName("Updated Test Entity");
            }

            return entity;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}