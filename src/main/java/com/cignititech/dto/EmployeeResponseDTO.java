package com.cignititech.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeResponseDTO {
    private Long empId;
    private String name;
    private int age;
    private String gender;
    private String city;
    private String pinCode;
}