package nrg.inc.synhubbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SynhubBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SynhubBackendApplication.class, args);
    }

}
