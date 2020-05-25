package com.leader.ren.common.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * redis缓存常量
 */
public interface RedisKey {

    /**
     * 监控链接
     */
    @Getter
    @AllArgsConstructor
    enum Monitor implements RedisKey {
        BDM_MONITOR("monitorurl:%s");

        private String key;

        public String getKey() {
            return key;
        }

        public String getKey(String... keys) {
            return String.format(key, keys);
        }
    }
}
