package com.leader.ren.model.system.vo;

import lombok.Data;

@Data
public class WeChatUserVo {
    private Long id;
    private String username;
    private String name;
    private String openId;
    private String permission;
}
