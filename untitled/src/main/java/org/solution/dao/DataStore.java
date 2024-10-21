package org.solution.dao;

import lombok.Getter;
import lombok.Setter;
import org.solution.dto.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class DataStore {
    private Map<Integer,Employee> employeeMap = new HashMap<>();
    private Map<Integer, List<Integer>> managerMap = new HashMap<>();
}
