package com.navigatedb.ws.repository;

import com.navigatedb.ws.io.entity.ErdEntity;
import com.navigatedb.ws.io.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ErdRepository extends PagingAndSortingRepository<ErdEntity, Long>, CrudRepository<ErdEntity, Long>, JpaRepository<ErdEntity, Long> {
    ErdEntity findByName(String erdName);
    ErdEntity findByErdId(String erdId);
    List<ErdEntity> findAllByUserDetails(UserEntity userEntity);
    Page<ErdEntity> findByUserDetailsUserId(String userId, Pageable pageable);
    boolean existsByUserDetailsUserIdAndName(String userId, String name);
}
