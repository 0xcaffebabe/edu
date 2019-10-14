package wang.ismy.edu.api.cms;

import wang.ismy.edu.domain.system.SysDictionary;

/**
 * @author MY
 * @date 2019/10/14 20:46
 */
public interface SysDictionaryControllerApi {

    SysDictionary findByType(String type);
}
