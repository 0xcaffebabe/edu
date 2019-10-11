package wang.ismy.edu.manage_cms.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.edu.api.cms.CmsConfigControllerApi;
import wang.ismy.edu.domain.cms.CmsConfig;
import wang.ismy.edu.manage_cms.service.CmsConfigService;

/**
 * @author MY
 * @date 2019/10/11 14:49
 */
@RestController
@AllArgsConstructor
@RequestMapping("cms/config")
public class CmsConfigController implements CmsConfigControllerApi {

    private CmsConfigService cmsConfigService;

    @GetMapping("model/{id}")
    @Override
    public CmsConfig findModel(@PathVariable String id) {
        return cmsConfigService.findModel(id);
    }
}
