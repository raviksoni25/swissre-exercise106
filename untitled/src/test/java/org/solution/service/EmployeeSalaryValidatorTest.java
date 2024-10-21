package org.solution.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.solution.dao.DataStore;
import org.solution.dto.Employee;
import org.solution.dto.EmployeeSalaryResponse;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class EmployeeSalaryValidatorTest {

    private DataStore dataStore;
    private EmployeeSalaryValidator validator;

    @BeforeEach
    void setUp() {
        dataStore = new DataStore();
        validator = new EmployeeSalaryValidator(dataStore);
    }

    @Test
    void testValidate_SalaryBelowMin() {
        // Arrange
        Employee manager = new Employee(1, "John", "Doe", 100000L, null);
        Employee employee = new Employee(2, "Jane", "Doe", 100000L, 1);

        dataStore.getEmployeeMap().put(manager.getId(), manager);
        dataStore.getEmployeeMap().put(employee.getId(), employee);
        dataStore.getManagerMap().put(manager.getId(), List.of(employee.getId()));

        // Act
        EmployeeSalaryResponse response = validator.validate();

        // Assert
        assertFalse(response.getEmployeeWithLessSalary().isEmpty());
        assertTrue(response.getEmployeeWithLessSalary().containsKey("John Doe"));
        assertEquals(20000.0, response.getEmployeeWithLessSalary().get("John Doe"));
    }

    @Test
    void testValidate_SalaryAboveMax() {
        // Arrange
        Employee manager = new Employee(1, "John", "Doe", 100000L, null);
        Employee employee = new Employee(2, "Jane", "Doe", 50000L, 1);

        dataStore.getEmployeeMap().put(manager.getId(), manager);
        dataStore.getEmployeeMap().put(employee.getId(), employee);
        dataStore.getManagerMap().put(manager.getId(), List.of(employee.getId()));

        // Act
        EmployeeSalaryResponse response = validator.validate();

        // Assert
        assertFalse(response.getEmployeeWithMoreSalary().isEmpty());
        assertTrue(response.getEmployeeWithMoreSalary().containsKey("John Doe"));
        assertEquals(25000.0, response.getEmployeeWithMoreSalary().get("John Doe"));

    }

    @Test
    void testValidate_DepthMoreThanFour() {
        // Arrange
        Employee topManager = new Employee(1, "John", "Doe", 200000L, null);
        Employee midManager = new Employee(2, "Jane", "Doe", 150000L, 1);
        Employee lowManager1 = new Employee(3, "Jim", "Beam", 120000L, 2);
        Employee lowManager2 = new Employee(4, "Jack", "Daniels", 120000L, 3);
        Employee lowEmployee3 = new Employee(5, "Raj", "Kumar", 90000L, 4);
        Employee lowEmployee4 = new Employee(6, "Tom", "Cruse", 90000L, 5);

        dataStore.getEmployeeMap().put(topManager.getId(), topManager);
        dataStore.getEmployeeMap().put(midManager.getId(), midManager);
        dataStore.getEmployeeMap().put(lowManager1.getId(), lowManager1);
        dataStore.getEmployeeMap().put(lowManager2.getId(), lowManager2);
        dataStore.getEmployeeMap().put(lowEmployee3.getId(), lowEmployee3);
        dataStore.getEmployeeMap().put(lowEmployee4.getId(), lowEmployee4);

        dataStore.getManagerMap().put(topManager.getId(), List.of(midManager.getId()));
        dataStore.getManagerMap().put(midManager.getId(), List.of(lowManager1.getId()));
        dataStore.getManagerMap().put(lowManager1.getId(), List.of(lowManager2.getId()));
        dataStore.getManagerMap().put(lowManager2.getId(), List.of(lowEmployee3.getId()));
        dataStore.getManagerMap().put(lowEmployee3.getId(), List.of(lowEmployee4.getId()));

        // Act
        EmployeeSalaryResponse response = validator.validate();

        // Assert
        assertFalse(response.getEmployeeWithMoreDepth().isEmpty());
        assertTrue(response.getEmployeeWithMoreDepth().containsKey("John Doe"));
        assertEquals(1, response.getEmployeeWithMoreDepth().get("John Doe"));
    }

    @Test
    void testValidate_NoIssues() {
        // Arrange
        Employee manager = new Employee(1, "John", "Doe", 120000L, null);
        Employee employee = new Employee(2, "Jane", "Doe", 100000L, 1);

        dataStore.getEmployeeMap().put(manager.getId(), manager);
        dataStore.getEmployeeMap().put(employee.getId(), employee);
        dataStore.getManagerMap().put(manager.getId(), List.of(employee.getId()));

        // Act
        EmployeeSalaryResponse response = validator.validate();

        // Assert
        assertTrue(response.getEmployeeWithLessSalary().isEmpty());
        assertTrue(response.getEmployeeWithMoreSalary().isEmpty());
        assertTrue(response.getEmployeeWithMoreDepth().isEmpty());
    }

}
