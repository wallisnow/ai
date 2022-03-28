//package com.ai.sys.service;
//
//import com.ai.sys.exception.ResourceOperationException;
//import com.ai.sys.model.entity.AlgoType;
//import com.ai.sys.repository.AlgoTypeRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class AlgoTypeServiceImpl implements AlgoTypeService {
//
//    private final AlgoTypeRepository algoTypeRepository;
//
//    @Override
//    public void create(AlgoType algoType) throws ResourceOperationException {
//        algoTypeRepository.save(algoType);
//    }
//
//    @Override
//    public void delete(String name) throws ResourceOperationException {
//        algoTypeRepository.deleteByName(name);
//    }
//
//    @Override
//    public AlgoType find(String name) throws ResourceOperationException {
//        return algoTypeRepository.findByName(name).orElseThrow(() -> ResourceOperationException.builder()
//                .status(HttpStatus.NOT_FOUND)
//                .resourceName("AlgoType")
//                .message("AlgoType cannot be found!")
//                .build());
//    }
//}
