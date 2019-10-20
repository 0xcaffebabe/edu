package wang.ismy.edu.manage_course.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wang.ismy.edu.manage_course.pojo.Course;

/**
 * @author MY
 * @date 2019/10/20 13:40
 */
public interface CourseIndexRepository extends ElasticsearchRepository<Course,String> { }
