package wang.ismy.edu.manage_cms.service;

import com.alibaba.fastjson.JSON;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.QueryResult;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.CmsSite;
import wang.ismy.edu.domain.cms.CmsTemplate;
import wang.ismy.edu.domain.cms.ext.CmsPostPageResult;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;
import wang.ismy.edu.domain.cms.response.CmsCode;
import wang.ismy.edu.domain.cms.response.CmsPageResult;
import wang.ismy.edu.manage_cms.config.RabbitMqConfig;
import wang.ismy.edu.manage_cms.dao.CmsPageRepository;
import wang.ismy.edu.manage_cms.dao.CmsSiteRepository;
import wang.ismy.edu.manage_cms.dao.CmsTemplateRepository;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/10/8 18:41
 */
@Service
@AllArgsConstructor
@Slf4j
public class PageService {

    private CmsPageRepository cmsPageRepository;

    private RestTemplate restTemplate;

    private CmsTemplateRepository cmsTemplateRepository;

    private CmsSiteRepository cmsSiteRepository;

    private GridFsTemplate gridFsTemplate;

    private GridFSBucket bucket;

    private RabbitTemplate rabbitTemplate;


    public QueryResponseResult findList(Integer page, Integer size, QueryPageRequest request) {
        if (request == null) {
            request = new QueryPageRequest();
        }

        CmsPage cmsPage = new CmsPage();

        Example<CmsPage> example = Example.of(cmsPage);
        if (page < 1) {
            page = 1;
        }

        if (size < 1) {
            size = 1;
        }

        QueryPageRequest finalRequest = request;
        List<CmsPage> ret = cmsPageRepository.findAll()
                .stream()
                .filter(p -> {
                    boolean f = true;
                    if (!StringUtils.isEmpty(finalRequest.getSiteId())) {
                        f = finalRequest.getSiteId().equals(p.getSiteId());
                    }

                    if (!StringUtils.isEmpty(finalRequest.getTemplateId())) {
                        f &= finalRequest.getTemplateId().equals(p.getTemplateId());
                    }

                    if (!StringUtils.isEmpty(finalRequest.getPageAliase())) {
                        f &= p.getPageAliase().contains(finalRequest.getPageAliase());
                    }
                    return f;
                })
                .collect(Collectors.toList());
        try {
            if (ret.size() > size) {
                ret = ret.subList((page - 1) * size, (page - 1) * size + size);
            }

            return new QueryResponseResult(CommonCode.SUCCESS,
                    new QueryResult<CmsPage>().setList(ret).setTotal(ret.size()));
        } catch (Throwable t) {
            return new QueryResponseResult(CommonCode.SUCCESS,
                    new QueryResult<CmsPage>().setList(List.of()).setTotal(0));
        }

    }

    public CmsPageResult add(CmsPage cmsPage) {

        if (cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(
                cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath()) != null) {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }

        cmsPage.setPageId(null);
        CmsPage save = cmsPageRepository.save(cmsPage);
        return new CmsPageResult(CommonCode.SUCCESS, save);


    }

    public CmsPage findById(String id) {
        Optional<CmsPage> opt = cmsPageRepository.findById(id);

        return opt.orElse(null);
    }

    public CmsPageResult update(CmsPage cmsPage) {
        if (cmsPage != null) {
            if (findById(cmsPage.getPageId()) != null) {
                CmsPage one = new CmsPage();
                one.setPageId(cmsPage.getPageId());
                one.setTemplateId(cmsPage.getTemplateId());
                one.setSiteId(cmsPage.getSiteId());
                one.setPageName(cmsPage.getPageName());
                one.setPageWebPath(cmsPage.getPageWebPath());
                one.setPagePhysicalPath(cmsPage.getPagePhysicalPath());
                return new CmsPageResult(CommonCode.SUCCESS, cmsPageRepository.save(cmsPage));

            }
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }

    public ResponseResult delete(String id) {

        Optional<CmsPage> opt = cmsPageRepository.findById(id);

        if (opt.isPresent()) {
            cmsPageRepository.deleteById(id);
            return new ResponseResult(CommonCode.SUCCESS);
        }
        return new ResponseResult(CommonCode.FAIL);
    }


    /**
     * 根据页面ID生成页面html
     *
     * @param pageId ID
     * @return html
     */
    public String getPageHtml(String pageId) {

        Map model = getModel(pageId);
        if (model == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }

        String pageTemplate = getPageTemplate(pageId);
        if (StringUtils.isEmpty(pageTemplate)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        String html = generateHtml(pageTemplate, model);
        if (StringUtils.isEmpty(html)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        return html;
    }

    private Map getModel(String pageId) {
        CmsPage page = findById(pageId);

        if (page == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTFOUND);
        }

        if (StringUtils.isEmpty(page.getDataUrl())) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }

        ResponseEntity<Map> entity = restTemplate.getForEntity(page.getDataUrl(), Map.class);

        return entity.getBody();
    }

    private String getPageTemplate(String pageId) {
        CmsPage page = findById(pageId);

        if (page == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTFOUND);
        }

        String templateId = page.getTemplateId();
        if (StringUtils.isEmpty(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        // 根据模板ID查询模板,获取到模板文件ID
        Optional<CmsTemplate> opt = cmsTemplateRepository.findById(templateId);
        if (opt.isPresent()) {
            String fileId = opt.get().getTemplateFileId();
            GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
            if (file == null) {
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
            }

            GridFSDownloadStream stream = bucket.openDownloadStream(file.getObjectId());
            GridFsResource resource = new GridFsResource(file, stream);
            try {
                return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String generateHtml(String template, Map model) {
        Configuration configuration = new Configuration(Configuration.getVersion());

        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate("template", template);
        configuration.setTemplateLoader(loader);

        try {
            return FreeMarkerTemplateUtils
                    .processTemplateIntoString(configuration.getTemplate("template"), model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return null;

    }

    public ResponseResult postPage(String pageId) {
        String html = getPageHtml(pageId);

        //保存静态化后的html到文件服务器
        CmsPage cmsPage = saveHtml(pageId, html);
        sendMessage(cmsPage);
        return new ResponseResult(CommonCode.SUCCESS);
    }

    private CmsPage saveHtml(String pageId, String html) {
        CmsPage cmsPage = findById(pageId);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTFOUND);
        }
        InputStream inputStream = null;
        try {
            // 保存
            inputStream = IOUtils.toInputStream(html, "utf8");
            ObjectId fileId = gridFsTemplate.store(inputStream, cmsPage.getPageName());
            // 更新文件ID到page
            cmsPage.setHtmlFileId(fileId.toHexString());
            return cmsPageRepository.save(cmsPage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void sendMessage(CmsPage page) {
        String msg = JSON.toJSONString(Map.of("pageId", page.getPageId()));
        log.info("发送一条页面发布请求:"+msg);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EX_ROUTING_CMS_POSTPAGE, page.getSiteId(), msg);

    }

    /**
     * 有则更新，无则添加
     *
     * @param page 页面实体
     * @return 添加结果
     */
    public CmsPageResult save(CmsPage page) {
        CmsPage cmsPage =
                cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(page.getPageName(), page.getSiteId(), page.getPageWebPath());
        if (cmsPage != null) {
            page.setPageId(cmsPage.getPageId());
            return update(page);
        }
        return add(page);
    }

    public CmsPostPageResult postPageQuick(CmsPage cmsPage) {
        // 存储页面信息
        CmsPageResult save = save(cmsPage);
        if (!save.isSuccess()){
            ExceptionCast.cast(CommonCode.FAIL);
        }
        // 执行页面发布
            // 静态化->保存到文件服务器->发送消息
        ResponseResult post = postPage(save.getCmsPage().getPageId());
        if (!post.isSuccess()){
            ExceptionCast.cast(CommonCode.FAIL);
        }

        CmsSite cmsSite = cmsSiteRepository.findById(save.getCmsPage().getSiteId()).orElse(null);
        if (cmsSite == null){
            ExceptionCast.cast(CommonCode.FAIL);
        }

        String pageUrl = cmsSite.getSiteDomain()+cmsSite.getSiteWebPath()+save.getCmsPage().getPageWebPath()+
                save.getCmsPage().getPageName();
        return new CmsPostPageResult(CommonCode.SUCCESS,pageUrl);

    }
}
