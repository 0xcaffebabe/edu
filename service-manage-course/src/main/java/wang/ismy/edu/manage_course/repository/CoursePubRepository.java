package wang.ismy.edu.manage_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.course.CoursePub;

/**
 * @author MY
 * @date 2019/10/19 16:06
 */
public interface CoursePubRepository extends JpaRepository<CoursePub,String> { }
