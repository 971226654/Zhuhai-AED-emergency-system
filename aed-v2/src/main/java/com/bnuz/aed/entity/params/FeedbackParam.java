package com.bnuz.aed.entity.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Leia Liang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "POST: 新增反馈的Param类")
public class FeedbackParam {

    @ApiModelProperty(value = "反馈内容", required = true)
    private String feedbackContent;

    @ApiModelProperty(value = "反馈星星", required = true)
    private int feedbackStars;

    @ApiModelProperty(value = "反馈时间", required = true)
    private String feedbackTime;

}
