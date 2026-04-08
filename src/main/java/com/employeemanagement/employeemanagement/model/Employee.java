package com.employeemanagement.employeemanagement.model;

import lombok.Data;

@Data
public class Employee {

    private String id;
    private String name;
    private double salary;
    private String status; // Active / Leave / Resigned
}