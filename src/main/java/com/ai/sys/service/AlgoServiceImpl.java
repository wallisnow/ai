package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.repository.AlgoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
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
                    .message("Algorithm create failed: " + e.getCause().getCause().getMessage())
                    .build();
        }
    }

    public void deleteAlgoById(long id) {
        algoRepository.deleteById(id);
    }

    public void update(Algo algo) {
        boolean exists = algoRepository.existsById(algo.getId());
        if (exists) {
            algoRepository.save(algo);
        } else {
            throw ResourceOperationException.builder()
                    .resourceName("Algorithm")
                    .status(HttpStatus.NOT_FOUND)
                    .message("Algorithm cannot be found")
                    .build();
        }
    }

    @Override
    public void updateCompleteStatus(Long id, boolean isCompleted) {
        if (!algoRepository.existsById(id)) {
            throw ResourceOperationException.builder()
                    .resourceName("Algorithm")
                    .status(HttpStatus.NOT_FOUND)
                    .message("Algorithm cannot be found")
                    .build();
        }
        algoRepository.updateAlgoCompletionById(id, isCompleted);
    }

    public Algo findAnAlgoById(Long id) throws ResourceOperationException {
        Optional<Algo> byName = algoRepository.findById(id);
        byName.orElseThrow(() -> ResourceOperationException.builder()
                .message("算法不存在!")
                .status(HttpStatus.NOT_FOUND)
                .build());
        return byName.get();
    }

    @Override
    public List<Algo> findAnAlgoByUserId(Long userid) throws ResourceOperationException {
        Optional<List<Algo>> bySysUserId = algoRepository.findAlgoBySysUser_Id(userid);
        bySysUserId.orElseThrow(() -> ResourceOperationException.builder().build());
        return bySysUserId.get();
    }
}
