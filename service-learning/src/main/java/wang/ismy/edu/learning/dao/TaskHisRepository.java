package wang.ismy.edu.learning.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import wang.ismy.edu.domain.task.XcTaskHis;

/**
 * @author MY
 * @date 2019/11/1 14:04
 */
public interface TaskHisRepository extends JpaRepository<XcTaskHis,String> { }
