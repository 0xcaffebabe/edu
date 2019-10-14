package wang.ismy.edu.manage_course.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.course.Teachplan;

import java.util.List;

/**
 * @author MY
 * @date 2019/10/14 13:45
 */
public interface TeachPlanRepository extends JpaRepository<Teachplan,String> {

    List<Teachplan> findByCourseidAndParentid(String courseId, String parentId);
}
