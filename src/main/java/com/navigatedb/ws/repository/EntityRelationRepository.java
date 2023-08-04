package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.EntityEntity;
import com.navigatedb.ws.io.entity.EntityRelationEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntityRelationRepository extends CrudRepository<EntityRelationEntity, Long>, PagingAndSortingRepository<EntityRelationEntity, Long> {

    List<EntityRelationEntity> findAllByEntityDetails(EntityEntity entityEntity);
    List<EntityRelationEntity> findAllByChannel(String channel);

}
