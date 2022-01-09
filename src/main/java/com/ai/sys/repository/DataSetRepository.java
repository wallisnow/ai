package com.ai.sys.repository;

import com.ai.sys.model.entity.DataSet;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Long> {
    List<DataSet> findByName(@NonNull String name);

    void deleteByName(@NonNull String name);

//    @Query(value = "select ds.* from (data_set_tags dst JOIN tag t ON t.id = dst.tags_id and t.name in :tags) dst_t " +
//            "join data_set ds on ds.id = dst_t.data_set_id", nativeQuery = true)
//    List<DataSet> findByTagNameIn(@Param("tags") List<String> tags);
}
