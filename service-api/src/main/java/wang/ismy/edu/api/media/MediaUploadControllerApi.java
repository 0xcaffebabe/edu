package wang.ismy.edu.api.media;

import org.springframework.web.multipart.MultipartFile;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.media.response.CheckChunkResult;

/**
 * @author MY
 * @date 2019/10/22 16:57
 */
public interface MediaUploadControllerApi {

    /**
     * 文件上传前注册工作
     * @param fileMd5 MD5
     * @param fileName 文件名
     * @param fileSize 文件大小
     * @param mimetype 文件类型
     * @param ext 文件扩展名
     * @return 响应结果
     */
    ResponseResult register(String fileMd5,String fileName,Long fileSize,String mimetype,String ext);

    /**
     * 判断分块是否存在
     * @param fileMd5 MD5
     * @param chunk 块下标
     * @param chunkSize 块大小
     * @return 分块校验结果
     */
    CheckChunkResult checkChunk(String fileMd5,Integer chunk,Integer chunkSize);

    /**
     * 上传分块
     * @param file
     * @param fileMd5
     * @param chunk
     * @return
     */
    ResponseResult uploadChunk(MultipartFile file,String fileMd5,Integer chunk);

    /**
     * 合并分块
     * @param fileMd5
     * @param fileName
     * @param fileSize
     * @param mimetype
     * @param ext
     * @return
     */
    ResponseResult mergeChunk(String fileMd5,String fileName,Long fileSize,String mimetype,String ext);
}
