package app;

import app.database.util.DbInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"app.controllers"})
@ComponentScan({"app"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

        DbInitializer.initialize();
    }
}
