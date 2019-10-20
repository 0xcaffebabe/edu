package wang.ismy.edu.manage_course.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wang.ismy.edu.domain.course.CoursePub;
import wang.ismy.edu.manage_course.pojo.Course;
import wang.ismy.edu.manage_course.repository.CourseIndexRepository;
import wang.ismy.edu.manage_course.repository.CoursePubRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/10/20 13:29
 */
@Service
@AllArgsConstructor
public class CoursePubService {

    private CourseIndexRepository courseIndexRepository;

    private CoursePubRepository coursePubRepository;

    /**
     * 将数据库course_pub中的数据转储到索引库
     */
    @Transactional(rollbackOn = Exception.class)
    public void saveIndex(){
        courseIndexRepository.deleteAll();
        List<Course> all = coursePubRepository.findAll()
                .stream()
                .map(Course::convert)
                .collect(Collectors.toList());
        courseIndexRepository.saveAll(all);
    }
}
