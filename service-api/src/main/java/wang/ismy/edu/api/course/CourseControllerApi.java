package wang.ismy.edu.api.course;

import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.course.CourseBase;
import wang.ismy.edu.domain.course.CourseMarket;
import wang.ismy.edu.domain.course.Teachplan;
import wang.ismy.edu.domain.course.ext.CourseInfo;
import wang.ismy.edu.domain.course.ext.TeachplanNode;
import wang.ismy.edu.domain.course.request.CourseListRequest;

/**
 * @author MY
 * @date 2019/10/13 22:15
 */
public interface CourseControllerApi {

    /**
     * 根据课程ID查询课程计划列表
     * @param courseId 课程ID
     * @return 课程计划节点列表
     */
    TeachplanNode findPlanList(String courseId);

    ResponseResult savePlan(Teachplan teachplan);

    /**
     * 分页查询课程列表
     * @param page 页码
     * @param size 页面大小
     * @param request 请求参数
     * @return 响应实体
     */
    QueryResponseResult<CourseInfo> findCourseList(Integer page, Integer size, CourseListRequest request);

    /**
     * 新增课程
     * @param courseBase 课程实体
     * @return 操作结果
     */
    ResponseResult saveCourse(CourseBase courseBase);

    CourseBase findCourse(String courseId);

    ResponseResult updateCourse(String courseId,CourseBase courseBase);

    CourseMarket findCourseMarket(String courseId);

    ResponseResult updateCourseMarket(String courseId,CourseMarket courseMarket);
}
