package fr.farmeurimmo.backenddemolocation;

import fr.farmeurimmo.backenddemolocation.database.DatabaseManager;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApp {

    public static BackendApp INSTANCE;

    public BackendApp() {
        INSTANCE = this;
    }

    public static void main(String[] args) {
        new BackendApp();

        try {
            SpringApplication.run(BackendApp.class, args);
            System.out.println("Application has started.");
        } catch (Exception e) {
            System.out.println("Failed to start the application.");
            e.printStackTrace(); // Print the stack trace for more details
            throw new RuntimeException(e);
        }
    }

    @PreDestroy
    public void onShutdown() {
        System.out.println("Application is shutting down...");

        DatabaseManager.INSTANCE.close();

        System.out.println("Application has been shut down.");
    }
}
