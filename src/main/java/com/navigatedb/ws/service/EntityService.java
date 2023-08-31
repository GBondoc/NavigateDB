package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.EntityDto;

import java.util.List;

public interface EntityService {
    EntityDto createEntity(EntityDto entity);
    EntityDto getEntity(String entityId, String erdId, String userId);
    EntityDto updateEntity(String entityId, EntityDto entity);
    void deleteEntity(String entityId);
    List<EntityDto> getEntities(int page, int limit);
    EntityDto getEntityByName(String entityName);
    List<EntityDto> getEntitiesforErdByUser(String userId, String erdId, int page, int limit);
}
