package wang.ismy.edu.manage_cms.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.QueryResult;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;
import wang.ismy.edu.domain.cms.response.CmsPageResult;
import wang.ismy.edu.manage_cms.dao.CmsPageRepository;

import java.nio.channels.MulticastChannel;
import java.nio.charset.MalformedInputException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/10/8 18:41
 */
@Service
@AllArgsConstructor
public class PageService {

    private CmsPageRepository repository;

    public QueryResponseResult findList(Integer page, Integer size, QueryPageRequest request) {
        if (request == null){
            request = new QueryPageRequest();
        }

        CmsPage cmsPage = new CmsPage();


        Example<CmsPage> example = Example.of(cmsPage);
        if (page < 1){
            page = 1;
        }

        if (size < 1){
            size=1;
        }

        QueryPageRequest finalRequest = request;
        List<CmsPage> ret = repository.findAll()
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
        try{
            if (ret.size() > size){
                ret = ret.subList((page - 1) * size, (page - 1) * size + size);
            }

            return new QueryResponseResult(CommonCode.SUCCESS,
                    new QueryResult<CmsPage>().setList(ret).setTotal(ret.size()));
        }catch (Throwable t){
            return new QueryResponseResult(CommonCode.SUCCESS,
                    new QueryResult<CmsPage>().setList(List.of()).setTotal(0));
        }

    }

    public CmsPageResult add(CmsPage cmsPage){
        if (repository.findByPageNameAndSiteIdAndPageWebPath(
                cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath()) == null) {
            cmsPage.setPageId(null);
            CmsPage save = repository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS,save);
        }
        return new CmsPageResult(CommonCode.FAIL,null);
    }
}
