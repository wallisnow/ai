package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.exception.ResourceOperationExceptionFactory;
import com.ai.sys.model.entity.DataSet;
import com.ai.sys.repository.DataSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.ai.sys.exception.ResourceOperationExceptionFactory.*;

@Service
@RequiredArgsConstructor
public class DataSetServiceImpl implements DataSetService {

    private final DataSetRepository dataSetRepository;

    public DataSet findById(long id) {
        return dataSetRepository
                .findById(id)
                .orElseThrow(() -> createDataSetException("Cannot found dataset", HttpStatus.BAD_REQUEST));
    }

    @Override
    public void create(DataSet dataSet) throws ResourceOperationException {
        try {
            dataSetRepository.save(dataSet);
        } catch (Exception e) {
            throw createDataSetException("Create dataset failed", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (dataSetRepository.existsById(id)) {
            dataSetRepository.deleteById(id);
        } else {
            throw createDataSetException("Cannot found dataset", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public void update(DataSet dataSet) throws ResourceOperationException {
        boolean exists = dataSetRepository.existsById(dataSet.getId());
        if (exists) {
            dataSetRepository.save(dataSet);
        } else {
            throw createDataSetException(dataSet.getName() + " does not exit!", HttpStatus.NOT_FOUND);
        }
    }

    public List<DataSet> findAll() {
        return new ArrayList<>(dataSetRepository.findAll());
    }
}
