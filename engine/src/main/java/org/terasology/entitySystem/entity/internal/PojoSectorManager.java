/*
 * Copyright 2017 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terasology.entitySystem.entity.internal;

import org.terasology.entitySystem.Component;
import org.terasology.entitySystem.entity.EntityBuilder;
import org.terasology.entitySystem.entity.EntityPool;
import org.terasology.entitySystem.entity.EntityRef;
import org.terasology.entitySystem.entity.SectorManager;
import org.terasology.entitySystem.prefab.Prefab;
import org.terasology.math.geom.Quat4f;
import org.terasology.math.geom.Vector3f;

import java.util.ArrayList;
import java.util.List;

/**
 */
public class PojoSectorManager implements SectorManager {

    private List<PojoEntityPool> pools;

    private PojoEntityManager entityManager;

    public PojoSectorManager(PojoEntityManager entityManager) {
        this.entityManager = entityManager;
        pools = new ArrayList<>();
        pools.add(new PojoEntityPool(entityManager));
    }

    @Override
    public void clear() {
        for (EntityPool pool : pools) {
            pool.clear();
        }
    }

    @Override
    public EntityBuilder newBuilder() {
        return new EntityBuilder(entityManager, this);
    }

    @Override
    public EntityBuilder newBuilder(String prefabName) {
        EntityBuilder builder = newBuilder();
        if (!builder.addPrefab(prefabName)) {
            return null;
        }
        return builder;
    }

    @Override
    public EntityBuilder newBuilder(Prefab prefab) {
        EntityBuilder builder = newBuilder();
        builder.addPrefab(prefab);
        return builder;
    }

    @Override
    public EntityRef create() {
        return getPool().create();
    }

    @Override
    public EntityRef create(Component... components) {
        return getPool().create(components);
    }

    @Override
    public EntityRef create(Iterable<Component> components) {
        return getPool().create(components);
    }

    @Override
    public EntityRef create(String prefabName) {
        return getPool().create(prefabName);
    }

    @Override
    public EntityRef create(Prefab prefab) {
        return getPool().create(prefab);
    }

    @Override
    public EntityRef create(String prefab, Vector3f position) {
        return getPool().create(prefab, position);
    }

    @Override
    public EntityRef create(Prefab prefab, Vector3f position) {
        return getPool().create(prefab, position);
    }

    @Override
    public EntityRef create(Prefab prefab, Vector3f position, Quat4f rotation) {
        return getPool().create(prefab, position, rotation);
    }

    @Override
    public EntityRef createEntityWithoutLifecycleEvents(Iterable<Component> components) {
        return getPool().createEntityWithoutLifecycleEvents(components);
    }

    @Override
    public EntityRef createEntityWithoutLifecycleEvents(String prefab) {
        return getPool().createEntityWithoutLifecycleEvents(prefab);
    }

    @Override
    public EntityRef createEntityWithoutLifecycleEvents(Prefab prefab) {
        return getPool().createEntityWithoutLifecycleEvents(prefab);
    }

    @Override
    public EntityRef createEntityWithId(long id, Iterable<Component> components) {
        return getPool().createEntityWithId(id, components);
    }

    @Override
    public EntityRef createEntityRefWithId(long id) {
        return getPool().createEntityRefWithId(id);
    }

    @Override
    public void destroy(long entityId) {
        getPool().destroy(entityId);
    }

    @Override
    public void destroyEntityWithoutEvents(EntityRef entity) {
        getPool().destroyEntityWithoutEvents(entity);
    }

    @Override
    public Iterable<EntityRef> getAllEntities() {
        return getPool().getAllEntities();
    }

    @Override
    public Iterable<EntityRef> getEntitiesWith(Class<? extends Component>... componentClasses) {
        return getPool().getEntitiesWith(componentClasses);
    }

    @Override
    public int getActiveEntityCount() {
        int count = 0;
        for (EntityPool pool : pools) {
            count += pool.getActiveEntityCount();
        }
        return count;
    }

    @Override
    public EntityRef getExistingEntity(long id) {
        EntityRef entity;
        for (EntityPool pool : pools) {
            entity = pool.getExistingEntity(id);
            if (entity != EntityRef.NULL && entity != null) {
                return entity;
            }
        }
        return EntityRef.NULL;
    }

    private PojoEntityPool getPool() {
        return pools.get(0);
    }

    @Override
    public boolean hasComponent(long entityId, Class<? extends Component> componentClass) {
        for (EntityPool pool : pools) {
            if (pool.hasComponent(entityId, componentClass)) {
                return true;
            }
        }
        return false;
    }

}