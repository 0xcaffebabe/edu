package wang.ismy.edu.domain.learning.response;

import lombok.AllArgsConstructor;
import wang.ismy.edu.common.model.response.ResultCode;

import javax.xml.stream.FactoryConfigurationError;

/**
 * @author MY
 * @date 2019/10/23 22:26
 */

public enum  LearningCode implements ResultCode {
    GET_LEARNING_URL_FAIL(false,31002,"获取学习地址失败");

    private boolean success;
    private int code;
    private String msg;



    LearningCode(boolean success, int code, String msg) {
        this.success = success;
        this.code = code;
        this.msg = msg;
    }

    @Override
    public boolean success() {
        return false;
    }

    @Override
    public int code() {
        return 0;
    }

    @Override
    public String message() {
        return null;
    }
}
