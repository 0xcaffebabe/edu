package wang.ismy.edu.manage_course.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;
import wang.ismy.edu.domain.cms.response.CmsPageResult;

/**
 * @author MY
 * @date 2019/10/15 22:34
 */
@FeignClient("service-manage-cms")
public interface CmsPageClient {

    @GetMapping("/cms/page/list/{page}/{size}")
    String findList(@PathVariable("page") Integer page,@PathVariable("size") Integer size);

    @PostMapping("/cms/page/save")
    CmsPageResult save(@RequestBody CmsPage cmsPage);

    @PostMapping("/cms/page/publish")
    CmsPageResult publish(@RequestBody CmsPage cmsPage);
}
