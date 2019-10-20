package wang.ismy.edu.api.search;

import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.search.CourseSearchParam;

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
}
