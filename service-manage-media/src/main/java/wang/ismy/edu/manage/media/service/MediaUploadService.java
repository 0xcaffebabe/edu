package wang.ismy.edu.manage.media.service;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.utils.MD5Util;
import wang.ismy.edu.domain.media.MediaFile;
import wang.ismy.edu.domain.media.response.CheckChunkResult;
import wang.ismy.edu.domain.media.response.MediaCode;
import wang.ismy.edu.manage.media.config.RabbitMQConfig;
import wang.ismy.edu.manage.media.dao.MediaFileRepository;

import javax.print.DocFlavor;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

/**
 * @author MY
 * @date 2019/10/22 17:09
 */
@Service

public class MediaUploadService {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value("${service-manage-media.upload-location}")
    String uploadLocation;

    @Value("${service-manage-media.mq.routingkey-media-video}")
    String routingKey;

    public ResponseResult register(String fileMd5, String fileName, Long fileSize, String mimetype, String ext) {
        // 检查文件是否存在
        String filePath = getFilePath(fileMd5, ext);

        if (new File(filePath).exists() && mediaFileRepository.existsById(fileMd5)) {
            ExceptionCast.cast(MediaCode.CHUNK_FILE_EXIST_CHECK);
        }

        // 不存在就创建相关目录
        File file = new File(getFolderPath(fileMd5));
        if (!file.exists()) {
            file.mkdirs();
        }

        return new ResponseResult(CommonCode.SUCCESS);
    }

    public CheckChunkResult checkChunk(String fileMd5, Integer chunk, Integer chunkSize) {
        String chunkFolderPath = getChunkFolderPath(fileMd5);

        return new CheckChunkResult(CommonCode.SUCCESS,
                new File(chunkFolderPath + chunk).exists());

    }

    public ResponseResult uploadChunk(MultipartFile file, String fileMd5, Integer chunk) {
        String chunkFolderPath = getChunkFolderPath(fileMd5);
        File chunkFolder = new File(chunkFolderPath);
        if (!chunkFolder.exists()) {
            chunkFolder.mkdirs();
        }

        try (InputStream inputStream = file.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(new File(chunkFolderPath + chunk))) {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }
        return new ResponseResult(CommonCode.SUCCESS);
    }

    public ResponseResult mergeChunks(String fileMd5, String fileName, Long fileSize, String mimetype, String ext) {
        // 合并所有分块
        String chunkFolderPath = getChunkFolderPath(fileMd5);
        File[] files = new File(chunkFolderPath).listFiles();
        if (files == null){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        String filePath = getFilePath(fileMd5, ext);
        File mergeFile = new File(filePath);
        try {
            mergeFile = mergeFile(Arrays.asList(files), mergeFile);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseResult(MediaCode.MERGE_FILE_FAIL);
        }
        // 校验MD5
        try {
            if (!checkFile(mergeFile, fileMd5)) {
                ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
            }
        } catch (IOException e) {
            e.printStackTrace();
            ExceptionCast.cast(MediaCode.MERGE_FILE_CHECKFAIL);
        }

        // 写入信息到数据库

        MediaFile mediaFile = new MediaFile();
        mediaFile.setFileId(fileMd5);
        mediaFile.setFileOriginalName(fileName);
        mediaFile.setFileName(fileName + "." + ext);
        mediaFile.setFilePath(getRelativePath(fileMd5,ext));
        mediaFile.setFileSize(fileSize);
        mediaFile.setUploadTime(new Date());
        mediaFile.setMimeType(mimetype);
        mediaFile.setFileStatus("301002");
        mediaFile.setFileType(ext);
        mediaFileRepository.save(mediaFile);

        sendMessage(mediaFile.getFileId());

        return new ResponseResult(CommonCode.SUCCESS);
    }

    public ResponseResult sendMessage(String mediaId) {
        if (!mediaFileRepository.existsById(mediaId)){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        String json = JSON.toJSONString(Map.of("mediaId", mediaId));
        try{
            rabbitTemplate.convertAndSend(RabbitMQConfig.EX_MEDIA_PROCESSTASK,routingKey,json);
        }catch (AmqpException e){
            e.printStackTrace();
            return new ResponseResult(CommonCode.FAIL);
        }

        return new ResponseResult(CommonCode.SUCCESS);
    }

    private boolean checkFile(File file, String md5) throws IOException {
        FileInputStream fis = new FileInputStream(file);
        return md5.equalsIgnoreCase(DigestUtils.md5Hex(fis));
    }

    private File mergeFile(List<File> fileList, File mergeFile) throws IOException {
        if (mergeFile.exists()) {
            mergeFile.delete();
        } else {
            mergeFile.createNewFile();
        }

        Collections.sort(fileList, Comparator.comparingInt(o -> Integer.parseInt(o.getName())));

        RandomAccessFile raf = new RandomAccessFile(mergeFile, "rw");
        byte[] buffer = new byte[1024];
        for (File file : fileList) {
            RandomAccessFile readRaf = new RandomAccessFile(file, "r");
            int len = -1;
            while ((len = readRaf.read(buffer)) != -1) {
                raf.write(buffer, 0, len);
            }
            readRaf.close();
        }
        raf.close();
        return mergeFile;
    }

    private String getFilePath(String md5, String ext) {
        return uploadLocation + md5.substring(0, 1) + "/" + md5.substring(1, 2) + "/" + md5 + "/" + md5 + "." + ext;
    }

    private String getFolderPath(String md5) {
        return uploadLocation + md5.substring(0, 1) + "/" + md5.substring(1, 2) + "/" + md5 + "/";
    }

    private String getChunkFolderPath(String md5) {
        return uploadLocation + md5.substring(0, 1) + "/" + md5.substring(1, 2) + "/" + md5 + "/chunk/";
    }

    private String getRelativePath(String md5,String ext){
        return md5.substring(0, 1) + "/" + md5.substring(1, 2) + "/" + md5 + "/" ;
    }
}
