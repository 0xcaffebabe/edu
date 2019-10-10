package wang.ismy.edu.common.exception;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import wang.ismy.edu.common.model.response.CommonCode;
import wang.ismy.edu.common.model.response.ResponseResult;
import wang.ismy.edu.common.model.response.ResultCode;

/**
 * @author MY
 * @date 2019/10/10 19:28
 */
@ControllerAdvice
@Slf4j
public class ExceptionCatch {

    private static final ImmutableMap<Class<? extends Throwable>, ResultCode>
            EXCEPTION_MAP = ImmutableMap.of(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult handle(CustomException e) {
        log.error("捕获异常:{}", e.getMessage(), e);
        return new ResponseResult(e.getResultCode());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult handle(Exception e) {
        log.error("捕获异常:{}", e.getMessage());

        ResultCode resultCode = EXCEPTION_MAP.get(e.getClass());
        if (resultCode == null){
            resultCode = CommonCode.SERVER_ERROR;
        }
        return new ResponseResult(resultCode);

    }


}
