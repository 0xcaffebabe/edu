package wang.ismy.edu.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import lombok.AllArgsConstructor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
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
import wang.ismy.edu.domain.cms.CmsTemplate;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;
import wang.ismy.edu.domain.cms.response.CmsCode;
import wang.ismy.edu.domain.cms.response.CmsPageResult;
import wang.ismy.edu.manage_cms.dao.CmsPageRepository;
import wang.ismy.edu.manage_cms.dao.CmsTemplateRepository;

import java.io.IOException;
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
public class PageService {

    private CmsPageRepository cmsPageRepository;

    private RestTemplate restTemplate;

    private CmsTemplateRepository cmsTemplateRepository;

    private GridFsTemplate gridFsTemplate;

    private GridFSBucket bucket;


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
        if (StringUtils.isEmpty(pageTemplate)){
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }

        String html = generateHtml(pageTemplate,model);
        if (StringUtils.isEmpty(html)){
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
            if (file == null){
                ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
            }

            GridFSDownloadStream stream = bucket.openDownloadStream(file.getObjectId());
            GridFsResource resource = new GridFsResource(file,stream);
            try {
                return IOUtils.toString(resource.getInputStream(), StandardCharsets.UTF_8);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private String generateHtml(String template,Map model){
        Configuration configuration = new Configuration(Configuration.getVersion());

        StringTemplateLoader loader = new StringTemplateLoader();
        loader.putTemplate("template",template);
        configuration.setTemplateLoader(loader);

        try {
            return FreeMarkerTemplateUtils
                    .processTemplateIntoString(configuration.getTemplate("template"), model);
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return null;

    }
}
