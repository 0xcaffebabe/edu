package wang.ismy.edu.manage_cms.controller;


import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.api.cms.CmsPageControllerApi;
import wang.ismy.edu.common.model.response.QueryResult;
import wang.ismy.edu.common.model.response.ResultCode;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;
import wang.ismy.edu.domain.cms.response.CmsPageResult;
import wang.ismy.edu.manage_cms.service.PageService;

import java.util.List;

/**
 * @author MY
 * @date 2019/10/7 21:59
 */
@RestController
@RequestMapping("cms/page")
@AllArgsConstructor
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
    public CmsPageResult update(CmsPage cmsPage) {
        return pageService.update(cmsPage);
    }
}
