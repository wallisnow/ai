package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;

import java.util.List;

public interface AlgoService {

    Algo findAnAlgoById(Long id) throws ResourceOperationException;

    List<Algo> findAnAlgoByUserId(Long userid) throws ResourceOperationException;

    void create(Algo algo) throws ResourceOperationException;

    void deleteAlgoById(long id);

    void update(Algo algo);

    void updateCompleteStatus(Long id, boolean isCompleted);
}
