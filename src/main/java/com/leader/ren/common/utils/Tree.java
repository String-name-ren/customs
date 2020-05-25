package com.leader.ren.common.utils;

import java.util.List;

public interface Tree {
    Long getId();
    Long getParentId();
    List<Tree> getChildren();
    void setChildren(List<Tree> its);
    Integer getLevel();
    void setLevel(Integer level);

    void add(Tree it);
}