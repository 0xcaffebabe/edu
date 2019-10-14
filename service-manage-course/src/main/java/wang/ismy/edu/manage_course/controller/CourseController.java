package wang.ismy.edu.manage_course.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wang.ismy.edu.api.course.CourseControllerApi;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.course.Teachplan;
import wang.ismy.edu.domain.course.ext.TeachplanNode;
import wang.ismy.edu.manage_course.service.CourseService;

/**
 * @author MY
 * @date 2019/10/14 13:14
 */
@RestController
@RequestMapping("course")
@AllArgsConstructor
public class CourseController implements CourseControllerApi {

    private CourseService courseService;

    @GetMapping("teachplan/list/{courseId}")
    @Override
    public TeachplanNode findList(@PathVariable String courseId) {
        return courseService.findTeachPlan(courseId);
    }

    @PostMapping("teachplan/add")
    @Override
    public ResponseResult save(@RequestBody Teachplan teachplan) {
        return courseService.save(teachplan);
    }
}
