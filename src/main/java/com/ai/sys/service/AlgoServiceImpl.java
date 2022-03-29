package com.ai.sys.service;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.repository.AlgoRepository;
import com.ai.sys.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlgoServiceImpl implements AlgoService {
    private final AlgoRepository algoRepository;
    private final CategoryRepository categoryRepository;

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
        Optional<Algo> byId = algoRepository.findById(algo.getId());
        byId.orElseThrow(() -> ResourceOperationException.builder()
                .resourceName("Algorithm")
                .status(HttpStatus.NOT_FOUND)
                .message("Algorithm cannot be found")
                .build());


//        AlgoType algoType = algo.getAlgoType();
//        Algo algoToUpdate = byId.get();

//        //update algo type if not null
//        Optional.ofNullable(algoType).ifPresent(
//                typeObjToUse -> {
//                    AlgoType typeToUse = algoTypeRepository.getById(typeObjToUse.getName());
//                    algoToUpdate.setAlgoType(typeToUse);
//                }
//        );

        Algo algoToUpdate = byId.get();

        algoToUpdate.setName(algo.getName());
        algoToUpdate.setDescription(algo.getDescription());
        algoToUpdate.setPath(algo.getPath());
        algoRepository.save(algoToUpdate);
    }

    public Algo findAnAlgoById(Long id) throws ResourceOperationException{
        Optional<Algo> byName = algoRepository.findById(id);
        byName.orElseThrow(()-> ResourceOperationException.builder().build());
        return byName.get();
    }

    @Override
    public List<Algo> findAnAlgoByUserId(Long userid) throws ResourceOperationException {
        Optional<List<Algo>> bySysUser_id = algoRepository.findAlgoBySysUser_Id(userid);
        bySysUser_id.orElseThrow(()-> ResourceOperationException.builder().build());
        return bySysUser_id.get();
    }
}
