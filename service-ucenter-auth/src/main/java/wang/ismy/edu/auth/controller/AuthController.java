package wang.ismy.edu.auth.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import wang.ismy.edu.api.auth.AuthControllerApi;
import wang.ismy.edu.auth.service.AuthService;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.utils.CookieUtil;
import wang.ismy.edu.domain.ucenter.ext.AuthToken;
import wang.ismy.edu.domain.ucenter.request.LoginRequest;
import wang.ismy.edu.domain.ucenter.response.LoginResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2019/10/28 21:11
 */
@RestController
@AllArgsConstructor
public class AuthController implements AuthControllerApi {

    private AuthService authService;

    @PostMapping("login")
    @Override
    public LoginResult login(LoginRequest request, HttpServletResponse response) {

        // 申请令牌
        AuthToken authToken = authService.login(request.getUsername(),request.getPassword());

        // 写入令牌
        CookieUtil.addCookie(response,"www.edu.com","/","token",authToken.getAccess_token(),3600,false);
        return new LoginResult(CommonCode.SUCCESS,authToken.getAccess_token());
    }

    @Override
    public ResponseResult logout() {
        return null;
    }
}
