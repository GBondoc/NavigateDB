package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.EntityDto;

public interface EntityService {

    EntityDto createEntity(EntityDto entity);

    EntityDto getEntity(String entityId);
}
