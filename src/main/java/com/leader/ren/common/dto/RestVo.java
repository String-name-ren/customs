package com.leader.ren.common.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.leader.ren.common.constant.RestMsg;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Date;

@Data
@ApiModel(value = "业务响应实体")
public class RestVo<T> implements Serializable {

    private static final long serialVersionUID = -5668554152671000203L;

    /**
     * 应用返回编码
     */
    @ApiModelProperty(value = "应用返回编码", name = "appCode")
    private String code;

    /**
     * 应用返回消息
     */
    @ApiModelProperty(value = "应用返回消息", name = "appMsg")
    private String msg;

    /**
     * 应用返回结果
     */
    @ApiModelProperty(value = "应用返回结果", name = "result")
    private T data;

    @ApiModelProperty(value = "应用返回结果", name = "success")
    private boolean flag = true;

    @ApiModelProperty(value = "服务器时间", name = "now")
    private Date now = new Date();

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getNowNumber() {
        return now;
    }

    public RestVo() {
        RestMsg result = RestMsg.SUCCESS;
        this.code = result.getCode();
        this.msg = result.getName();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        if (StringUtils.isBlank(this.code) || !RestMsg.SUCCESS.getCode().equals(this.code)) {
            this.flag = false;
        }
    }

    public void setAppCode(RestMsg result) {
        this.code = result.getCode();
        this.msg = result.getName();
        if (StringUtils.isBlank(this.code) || !RestMsg.SUCCESS.getCode().equals(this.code)) {
            this.flag = false;
        }
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static RestVo SUCCESS() {
        RestVo<Object> restVo = new RestVo<>();
        RestMsg result = RestMsg.SUCCESS;
        restVo.setCode(result.getCode());
        restVo.setMsg(result.getName());
        return restVo;
    }

    public static RestVo SUCCESS(Object obj) {
        RestVo<Object> restVo = new RestVo<>();
        RestMsg result = RestMsg.SUCCESS;
        restVo.setCode(result.getCode());
        restVo.setMsg(result.getName());
        restVo.setData(obj);
        return restVo;
    }

    public static RestVo SUCCESS(Object obj, String msg) {
        RestVo<Object> restVo = new RestVo<>();
        RestMsg result = RestMsg.SUCCESS;
        restVo.setCode(result.getCode());
        restVo.setMsg(msg);
        restVo.setData(obj);
        return restVo;
    }


    public static RestVo FAIL() {
        RestVo<Object> restVo = new RestVo<>();
        RestMsg result = RestMsg.FAIL;
        restVo.setCode(result.getCode());
        restVo.setMsg(result.getName());
        restVo.setFlag(false);
        return restVo;
    }

    public static RestVo FAIL(String msg) {
        RestVo<Object> restVo = new RestVo<>();
        RestMsg result = RestMsg.FAIL;
        restVo.setCode(result.getCode());
        restVo.setMsg(msg);
        restVo.setFlag(false);
        return restVo;
    }


    public static RestVo FAIL(RestMsg result) {
        RestVo<Object> restVo = new RestVo<>();
        restVo.setCode(result.getCode());
        restVo.setMsg(result.getName());
        restVo.setFlag(false);
        return restVo;
    }

    public static RestVo ERROR() {
        RestVo<Object> restVo = new RestVo<>();
        restVo.setAppCode(RestMsg.ERROR);
        return restVo;
    }

    public static RestVo FAIL(String code, String msg) {
        RestVo<Object> restVo = new RestVo<>();
        restVo.setCode(code);
        restVo.setMsg(msg);
        restVo.setFlag(false);
        return restVo;
    }

    public static RestVo FAIL(RestMsg result, String msg) {
        RestVo<Object> restVo = new RestVo<>();
        restVo.setCode(result.getCode());
        restVo.setMsg(msg);
        restVo.setFlag(false);
        return restVo;
    }

    public static RestVo FAIL(Object obj, String code, String msg) {
        RestVo<Object> restVo = new RestVo<>();
        restVo.setCode(code);
        restVo.setMsg(msg);
        restVo.setData(obj);
        restVo.setFlag(false);
        return restVo;
    }

    public static RestVo FAIL(Object obj, String msg) {
        RestVo<Object> restVo = new RestVo<>();
        RestMsg result = RestMsg.FAIL;
        restVo.setCode(result.getCode());
        restVo.setMsg(msg);
        restVo.setData(obj);
        restVo.setFlag(false);
        return restVo;
    }

    /**
     * 用于替换参数的形式
     *
     * @param result
     * @param arguments 替换参数，可多个 {%s},{%s}...
     * @return cn.com.waterelephant.wenewrs.domain.common.vo.RestVo
     * @author 施冬冬
     * date:  2019/4/28 13:37
     */
    public static RestVo FAIL(RestMsg result, Object... arguments) {
        RestVo<Object> restVo = new RestVo<>();
        restVo.setCode(result.getCode());
        restVo.setMsg(MessageFormat.format(result.getName(), arguments));
        restVo.setFlag(false);
        return restVo;
    }

    /**
     * 如果接口对象不为空并且返回码为成功(SUCCESS.getCode()), 返回true，否则返回false
     *
     * @param restVo
     * @return
     * @author: 兰祥建
     * date 2018/12/27 18:23
     */
    public static boolean checkSuccessCode(RestVo restVo) {
        return restVo != null && RestMsg.SUCCESS.getCode().equals(restVo.getCode());
    }

    public boolean isSuccess() {
        return flag;
    }
}
