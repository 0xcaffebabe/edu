package wang.ismy.edu.manage_cms.dao;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.io.IOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import wang.ismy.edu.domain.cms.CmsConfig;
import wang.ismy.edu.domain.cms.CmsPage;


import java.awt.image.RescaleOp;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmsPageRepositoryTest {

    @Autowired
    CmsPageRepository repository;

    @Autowired
    RestTemplate template;

    @Autowired
    GridFsTemplate gridFsTemplate;

    @Autowired
    GridFSBucket bucket;

    @Test
    public void test(){
        repository.findAll().forEach(System.out::println);
    }

    @Test
    public void findPage(){
        Page<CmsPage> list = repository.findAll(PageRequest.of(1, 5));

        assertEquals(5,list.getSize());
    }

    @Test
    public void findByExample(){
        ExampleMatcher exampleMatcher=ExampleMatcher.matching()
                .withMatcher("pageAliase",ExampleMatcher.GenericPropertyMatchers.contains());

        CmsPage cmsPage = new CmsPage();
        cmsPage.setPageAliase("ccc");
        Example<CmsPage>example=Example.of(cmsPage,exampleMatcher);
        List<CmsPage> all = repository.findAll(example);
        assertNotEquals(0,all.size());
    }

    @Test
    public void testRest(){
        ResponseEntity<CmsConfig> entity = template.getForEntity("http://127.0.0.1:31001/cms/config/model/5a791725dd573c3574ee333f", CmsConfig.class);

        CmsConfig body = entity.getBody();

        System.out.println(body);

    }

    @Test
    public void testGridFS() throws FileNotFoundException {
        ObjectId id = gridFsTemplate.store(new FileInputStream("D:\\学习\\黑马JavaEE 57期\\19 微服务项目【学成在线】\\day09 课程预览 Eureka Feign\\资料\\课程详情页面模板\\course.ftl"),"course.ftl");
        //5da86359ca9ce3561c70ba67
        System.out.println(id);
    }

    @Test
    public void down() throws IOException {
        String id = "5da0381a8baddb2d54631077";
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(id)));

        GridFSDownloadStream stream = bucket.openDownloadStream(file.getObjectId());
        GridFsResource resource = new GridFsResource(file,stream);
        String ret = IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);

        System.out.println(ret);
    }



}