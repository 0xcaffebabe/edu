package wang.ismy.edu.manage_cms.client.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.cms.CmsPage;

/**
 * @author MY
 * @date 2019/10/13 19:52
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String> {
}