package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.DataSet;
import com.ai.sys.repository.DataSetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DataSetServiceImpl implements DataSetService {

    private final DataSetRepository dataSetRepository;

    public DataSet findById(long id) {
        return dataSetRepository
                .findById(id)
                .orElseThrow(() -> ResourceOperationException.builder()
                        .resourceName(DataSet.class.getName())
                        .message("data set cannot be found!")
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    public List<DataSet> findByTags(List<String> tags) {
        return dataSetRepository.findByTagNameIn(tags);
    }

    @Override
    public List<DataSet> findByName(String name) {
        return dataSetRepository.findByName(name);
    }

    @Override
    public void create(DataSet dataSet) throws ResourceOperationException {
        try {
            dataSetRepository.save(dataSet);
        } catch (Exception e) {
            throw ResourceOperationException
                    .builder()
                    .resourceName(DataSet.class.getName())
                    .message(e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @Override
    @Transactional
    public void deleteByName(String name) {
        dataSetRepository.deleteByName(name);
    }

    @Override
    public void update(DataSet dataSet) throws ResourceOperationException {
        List<DataSet> byName = dataSetRepository.findByName(dataSet.getName());
        if (byName.isEmpty()) {
            throw ResourceOperationException
                    .builder()
                    .resourceName(DataSet.class.getName())
                    .message(dataSet.getName() + " does not exit!")
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
        DataSet updateDataSet = byName.get(0);
        updateDataSet.setName(dataSet.getName());
        updateDataSet.setPath(dataSet.getPath());
        updateDataSet.setTags(dataSet.getTags());
        dataSetRepository.save(updateDataSet);
    }

    public List<DataSet> findAll() {
        return new ArrayList<>(dataSetRepository.findAll());
    }
}
