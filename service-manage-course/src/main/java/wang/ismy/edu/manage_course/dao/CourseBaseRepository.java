package wang.ismy.edu.manage_course.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.course.CourseBase;


/**
 * @author MY
 */
public interface CourseBaseRepository extends JpaRepository<CourseBase,String> { }