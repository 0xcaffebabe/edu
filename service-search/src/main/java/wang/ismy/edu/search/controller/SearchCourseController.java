package wang.ismy.edu.search.controller;

import lombok.AllArgsConstructor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.edu.api.search.SearchCourseControllerApi;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.course.CoursePub;
import wang.ismy.edu.domain.course.TeachplanMedia;
import wang.ismy.edu.domain.search.CourseSearchParam;
import wang.ismy.edu.search.service.SearchCourseService;

import java.util.Map;

/**
 * @author MY
 * @date 2019/10/20 14:39
 */
@RestController
@AllArgsConstructor
@RequestMapping("search")
public class SearchCourseController implements SearchCourseControllerApi {

    private SearchCourseService searchCourseService;

    @GetMapping("list/{page}/{size}")
    @Override
    public QueryResponseResult findList(@PathVariable Integer page, @PathVariable Integer size, CourseSearchParam param) {
        return searchCourseService.findList(page, size, param);
    }

    @GetMapping("getall/{id}")
    @Override
    public Map<String, CoursePub> findCoursePub(@PathVariable String id) {
        return searchCourseService.findCoursePub(id);
    }

    @GetMapping("getmedia/{id}")
    @Override
    public TeachplanMedia findCourseMedia(@PathVariable String id) {

        QueryResponseResult<TeachplanMedia> result = searchCourseService.findCourseMedia(id);
        if (!CollectionUtils.isEmpty(result.getQueryResult().getList())) {
            return result.getQueryResult().getList().get(0);
        }
        return new TeachplanMedia();
    }
}
