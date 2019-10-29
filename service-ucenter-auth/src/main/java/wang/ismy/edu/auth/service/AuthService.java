package wang.ismy.edu.auth.service;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaSigner;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.domain.ucenter.ext.AuthToken;
import wang.ismy.edu.domain.ucenter.response.AuthCode;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
        UserDetails details = userService.loadUserByUsername(username);
        if (details == null) {
            ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
        }
        if (new BCryptPasswordEncoder().matches(password, details.getPassword())) {
            AuthToken authToken = new AuthToken();
            String accessToken = UUID.randomUUID().toString();
            authToken.setAccess_token(accessToken);
            authToken.setRefresh_token(accessToken);
            authToken.setJwt_token(generateJwt(details));
            redisTemplate.opsForValue().set("user_token:" + accessToken, authToken.getJwt_token(), 1, TimeUnit.HOURS);
            return authToken;
        } else {
            ExceptionCast.cast(AuthCode.AUTH_CREDENTIAL_ERROR);
        }
        return null;
    }

    private String generateJwt(UserDetails details) {
        Map<String, String> map = new HashMap<>();
        map.put("username", details.getUsername());
        map.put("password", details.getPassword());
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

        // 密钥对
        KeyPair keyPair = factory.getKeyPair(alias, keyPassword.toCharArray());
        // 私钥
        RSAPrivateKey key = (RSAPrivateKey) keyPair.getPrivate();


        Jwt jwt = JwtHelper.encode(map.toString(), new RsaSigner(key));
        return jwt.getEncoded();
    }
}
