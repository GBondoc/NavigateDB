package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.EntityEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends PagingAndSortingRepository<EntityEntity, Long>, CrudRepository<EntityEntity, Long> {

}
