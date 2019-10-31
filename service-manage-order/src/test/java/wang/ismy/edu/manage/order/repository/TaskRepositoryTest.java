package wang.ismy.edu.manage.order.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.domain.task.XcTask;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskRepositoryTest {

    @Autowired
    TaskRepository repository;

    @Test
    public void findByUpdateTimeBefore() {

        Page<XcTask> list = repository.findByUpdateTimeLessThan( new Date(),PageRequest.of(0, 2));
        assertEquals(1,list.getContent().size());
    }
}