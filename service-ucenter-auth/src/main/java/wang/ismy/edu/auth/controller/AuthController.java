package wang.ismy.edu.auth.controller;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import wang.ismy.edu.api.auth.AuthControllerApi;
import wang.ismy.edu.auth.service.AuthService;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.utils.CookieUtil;
import wang.ismy.edu.domain.ucenter.ext.AuthToken;
import wang.ismy.edu.domain.ucenter.request.LoginRequest;
import wang.ismy.edu.domain.ucenter.response.JwtResult;
import wang.ismy.edu.domain.ucenter.response.LoginResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2019/10/28 21:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("auth")
public class AuthController implements AuthControllerApi {

    private AuthService authService;

    @PostMapping("login")
    @Override
    public LoginResult login(LoginRequest request, HttpServletResponse response) {

        // 申请令牌
        AuthToken authToken = authService.login(request.getUsername(), request.getPassword());

        // 写入令牌
        CookieUtil.addCookie(response, "edu.com", "/", "token", authToken.getAccess_token(), 3600, false);
        return new LoginResult(CommonCode.SUCCESS, authToken.getAccess_token());
    }

    @GetMapping("userjwt")
    @Override
    public JwtResult userJwt(@CookieValue("token") String token) {
        // 获取cookie中的token
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        // 根据token查询jwt

        AuthToken userToken = authService.getUserToken(token);
        return new JwtResult(CommonCode.SUCCESS, userToken.getJwt_token());
    }

    @PostMapping("userlogout")
    @Override
    public ResponseResult logout(@CookieValue("token") String token, HttpServletResponse response) {
        // 删除redis token
        authService.clearToken(token);
        // 清除cookie
        CookieUtil.addCookie(response, "edu.com", "/", "token", "", 0, false);
        return new ResponseResult(CommonCode.SUCCESS);
    }
}
