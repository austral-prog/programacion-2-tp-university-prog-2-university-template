package com.university.service;

import com.university.CRUDRepository;
import com.university.inOut.IncompatibleEntity;
import com.university.model.*;

import java.util.HashSet;
import java.util.Map;

// This class is responsible for managing the entities of the system,
// it provides methods to create new instances of these entities, ensure they are unique,
// and store them in collections for easy access. Each entity has a unique ID.

public class EntityManager<E extends Entity> implements CRUDRepository<E> {

    public EntityManager(Class<E> entityClass) {
        this.entityClass = entityClass;
    }

    static Map<Integer, Entity> MapIdEntities = ManagerHolder.MapIdEntities;
    private static HashSet<Entity> globalEntities = ManagerHolder.globalEntities;

    private final Class<E> entityClass;  // Store the class type of E for type checking
    public HashSet<E> entities = new HashSet<>();

    // Registers an entity

    public void registerEntity(E entity) {
        entity.setId(newId());
        globalEntities.add(entity);
        MapIdEntities.put(entity.getId(), entity);
        entities.add(entity);
    }

    // generates a new unique ID for an entity
    public static int newId() {
        return MapIdEntities.size() + 1;
    }

    // Create or fetch methods

    public E createOrFetchEntity(E entity) {
        if (globalEntities.contains(entity)) {return entity;}
        registerEntity(entity);
        return entity;
    }

    public boolean deleteEntity(E entity) {
        boolean entityRemoved = Remover.disconnection(entity, this);
        return entityRemoved;
    }

    public Map<Integer, Entity> getEntityMap() {
        return MapIdEntities;
    }

    public HashSet<Entity> getGlobalEntities() {
        return globalEntities;
    }

    @Override
    public void create(E entity) {
        if (globalEntities.contains(entity)) {return;}
        registerEntity(entity);
    }

    @Override
    public E read(int id) {
        Entity entity = MapIdEntities.get(id);
        if (entityClass.isInstance(entity)) {
            return entityClass.cast(entity);  // Safe cast using entityClass
        }
        throw new IncompatibleEntity("Entity is not of the correct type");
    }


    @Override
    public void update(int id, Entity entity) {
        entity.setId(id);
        MapIdEntities.put(id, entity);
    }

    @Override
    public boolean delete(int id) {
        Entity entity = MapIdEntities.get(id);
        if (entityClass.isInstance(entity)) {
            boolean flag = deleteEntity(entityClass.cast(entity));  // Safe cast
            return flag;
        }
        throw new IncompatibleEntity("Entity is not of the correct type");
    }

    @Override
    public String getIdentifier(boolean b) {
        return entityClass.getSimpleName();
    }

    @Override
    public Class<Entity> getEntityClass() {
        return Entity.class;
    }
}