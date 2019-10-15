package wang.ismy.edu.base_filesystem.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.edu.base_filesystem.dao.FileSystemRepository;
import wang.ismy.edu.domain.filesystem.response.UploadFileResult;

import java.io.IOException;

/**
 * @author MY
 * @date 2019/10/15 17:21
 */
@Service
@AllArgsConstructor
public class FileSystemService {

    private FileSystemRepository fileSystemRepository;
    private FastFileStorageClient storageClient;

    public UploadFileResult upload(MultipartFile file, String businessKey, String fileTag, String metadata){
        // 上传文件到文件服务器
        try {
            StorePath storePath = storageClient
                    .uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),"扩展名", null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 保存文件数据
        // TODO
        return null;
    }

}
