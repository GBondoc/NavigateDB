package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.EntityDto;

import java.util.List;

public interface EntityService {

    EntityDto createEntity(EntityDto entity);

    EntityDto getEntity(String entityId);

    EntityDto updateEntity(String entityId, EntityDto entity);

    void deleteEntity(String entityId);

    List<EntityDto> getEntities(int page, int limit);
}
