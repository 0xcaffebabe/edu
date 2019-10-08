package wang.ismy.edu.manage_cms.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.domain.cms.CmsPage;


import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository repository;

    @Test
    public void test(){
        repository.findAll().forEach(System.out::println);
    }

    @Test
    public void findPage(){
        Page<CmsPage> list = repository.findAll(PageRequest.of(1, 5));

        assertEquals(5,list.getSize());
    }
}