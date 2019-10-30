package wang.ismy.edu.auth.service;

import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;

import org.springframework.security.rsa.crypto.KeyStoreKeyFactory;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.domain.ucenter.XcMenu;
import wang.ismy.edu.domain.ucenter.ext.AuthToken;
import wang.ismy.edu.domain.ucenter.ext.UserTokenStore;
import wang.ismy.edu.domain.ucenter.ext.XcUserExt;
import wang.ismy.edu.domain.ucenter.response.AuthCode;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author MY
 * @date 2019/10/28 21:13
 */
@Service
@AllArgsConstructor
public class AuthService {

    private UserService userService;

    private StringRedisTemplate redisTemplate;

    public AuthToken login(String username, String password) {
        XcUserExt ext = userService.loadUserByUsername(username);

        if (ext == null) {
            ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
        }
        if (new BCryptPasswordEncoder().matches(password, ext.getPassword())) {
            AuthToken authToken = new AuthToken();
            String accessToken = UUID.randomUUID().toString();
            authToken.setAccess_token(accessToken);
            authToken.setRefresh_token(accessToken);
            authToken.setJwt_token(generateJwt(ext));
            redisTemplate.opsForValue().set("user_token:" + accessToken, authToken.getJwt_token(), 1, TimeUnit.HOURS);
            return authToken;
        } else {
            ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
        }
        return null;
    }

    public AuthToken getUserToken(String token) {
        String result = redisTemplate.opsForValue().get("user_token:" + token);

        AuthToken authToken = new AuthToken();
        if (StringUtils.isEmpty(result)) {
            return authToken;
        }
        authToken.setAccess_token(token);
        authToken.setRefresh_token(token);
        authToken.setJwt_token(result);
        return authToken;
    }

    private String generateJwt(XcUserExt ext) {
        Map<String, String> map = new HashMap<>();
        map.put("username", ext.getUsername());
        map.put("password", ext.getPassword());
        List<String> permissions = ext.getPermissions().stream().map(XcMenu::getCode).collect(Collectors.toList());
        map.put("authorities", JSON.toJSONString(permissions));
        map.put("companyId", ext.getCompanyId());
        if (true) {
            return JSON.toJSONString(map);
        }
        // 密钥文件
        String keystore = "xc.keystore";

        // 密钥库密码
        String keystorePassword = "xuechengkeystore";

        // 密钥别名
        String alias = "xckey";

        // 密钥访问密码
        String keyPassword = "xuecheng";

        ClassPathResource classPathResource = new ClassPathResource(keystore);
        KeyStoreKeyFactory factory = new KeyStoreKeyFactory(classPathResource, keystorePassword.toCharArray());

//        // 密钥对
//        KeyPair keyPair = factory.getKeyPair(alias, keyPassword.toCharArray());
//        // 私钥
//        RSAPrivateKey key = (RSAPrivateKey) keyPair.getPrivate();
//
//
//        Jwt jwt = JwtHelper.encode(map.toString(), new RsaSigner(key));
        return map.toString();
    }

    public void clearToken(String token) {
        redisTemplate.delete("user_token:" + token);
    }
}
