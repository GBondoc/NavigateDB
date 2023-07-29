package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.RelationDto;

public interface RelationService {
    RelationDto createRelation(RelationDto relation);
    RelationDto getRelationByRelationId(String relationId);

    RelationDto updateRelation(String relationId, RelationDto relation);

    void deleteRelation(String relationId);
}
