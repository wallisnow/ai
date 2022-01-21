package com.ai.sys.controller;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.service.AlgoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/algo")
public class AlgoController {
    private final AlgoService algoService;

    @GetMapping("/{name}")
    public @ResponseBody
    ResponseEntity<Algo> findAlgoByName(@PathVariable("name") String name) {
        try {
            Algo anAlgoByName = algoService.findAnAlgoByName(name);
            return ResponseEntity.ok(anAlgoByName);
        } catch (ResourceOperationException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping(value = "/", consumes = {"application/json"})
    public ResponseEntity<String> addAlgo(@RequestBody Algo algo) {
        try {
            algoService.create(algo);
            return ResponseEntity.ok().build();
        } catch (ResourceOperationException e) {
            log.debug("create algo failed");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> deleteAlgo(@PathVariable("name") String name) {
        try {
            Algo anAlgoByName = algoService.findAnAlgoByName(name);
            if (Objects.isNull(anAlgoByName)) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            } else {
                algoService.deleteAlgoById(anAlgoByName.getId());
            }
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/", consumes = {"application/json"})
    public ResponseEntity<String> modifyAlgo(@RequestBody Algo algo) {
        try {
            algoService.update(algo);
            return ResponseEntity.ok().build();
        } catch (ResourceOperationException e) {
            log.debug("update Algo failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}