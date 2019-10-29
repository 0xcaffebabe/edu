package wang.ismy.edu.ucenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * @author MY
 * @date 2019/10/29 16:39
 */
@SpringBootApplication
@EntityScan("wang.ismy.edu.domain.ucenter")
public class UserCenterApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserCenterApplication.class,args);
    }
}
