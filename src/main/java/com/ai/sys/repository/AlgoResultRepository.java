package com.ai.sys.repository;

import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.AlgoResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlgoResultRepository extends JpaRepository<AlgoResult, Long> {
}
