package com.leader.ren.model.bigscreen;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class LineVo {

    private List<String> title;
    private List<Map<String,String>> value;

}
