package com.ai.sys.repository;

import com.ai.sys.model.entity.Algo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlgoRepository extends JpaRepository<Algo, Long> {
    @Modifying
    @Transactional
    @Query("update Algo a set a.isCompleted = ?2 where a.id=?1")
    int updateAlgoCompletionById(Long id, boolean isCompleted);

    Optional<Algo> findByName(String name);
    Optional<List<Algo>> findAlgoBySysUser_Id(Long userid);
}
