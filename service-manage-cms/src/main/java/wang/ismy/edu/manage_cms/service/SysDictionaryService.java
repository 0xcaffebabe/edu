package wang.ismy.edu.manage_cms.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wang.ismy.edu.domain.system.SysDictionary;
import wang.ismy.edu.manage_cms.dao.SysDictionaryRepository;

/**
 * @author MY
 * @date 2019/10/14 20:48
 */
@Service
@AllArgsConstructor
public class SysDictionaryService {

    private SysDictionaryRepository sysDictionaryRepository;

    public SysDictionary findByType(String type){
        return sysDictionaryRepository.findBydType(type);
    }
}
