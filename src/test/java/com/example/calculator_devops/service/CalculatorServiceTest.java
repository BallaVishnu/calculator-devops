package com.example.calculator_devops.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorServiceTest {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void test1() {
        double result =calculatorService.add(2, 3);
        assertEquals(5, result);
    }

    @Test
    void test2(){
        double result = calculatorService.add(3, -10);
        assertEquals(-7, result);
    }

    @Test
    void test3(){
        double result = calculatorService.add(5.23, -3.46);
        assertEquals(1.77, result, 0.01);
    }

    //subtract
    @Test
    void subtractTest1(){
        double result = calculatorService.subtract(5, 2.8);
        assertEquals(2.2, result, 0.01);
    }

    @Test
    void subtractTest2(){
        double result = calculatorService.subtract(5, -8);
        assertEquals(13.0, result, 0.01);
    }
}
