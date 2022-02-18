package com.ai.sys.controller;

import com.ai.sys.exception.ResourceOperationException;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.model.entity.DataSet;
import com.ai.sys.service.DataSetService;
import com.ai.sys.service.FileTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/dataset")
public class DataSetController {

    private final DataSetService dataSetService;
    private final FileTransferService fileTransferService;

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


    @PostMapping(value = "/simples", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addAlgoWithFile(@RequestPart("dataset") DataSet dataSet, @RequestPart("file") MultipartFile file) {
        try {
            String algoScriptPath = fileTransferService.save(file);
            dataSet.setPath(algoScriptPath);
            dataSetService.create(dataSet);
            return ResponseEntity
                    .ok()
                    .body("Dataset created, and algo script path is: " + algoScriptPath);
        } catch (ResourceOperationException e) {
            log.debug("create dataset failed");
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }
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
