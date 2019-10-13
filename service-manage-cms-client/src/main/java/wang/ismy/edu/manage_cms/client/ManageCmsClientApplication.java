package wang.ismy.edu.manage_cms.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author MY
 * @date 2019/10/13 19:41
 */
@SpringBootApplication
@EntityScan("wang.ismy.edu.domain.cms")
@ComponentScan("wang.ismy")
public class ManageCmsClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsClientApplication.class,args);
    }
}
