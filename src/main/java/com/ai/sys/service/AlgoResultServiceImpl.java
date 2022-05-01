package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.AlgoResult;
import com.ai.sys.repository.AlgoResultRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

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
