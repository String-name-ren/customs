package com.leader.ren.common.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@ApiModel(description = "分页响应实体")
public class PageVo<T> implements Serializable {
	private static final long serialVersionUID = -5668554152671000202L;
	@ApiModelProperty(value="当前页数", name="page")
	private Integer page = 1;

	@ApiModelProperty(value="每页条数", name="size")
	private Integer size = 10;

	@ApiModelProperty(value="总数", name="total")
	private Long total;

	@ApiModelProperty(value="数据", name="data")
	private List<T> data;
}
