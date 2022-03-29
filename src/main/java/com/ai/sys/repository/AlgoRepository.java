package com.ai.sys.repository;

import com.ai.sys.model.entity.Algo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlgoRepository extends JpaRepository<Algo, Long> {
    Optional<Algo> findByName(String name);
    Optional<List<Algo>> findAlgoBySysUser_Id(Long userid);
}
