package wang.ismy.edu.domain.cms.ext;

import lombok.Data;
import lombok.NoArgsConstructor;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.model.response.ResultCode;

/**
 * @author MY
 * @date 2019/10/17 22:41
 */
@Data
@NoArgsConstructor
public class CmsPostPageResult extends ResponseResult {
    String pageUrl;

    public CmsPostPageResult(ResultCode resultCode, String pageUrl) {
        super(resultCode);
        this.pageUrl = pageUrl;
    }
}
