package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.ErdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErdRepository extends PagingAndSortingRepository<ErdEntity, Long>, CrudRepository<ErdEntity, Long> {
    ErdEntity findByName(String erdName);
    ErdEntity findByErdId(String erdId);
}
