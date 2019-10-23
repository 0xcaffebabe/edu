package wang.ismy.edu.api.learning;

import wang.ismy.edu.domain.learning.response.GetMediaResult;

/**
 * @author MY
 * @date 2019/10/23 22:08
 */
public interface CourseLearningControllerApi {

    /**
     * 获取课程计划媒体信息
     * @param courseId 课程ID
     * @param teachPlanId 课程计划ID
     * @return 媒体信息
     */
    GetMediaResult getMedia(String courseId,String teachPlanId);
}
