package wang.ismy.edu.manage_course.dao;

import wang.ismy.edu.domain.course.ext.TeachplanNode;

/**
 * @author MY
 * @date 2019/10/13 22:51
 */
public interface TeachplanMapper {

    TeachplanNode selectList(String courseId);
}
