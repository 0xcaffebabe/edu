package wang.ismy.edu.api.auth;

import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.domain.ucenter.request.LoginRequest;
import wang.ismy.edu.domain.ucenter.response.LoginResult;

import javax.servlet.http.HttpServletResponse;

/**
 * @author MY
 * @date 2019/10/28 20:26
 */
public interface AuthControllerApi {

    LoginResult login(LoginRequest request, HttpServletResponse response);

    ResponseResult logout();
}