package wang.ismy.edu.manage_course.aspect;

import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import wang.ismy.edu.common.annotation.Permission;
import wang.ismy.edu.common.exception.ExceptionCast;
import wang.ismy.edu.domain.ucenter.response.AuthCode;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author MY
 * @date 2019/10/30 19:29
 */
@Aspect
@Component
public class PermissionAspect {

    @Pointcut("@annotation(wang.ismy.edu.common.annotation.Permission)")
    public void point() {
    }

    @Before("point()")
    public void before(JoinPoint joinPoint) throws IOException {
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();

        Permission permission = method.getAnnotation(Permission.class);
        if (permission == null){
            return;
        }
        ServletRequestAttributes ra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = ra.getRequest();

        String jwt = request.getHeader("jwt");

        Map map = JSON.parseObject(jwt, Map.class);
        List authorities = new ArrayList();
        try{
             authorities = JSON.parseObject(map.get("authorities").toString(), List.class);
        }catch (Exception e){

        }
        
        if (!authorities.contains(permission.value())) {
            // 没有权限，拒绝访问

            ra.getResponse().sendError(HttpStatus.SC_UNAUTHORIZED);
            //ExceptionCast.cast(AuthCode.UNAUTHORIZED);
        }
    }
}
