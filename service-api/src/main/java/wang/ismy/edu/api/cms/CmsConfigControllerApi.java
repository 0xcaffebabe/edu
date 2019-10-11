package wang.ismy.edu.api.cms;

import wang.ismy.edu.domain.cms.CmsConfig;

/**
 * @author MY
 * @date 2019/10/11 14:45
 */
public interface CmsConfigControllerApi {

    CmsConfig findModel(String id);
}
