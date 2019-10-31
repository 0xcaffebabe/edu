package wang.ismy.edu.manage.order.task;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wang.ismy.edu.domain.task.XcTask;
import wang.ismy.edu.manage.order.service.TaskService;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

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
        log.info("执行任务扫描");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        calendar.set(GregorianCalendar.MINUTE, -1);

        List<XcTask> taskList = taskService.findTaskList(calendar.getTime(), 100);
        for (XcTask xcTask : taskList) {
            String mqExchange = xcTask.getMqExchange();
            String mqRoutingkey = xcTask.getMqRoutingkey();
            taskService.publish(xcTask,mqExchange,mqRoutingkey);
        }
    }
}
