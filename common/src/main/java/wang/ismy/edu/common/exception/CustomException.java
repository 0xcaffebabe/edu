package wang.ismy.edu.common.exception;

import lombok.Data;
import wang.ismy.edu.common.model.response.ResultCode;

/**
 * @author MY
 * @date 2019/10/10 19:23
 */
@Data
public class CustomException extends RuntimeException {

    private ResultCode resultCode;

    public CustomException(ResultCode resultCode) {

        this.resultCode = resultCode;
    }
}
