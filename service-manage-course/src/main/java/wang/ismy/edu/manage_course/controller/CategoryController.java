package wang.ismy.edu.manage_course.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.edu.api.course.CategoryControllerApi;
import wang.ismy.edu.domain.course.ext.CategoryNode;
import wang.ismy.edu.manage_course.service.CategoryService;

import java.util.List;

/**
 * @author MY
 * @date 2019/10/14 20:19
 */
@RestController
@RequestMapping("category")
@AllArgsConstructor
public class CategoryController implements CategoryControllerApi {

    private CategoryService categoryService;

    @GetMapping("list")
    @Override
    public CategoryNode findList() {
        return categoryService.findList();
    }
}
