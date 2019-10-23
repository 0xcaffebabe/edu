package wang.ismy.edu.learning.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import wang.ismy.edu.api.learning.CourseLearningControllerApi;
import wang.ismy.edu.domain.learning.response.GetMediaResult;
import wang.ismy.edu.learning.service.LearningService;

/**
 * @author MY
 * @date 2019/10/23 22:16
 */
@RestController
@RequestMapping("learning/course")
@AllArgsConstructor
public class CourseLearningController implements CourseLearningControllerApi {

    private LearningService learningService;

    @GetMapping("getmedia/{courseId}/{teachPlanId}")
    @Override
    public GetMediaResult getMedia(@PathVariable String courseId,@PathVariable String teachPlanId) {
        return learningService.getMedia(courseId,teachPlanId);
    }
}
