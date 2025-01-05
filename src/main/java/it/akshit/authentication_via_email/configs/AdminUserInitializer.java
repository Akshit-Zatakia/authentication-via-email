package it.akshit.authentication_via_email.configs;

import it.akshit.authentication_via_email.models.User;
import it.akshit.authentication_via_email.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Value("${admin.email:akshitzatakia17@gmail.com}")
    private String adminUserEmail;

    public AdminUserInitializer(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail(adminUserEmail).isEmpty()) {
            User adminUser = new User();
            adminUser.setEmail(adminUserEmail);
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Admin");
            adminUser.setRoles(List.of("ADMIN"));
            adminUser.setIsActive(true);
            userRepository.save(adminUser);
            System.out.println("Admin user created: " + adminUserEmail);
        } else {
            System.out.println("Admin user already exists: " + adminUserEmail);
        }
    }
}
