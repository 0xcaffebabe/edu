package wang.ismy.edu.manage_course.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.domain.course.ext.TeachplanNode;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TeachplanMapperTest {

    @Autowired
    TeachplanMapper mapper;

    @Test
    public void test(){
        TeachplanNode teachplanNode = mapper.selectList("4028e581617f945f01617f9dabc40000");

        System.out.println(teachplanNode);
    }
}