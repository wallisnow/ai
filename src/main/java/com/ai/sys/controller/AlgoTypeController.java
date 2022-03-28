//package com.ai.sys.controller;
//
//import com.ai.sys.exception.ResourceOperationException;
//import com.ai.sys.model.entity.AlgoType;
//import com.ai.sys.service.AlgoTypeService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.log4j.Log4j2;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@Slf4j
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/api/v1/algotype")
//public class AlgoTypeController {
//
//    private final AlgoTypeService algoTypeService;
//
//    @GetMapping("/{name}")
//    public @ResponseBody
//    ResponseEntity<AlgoType> findAlgoTypeByName(@PathVariable("name") String name) {
//        try {
//            AlgoType algoType = algoTypeService.find(name);
//            return ResponseEntity.ok(algoType);
//        } catch (ResourceOperationException e) {
//            return ResponseEntity
//                    .status(e.getStatus())
//                    .body(null);
//        }
//    }
//
//    @PostMapping(value = "/add", consumes = {"application/json"})
//    public ResponseEntity<String> addAlgoType(@RequestBody AlgoType algoType) {
//        try {
//            algoTypeService.create(algoType);
//            return ResponseEntity.ok().build();
//        } catch (ResourceOperationException e) {
//            log.debug("create category failed");
//            return ResponseEntity
//                    .status(HttpStatus.BAD_REQUEST)
//                    .body(e.getMessage());
//        }
//    }
//
//    @DeleteMapping("/{name}")
//    public ResponseEntity<HttpStatus> deleteAlgoType(@PathVariable("name") String name) {
//        try {
//            algoTypeService.delete(name);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (Exception e) {
//            log.error(String.valueOf(e.getStackTrace()));
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//}
