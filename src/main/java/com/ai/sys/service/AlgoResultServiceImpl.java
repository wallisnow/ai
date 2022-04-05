package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.AlgoResult;
import com.ai.sys.repository.AlgoRepository;
import com.ai.sys.repository.AlgoResultRepository;
import com.ai.sys.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlgoResultServiceImpl implements AlgoResultService {
    private final AlgoResultRepository algoResultRepository;

    public void create(AlgoResult algoResult) throws ResourceOperationException {
        try {
            algoResultRepository.save(algoResult);
        } catch (Exception e) {
            throw ResourceOperationException.builder()
                    .resourceName("AlgorithmResult")
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Algorithm result create failed: " + e.getCause().getCause().getMessage())
                    .build();
        }
    }
}
