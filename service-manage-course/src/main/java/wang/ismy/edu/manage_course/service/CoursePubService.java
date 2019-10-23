package wang.ismy.edu.manage_course.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import wang.ismy.edu.domain.course.CoursePub;
import wang.ismy.edu.manage_course.pojo.Course;
import wang.ismy.edu.manage_course.pojo.MediaIndex;
import wang.ismy.edu.manage_course.repository.CourseIndexRepository;
import wang.ismy.edu.manage_course.repository.CoursePubRepository;
import wang.ismy.edu.manage_course.repository.MediaIndexRepository;
import wang.ismy.edu.manage_course.repository.TeachPlanMediaRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/10/20 13:29
 */
@Service
@AllArgsConstructor
@Slf4j
public class CoursePubService {

    private CourseIndexRepository courseIndexRepository;
    private CoursePubRepository coursePubRepository;

    private MediaIndexRepository mediaIndexRepository;
    private TeachPlanMediaRepository teachPlanMediaRepository;

    /**
     * 将数据库course_pub中的数据转储到索引库
     */
    @Transactional(rollbackOn = Exception.class)
    @Scheduled(fixedDelay = 60*1000)
    public void saveCourseIndex(){
        log.info("course索引转储");
        courseIndexRepository.deleteAll();
        List<Course> all = coursePubRepository.findAll()
                .stream()
                .map(Course::convert)
                .collect(Collectors.toList());
        courseIndexRepository.saveAll(all);
    }

    /**
    * 将teachplan_media 表中的数据转储到索引库
    * */
    @Transactional(rollbackOn = Exception.class)
    @Scheduled(fixedDelay = 60*1000)
    public void saveMediaIndex(){
        log.info("media索引转储");
        mediaIndexRepository.deleteAll();
        List<MediaIndex> collect = teachPlanMediaRepository.findAll()
                .stream()
                .map(MediaIndex::convert)
                .collect(Collectors.toList());
        mediaIndexRepository.saveAll(collect);
    }

}
