package com.employeemanagement.employeemanagement.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    @Bean
    public FirebaseApp firebaseApp() throws Exception {

        String firebaseKey = System.getenv("FIREBASE_KEY");
        InputStream serviceAccount;

        if (firebaseKey != null && !firebaseKey.isEmpty()) {
            // ✅ For Render (environment variable)
            serviceAccount = new ByteArrayInputStream(firebaseKey.getBytes());
            System.out.println("Using FIREBASE_KEY from ENV");
        } else {
            // ✅ For Local (resources folder)
            serviceAccount = getClass()
                    .getClassLoader()
                    .getResourceAsStream("firebase.json");

            if (serviceAccount == null) {
                throw new RuntimeException("firebase.json not found in resources");
            }

            System.out.println("Using firebase.json from resources");
        }

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://employeemanagement-3536d-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .build();

        if (FirebaseApp.getApps().isEmpty()) {
            return FirebaseApp.initializeApp(options);
        }

        return FirebaseApp.getInstance();
    }
}