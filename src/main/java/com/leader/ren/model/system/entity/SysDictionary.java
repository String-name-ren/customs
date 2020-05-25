package com.leader.ren.model.system.entity;

import lombok.Data;

@Data
public class SysDictionary {
    private Long dicId;

    private String dicCode;

    private String dicDesc;

    private String dicName;

    private Integer dicValue;

    private Boolean active;

    private Boolean dflag;

}