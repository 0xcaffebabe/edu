package wang.ismy.edu.base_filesystem.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import wang.ismy.edu.domain.filesystem.FileSystem;

/**
 * @author MY
 * @date 2019/10/15 17:20
 */
public interface FileSystemRepository extends MongoRepository<FileSystem,String> { }
