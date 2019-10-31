package wang.ismy.edu.manage.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author MY
 * @date 2019/10/31 20:24
 */
@SpringBootApplication
@EntityScan("wang.ismy.edu.domain.task")
public class OrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderApplication.class,args);
    }
}
