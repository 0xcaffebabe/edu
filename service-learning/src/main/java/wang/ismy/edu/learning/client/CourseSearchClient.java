package wang.ismy.edu.learning.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.edu.common.client.ServiceList;
import wang.ismy.edu.domain.course.TeachplanMedia;

/**
 * @author MY
 * @date 2019/10/23 22:19
 */
@FeignClient(ServiceList.SERVICE_SEARCH)
@RequestMapping("search")
public interface CourseSearchClient {

    /**
     * 远程调用搜索服务获取课程计划媒体信息
     * @param id
     * @return
     */
    @GetMapping("getmedia/{id}")
    TeachplanMedia findCourseMedia(@PathVariable String id);
}
