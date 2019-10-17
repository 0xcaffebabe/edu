package wang.ismy.edu.domain.course.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.model.response.ResultCode;

/**
 * @author MY
 * @date 2019/10/17 21:34
 */
@Data
@NoArgsConstructor
public class CoursePublishResult extends ResponseResult {

    String previewUrl;

    public CoursePublishResult(ResultCode resultCode, String previewUrl) {
        super(resultCode);
        this.previewUrl = previewUrl;
    }
}
