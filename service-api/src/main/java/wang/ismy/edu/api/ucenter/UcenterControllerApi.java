package wang.ismy.edu.api.ucenter;

import wang.ismy.edu.domain.ucenter.ext.XcUserExt;

/**
 * @author MY
 * @date 2019/10/29 16:43
 */
public interface UcenterControllerApi {

    XcUserExt getUser(String username);
}
