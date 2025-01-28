package com.cignititech.controller;

import com.cignititech.dto.EmployeeRequestDTO;
import com.cignititech.dto.EmployeeResponseDTO;
import com.cignititech.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        log.info("Received request to create employee: {}", employeeRequestDTO);
        EmployeeResponseDTO responseDTO = employeeService.createEmployee(employeeRequestDTO);
        log.info("Employee created successfully: {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        log.info("Received request to fetch all employees");
        List<EmployeeResponseDTO> employees = employeeService.getAllEmployees();
        log.info("Fetched {} employees", employees.size());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeResponseDTO> getEmployeeById(@PathVariable Long empId) {
        log.info("Received request to fetch employee by ID: {}", empId);
        EmployeeResponseDTO responseDTO = employeeService.getEmployeeById(empId);
        log.info("Fetched employee: {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @PutMapping("/{empId}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long empId,
            @Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        log.info("Received request to update employee with ID {}: {}", empId, employeeRequestDTO);
        EmployeeResponseDTO responseDTO = employeeService.updateEmployee(empId, employeeRequestDTO);
        log.info("Employee updated successfully: {}", responseDTO);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long empId) {
        log.info("Received request to delete employee with ID: {}", empId);
        employeeService.deleteEmployee(empId);
        log.info("Employee deleted successfully with ID: {}", empId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
