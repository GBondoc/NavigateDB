package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.ErdEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface TupleRepository extends PagingAndSortingRepository<ErdEntity, Long>, CrudRepository<ErdEntity, Long> {

}
