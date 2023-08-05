package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.EntityDto;
import com.navigatedb.ws.shared.dto.EntityRelationDto;
import com.navigatedb.ws.ui.model.request.EntityRelationTargetsDetailsRequestModel;

import java.util.List;

public interface EntityRelationService {
    List<EntityDto> getEntities(String entityName);
    List<EntityRelationDto> getEntitiesByChannel(String channel);
    void createEntityRelation(EntityRelationTargetsDetailsRequestModel relation);
}
