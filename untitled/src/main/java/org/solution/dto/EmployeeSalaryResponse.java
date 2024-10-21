package org.solution.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
public class EmployeeSalaryResponse {
    private Map<String, Double> employeeWithLessSalary = new HashMap<>();
    private Map<String, Double> employeeWithMoreSalary = new HashMap<>();
    private Map<String, Integer> employeeWithMoreDepth = new HashMap<>();
}
