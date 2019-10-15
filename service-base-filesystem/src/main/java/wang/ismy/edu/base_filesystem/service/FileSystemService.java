package wang.ismy.edu.base_filesystem.service;

import com.alibaba.fastjson.JSON;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.edu.base_filesystem.dao.FileSystemRepository;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.domain.filesystem.FileSystem;
import wang.ismy.edu.domain.filesystem.response.FileSystemCode;
import wang.ismy.edu.domain.filesystem.response.UploadFileResult;

import java.io.IOException;
import java.util.Map;

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

        try {
            // 上传文件到文件服务器
            if (StringUtils.isEmpty(file.getOriginalFilename())){
                ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
            }
            String extName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
            StorePath storePath = storageClient
                    .uploadImageAndCrtThumbImage(file.getInputStream(), file.getSize(),extName, null);
            // 保存文件数据
            FileSystem fileSystem = new FileSystem();
            fileSystem.setFileId(storePath.getFullPath());
            fileSystem.setFilePath(storePath.getFullPath());
            fileSystem.setFiletag(fileTag);
            fileSystem.setBusinesskey(businessKey);
            fileSystem.setFileName(file.getOriginalFilename());
            fileSystem.setFileType(file.getContentType());
            fileSystem.setMetadata(JSON.parseObject(metadata, Map.class));
            fileSystemRepository.save(fileSystem);
            return new UploadFileResult(CommonCode.SUCCESS,fileSystem);
        } catch (IOException e) {
            ExceptionCast.cast(FileSystemCode.FS_UPLOADFILE_SERVERFAIL);
        }

        return null;
    }

}
