package com.leader.ren.common.jwt;


public interface IJWTInfo {
    /**
     * 获取主键
     * @return
     */
    String getId();

    /**
     * 获取编码
     * @return
     */
    String getCode();

    /**
     * 获取名称
     * @return
     */
    String getName();

    /**
     * 获取时间
     * @return
     */
    Long getTime();

}
