package com.employeemanagement.employeemanagement.service;
import com.employeemanagement.employeemanagement.model.Employee;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class FirebaseService {

    private final DatabaseReference dbRef;

    // ✅ Inject FirebaseApp to ensure initialization happens first
    public FirebaseService(FirebaseApp firebaseApp) {
        this.dbRef = FirebaseDatabase.getInstance(firebaseApp)
                .getReference("employees");
    }

    public String addEmployee(Employee employee) {
        String id = dbRef.push().getKey();
        employee.setId(id);
        dbRef.child(id).setValueAsync(employee);
        return id;
    }

    // ✅ GET DATA FROM FIREBASE
    public CompletableFuture<DataSnapshot> getEmployees() {
        CompletableFuture<DataSnapshot> future = new CompletableFuture<>();

        dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                future.complete(snapshot);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                future.completeExceptionally(error.toException());
            }
        });

        return future;
    }

    public void updateEmployee(String id, Employee employee) {
        dbRef.child(id).setValueAsync(employee);
    }

    public void deleteEmployee(String id) {
        dbRef.child(id).removeValueAsync();
    }
}