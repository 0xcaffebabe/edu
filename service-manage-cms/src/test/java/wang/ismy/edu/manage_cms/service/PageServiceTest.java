package wang.ismy.edu.manage_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author MY
 * @date 2019/10/11 16:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PageServiceTest {

    @Autowired
    PageService pageService;

    @Test
    public void test(){
        String pageHtml = pageService.getPageHtml("5a795ac7dd573c04508f3a56");

        System.out.println(pageHtml);
    }
}
