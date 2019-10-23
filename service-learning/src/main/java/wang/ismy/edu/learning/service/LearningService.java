package wang.ismy.edu.learning.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.domain.course.TeachplanMedia;
import wang.ismy.edu.domain.learning.response.GetMediaResult;
import wang.ismy.edu.domain.learning.response.LearningCode;
import wang.ismy.edu.learning.client.CourseSearchClient;

/**
 * @author MY
 * @date 2019/10/23 22:17
 */
@Service
@AllArgsConstructor
public class LearningService {

    private CourseSearchClient courseSearchClient;

    public GetMediaResult getMedia(String courseId, String teachPlanId) {
        TeachplanMedia courseMedia = courseSearchClient.findCourseMedia(teachPlanId);
        if (courseMedia == null || StringUtils.isEmpty(courseMedia.getMediaUrl())){
            ExceptionCast.cast(LearningCode.GET_LEARNING_URL_FAIL);
        }

        return new GetMediaResult(CommonCode.SUCCESS,courseMedia.getMediaUrl());
    }
}
