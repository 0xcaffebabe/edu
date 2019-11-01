package wang.ismy.edu.learning.task;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.task.XcTask;
import wang.ismy.edu.learning.config.RabbitMQConfig;
import wang.ismy.edu.learning.service.LearningService;

import java.util.Date;
import java.util.Map;

/**
 * @author MY
 * @date 2019/11/1 14:19
 */
@Component
@AllArgsConstructor
@Slf4j
public class ChooseCourseTask {

    private LearningService learningService;
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = RabbitMQConfig.XC_LEARNING_ADDCHOOSECOURSE)
    public void process(XcTask task) {
        log.info("接收到课程处理消息:{}",task.getId());
        Map map = JSON.parseObject(task.getRequestBody(), Map.class);
        String userId = map.get("userId").toString();
        String courseId = map.get("courseId").toString();
        String valid = null;
        Date startTime = (Date) map.get("startTime");
        Date endTime = (Date) map.get("endTime");
        ResponseResult result = learningService.addCourse(userId, courseId, valid, null, null, task);
        if (result.isSuccess()) {
            // 发送选课完成消息
            rabbitTemplate.convertAndSend(RabbitMQConfig.EX_LEARNING_ADDCHOOSECOURSE,
                    RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE_KEY, task);
        }
    }
}
