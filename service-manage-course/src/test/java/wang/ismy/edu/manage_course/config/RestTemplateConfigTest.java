package wang.ismy.edu.manage_course.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateConfigTest {

    @Autowired
    RestTemplate restTemplate;

    @Test
    public void test(){
        String serviceId = "service-manage-cms";

        ResponseEntity<String> entity = restTemplate.getForEntity("http://" + serviceId + "/cms/page/list/1/2", String.class);

        String body = entity.getBody();
        System.out.println(body);
    }
}