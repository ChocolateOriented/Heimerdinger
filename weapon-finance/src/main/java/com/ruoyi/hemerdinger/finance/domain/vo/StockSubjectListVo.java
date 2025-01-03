package com.ruoyi.hemerdinger.finance.domain.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.hemerdinger.finance.constant.EsConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@ToString(callSuper = true)
@ApiModel(value = "概念相关股票列表")
public class StockSubjectListVo {

        /** 名称 */
        @Excel(name = "名称")
        @ApiModelProperty(value = "名称", example = "1")
        private String name;

        /** 代码 */
        @Excel(name = "代码")
        @ApiModelProperty(value = "代码", example = "1")
        private String code;

        /** 评分 */
        @Excel(name = "评分")
        @ApiModelProperty(value = "评分", example = "1")
        private Double score;
}
