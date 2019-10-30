package wang.ismy.edu.gateway.service;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import wang.ismy.edu.common.utils.CookieUtil;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @author MY
 * @date 2019/10/29 22:38
 */
@Service
@AllArgsConstructor
public class AuthService {

    private StringRedisTemplate redisTemplate;

    public String getToken(HttpServletRequest request) {
        return CookieUtil.readCookie(request, "token").get("token");
    }

    public boolean exist(String token) {
        if (StringUtils.isEmpty(token)) {
            return false;
        }

        return redisTemplate.hasKey("user_token:"+token);
    }

    public String getJwt(String token){
        return redisTemplate.opsForValue().get("user_token:"+token);
    }
}
