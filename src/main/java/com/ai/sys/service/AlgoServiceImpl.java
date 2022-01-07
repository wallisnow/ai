package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.Algo;
import com.ai.sys.repository.AlgoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlgoServiceImpl implements AlgoService {
    private final AlgoRepository algoRepository;

    public void create(Algo algo) throws ResourceOperationException {
        try {
            algoRepository.save(algo);
        } catch (Exception e) {
            throw ResourceOperationException.builder()
                    .resourceName("Algorithm")
                    .status(HttpStatus.BAD_REQUEST)
                    .message("Algorithm create failed:" + e.getCause().getMessage())
                    .build();
        }
    }

    public void deleteAlgoById(long id) {
        algoRepository.deleteById(id);
    }

    public void update(Algo algo) {
        Optional<Algo> byId = algoRepository.findByName(algo.getName());
        byId.orElseThrow(() -> ResourceOperationException.builder()
                .resourceName("Algorithm")
                .status(HttpStatus.NOT_FOUND)
                .message("Algorithm cannot be found")
                .build());
        Algo algoToUpdate = byId.get();
        algoToUpdate.setName(algo.getName());
        algoToUpdate.setDescription(algo.getDescription());
        algoToUpdate.setPath(algo.getPath());
        algoRepository.save(algoToUpdate);
    }

    public Algo findAnAlgoByName(String name) throws ResourceOperationException{
        Optional<Algo> byName = algoRepository.findByName(name);
        byName.orElseThrow(()-> ResourceOperationException.builder().build());
        return byName.get();
    }
}
