package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.DataSet;

import java.util.List;


public interface DataSetService {
    List<DataSet> findAll();

    DataSet findById(long id) throws ResourceOperationException;

    List<DataSet> findByName(String name);
    List<DataSet> findByTags(List<String> tags);

    void create(DataSet dataSet) throws ResourceOperationException;

    void deleteByName(String name);

    void update(DataSet dataSet) throws ResourceOperationException;
}
