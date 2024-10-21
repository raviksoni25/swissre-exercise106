package org.solution.util;

import org.solution.dao.DataStore;
import org.solution.dto.Employee;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileUtil {

    public DataStore readFile(String filePath) {
        DataStore dataStore = new DataStore();
        final Map<Integer, List<Integer>> managerMap = new HashMap<>();
        final Map<Integer, Employee> empoyeeMap = new HashMap<>();

        if (filePath == null || filePath.isBlank()) {
            throw new IllegalArgumentException("Invalid file");
        }
        try (BufferedReader bf = new BufferedReader(new FileReader(filePath))) {
            // Skip the header line
            bf.readLine();

            // Read all lines and process them
            bf.lines().map(this::parseEmployee).forEach(employee -> {
                empoyeeMap.put(employee.getId(),employee);
                if (employee.getManagerId() != null) {
                    managerMap.putIfAbsent(employee.getManagerId(), new ArrayList<>());
                    managerMap.get(employee.getManagerId()).add(employee.getId());
                }
            });

            dataStore.setManagerMap(managerMap);
            dataStore.setEmployeeMap(empoyeeMap);

        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + e.getMessage(), e);
        }
        return dataStore;
    }


    private Employee parseEmployee(String line) {
        String[] data = line.split(",");

        int id = Integer.parseInt(data[0]);
        String firstName = data[1];
        String lastName = data[2];
        long salary = Long.parseLong(data[3]);
        Integer managerId = data.length > 4 && !data[4].isEmpty() ? Integer.parseInt(data[4]) : null;

        return Employee.builder()
                .id(id)
                .firstName(firstName)
                .lastName(lastName)
                .salary(salary)
                .managerId(managerId)
                .build();
    }

}
