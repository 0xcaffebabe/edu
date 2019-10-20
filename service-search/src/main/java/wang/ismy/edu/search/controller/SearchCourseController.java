package wang.ismy.edu.search.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.edu.api.search.SearchCourseControllerApi;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.search.CourseSearchParam;
import wang.ismy.edu.search.service.SearchCourseService;

/**
 * @author MY
 * @date 2019/10/20 14:39
 */
@RestController
@AllArgsConstructor
public class SearchCourseController implements SearchCourseControllerApi {

    private SearchCourseService searchCourseService;

    @GetMapping("search/list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable Integer page, @PathVariable Integer size, CourseSearchParam param) {
        return searchCourseService.findList(page,size,param);
    }
}
