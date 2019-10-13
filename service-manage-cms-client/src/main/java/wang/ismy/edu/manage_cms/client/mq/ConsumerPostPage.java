package wang.ismy.edu.manage_cms.client.mq;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wang.ismy.edu.manage_cms.client.service.PageService;

import java.util.Map;

/**
 * @author MY
 * @date 2019/10/13 20:18
 */
@Component
@AllArgsConstructor
@Slf4j
public class ConsumerPostPage {

    private PageService pageService;


    @RabbitListener(queues = {"${edu.mq.queue}"})
    public void postPage(String msg){
        Map map = JSON.parseObject(msg, Map.class);

        String pageId = (String) map.get("pageId");
        log.info("接收到页面发布请求:{}",pageId);
        pageService.savePage(pageId);

    }
}
