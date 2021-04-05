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
@ApiModel(value = "POST: 反馈结果的Param类")
public class FeedbackResultParam {

    @ApiModelProperty(value = "反馈ID", required = true)
    private String feedbackId;

    @ApiModelProperty(value = "反馈结果", required = true)
    private String result;

    @ApiModelProperty(value = "处理时间", required = true)
    private String resultTime;

}
