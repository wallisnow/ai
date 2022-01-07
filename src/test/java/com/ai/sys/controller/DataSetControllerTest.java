package com.ai.sys.controller;

import com.ai.sys.service.DataSetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = DataSetController.class)
class DataSetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DataSetService dataSetService;

    @Test
    void findDataSetById() throws Exception {
        mockMvc.perform(get("/api/v1/dataset/").param("name", "mydata1"))
                .andExpect(status().isOk());
    }
}