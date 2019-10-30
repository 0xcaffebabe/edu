package wang.ismy.edu.manage_cms.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import wang.ismy.edu.common.model.response.*;
import wang.ismy.edu.api.cms.CmsPageControllerApi;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.ext.CmsPostPageResult;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;
import wang.ismy.edu.domain.cms.response.CmsPageResult;
import wang.ismy.edu.manage_cms.service.PageService;


/**
 * @author MY
 * @date 2019/10/7 21:59
 */
@RestController
@RequestMapping("cms/page")
@AllArgsConstructor
@Slf4j
public class CmsPageController implements CmsPageControllerApi {

    private PageService pageService;

    @GetMapping("list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable Integer page,@PathVariable Integer size, QueryPageRequest request) {
        return pageService.findList(page, size, request);
    }

    @PostMapping("add")
    @Override
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return pageService.add(cmsPage);
    }

    @GetMapping("get/{id}")
    @Override
    public CmsPage findById(@PathVariable String id) {
        return pageService.findById(id);
    }

    @PutMapping("edit")
    @Override
    public CmsPageResult update(@RequestBody CmsPage cmsPage) {
        return pageService.update(cmsPage);
    }

    @DeleteMapping("del/{id}")
    @Override
    public ResponseResult delete(@PathVariable String id) {
        return pageService.delete(id);
    }

    @PostMapping("postPage/{pageId}")
    @Override
    public ResponseResult postPage(@PathVariable String pageId) {
        return pageService.postPage(pageId);
    }

    @PostMapping("save")
    @Override
    public CmsPageResult save(@RequestBody CmsPage cmsPage,@RequestHeader("jwt") String jwt) {
        log.info("接收到jwt：{}",jwt);
        return pageService.save(cmsPage);
    }

    @PostMapping("postPageQuick")
    @Override
    public CmsPostPageResult postPageQuick(@RequestBody CmsPage cmsPage) {
        return pageService.postPageQuick(cmsPage);
    }
}
