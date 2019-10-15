package wang.ismy.edu.manage_course.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;

/**
 * @author MY
 * @date 2019/10/15 22:34
 */
@FeignClient("service-manage-cms")
public interface CmsPageClient {

    @GetMapping("/cms/page/list/{page}/{size}")
    String findList(@PathVariable("page") Integer page,@PathVariable("size") Integer size);
}
