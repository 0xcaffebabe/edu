package wang.ismy.edu.manage.order.service;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.exception.ExceptionCatch;
import wang.ismy.edu.domain.task.XcTask;
import wang.ismy.edu.manage.order.repository.TaskRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author MY
 * @date 2019/10/31 21:29
 */
@Service
@AllArgsConstructor
public class TaskService {

    private TaskRepository taskRepository;
    private RabbitTemplate rabbitTemplate;

    public List<XcTask> findTaskList(Date updateTime, int size) {
        Page<XcTask> result = taskRepository.findAll(PageRequest.of(0, size));
        return result.getContent();
    }

    @Transactional(rollbackOn = Exception.class)
    public void publish(XcTask task, String exchange, String routingKey) {
        Optional<XcTask> opt = taskRepository.findById(task.getId());

        if (opt.isPresent()) {
            rabbitTemplate.convertAndSend(exchange, routingKey, task);
            taskRepository.updateTaskTime(task.getId(),new Date());
        }

    }
}
