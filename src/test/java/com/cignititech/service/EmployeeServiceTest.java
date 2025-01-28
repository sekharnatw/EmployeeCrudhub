package com.cignititech.service;


import com.cignititech.dto.EmployeeRequestDTO;
import com.cignititech.dto.EmployeeResponseDTO;
import com.cignititech.entity.Employee;
import com.cignititech.exception.ResourceNotFoundException;
import com.cignititech.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeRequestDTO employeeRequestDTO;
    private EmployeeResponseDTO employeeResponseDTO;

    @BeforeEach
    public void setUp() {
        initializeTestObjects();
    }

    @Test
    void testCreateEmployee() {
        // Mock repository behavior
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the service method
        EmployeeResponseDTO result = employeeService.createEmployee(employeeRequestDTO);

        // Verify the result
        assertNotNull(result);
        assertEquals(employeeResponseDTO.getEmpId(), result.getEmpId());
        assertEquals(employeeResponseDTO.getName(), result.getName());
        assertEquals(employeeResponseDTO.getAge(), result.getAge());
        assertEquals(employeeResponseDTO.getGender(), result.getGender());
        assertEquals(employeeResponseDTO.getCity(), result.getCity());
        assertEquals(employeeResponseDTO.getPinCode(), result.getPinCode());

        // Verify repository interaction
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testGetAllEmployees() {
        // Mock repository behavior
        when(employeeRepository.findAll()).thenReturn(Collections.singletonList(employee));

        // Call the service method
        List<EmployeeResponseDTO> result = employeeService.getAllEmployees();

        // Verify the result
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeResponseDTO.getEmpId(), result.get(0).getEmpId());
        assertEquals(employeeResponseDTO.getName(), result.get(0).getName());
        assertEquals(employeeResponseDTO.getAge(), result.get(0).getAge());
        assertEquals(employeeResponseDTO.getGender(), result.get(0).getGender());
        assertEquals(employeeResponseDTO.getCity(), result.get(0).getCity());
        assertEquals(employeeResponseDTO.getPinCode(), result.get(0).getPinCode());

        // Verify repository interaction
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    void testGetEmployeeById() {
        // Mock repository behavior
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        // Call the service method
        EmployeeResponseDTO result = employeeService.getEmployeeById(1L);

        // Verify the result
        assertNotNull(result);
        assertEquals(employeeResponseDTO.getEmpId(), result.getEmpId());
        assertEquals(employeeResponseDTO.getName(), result.getName());
        assertEquals(employeeResponseDTO.getAge(), result.getAge());
        assertEquals(employeeResponseDTO.getGender(), result.getGender());
        assertEquals(employeeResponseDTO.getCity(), result.getCity());
        assertEquals(employeeResponseDTO.getPinCode(), result.getPinCode());

        // Verify repository interaction
        verify(employeeRepository, times(1)).findById(1L);
    }

    @Test
    void testGetEmployeeById_NotFound() {
        // Mock repository behavior
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(ResourceNotFoundException.class, () -> employeeService.getEmployeeById(999L));

        // Verify repository interaction
        verify(employeeRepository, times(1)).findById(999L);
    }

    @Test
    void testUpdateEmployee() {
        // Mock repository behavior
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Call the service method
        EmployeeResponseDTO result = employeeService.updateEmployee(1L, employeeRequestDTO);

        // Verify the result
        assertNotNull(result);
        assertEquals(employeeResponseDTO.getEmpId(), result.getEmpId());
        assertEquals(employeeResponseDTO.getName(), result.getName());
        assertEquals(employeeResponseDTO.getAge(), result.getAge());
        assertEquals(employeeResponseDTO.getGender(), result.getGender());
        assertEquals(employeeResponseDTO.getCity(), result.getCity());
        assertEquals(employeeResponseDTO.getPinCode(), result.getPinCode());

        // Verify repository interaction
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).save(any(Employee.class));
    }

    @Test
    void testUpdateEmployee_NotFound() {
        // Mock repository behavior
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        assertThrows(ResourceNotFoundException.class, () -> employeeService.updateEmployee(999L, employeeRequestDTO));

        // Verify repository interaction
        verify(employeeRepository, times(1)).findById(999L);
        verify(employeeRepository, never()).save(any(Employee.class));
    }

    @Test
    void testDeleteEmployee() {
        // Mock repository behavior
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        doNothing().when(employeeRepository).delete(any(Employee.class));

        // Call the service method
        employeeService.deleteEmployee(1L);

        // Verify repository interaction
        verify(employeeRepository, times(1)).findById(1L);
        verify(employeeRepository, times(1)).delete(any(Employee.class));
    }

    @Test
    void testDeleteEmployee_NotFound() {
        // Mock repository behavior: Return empty Optional for non-existing employee
        when(employeeRepository.findById(999L)).thenReturn(Optional.empty());

        // Call the service method and expect an exception
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            employeeService.deleteEmployee(999L);
        });

        // Verify the exception message
        assertEquals("Employee not found with ID: 999", exception.getMessage());

        // Verify repository interaction
        verify(employeeRepository, times(1)).findById(999L);
        verify(employeeRepository, never()).delete(any(Employee.class));
    }

    private void initializeTestObjects() {
        employee = Employee.builder()
                .empId(1L)
                .name("John Doe")
                .age(30)
                .gender("Male")
                .city("New York")
                .pinCode("100001")
                .build();

        employeeRequestDTO = EmployeeRequestDTO.builder()
                .name("John Doe")
                .age(30)
                .gender("Male")
                .city("New York")
                .pinCode("100001")
                .build();

        employeeResponseDTO = EmployeeResponseDTO.builder()
                .empId(1L)
                .name("John Doe")
                .age(30)
                .gender("Male")
                .city("New York")
                .pinCode("100001")
                .build();
    }

}