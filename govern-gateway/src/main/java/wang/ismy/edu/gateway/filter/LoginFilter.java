package wang.ismy.edu.gateway.filter;

import com.alibaba.fastjson.JSON;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.gateway.service.AuthService;

import javax.servlet.http.HttpServletRequest;


/**
 * 身份校验过滤器
 *
 * @author MY
 * @date 2019/10/29 22:23
 */
@Component
@AllArgsConstructor
public class LoginFilter extends ZuulFilter {

    private AuthService authService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (!authService.exist(authService.getToken(request))) {
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(401);
            ResponseResult result = new ResponseResult(CommonCode.UNAUTHENTICATED);
            currentContext.setResponseBody(JSON.toJSONString(result));
            currentContext.getResponse().setContentType("application/json;charset=utf-8");
        }

        return null;
    }
}
