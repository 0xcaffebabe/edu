package wang.ismy.edu.manage_course.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import wang.ismy.edu.manage_course.pojo.MediaIndex;

/**
 * @author MY
 * @date 2019/10/23 21:10
 */
public interface MediaIndexRepository extends ElasticsearchRepository<MediaIndex,String> { }
