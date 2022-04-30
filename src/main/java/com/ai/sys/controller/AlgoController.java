package com.ai.sys.controller;

import com.ai.sys.common.Response;
import com.ai.sys.model.entity.Algo;
import com.ai.sys.service.AlgoService;
import com.ai.sys.service.FileTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/algo")
public class AlgoController {

    private final AlgoService algoService;
    private final FileTransferService fileTransferService;

    @GetMapping("/{id}")
    //@PreAuthorize("hasAuthority('DEVELOPER')")
    public @ResponseBody
    Response findAlgoById(@PathVariable("id") Long id) {
        Algo anAlgoByName = algoService.findAnAlgoById(id);
        return Response.httpOk(anAlgoByName);
    }

    @GetMapping("/list/{userid}")
    //@PreAuthorize("hasAuthority('DEVELOPER')")
    public @ResponseBody
    Response findAlgoByUserId(@PathVariable("userid") Long userid) {
        List<Algo> anAlgoById = algoService.findAnAlgoByUserId(userid);
        return Response.httpOk(anAlgoById);
    }

    @PostMapping(value = "/script", consumes = {MediaType.MULTIPART_MIXED_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
    //@PreAuthorize("hasAnyAuthority('ASSISTANT_MANAGER', 'MANAGER', 'ADMIN')")
    public Response addAlgoWithFile(Algo algo, @RequestParam("file") MultipartFile file) {
        try {
            String algoScriptPath = fileTransferService.save(file);
            algo.setPath(algoScriptPath);
            algoService.create(algo);
            String msg = "Algo created, and algo script path is: " + file.getOriginalFilename();
            return Response.httpOk(msg);
        } catch (IOException e) {
            e.printStackTrace();
            String msg = "Could not upload the file: " + file.getOriginalFilename() + "!";
            return Response.httpError(HttpStatus.BAD_REQUEST, msg);
        }
    }

    @PostMapping(value = "/add", consumes = {"application/json"})
    public Response addAlgo(@RequestBody Algo algo) {
        algoService.create(algo);
        return Response.httpOk();
    }

    @DeleteMapping("/{id}")
    public Response deleteAlgo(@PathVariable("id") Long id) {
        algoService.deleteAlgoById(id);
        return Response.httpOk();
    }

    @PutMapping(value = "/", consumes = {"application/json"})
    public Response modifyAlgo(@RequestBody Algo algo) {
        algoService.update(algo);
        return Response.httpOk();
    }
}
