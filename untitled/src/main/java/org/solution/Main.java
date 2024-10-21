package org.solution;

import org.solution.dao.DataStore;
import org.solution.dto.EmployeeSalaryResponse;
import org.solution.service.EmployeeSalaryValidator;
import org.solution.util.FileUtil;

public class Main {
    public static void main(String[] args) {
        // Default file path
        String defaultFilePath = "src/main/resources/employee_salary.csv";

        // Check if a file path is provided as an argument
        String filePath;
        if (args.length > 0 && args[0] != null && !args[0].isBlank()) {
            filePath = args[0]; // Use the provided path
        } else {
            filePath = defaultFilePath; // Use the default path
        }

        // Use FileUtil to read the file
        FileUtil fileUtil = new FileUtil();
        DataStore dataStore = fileUtil.readFile(filePath);

        EmployeeSalaryValidator employeeSalaryValidator = new EmployeeSalaryValidator(dataStore);
        EmployeeSalaryResponse response = employeeSalaryValidator.validate();

        displayResults(response);
    }

    private static void displayResults(EmployeeSalaryResponse response) {
        System.out.println("\nManager(s) getting less salary (count: " + response.getEmployeeWithLessSalary().size() + "):");
        response.getEmployeeWithLessSalary().forEach((name, amount) ->
                System.out.printf("%s earns less by %.2f%n", name, amount)
        );

        System.out.println("\nManager(s) getting more salary (count: " + response.getEmployeeWithMoreSalary().size() + "):");
        response.getEmployeeWithMoreSalary().forEach((name, amount) ->
                System.out.printf("%s earns more by %.2f%n", name, amount)
        );

        System.out.println("\nManager(s) having depth more than 4 (count: " + response.getEmployeeWithMoreDepth().size() + "):");
        response.getEmployeeWithMoreDepth().forEach((name, depth) ->
                System.out.printf("%s has a reporting depth of %d%n", name, depth)
        );
    }
}