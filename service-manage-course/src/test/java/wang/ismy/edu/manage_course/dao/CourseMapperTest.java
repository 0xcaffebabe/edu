package wang.ismy.edu.manage_course.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.domain.course.CourseBase;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CourseMapperTest {

    @Autowired
    CourseMapper courseMapper;

    @Test
    public void findCourseListPage() {

    }
}