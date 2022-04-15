package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Category;
import com.ai.sys.model.entity.DataSet;
import com.ai.sys.repository.DataSetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.ai.sys.exception.ResourceOperationExceptionFactory.createDataSetException;

@Slf4j
@Service
@RequiredArgsConstructor
public class DataSetServiceImpl implements DataSetService {

    private final DataSetRepository dataSetRepository;
    private final CategoryService categoryService;

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
            Optional.ofNullable(dataSet.getCategory())
                    .ifPresentOrElse(
                            category -> {
                                String name = category.getName();
                                Category inDb = categoryService.findByName(name)
                                        .orElseThrow(() -> createDataSetException("类别 category " + category.getName() + "不存在，请先添加类别!", HttpStatus.NOT_FOUND));
                                dataSet.setCategory(inDb);
                            },
                            () -> {
                                throw createDataSetException("类别 category 不能为空，请先添加类别!", HttpStatus.BAD_REQUEST);
                            });
            dataSetRepository.save(dataSet);
        } else {
            throw createDataSetException(dataSet.getName() + " does not exit!", HttpStatus.NOT_FOUND);
        }
    }

    public List<DataSet> findAll() {
        return new ArrayList<>(dataSetRepository.findAll());
    }
}
