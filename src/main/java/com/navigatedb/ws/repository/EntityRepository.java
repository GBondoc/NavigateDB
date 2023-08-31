package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.EntityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends PagingAndSortingRepository<EntityEntity, Long>, CrudRepository<EntityEntity, Long>, JpaRepository<EntityEntity, Long> {
    EntityEntity findByName(String name);
    EntityEntity findByEntityId(String entityId);
    Page<EntityEntity> findByErdDetailsErdIdAndErdDetailsUserDetailsUserId(String erdId, String userId, Pageable pageable);
    boolean existsByErdDetailsErdIdAndName(String erdId, String name);

    EntityEntity findByErdDetailsErdIdAndErdDetailsUserDetailsUserIdAndEntityId(String erdId, String userId, String entityId);
}
