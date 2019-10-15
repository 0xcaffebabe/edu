package wang.ismy.edu.manage_course.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsPageClientTest {

    @Autowired
    CmsPageClient client;

    @Test
    public void test(){

        System.out.println(client.findList(1, 2));
    }
}