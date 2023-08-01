package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.EntityRelationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRelationRepository extends CrudRepository<EntityRelationEntity, Long>, PagingAndSortingRepository<EntityRelationEntity, Long> {
}
