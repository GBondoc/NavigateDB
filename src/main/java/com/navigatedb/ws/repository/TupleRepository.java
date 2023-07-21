package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.TupleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TupleRepository extends PagingAndSortingRepository<TupleEntity, Long>, CrudRepository<TupleEntity, Long> {

    TupleEntity findByConstraintType(String constraintType);
    TupleEntity findByColumnName(String columnName);
    TupleEntity findByDataType(String dataType);
    TupleEntity findByTupleId(String tupleId);
}
