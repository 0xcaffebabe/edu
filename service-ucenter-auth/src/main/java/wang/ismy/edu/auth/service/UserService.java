package wang.ismy.edu.auth.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import wang.ismy.edu.auth.client.UserClient;
import wang.ismy.edu.domain.ucenter.XcMenu;
import wang.ismy.edu.domain.ucenter.ext.XcUserExt;

import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/10/28 22:06
 */
@Service
@AllArgsConstructor
public class UserService {

    private UserClient userClient;

    public XcUserExt loadUserByUsername(String username){
        // 远程调用用户中心服务获取信息
        return userClient.getUserExt(username);

//        if (userExt == null){
//            return null;
//        }
//        String permissions = StringUtils.joinWith(",",userExt.getPermissions().stream().map(XcMenu::getCode).collect(Collectors.toList()));
//        return new User(username,userExt.getPassword(), AuthorityUtils.commaSeparatedStringToAuthorityList(permissions));
    }
}
