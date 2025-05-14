package nrg.inc.synhubbackend.analytics.infrastructure.client;


import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;

public class TaskManagementClientConfig {

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
        };
    }
}