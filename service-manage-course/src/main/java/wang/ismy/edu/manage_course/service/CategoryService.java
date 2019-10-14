package wang.ismy.edu.manage_course.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wang.ismy.edu.domain.course.ext.CategoryNode;
import wang.ismy.edu.manage_course.dao.CategoryMapper;

import java.util.List;

/**
 * @author MY
 * @date 2019/10/14 20:25
 */
@Service
@AllArgsConstructor
public class CategoryService {

    private CategoryMapper categoryMapper;

    public CategoryNode findList(){
        return categoryMapper.findList();
    }
}
