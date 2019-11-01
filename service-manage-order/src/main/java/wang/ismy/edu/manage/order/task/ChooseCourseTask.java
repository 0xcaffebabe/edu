package wang.ismy.edu.manage.order.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wang.ismy.edu.domain.task.XcTask;
import wang.ismy.edu.manage.order.config.RabbitMQConfig;
import wang.ismy.edu.manage.order.service.TaskService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/10/31 21:32
 */
@Component
@Slf4j
@AllArgsConstructor
public class ChooseCourseTask {

    private TaskService taskService;

    @Scheduled(cron = "0/3 * * * * *")
    public void chooseCourse() {

        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE, -1);

        List<XcTask> taskList = taskService.findTaskList(calendar.getTime(), 100).stream()
                .filter(t -> System.currentTimeMillis() - t.getUpdateTime().getTime() > 10000)
                .collect(Collectors.toList());
        for (XcTask xcTask : taskList) {
            if (taskService.getTask(xcTask.getId(), xcTask.getVersion()) > 0) {
                log.info("执行任务发送：{}", xcTask.getId());
                String mqExchange = xcTask.getMqExchange();
                String mqRoutingkey = xcTask.getMqRoutingkey();
                taskService.publish(xcTask, mqExchange, mqRoutingkey);
            }

        }
    }

    @RabbitListener(queues = RabbitMQConfig.XC_LEARNING_FINISHADDCHOOSECOURSE)
    public void process(XcTask task){
        if (task != null && !StringUtils.isEmpty(task.getId())){
            log.info("接收到完成课程选择消息:{}",task.getId());
            taskService.finshTask(task.getId());
        }
    }
}
