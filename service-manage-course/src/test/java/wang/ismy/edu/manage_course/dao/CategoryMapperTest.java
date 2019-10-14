package wang.ismy.edu.manage_course.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.domain.course.ext.CategoryNode;

import javax.swing.event.ListDataEvent;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryMapperTest {

    @Autowired
    CategoryMapper categoryMapper;

    @Test
    public void findList() {

    }
}