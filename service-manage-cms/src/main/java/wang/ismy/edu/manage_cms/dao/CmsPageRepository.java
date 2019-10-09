package wang.ismy.edu.manage_cms.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.cms.CmsPage;

/**
 * @author MY
 * @date 2019/10/7 22:17
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {

    CmsPage findByPageNameAndSiteIdAndPageWebPath(String pageName,String siteId,String pageWebPath);
}
