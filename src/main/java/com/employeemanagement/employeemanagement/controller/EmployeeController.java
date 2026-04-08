package com.employeemanagement.employeemanagement.controller;
import com.employeemanagement.employeemanagement.model.Employee;
import com.employeemanagement.employeemanagement.service.FirebaseService;
import com.google.firebase.database.DataSnapshot;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/employees")
@CrossOrigin
public class EmployeeController {
    private final FirebaseService service;

    public EmployeeController(FirebaseService service) {
        this.service = service;
    }
    // ✅ ADD EMPLOYEE
    @PostMapping
    public String createEmployee(@RequestBody Employee employee) {
        return service.addEmployee(employee);
    }
    // ✅ GET ALL EMPLOYEES (FIXED)
    @GetMapping
    public Object getEmployees() throws Exception {
        DataSnapshot snapshot = service.getEmployees().get();
        return snapshot.getValue();
    }
    // ✅ UPDATE EMPLOYEE
    @PutMapping("/{id}")
    public String updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
        service.updateEmployee(id, employee);
        return "Updated Successfully";
    }
    // ✅ DELETE EMPLOYEE
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable String id) {
        service.deleteEmployee(id);
        return "Deleted Successfully";
    }
}