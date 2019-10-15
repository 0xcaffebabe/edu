package wang.ismy.edu.api.filesystem;

import org.springframework.web.multipart.MultipartFile;
import wang.ismy.edu.domain.filesystem.response.UploadFileResult;

import java.util.Map;

/**
 * @author MY
 * @date 2019/10/15 17:15
 */
public interface FileSystemControllerApi {

    /**
     * 上传文件
     * @param file 文件实体
     * @param businesskey 上传方自定义业务键
     * @param filetag 上传方自定义文件标签
     * @param metadata 上传方自定义元数据
     * @return
     */
    UploadFileResult upload(MultipartFile file, String businesskey, String filetag, String metadata);
}
