package wang.ismy.edu.api.course;

import wang.ismy.edu.domain.course.ext.TeachplanNode;

/**
 * @author MY
 * @date 2019/10/13 22:15
 */
public interface CourseControllerApi {

    /**
     * 根据课程ID查询课程计划列表
     * @param courseId 课程ID
     * @return 课程计划节点列表
     */
    TeachplanNode findList(String courseId);
}
