package wang.ismy.edu.manage_cms.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import wang.ismy.edu.common.web.BaseController;
import wang.ismy.edu.manage_cms.service.PageService;

import java.io.IOException;

/**
 * @author MY
 * @date 2019/10/11 17:06
 */
@Controller
@AllArgsConstructor
public class CmsPagePreviewController extends BaseController {

    private PageService pageService;

    @GetMapping("cms/preview/{id}")
    public void preview(@PathVariable String id) throws IOException {
        String pageHtml = pageService.getPageHtml(id);

        response.getWriter().println(pageHtml);
    }
}
