package wang.ismy.edu.manage_cms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;


/**
 * @author MY
 * @date 2019/10/7 21:44
 */
@SpringBootApplication
@EntityScan("wang.ismy.edu.domain.cms")
public class ManageCmsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCmsApplication.class,args);
    }
}
