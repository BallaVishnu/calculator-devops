package com.example.calculator_devops.controller;
import com.example.calculator_devops.service.CalculatorService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CalculatorController.class)
class CalculatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private CalculatorService calculatorService;
    
    @Test
    void apiAdd() throws Exception{
        when(calculatorService.add(2, 3.12)).thenReturn(5.12);
        mockMvc.perform(get("/api/v1/add").param("a","2").param("b","3.12"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.operation").value("add"))
        .andExpect(jsonPath("$.a").value("2.0"))
        .andExpect(jsonPath("$.b").value("3.12"))
        .andExpect(jsonPath("$.result").value("5.12"));
    }

    @Test
    void apiAddNegative() throws Exception{
        when(calculatorService.add(-5, 8.32)).thenReturn(3.32);
        mockMvc.perform(get("/api/v1/add").param("a","-5").param("b","8.32"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.operation").value("add"))
        .andExpect(jsonPath("$.a").value("-5.0"))
        .andExpect(jsonPath("$.b").value("8.32"))
        .andExpect(jsonPath("$.result").value("3.32"));
    }
}