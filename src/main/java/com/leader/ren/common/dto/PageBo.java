package com.leader.ren.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ApiModel(description = "分页请求实体")
public class PageBo<T> implements Serializable {

	@NotNull(message = "起始页为空")
	@Min(value = 1, message = "起始页最小1")
	@ApiModelProperty(value="当前页数", required = true, example = "1")
	private Integer page;

	@NotNull(message = "页大小为空")
	@Min(value = 5, message = "页大小最小5")
	@Max(value = 100, message = "页大小最大100")
	@ApiModelProperty(value="每页显示数", required = true, example = "10")
	private Integer size;

	@ApiModelProperty(value="参数")
	private T param;
}
