package sia.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sia.app.model.User;
import sia.app.repository.UserRepository;

@SpringBootApplication
public class FinalProjectApplication implements CommandLineRunner {
    private final UserRepository userRepository;

    public FinalProjectApplication(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        if(userRepository.findAll().isEmpty()){
            userRepository.save(new User("login1","password1", false));
            userRepository.save(new User("login2","password2", false));
            userRepository.save(new User("login3","password3", true));
        }
    }
}
