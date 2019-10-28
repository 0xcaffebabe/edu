package wang.ismy.edu.learning;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author MY
 * @date 2019/10/23 22:06
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class LearningApplication {
    public static void main(String[] args) {
        SpringApplication.run(LearningApplication.class,args);
    }
}