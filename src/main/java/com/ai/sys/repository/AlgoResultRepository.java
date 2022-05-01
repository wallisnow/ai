package com.ai.sys.repository;

import com.ai.sys.model.entity.AlgoResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlgoResultRepository extends JpaRepository<AlgoResult, Long> {
}
