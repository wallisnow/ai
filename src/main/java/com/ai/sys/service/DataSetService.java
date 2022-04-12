package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.DataSet;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface DataSetService {
    List<DataSet> findAll();

    DataSet findById(long id) throws ResourceOperationException;

    void create(DataSet dataSet) throws ResourceOperationException;

    void deleteById(Long id) throws ResourceOperationException;

    void update(DataSet dataSet) throws ResourceOperationException;
}
