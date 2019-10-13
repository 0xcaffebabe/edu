package wang.ismy.edu.manage_course.dao;


import org.apache.ibatis.annotations.Mapper;
import wang.ismy.edu.domain.course.CourseBase;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {

   CourseBase findCourseBaseById(String id);
}
