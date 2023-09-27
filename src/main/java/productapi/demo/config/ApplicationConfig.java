package productapi.demo.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

  @Bean
  public Dotenv dotenv() {
    return Dotenv.configure().directory(System.getProperty("user.dir")).load();
  }
}
