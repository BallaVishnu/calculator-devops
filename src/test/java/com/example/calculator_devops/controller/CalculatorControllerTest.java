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
        .andExpect(jsonPath("$.a").value(2.0))
        .andExpect(jsonPath("$.b").value(3.12))
        .andExpect(jsonPath("$.result").value(5.12));
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

    @Test
    void subtractTest1() throws Exception{
        when(calculatorService.subtract(5.0, 3.12)).thenReturn(1.88);
        mockMvc.perform(get("/api/v1/subtract").param("a","5.0").param("b","3.12"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.operation").value("subtract"))
        .andExpect(jsonPath("$.a").value("5.0"))
        .andExpect(jsonPath("$.b").value("3.12"))
        .andExpect(jsonPath("$.result").value("1.88"));
    }

    @Test
    void multiplyTest1() throws Exception {
        when(calculatorService.multiply(4.0, 5.0)).thenReturn(20.0);
        mockMvc.perform(get("/api/v1/multiply").param("a", "4").param("b", "5"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.operation").value("multiply"))
        .andExpect(jsonPath("$.a").value(4.0))
        .andExpect(jsonPath("$.b").value(5.0))
        .andExpect(jsonPath("$.result").value(20.0));
    }

    @Test
    void divideTest1() throws Exception {
        when(calculatorService.divide(50.0, 10.0)).thenReturn(5.0);
        mockMvc.perform(get("/api/v1/divide").param("a", "50").param("b", "10"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.operation").value("divide"))
        .andExpect(jsonPath("$.a").value(50.0))
        .andExpect(jsonPath("$.b").value(10.0))
        .andExpect(jsonPath("$.result").value(5.0));
    }

    @Test
    void divideByZeroTest() throws Exception {
        when(calculatorService.divide(10.0, 0.0)).thenThrow(new IllegalArgumentException("Cannot divide by zero"));
        mockMvc.perform(get("/api/v1/divide").param("a", "10").param("b", "0"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error")
        .value("Cannot divide by zero"));
    }

    @Test
    void missingParameterTest() throws Exception {
        mockMvc.perform(get("/api/v1/add").param("a", "10"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error")
        .value("Invalid request parameters"));
    }

    @Test
    void invalidParameterTest() throws Exception {
        mockMvc.perform(get("/api/v1/subtract").param("a", "hello").param("b", "2"))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.error")
        .value("Invalid request parameters"));
    }
}