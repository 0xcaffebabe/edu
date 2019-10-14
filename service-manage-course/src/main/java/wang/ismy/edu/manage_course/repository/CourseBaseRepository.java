package wang.ismy.edu.manage_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.course.CourseBase;

/**
 * @author MY
 * @date 2019/10/14 13:49
 */
public interface CourseBaseRepository extends JpaRepository<CourseBase,String> {
}
