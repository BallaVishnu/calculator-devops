package com.example.calculator_devops.service;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CalculatorServiceTest {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void test1() {
        double result =calculatorService.add(2, 3);
        assertEquals(5.0, result, 0.01);
    }

    @Test
    void test2(){
        double result = calculatorService.add(3, -10);
        assertEquals(-7.0, result, 0.01);
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

    //multiply
    @Test
    void multiplyTest1(){
        double result = calculatorService.multiply(5, 2.8);
        assertEquals(14.0, result, 0.01);
    }

    @Test
    void multiplyTest2(){
        double result = calculatorService.multiply(5, -8);
        assertEquals(-40.0, result, 0.01);
    }

    @Test
    void divideTest1() {
        assertEquals(5.0, calculatorService.divide(10.0, 2.0));
    }

    @Test
    void divideByZero() {
        assertThrows(
            IllegalArgumentException.class,
            () -> calculatorService.divide(10.0, 0.0)
        );
    }
}
