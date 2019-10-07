package wang.ismy.edu.domain.cms.request;

import lombok.Data;

/**
 * @author MY
 * @date 2019/10/7 21:16
 */

@Data
public class QueryPageRequest {

    private String siteId;

    private String pageId;

    private String pageName;

    private String pageAlias;

    private String templateId;
}
