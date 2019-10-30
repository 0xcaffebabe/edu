package wang.ismy.edu.manage_course.dao;

import com.github.pagehelper.Page;
import wang.ismy.edu.domain.course.CourseBase;
import wang.ismy.edu.domain.course.ext.CourseInfo;
import wang.ismy.edu.domain.course.request.CourseListRequest;

/**
 * @author MY
 * @date 2019/10/14 19:43
 */
public interface CourseMapper {

    CourseBase find(String courseId);

    Page<CourseInfo> findCourseListPage(String companyId);
}
