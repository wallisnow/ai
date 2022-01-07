package com.ai.sys.repository;

import com.ai.sys.model.DataSet;
import com.ai.sys.model.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;
import java.util.Set;

class DataSetRepositoryTest extends RepositoryTest {

    @Autowired
    private DataSetRepository dataSetRepository;
    private DataSet dataSet;

    private final static String TEST_DATASET_NAME = "FOR_BAR_DATA";
    private final static String TEST_DATASET_PATH = "/dev/path";

    @BeforeEach
    void setUp() {
        Instant now = Instant.now();
        Instant updatedAt = Instant.now().plusSeconds(10);

        Tag tagKmean = Tag.builder().name("K-mean").createdAt(now).updatedAt(updatedAt).build();
        Tag tagApriori = Tag.builder().name("Apriori").createdAt(now).updatedAt(updatedAt).build();

        dataSet = DataSet.builder()
                .name(TEST_DATASET_NAME)
                .path(TEST_DATASET_PATH)
                .tags(Set.of(tagKmean, tagApriori))
                .createdAt(now)
                .updatedAt(updatedAt)
                .build();
    }

    @AfterEach
    void tearDown() {
        dataSetRepository.deleteAll();
    }

    @Test
    void findByName() {
        dataSetRepository.save(dataSet);
        List<DataSet> byName = dataSetRepository.findByName(TEST_DATASET_NAME);

        Assertions.assertNotNull(byName);
        Assertions.assertEquals(1, byName.size());
        Assertions.assertEquals(TEST_DATASET_PATH, byName.get(0).getPath());
    }

}