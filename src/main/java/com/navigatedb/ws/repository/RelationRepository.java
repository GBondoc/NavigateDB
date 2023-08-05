package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.RelationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RelationRepository extends PagingAndSortingRepository<RelationEntity, Long>, CrudRepository<RelationEntity, Long> {
    RelationEntity findByRelationId(String relationId);
    RelationEntity findByRelationTypeAndNullable(String relationType, String nullable);
    RelationEntity findByNullable(String nullable);
}
