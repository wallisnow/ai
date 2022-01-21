package com.ai.sys.controller;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.DataSet;
import com.ai.sys.service.DataSetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dataset")
public class DataSetController {

    private final DataSetService dataSetService;

    @GetMapping("/{id}")
    public @ResponseBody
    DataSet findDataSetById(@PathVariable("id") long id) {
        return dataSetService.findById(id);
    }

    @GetMapping("/")
    public @ResponseBody
    List<DataSet> findDataSetByName(@RequestParam("name") String name) {
        return dataSetService.findByName(name);
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<String> addDataSet(@RequestBody DataSet dataSet) {
        try {
            dataSetService.create(dataSet);
            return ResponseEntity.ok().build();
        } catch (ResourceOperationException e) {
            log.debug("create dataset failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @PutMapping(value = "/", consumes = {"application/json"})
    public ResponseEntity<String> modifyDataSet(@RequestBody DataSet dataSet) {
        try {
            dataSetService.update(dataSet);
            return ResponseEntity.ok().build();
        } catch (ResourceOperationException e) {
            log.debug("update dataset failed");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<HttpStatus> deleteDataSet(@PathVariable("name") String name) {
        try {
            dataSetService.deleteByName(name);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public List<DataSet> findAllDataSet() {
        return dataSetService.findAll();
    }

}
