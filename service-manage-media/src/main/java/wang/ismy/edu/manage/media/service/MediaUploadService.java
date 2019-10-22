package wang.ismy.edu.manage.media.service;

import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.media.response.MediaCode;
import wang.ismy.edu.manage.media.dao.MediaFileRepository;

import java.io.File;
import java.lang.reflect.Field;

/**
 * @author MY
 * @date 2019/10/22 17:09
 */
@Service

public class MediaUploadService {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Value("${service-manage-media.upload-location}")
    String uploadLocation;

    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String ext) {
        // 检查文件是否存在
        String filePath = getFilePath(fileMd5, ext);

        if (new File(filePath).exists() && mediaFileRepository.existsById(fileMd5)) {
            ExceptionCast.cast(MediaCode.CHUNK_FILE_EXIST_CHECK);
        }

        File file = new File(getFolderPath(fileMd5));
        if (!file.exists()) {
            file.mkdirs();
        }
        //TODO
        return null;
    }

    private String getFilePath(String md5, String ext) {
        return uploadLocation + md5.substring(0, 1) + "/" + md5.substring(1, 2) + "/" + md5 + "/" + md5 + "." + ext;

    }

    private String getFolderPath(String md5) {
        return uploadLocation + md5.substring(0, 1) + "/" + md5.substring(1, 2) + "/" + md5 + "/";

    }
}
