package wang.ismy.edu.learning.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.learning.XcLearningCourse;

/**
 * @author MY
 * @date 2019/11/1 14:02
 */
public interface LearningCourseRepository extends JpaRepository<XcLearningCourse,String> {

    /**
     * 根据课程ID与用户ID查询
     * @param courseId
     * @param userId
     * @return 学习课程联系
     */
    XcLearningCourse findByCourseIdAndUserId(String courseId,String userId);
}
