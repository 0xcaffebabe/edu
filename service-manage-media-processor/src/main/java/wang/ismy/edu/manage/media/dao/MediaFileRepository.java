package wang.ismy.edu.manage.media.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.media.MediaFile;

/**
 * @author MY
 * @date 2019/10/22 21:41
 */
public interface MediaFileRepository extends MongoRepository<MediaFile,String> {
}
