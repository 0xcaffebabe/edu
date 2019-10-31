package wang.ismy.edu.manage.order.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wang.ismy.edu.domain.task.XcTask;


import java.util.Date;

/**
 * @author MY
 * @date 2019/10/31 21:26
 */
public interface TaskRepository extends JpaRepository<XcTask,String> {

    /**
     * 分页根据最后更新时间查询任务
     * @param pageable 分页参数
     * @param updateTime 最后更新时间
     * @return 分页查询结果
     */
    Page<XcTask> findByUpdateTimeLessThan( Date updateTime,Pageable pageable);

    @Modifying
    @Query("UPDATE XcTask t SET t.updateTime = :updateTime WHERE t.id = :taskId")
    int updateTaskTime(@Param("taskId") String taskId, @Param("updateTime") Date updateTime);
}
