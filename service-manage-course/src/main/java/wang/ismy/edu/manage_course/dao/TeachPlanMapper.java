package wang.ismy.edu.manage_course.dao;

import wang.ismy.edu.domain.course.ext.TeachplanNode;

/**
 * @author MY
 * @date 2019/10/14 13:06
 */
public interface TeachPlanMapper {

    TeachplanNode selectList(String id);
}
