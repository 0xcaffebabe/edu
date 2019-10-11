package wang.ismy.edu.manage_cms.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.cms.CmsConfig;

/**
 * @author MY
 * @date 2019/10/11 14:47
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String> { }
