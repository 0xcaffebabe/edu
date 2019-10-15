package wang.ismy.edu.manage_course.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import wang.ismy.edu.api.course.CourseControllerApi;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.course.CourseBase;
import wang.ismy.edu.domain.course.CourseMarket;
import wang.ismy.edu.domain.course.CoursePic;
import wang.ismy.edu.domain.course.Teachplan;
import wang.ismy.edu.domain.course.ext.CourseInfo;
import wang.ismy.edu.domain.course.ext.TeachplanNode;
import wang.ismy.edu.domain.course.request.CourseListRequest;
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
    public TeachplanNode findPlanList(@PathVariable String courseId) {
        return courseService.findTeachPlan(courseId);
    }

    @PostMapping("teachplan/add")
    @Override
    public ResponseResult savePlan(@RequestBody Teachplan teachplan) {
        return courseService.savePlan(teachplan);
    }

    @GetMapping("coursebase/list/{page}/{size}")
    @Override
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable Integer page, @PathVariable Integer size, CourseListRequest request) {
        return courseService.findCourseList(page,size,request);
    }

    @GetMapping("coursebase/{courseId}")
    @Override
    public CourseBase findCourse(@PathVariable String courseId) {
        return courseService.findCourse(courseId);
    }

    @PostMapping("coursebase/add")
    @Override
    public ResponseResult saveCourse(@RequestBody CourseBase courseBase) {
        return courseService.saveCourse(courseBase);
    }

    @PutMapping("coursebase/{courseId}")
    @Override
    public ResponseResult updateCourse(@PathVariable String courseId, @RequestBody CourseBase courseBase) {
        return courseService.updateCourse(courseId,courseBase);
    }

    @GetMapping("coursemarket/{courseId}")
    @Override
    public CourseMarket findCourseMarket(@PathVariable String courseId) {
        return courseService.findCourseMarket(courseId);
    }

    @PutMapping("coursemarket/{courseId}")
    @Override
    public ResponseResult updateCourseMarket(@PathVariable String courseId,@RequestBody CourseMarket courseMarket) {
        return courseService.updateCourseMarket(courseId,courseMarket);
    }

    @PostMapping("coursepic/add")
    @Override
    public ResponseResult addCoursePic(String courseId, String pic) {
        return courseService.addCoursePic(courseId,pic);
    }

    @GetMapping("coursepic/list/{courseId}")
    @Override
    public CoursePic findCoursePic(@PathVariable String courseId) {
        return courseService.findCoursePic(courseId);
    }

    @DeleteMapping("coursepic/delete")
    @Override
    public ResponseResult deleteCoursePic(@RequestParam String courseId) {
        return courseService.deleteCoursePic(courseId);
    }
}
