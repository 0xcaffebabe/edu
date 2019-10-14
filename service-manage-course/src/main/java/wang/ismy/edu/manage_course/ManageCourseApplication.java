package wang.ismy.edu.manage_course;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @author MY
 * @date 2019/10/13 22:49
 */
@SpringBootApplication
@MapperScan("wang.ismy.edu.manage_course.dao")
@EntityScan("wang.ismy.edu.domain.course")
public class ManageCourseApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageCourseApplication.class,args);
    }
}
