package com.cignititech.dto;

import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private Long empId;
    private String name;
    private int age;
    private String gender;
    private String city;
    private String pinCode;
}