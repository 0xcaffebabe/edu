package wang.ismy.edu.api.search;

import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.course.CoursePub;
import wang.ismy.edu.domain.course.TeachplanMedia;
import wang.ismy.edu.domain.search.CourseSearchParam;

import java.util.Map;

/**
 * @author MY
 * @date 2019/10/20 14:30
 */
public interface SearchCourseControllerApi {

    /**
     * 查询课程
     * @param page 页数
     * @param size 每页数量
     * @param param 搜索参数
     * @return 列表结果
     */
    QueryResponseResult findList(Integer page, Integer size, CourseSearchParam param);

    /**
     * 根据课程ID查询课程信息
     * @param id 课程ID
     * @return
     */
    Map<String, CoursePub> findCoursePub(String id);

    /**
     * 根据课程计划ID查询课程计划媒资
     * @param id 课程计划ID
     * @return 课程计划媒体信息
     */
    TeachplanMedia findCourseMedia(String id);
}
