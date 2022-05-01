package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.repository.AlgoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

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
                    .message("Algorithm create failed: " + e.getCause().getCause().getMessage())
                    .build();
        }
    }

    public void deleteAlgoById(long id) throws ResourceOperationException {
        handleAlgoExistence(() -> {
            algoRepository.deleteById(id);
            return null;
        }, id);
    }

    public void update(Algo algo) {
        handleAlgoExistence(() -> {
            algoRepository.save(algo);
            return null;
        }, algo.getId());
    }

    @Override
    public void updateCompleteStatus(Long id, boolean isCompleted) {
        handleAlgoExistence(() -> {
            algoRepository.updateAlgoCompletionById(id, isCompleted);
            return null;
        }, id);
    }

    @Override
    public Algo findAnAlgoById(Long id) throws ResourceOperationException {
        //.get() is checked in handleAlgoExistence
        return handleAlgoExistence(() -> algoRepository.findById(id).get(), id);
    }

    private Algo handleAlgoExistence(Supplier<Algo> supplier, long id) {
        if (algoRepository.existsById(id)) {
            return supplier.get();
        } else {
            throw ResourceOperationException.builder()
                    .resourceName("Algorithm")
                    .status(HttpStatus.NOT_FOUND)
                    .message("Algorithm cannot be found")
                    .build();
        }
    }

    @Override
    public List<Algo> findAnAlgoByUserId(Long userid) throws ResourceOperationException {
        Optional<List<Algo>> bySysUserId = algoRepository.findAlgoBySysUser_Id(userid);
        bySysUserId.orElseThrow(() -> ResourceOperationException.builder().build());
        return bySysUserId.get();
    }
}
