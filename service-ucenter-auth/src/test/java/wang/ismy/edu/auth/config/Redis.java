package wang.ismy.edu.auth.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author MY
 * @date 2019/10/28 19:47
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class Redis {

    @Autowired
    StringRedisTemplate template;

    @Test
    public void test(){
        template.opsForValue().set("user_token:my","{user:my}");
    }
}
