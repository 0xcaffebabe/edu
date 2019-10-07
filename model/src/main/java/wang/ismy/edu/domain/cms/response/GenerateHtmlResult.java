package wang.ismy.edu.domain.cms.response;

import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.model.response.ResultCode;
import lombok.Data;

/**
 * Created by mrt on 2018/3/31.
 */
@Data
public class GenerateHtmlResult extends ResponseResult {
    String html;
    public GenerateHtmlResult(ResultCode resultCode, String html) {
        super(resultCode);
        this.html = html;
    }
}
