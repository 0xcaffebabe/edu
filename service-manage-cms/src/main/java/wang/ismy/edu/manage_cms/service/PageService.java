package wang.ismy.edu.manage_cms.service;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.QueryResponseResult;
import wang.ismy.edu.common.model.response.QueryResult;
import wang.ismy.edu.domain.cms.CmsPage;
import wang.ismy.edu.domain.cms.request.QueryPageRequest;
import wang.ismy.edu.manage_cms.dao.CmsPageRepository;

/**
 * @author MY
 * @date 2019/10/8 18:41
 */
@Service
@AllArgsConstructor
public class PageService {

    private CmsPageRepository repository;

    public QueryResponseResult findList(Integer page, Integer size, QueryPageRequest request) {
        if (page < 1){
            page = 1;
        }

        if (size < 1){
            size=1;
        }

        Page<CmsPage> all = repository.findAll(PageRequest.of(page - 1, size));
        QueryResponseResult result = new QueryResponseResult(CommonCode.SUCCESS,
                new QueryResult<CmsPage>().setList(all.getContent()).setTotal(all.getTotalElements()));

        return result;
    }
}
