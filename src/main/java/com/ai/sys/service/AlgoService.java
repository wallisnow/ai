package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;

public interface AlgoService {

    Algo findAnAlgoByName(String name) throws ResourceOperationException;;

    void create(Algo algo) throws ResourceOperationException;

    void deleteAlgoById(long id);

    void update(Algo algo);
}
