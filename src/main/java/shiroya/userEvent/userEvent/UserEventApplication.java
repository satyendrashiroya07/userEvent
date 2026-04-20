package shiroya.userEvent.userEvent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserEventApplication.class, args);
	}

}
