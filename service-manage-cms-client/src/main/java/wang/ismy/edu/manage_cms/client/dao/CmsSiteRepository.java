package wang.ismy.edu.manage_cms.client.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.cms.CmsSite;

/**
 * @author MY
 * @date 2019/10/13 19:53
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
