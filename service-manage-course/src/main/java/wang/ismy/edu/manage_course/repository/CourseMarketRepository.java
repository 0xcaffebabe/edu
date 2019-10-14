package wang.ismy.edu.manage_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.course.CourseMarket;

/**
 * @author MY
 * @date 2019/10/14 21:26
 */
public interface CourseMarketRepository extends JpaRepository<CourseMarket,String> {
}
