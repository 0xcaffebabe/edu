package wang.ismy.edu.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import wang.ismy.edu.auth.client.UserClient;
import wang.ismy.edu.domain.ucenter.ext.XcUserExt;

/**
 * @author MY
 * @date 2019/10/28 22:06
 */
@Service
@AllArgsConstructor
public class UserService {

    private UserClient userClient;

    public UserDetails loadUserByUsername(String username){
        // 远程调用用户中心服务获取信息
        XcUserExt userExt = userClient.getUserExt(username);
        if (userExt == null){
            return null;
        }

        return new User(username,userExt.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(""));
    }
}
