package com.leader.ren.common.jwt;

import java.io.Serializable;

public class JWTInfo implements IJWTInfo,Serializable {
    private String id;
    private String code;
    private String name;
    private Long   time;



    public JWTInfo(String id, String code, String name, Long time) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) { this.code = code; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }


    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        JWTInfo jwtInfo = (JWTInfo) o;

        if (id != null ? !id.equals(jwtInfo.id) : jwtInfo.id != null) {
            return false;
        }

        return code != null ? code.equals(jwtInfo.code) : jwtInfo.code == null;
    }

    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
