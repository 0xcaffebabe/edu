package wang.ismy.edu.manage_cms.client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.CmsSite;
import wang.ismy.edu.manage_cms.client.dao.CmsPageRepository;
import wang.ismy.edu.manage_cms.client.dao.CmsSiteRepository;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author MY
 * @date 2019/10/13 19:54
 */
@Service
@AllArgsConstructor
@Slf4j
public class PageService {

    private CmsPageRepository cmsPageRepository;
    private CmsSiteRepository cmsSiteRepository;
    private GridFsTemplate gridFsTemplate;
    private GridFSBucket bucket;

    /**
     * 保存页面到物理路径
     *
     * @param pageId 页面ID
     */
    public void savePage(String pageId) {
        // 查询HTML文件
        CmsPage cmsPage = findPage(pageId);
        if (cmsPage == null) {

            return;
        }
        InputStream inputStream = findFile(cmsPage.getHtmlFileId());

        if (inputStream == null) {
            log.error("获取文件输入流失败,id:{}", cmsPage.getHtmlFileId());
            return;
        }
        CmsSite site = findSite(cmsPage.getSiteId());
        // 保存
        String pagePath = site.getSiteWebPath() + cmsPage.getPagePhysicalPath() + cmsPage.getPageName();
        try (OutputStream os = new FileOutputStream(pagePath)) {
            IOUtils.copy(inputStream, os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private CmsPage findPage(String pageId) {
        return cmsPageRepository.findById(pageId).orElse(null);
    }

    private CmsSite findSite(String siteId) {
        return cmsSiteRepository.findById(siteId).orElse(null);
    }

    private InputStream findFile(String fileId) {
        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));

        GridFSDownloadStream stream = bucket.openDownloadStream(file.getObjectId());

        GridFsResource resource = new GridFsResource(file, stream);

        try {
            return resource.getInputStream();
        } catch (IOException e) {
            return null;
        }
    }


}
