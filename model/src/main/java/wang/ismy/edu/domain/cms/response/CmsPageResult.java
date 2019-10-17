package wang.ismy.edu.domain.cms.response;

import lombok.NoArgsConstructor;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.model.response.ResultCode;
import lombok.Data;


/**
 * @author MY
 */
@Data
@NoArgsConstructor
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage;
    public CmsPageResult(ResultCode resultCode,CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
