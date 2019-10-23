package wang.ismy.edu.api.media;

import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.domain.media.request.QueryMediaFileRequest;

/**
 * @author MY
 * @date 2019/10/23 13:13
 */
public interface MediaFileControllerApi {

    /**
     * 查询媒资列表
     * @param page 页数
     * @param size 尺寸
     * @param params 参数类
     * @return 列表结果
     */
    QueryResponseResult findList(Integer page, Integer size, QueryMediaFileRequest params);
}
