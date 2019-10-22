package wang.ismy.edu.manage.media;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wang.ismy.edu.common.utils.Mp4VideoUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ManageMediaProcessorApplicationTest {

    @Test
    public void test() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.redirectErrorStream(true);

        processBuilder.command("ping","www.baidu.com");
        Process start = processBuilder.start();

        InputStreamReader reader = new InputStreamReader(start.getInputStream(),"gbk");
        char[] chars = new char[1024];
        int len = -1;
        while ((len=reader.read(chars))!=-1){
            System.out.print(new String(chars,0,len));
        }
    }

    @Test
    public void convert(){
        Mp4VideoUtil mp4VideoUtil = new Mp4VideoUtil("C:/dev/ffmpeg-20191022-0b8956b-win64-static/bin/ffmpeg.exe",
                "D:\\学习\\黑马JavaEE 57期\\19 微服务项目【学成在线】\\day14 媒资管理\\资料\\solr.avi",
                "1.mp4",
                "c:/dev/video");
        String s = mp4VideoUtil.generateMp4();
        System.out.println(s);

    }
}