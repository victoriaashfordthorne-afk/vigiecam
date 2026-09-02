package Mbemlevel.example.VigieCam;

import Mbemlevel.example.VigieCam.Config.ModeratorProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(ModeratorProperties.class)
@SpringBootApplication
public class VigieCamApplication {

	public static void main(String[] args) {
		SpringApplication.run(VigieCamApplication.class, args);
	}

}
