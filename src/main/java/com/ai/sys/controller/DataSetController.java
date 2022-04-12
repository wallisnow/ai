package com.ai.sys.controller;

import com.ai.sys.common.Response;
import com.ai.sys.model.entity.DataSet;
import com.ai.sys.service.DataSetService;
import com.ai.sys.service.FileTransferService;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/all")
    public List<DataSet> findAllDataSet() {
        return dataSetService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody
    DataSet findDataSetById(@PathVariable("id") long id) {
        return dataSetService.findById(id);
    }


    @PostMapping(value = "/simples", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<String> addAlgoWithFile(@RequestPart("dataset") DataSet dataSet, @RequestPart("file") MultipartFile file) {
        try {
            String algoScriptPath = fileTransferService.save(file);
            dataSet.setPath(algoScriptPath);
            dataSetService.create(dataSet);
            return ResponseEntity
                    .ok()
                    .body("Dataset created, and algo script path is: " + algoScriptPath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Could not upload the file: " + file.getOriginalFilename() + "!");
        }
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public ResponseEntity<String> addDataSet(@RequestBody DataSet dataSet) {
        dataSetService.create(dataSet);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/modify", consumes = {"application/json"})
    public Response modifyDataSet(@RequestBody DataSet dataSet) {
        dataSetService.update(dataSet);
        return Response.httpOk();
    }

    @DeleteMapping("/{id}")
    public Response deleteDataSet(@PathVariable("id") Long id) {
        dataSetService.deleteById(id);
        return Response.httpOk();
    }
}
