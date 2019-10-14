package wang.ismy.edu.manage_cms.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.edu.api.cms.SysDictionaryControllerApi;
import wang.ismy.edu.domain.system.SysDictionary;
import wang.ismy.edu.manage_cms.service.SysDictionaryService;

/**
 * @author MY
 * @date 2019/10/14 20:46
 */
@RestController
@RequestMapping("sys/dictionary")
@AllArgsConstructor
public class SysDictionaryController implements SysDictionaryControllerApi {

    private SysDictionaryService sysDictionaryService;

    @GetMapping("get/{type}")
    @Override
    public SysDictionary findByType(@PathVariable String type) {
        return sysDictionaryService.findByType(type);
    }
}
