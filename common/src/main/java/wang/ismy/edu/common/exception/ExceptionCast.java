package wang.ismy.edu.common.exception;

import wang.ismy.edu.common.model.response.ResultCode;

/**
 * @author MY
 * @date 2019/10/10 19:27
 */
public class ExceptionCast {

    public static void cast (ResultCode code){
        throw new CustomException(code);
    }
}
