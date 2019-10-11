package wang.ismy.edu.manage_cms.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.cms.CmsTemplate;

/**
 * @author MY
 * @date 2019/10/11 16:33
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String> {
}
