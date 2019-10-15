package wang.ismy.edu.manage_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.course.CoursePic;

/**
 * @author MY
 * @date 2019/10/15 20:10
 */
public interface CoursePicRepository extends JpaRepository<CoursePic,String> {

    long deleteByCourseid(String courseid);
}
