package wang.ismy.edu.learning.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.course.TeachplanMedia;
import wang.ismy.edu.domain.learning.XcLearningCourse;
import wang.ismy.edu.domain.learning.response.GetMediaResult;
import wang.ismy.edu.domain.learning.response.LearningCode;
import wang.ismy.edu.domain.task.XcTask;
import wang.ismy.edu.domain.task.XcTaskHis;
import wang.ismy.edu.learning.client.CourseSearchClient;
import wang.ismy.edu.learning.dao.LearningCourseRepository;
import wang.ismy.edu.learning.dao.TaskHisRepository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * @author MY
 * @date 2019/10/23 22:17
 */
@Service
@AllArgsConstructor
public class LearningService {

    private CourseSearchClient courseSearchClient;
    private LearningCourseRepository learningCourseRepository;
    private TaskHisRepository taskHisRepository;

    public GetMediaResult getMedia(String courseId, String teachPlanId) {
        TeachplanMedia courseMedia = courseSearchClient.findCourseMedia(teachPlanId);
        if (courseMedia == null || StringUtils.isEmpty(courseMedia.getMediaUrl())) {
            ExceptionCast.cast(LearningCode.GET_LEARNING_URL_FAIL);
        }
        return new GetMediaResult(CommonCode.SUCCESS, courseMedia.getMediaUrl());
    }

    @Transactional(rollbackOn = Exception.class)
    public ResponseResult addCourse(String userId, String courseId, String valid,
                                    Date startTime, Date endTime, XcTask task) {
        XcLearningCourse learningCourse = learningCourseRepository.findByCourseIdAndUserId(courseId, userId);
        if (learningCourse != null) {
            // 更新课程
            learningCourse.setStartTime(startTime);
            learningCourse.setEndTime(endTime);
            learningCourse.setValid(valid);
            learningCourse.setStatus("501001");

        } else {
            learningCourse = new XcLearningCourse();
            learningCourse.setStartTime(startTime);
            learningCourse.setEndTime(endTime);
            learningCourse.setValid(valid);
            learningCourse.setStatus("501001");
            learningCourse.setUserId(userId);
            learningCourse.setCourseId(courseId);
        }
        learningCourseRepository.save(learningCourse);

        // 插入历史任务记录
        Optional<XcTaskHis> opt = taskHisRepository.findById(task.getId());
        if (!opt.isPresent()) {
            XcTaskHis taskHis = new XcTaskHis();
            BeanUtils.copyProperties(task,taskHis);
            taskHisRepository.save(taskHis);
        }
        return new ResponseResult(CommonCode.SUCCESS);

    }
}
