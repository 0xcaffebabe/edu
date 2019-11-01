package wang.ismy.edu.manage.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.task.XcTaskHis;

/**
 * @author MY
 * @date 2019/11/1 14:50
 */
public interface TaskHisRpository extends JpaRepository<XcTaskHis,String> { }
