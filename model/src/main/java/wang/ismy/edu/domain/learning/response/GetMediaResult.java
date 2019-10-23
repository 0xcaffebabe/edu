package wang.ismy.edu.domain.learning.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.model.response.ResultCode;

/**
 * @author MY
 * @date 2019/10/23 22:09
 */
@Data
@NoArgsConstructor
public class GetMediaResult extends ResponseResult {

    String fileUrl;

    public GetMediaResult(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public GetMediaResult(ResultCode resultCode, String fileUrl) {
        super(resultCode);
        this.fileUrl = fileUrl;
    }
}
