package wang.ismy.edu.manage.media.cotroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.edu.api.media.MediaUploadControllerApi;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.media.response.CheckChunkResult;
import wang.ismy.edu.manage.media.service.MediaUploadService;

/**
 * @author MY
 * @date 2019/10/22 17:08
 */
@RestController
@RequestMapping("media/upload")

public class MediaUploadController implements MediaUploadControllerApi {

    @Autowired
    private MediaUploadService mediaUploadService;

    @PostMapping("register")
    @Override
    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String ext) {
        return mediaUploadService.register(fileMd5,fileName,fileSize,mimetype,ext);
    }

    @Override
    public CheckChunkResult checkChunk(String fileMd5, Integer chunk, Integer chunkSize) {
        return null;
    }

    @Override
    public ResponseResult uploadChunk(MultipartFile file, String fileMd5, Integer chunk) {
        return null;
    }

    @Override
    public ResponseResult mergeChunk(String fileMd5, String fileName, Long fileSize, String mimetype, String ext) {
        return null;
    }
}
