package com.navigatedb.ws.service;

import com.navigatedb.ws.shared.dto.RelationDto;

import java.util.List;

public interface RelationService {
    RelationDto createRelation(RelationDto relation);
    RelationDto getRelationByRelationId(String relationId);
    RelationDto updateRelation(String relationId, RelationDto relation);
    void deleteRelation(String relationId);
    List<RelationDto> getRelations(int page, int limit);
}
