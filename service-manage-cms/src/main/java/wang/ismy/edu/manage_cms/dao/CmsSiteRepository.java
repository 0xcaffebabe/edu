package wang.ismy.edu.manage_cms.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.cms.CmsSite;

/**
 * @author MY
 * @date 2019/10/17 22:48
 */
public interface CmsSiteRepository extends MongoRepository<CmsSite,String> {
}
