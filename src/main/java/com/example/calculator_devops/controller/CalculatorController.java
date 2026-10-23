package com.example.calculator_devops.controller;

import com.example.calculator_devops.service.CalculatorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class CalculatorController {
    private final CalculatorService calculatorService;

    public CalculatorController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @GetMapping("/add")
    //contoller handle request only
    //instead, service handles the business logic
    public Map<String, Object> add(@RequestParam double a, @RequestParam double b){
        return Map.of("operation", "add", 
            "a", a,  
            "b", b, 
            "result", calculatorService.add(a, b));
    }

    @GetMapping("/subtract")
    public Map<String,Object> subtract(@RequestParam double a, @RequestParam double b){
        return Map.of("operation", "subtract",
            "a",a,
            "b",b,
            "result", calculatorService.subtract(a,b));
    }

}
