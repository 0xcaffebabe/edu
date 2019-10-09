package wang.ismy.edu.domain.cms.response;

import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.model.response.ResultCode;
import lombok.Data;


/**
 * @author MY
 */
@Data
public class CmsPageResult extends ResponseResult {
    CmsPage cmsPage;
    public CmsPageResult(ResultCode resultCode,CmsPage cmsPage) {
        super(resultCode);
        this.cmsPage = cmsPage;
    }
}
