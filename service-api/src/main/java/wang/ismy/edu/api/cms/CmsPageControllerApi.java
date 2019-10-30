package wang.ismy.edu.api.cms;

import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.ext.CmsPostPageResult;
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

    /**添加页面
     * @param cmsPage 实体
     * @return 包含实体的响应结果
     */
    CmsPageResult add(CmsPage cmsPage);

    /** 根据ID查询页面
     * @param id 页面ID
     * @return 页面实体
     */
    CmsPage findById(String id);

    /**
     * 更新页面
     * @param cmsPage 页面实体
     * @return 包含实体的响应结果
     */
    CmsPageResult update(CmsPage cmsPage);

    ResponseResult delete(String id);

    ResponseResult postPage(String pageId);

    CmsPageResult save(CmsPage cmsPage,String jwt);

    CmsPostPageResult postPageQuick(CmsPage cmsPage);
}
