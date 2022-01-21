package com.ai.sys.repository;

import com.ai.sys.model.entity.AlgoType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlgoTypeRepository extends JpaRepository<AlgoType, String> {
    @Query("select a from AlgoType a where a.name = ?1")
    Optional<AlgoType> findByName(String name);

    @Modifying
    @Query("delete from AlgoType a where a.name = ?1")
    int deleteByName(String name);
}