package wang.ismy.edu.manage_cms.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.system.SysDictionary;

/**
 * @author MY
 * @date 2019/10/14 20:47
 */
public interface SysDictionaryRepository extends MongoRepository<SysDictionary,String> {

    SysDictionary findBydType(String type);
}
