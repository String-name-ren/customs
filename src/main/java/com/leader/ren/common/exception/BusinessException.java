package com.leader.ren.common.exception;




import com.leader.ren.common.constant.RestMsg;

import java.text.MessageFormat;

public class BusinessException extends RuntimeException {

    private RestMsg result;

    public BusinessException(RestMsg result) {
        super(result.getName());
        this.result = result;
    }

    public BusinessException(RestMsg result, String message) {
        super(message);
        this.result = result;
    }

    public BusinessException(String message) {
        super(message);
        this.result = RestMsg.FAIL;
    }

    public BusinessException(String message, Throwable throwable) {
        super(message, throwable);
        this.result = RestMsg.FAIL;
    }

    public RestMsg getResult() {
        return result;
    }

    /**
     * @param message 异常信息
     * @return cn.com.waterelephant.wenewrs.common.excepion.BusinessException
     * @author kongchong
     * @date 2019-03-18 17:17
     */
    public static BusinessException badRequest(String message) {
        return new BusinessException(message);
    }

    /**
     * @param message   异常信息
     * @param arguments 替换参数，可多个 {%s},{%s}...
     * @return cn.com.waterelephant.wenewrs.common.excepion.BusinessException
     * @author kongchong
     * @date 2019-03-18 17:17
     */
    public static BusinessException badRequest(String message, Object... arguments) {
        return new BusinessException(MessageFormat.format(message, arguments));
    }


}
