package nrg.inc.synhubbackend.analytics.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = {
        "nrg.inc.synhubbackend.analytics",
        "nrg.inc.synhubbackend.taskManagement.interfaces.rest.resources"
})
@EnableFeignClients(basePackages = "nrg.inc.synhubbackend.analytics.infrastructure.client")
public class AnalyticsApplication {
    public static void main(String[] args) {
        SpringApplication.run(AnalyticsApplication.class, args);
    }
}