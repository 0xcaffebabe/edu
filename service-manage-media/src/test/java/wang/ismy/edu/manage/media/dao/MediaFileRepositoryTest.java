package wang.ismy.edu.manage.media.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.manage.media.service.MediaFileService;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MediaFileRepositoryTest {

    @Autowired
    MediaFileRepository repository;

    @Autowired
    MediaFileService service;

    @Test
    public void test(){
        QueryResponseResult list = service.findList(1, 10, null);
        System.out.println(list);
    }
}