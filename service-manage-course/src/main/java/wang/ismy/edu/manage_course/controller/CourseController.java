package wang.ismy.edu.manage_course.controller;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.*;
import wang.ismy.edu.api.course.CourseControllerApi;
import wang.ismy.edu.common.annotation.Permission;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.course.*;
import wang.ismy.edu.domain.course.ext.CourseInfo;
import wang.ismy.edu.domain.course.ext.CoursePublishResult;
import wang.ismy.edu.domain.course.ext.CourseView;
import wang.ismy.edu.domain.course.ext.TeachplanNode;
import wang.ismy.edu.domain.course.request.CourseListRequest;
import wang.ismy.edu.manage_course.service.CourseService;

import java.util.Map;

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

    @Permission("xc_teachmanager_course_market")
    @GetMapping("coursebase/list/{page}/{size}")
    @Override
    public QueryResponseResult<CourseInfo> findCourseList(@PathVariable Integer page, @PathVariable Integer size, CourseListRequest request,@RequestHeader("jwt") String jwt) {
        if (request == null){
            request = new CourseListRequest();
        }
        ;
        String companyId = JSON.parseObject(jwt, Map.class).get("companyId").toString();

        request.setCompanyId(companyId);
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

    @GetMapping("courseview/{courseId}")
    @Override
    public CourseView findCourseView(@PathVariable String courseId) {
        return courseService.findCourseView(courseId);
    }

    @PostMapping("preview/{courseId}")
    @Override
    public CoursePublishResult preview(@PathVariable String courseId) {
        return courseService.preview(courseId);
    }

    @PostMapping("publish/{courseId}")
    @Override
    public CoursePublishResult publish(@PathVariable String courseId) {
        return courseService.publish(courseId);
    }

    @PostMapping("savemedia")
    @Override
    public ResponseResult saveMedia(@RequestBody TeachplanMedia media) {

        return courseService.saveMedia(media);
    }
}
