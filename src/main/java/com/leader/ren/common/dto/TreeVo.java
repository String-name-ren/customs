package com.leader.ren.common.dto;

import com.leader.ren.common.utils.Tree;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value = "树型结构实体")
public class TreeVo implements Tree, Serializable {
    private static final long serialVersionUID = -5668554152671000204L;

    /**
     * 本级主键
     */
    @ApiModelProperty(value = "本级主键", name = "id")
    private Long id;

    /**
     * 父级主键
     */
    @ApiModelProperty(value = "父级主键", name = "parentId")
    private Long parentId;

    /**
     * 子级节点
     */
    @ApiModelProperty(value = "子级节点", name = "children")
    List<Tree> children = new ArrayList<Tree>();

    @Override
    public void setChildren(List<Tree> its) {
        this.children = its;
    }

    @Override
    public List<Tree> getChildren() {
        return this.children;
    }

    /**
     * 节点等级
     */
    @ApiModelProperty(value = "节点等级", name = "level")
    Integer level = 0;

    @Override
    public void add(Tree node) {
        children.add(node);
    }
}