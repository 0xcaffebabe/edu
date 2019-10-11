package wang.ismy.edu.manage_cms.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wang.ismy.edu.domain.cms.CmsConfig;
import wang.ismy.edu.manage_cms.dao.CmsConfigRepository;

import java.util.Optional;

/**
 * @author MY
 * @date 2019/10/11 14:48
 */
@Service
@AllArgsConstructor
public class CmsConfigService {

    private CmsConfigRepository repository;

    public CmsConfig findModel(String id){
        Optional<CmsConfig> opt = repository.findById(id);

        return opt.orElse(null);
    }
}
