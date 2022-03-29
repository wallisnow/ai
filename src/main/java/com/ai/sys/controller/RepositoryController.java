package com.ai.sys.controller;


import com.ai.sys.model.entity.Repository;
import com.ai.sys.service.RepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/repository")
public class RepositoryController {
    private final RepositoryService repositoryService;

    @GetMapping("/all")
    public List<Repository> findAllDataSet() {
        return repositoryService.findAll();
    }

}
