package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.DataSet;
import com.ai.sys.repository.DataSetRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;


class DataSetServiceTest extends ServiceTest {

    @Mock
    private DataSetRepository dataSetRepository;

    @InjectMocks
    private DataSetServiceImpl dataSetService;

    private static final String myData = "myData";
    private static final String path = "/dev/data";

    private DataSet testData;

    @BeforeEach
    void setUp() {
        testData = DataSet.builder()
                .name(myData)
                .path(path)
                .build();
    }

    @Test
    void testCreateDataSet() {
        dataSetService.create(testData);
        Mockito.verify(dataSetRepository, Mockito.times(1)).save(testData);
    }

    @Test
    void testCreateDataSetFailed() {
        Mockito.when(dataSetRepository.save(testData)).thenThrow(new RuntimeException());
        Assertions.assertThatExceptionOfType(ResourceOperationException.class)
                .isThrownBy(() -> dataSetService.create(testData));
    }
}