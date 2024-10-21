package org.solution.service;

import org.solution.dao.DataStore;
import org.solution.dto.Employee;
import org.solution.dto.EmployeeSalaryResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * The EmployeeSalaryValidator class is responsible for validating employee salaries
 * and analyzing reporting structures within a company. It checks for:
 * 1. Employees whose salaries are significantly below or above their subordinate's average salary.
 * 2. Employees who are part of reporting lines longer than four levels deep.
 */
public class EmployeeSalaryValidator {

    private final DataStore dataStore;

    /**
     * Constructs an EmployeeSalaryValidator with the given DataStore.
     *
     * @param dataStore the DataStore containing employee and manager information
     */
    public EmployeeSalaryValidator(DataStore dataStore) {
        this.dataStore = dataStore;
    }


    /**
     * Validates employee salaries and reporting structures.
     *
     * @return an EmployeeSalaryResponse containing employees with salary issues and deep reporting lines
     */
    public EmployeeSalaryResponse validate() {
        EmployeeSalaryResponse response = new EmployeeSalaryResponse();
        List<Employee> employees = new ArrayList<>(dataStore.getEmployeeMap().values());

        // Analyze salaries of employees with managers
        employees.forEach(emp -> {
            double avgSalary = calculateAverageSalary(emp.getId());
            double minSalary = avgSalary * 1.2; // 20% above avg
            double maxSalary = avgSalary * 1.5; // 50% above avg

            // Check if the employee's salary is below the minimum or above the maximum
            if (avgSalary > 0) {
                if (emp.getSalary() < minSalary) {
                    response.getEmployeeWithLessSalary().put(
                            emp.getFullName(), (minSalary - emp.getSalary())
                    );
                } else if (emp.getSalary() > maxSalary) {
                    response.getEmployeeWithMoreSalary().put(
                            emp.getFullName(), (emp.getSalary() - maxSalary)
                    );
                }
            }
        });

        // Check for reporting lines longer than 4 levels
        employees.forEach(emp -> {
            int currentHeight = getHeight(emp.getId());
            if (currentHeight > 4) {
                response.getEmployeeWithMoreDepth().put(
                        emp.getFullName(), (currentHeight - 4)
                );
            }
        });
        return response;
    }

    /**
     * Calculates the average salary of all subordinates under a given manager.
     *
     * @param managerId the ID of the manager
     * @return the average salary of the manager's subordinates, or 0 if there are none
     */
    private double calculateAverageSalary(Integer managerId) {
        List<Integer> subordinates = getAllSubordinates(managerId);
        if (subordinates.isEmpty()) {
            return 0;
        }
        double totalSalary = subordinates.stream()
                .mapToDouble(subId -> dataStore.getEmployeeMap().get(subId).getSalary())
                .sum();
        return totalSalary / subordinates.size();
    }

    /**
     * Recursively retrieves all subordinates under a given manager.
     *
     * @param managerId the ID of the manager
     * @return a list of employee IDs representing all subordinates
     */
    private List<Integer> getAllSubordinates(Integer managerId) {
        List<Integer> allSubordinates = new ArrayList<>();
        if (dataStore.getManagerMap().containsKey(managerId)) {
            dataStore.getManagerMap().get(managerId).forEach(subId -> {
                allSubordinates.add(subId);
                allSubordinates.addAll(getAllSubordinates(subId)); // Recursion for deeper levels
            });
        }
        return allSubordinates;
    }

    /**
     * Calculates the height of the reporting structure for a given manager.
     * Height is defined as the number of levels of subordinates.
     *
     * @param managerId the ID of the manager
     * @return the height of the reporting structure
     */
    private int getHeight(Integer managerId) {
        if (managerId == null) {
            return 0;
        }
        List<Integer> subordinates = dataStore.getManagerMap().get(managerId);
        if (subordinates == null || subordinates.isEmpty()) {
            return 0;
        }
        return subordinates.stream()
                .mapToInt(this::getHeight)
                .max()
                .orElse(0) + 1;
    }
}
