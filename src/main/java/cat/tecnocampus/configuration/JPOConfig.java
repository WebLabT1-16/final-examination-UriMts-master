package cat.tecnocampus.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Created by roure on 30/11/2016.
 */
@Configuration
public class JPOConfig {

    @Bean
    public RestTemplate restTemplateBean() {
        return new RestTemplate();
    }
}
