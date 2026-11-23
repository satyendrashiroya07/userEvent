package shiroya.userEvent.userEvent;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
@EnableScheduling
public class UserEventApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserEventApplication.class, args);

		SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

		String base64Key =
				Base64.getEncoder().encodeToString(key.getEncoded());

		System.out.println("Secrete Key  **************************************************************************************************:   "+base64Key);
	}
}
