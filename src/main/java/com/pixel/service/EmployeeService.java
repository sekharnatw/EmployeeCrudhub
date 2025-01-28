package com.pixel.service;

import com.pixel.dto.EmployeeRequestDTO;
import com.pixel.dto.EmployeeResponseDTO;
import com.pixel.entity.Employee;
import com.pixel.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = new Employee();
        employee.setName(employeeRequestDTO.getName());
        employee.setAge(employeeRequestDTO.getAge());
        employee.setGender(employeeRequestDTO.getGender());
        employee.setCity(employeeRequestDTO.getCity());
        employee.setPinCode(employeeRequestDTO.getPinCode());

        Employee savedEmployee = employeeRepository.save(employee);
        return mapToResponseDTO(savedEmployee);
    }

    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    public EmployeeResponseDTO getEmployeeById(Long empId) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return mapToResponseDTO(employee);
    }

    public EmployeeResponseDTO updateEmployee(Long empId, EmployeeRequestDTO employeeRequestDTO) {
        Employee employee = employeeRepository.findById(empId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setName(employeeRequestDTO.getName());
        employee.setAge(employeeRequestDTO.getAge());
        employee.setGender(employeeRequestDTO.getGender());
        employee.setCity(employeeRequestDTO.getCity());
        employee.setPinCode(employeeRequestDTO.getPinCode());

        Employee updatedEmployee = employeeRepository.save(employee);
        return mapToResponseDTO(updatedEmployee);
    }

    public void deleteEmployee(Long empId) {
        employeeRepository.deleteById(empId);
    }

    private EmployeeResponseDTO mapToResponseDTO(Employee employee) {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        responseDTO.setEmpId(employee.getEmpId());
        responseDTO.setName(employee.getName());
        responseDTO.setAge(employee.getAge());
        responseDTO.setGender(employee.getGender());
        responseDTO.setCity(employee.getCity());
        responseDTO.setPinCode(employee.getPinCode());
        return responseDTO;
    }
}