package wang.ismy.edu.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wang.ismy.edu.common.client.ServiceList;
import wang.ismy.edu.domain.ucenter.ext.XcUserExt;

/**
 * @author MY
 * @date 2019/10/29 17:09
 */
@FeignClient(ServiceList.SERVICE_UCENTER)
public interface UserClient {

    /**
     * 远程调用用户中心服务获取信息
     * @param username 用户名
     * @return 用户扩展实体
     */
    @GetMapping("ucenter/getuserext")
    XcUserExt getUserExt(@RequestParam("username") String username);
}
