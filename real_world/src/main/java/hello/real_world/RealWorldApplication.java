package hello.real_world;

import hello.real_world.config.Config;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@Import(Config.class)
@SpringBootApplication(scanBasePackages = "hello.real_world.web")
public class RealWorldApplication {

	public static void main(String[] args) {
		SpringApplication.run(RealWorldApplication.class, args);
	}
}
