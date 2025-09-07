package dat250.oblig;

import dat250.oblig.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
public class ObligApplication {

	public static void main(String[] args) {
		SpringApplication.run(ObligApplication.class, args);
	}
}
