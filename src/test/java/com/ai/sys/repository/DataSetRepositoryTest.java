package com.ai.sys.repository;

import com.ai.sys.model.entity.Category;
import com.ai.sys.model.entity.DataSet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

class DataSetRepositoryTest extends RepositoryTest {

    @Autowired
    private DataSetRepository dataSetRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    private Category imageCategory;
    private DataSet dataSet;

    private final static String TEST_DATASET_NAME = "FOR_BAR_DATA";
    private final static String TEST_DATASET_PATH = "/dev/path";

    @BeforeEach
    void setUp() {
        Instant now = Instant.now();
        Instant updatedAt = Instant.now().plusSeconds(10);

        imageCategory = Category.builder()
                .name("image")
                .description("image type")
                .createdAt(now)
                .updatedAt(updatedAt)
                .build();

        dataSet = DataSet.builder()
                .name(TEST_DATASET_NAME)
                .path(TEST_DATASET_PATH)
                .category(imageCategory)
                .createdAt(now)
                .updatedAt(updatedAt)
                .build();
    }

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
        dataSetRepository.deleteAll();
    }

}