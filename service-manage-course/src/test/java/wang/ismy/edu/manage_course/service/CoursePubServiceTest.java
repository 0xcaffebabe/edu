package wang.ismy.edu.manage_course.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.manage_course.repository.CourseIndexRepository;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoursePubServiceTest {

    @Autowired
    CoursePubService coursePubService;

    @Autowired


    @Test
    public void test(){

    }

    @Test
    public void saveIndex(){
        coursePubService.saveIndex();
    }
}