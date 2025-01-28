package com.cignititech.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.cignititech.dto.EmployeeRequestDTO;
import com.cignititech.dto.EmployeeResponseDTO;
import com.cignititech.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.when;

import java.util.*;

public class EmployeeControllerTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    private MockMvc mockMvc;

    private EmployeeRequestDTO employeeRequestDTO;
    private EmployeeResponseDTO employeeResponseDTO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
        initializeDTOs();
    }

    @Test
    void testCreateEmployee() {
        // Mock service method
        when(employeeService.createEmployee(any(EmployeeRequestDTO.class))).thenReturn(employeeResponseDTO);

        // Call the controller method
        ResponseEntity<EmployeeResponseDTO> response = employeeController.createEmployee(employeeRequestDTO);

        // Verify the response
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(employeeResponseDTO, response.getBody());

        // Verify interaction with the service
        verify(employeeService, times(1)).createEmployee(any(EmployeeRequestDTO.class));
    }

    @Test
    void testGetAllEmployees() {
        List<EmployeeResponseDTO> employees = Arrays.asList(employeeResponseDTO);
        when(employeeService.getAllEmployees()).thenReturn(employees);

        ResponseEntity<List<EmployeeResponseDTO>> response = employeeController.getAllEmployees();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employees, response.getBody());

        verify(employeeService, times(1)).getAllEmployees();
    }

    @Test
    void testGetEmployeeById() {
        when(employeeService.getEmployeeById(1L)).thenReturn(employeeResponseDTO);

        ResponseEntity<EmployeeResponseDTO> response = employeeController.getEmployeeById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeResponseDTO, response.getBody());

        verify(employeeService, times(1)).getEmployeeById(1L);
    }

    @Test
    void testUpdateEmployee() {
        when(employeeService.updateEmployee(eq(1L), any(EmployeeRequestDTO.class))).thenReturn(employeeResponseDTO);

        ResponseEntity<EmployeeResponseDTO> response = employeeController.updateEmployee(1L, employeeRequestDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(employeeResponseDTO, response.getBody());

        verify(employeeService, times(1)).updateEmployee(eq(1L), any(EmployeeRequestDTO.class));
    }

    @Test
    void testDeleteEmployee() {
        doNothing().when(employeeService).deleteEmployee(1L);

        ResponseEntity<Void> response = employeeController.deleteEmployee(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(employeeService, times(1)).deleteEmployee(1L);
    }

    private void initializeDTOs() {
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
