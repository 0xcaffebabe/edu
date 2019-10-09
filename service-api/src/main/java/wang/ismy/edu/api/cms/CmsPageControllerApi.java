package wang.ismy.edu.api.cms;

import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;
import wang.ismy.edu.domain.cms.response.CmsPageResult;

/**
 * @author MY
 * @date 2019/10/7 21:19
 */
public interface CmsPageControllerApi {

    /**
     * 根据条件查询CMS PAGE
     * @param page 页码
     * @param size 每页数量
     * @param request 请求实体
     * @return 响应实体
     */
    QueryResponseResult findList(Integer page, Integer size, QueryPageRequest request);

    CmsPageResult add(CmsPage cmsPage);
}
