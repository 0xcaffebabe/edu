package wang.ismy.edu.manage.media.mq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wang.ismy.edu.common.utils.HlsVideoUtil;
import wang.ismy.edu.common.utils.Mp4VideoUtil;
import wang.ismy.edu.domain.media.MediaFile;
import wang.ismy.edu.domain.media.MediaFileProcess_m3u8;
import wang.ismy.edu.manage.media.dao.MediaFileRepository;

import java.util.Map;
import java.util.Optional;

/**
 * @author MY
 * @date 2019/10/22 21:35
 */
@Component
@Slf4j
public class MediaProcessTask {

    @Autowired
    private MediaFileRepository mediaFileRepository;

    @Value("${service-manage-media.ffmpeg-path}")
    String transferUrl;

    @Value("${service-manage-media.video-location}")
    String videoLocation;

    @RabbitListener(queues = "${service-manage-media.mq.queue-media-video-processor}",containerFactory = "customContainerFactory")
    public void process(String msg) {
        // 解析得到media id

        Map map = JSON.parseObject(msg, Map.class);
        String mediaId = map.get("mediaId").toString();
        log.info("接收到视频处理请求：{}",mediaId);
        // 取出文件信息
        Optional<MediaFile> opt = mediaFileRepository.findById(mediaId);
        if (!opt.isPresent()) {
            log.info("无法获取文件信息：{}", mediaId);
            return;
        }
        MediaFile mediaFile = opt.get();
        if (!mediaFile.getFileType().equalsIgnoreCase("avi")) {
            mediaFile.setProcessStatus("303004");
            log.info("无需处理");
            return;
        } else {
            mediaFile.setProcessStatus("303001");
            mediaFileRepository.save(mediaFile);
        }

        // 转换视频格式
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil(transferUrl,
                videoLocation + mediaFile.getFilePath() +mediaFile.getFileId()+".avi",
                mediaId + ".mp4",
                videoLocation + mediaFile.getFilePath());
        String result = mp4VideoUtil.generateMp4();
        if (!"success".equals(result)) {
            log.info("视频转换失败:{},{}", mediaId, result);
            mediaFile.setProcessStatus("303003");
            mediaFileRepository.save(mediaFile);
            return;
        }

        // 生成M3U8
        String m3u8_name = mediaId + ".m3u8";
        String m3u8folder_path = videoLocation + mediaFile.getFilePath() + "hls/";
        String video_path = videoLocation + mediaFile.getFilePath() + mediaId + ".mp4";
        HlsVideoUtil hlsVideoUtil = new HlsVideoUtil(transferUrl,
                video_path,
                m3u8_name,
                m3u8folder_path);
        result = hlsVideoUtil.generateM3u8();
        if (!"success".equals(result)) {
            log.info("生成m3u8失败,{}", result);
            mediaFile.setProcessStatus("303003");
            mediaFileRepository.save(mediaFile);
            return;
        }
        mediaFile.setProcessStatus("303002");
        MediaFileProcess_m3u8 mediaFileProcessM3u8 = new MediaFileProcess_m3u8();
        mediaFileProcessM3u8.setTslist(hlsVideoUtil.get_ts_list());
        mediaFile.setMediaFileProcess_m3u8(mediaFileProcessM3u8);
        mediaFile.setFileUrl(mediaFile.getFilePath() + "hls/" + mediaId + ".m3u8");
        mediaFileRepository.save(mediaFile);

    }
}
