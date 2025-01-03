package com.ruoyi.hemerdinger.finance.domain;

import com.alibaba.fastjson.annotation.JSONField;
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
@Document(indexName = EsConstant.REPORT_INDEX_NAME)
@ApiModel(value = "经营评述")
public class StockReport {

        private static final long serialVersionUID = -2124485309475024490L;

        @Id
        @ApiModelProperty(value = "经营评述ID")
        private String id;

        @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
        @ApiModelProperty(value = "股票编码")
        private String SECUCODE;

        @Field(type = FieldType.Keyword)
        @ApiModelProperty(value = "编码")
        private String SECURITY_CODE;

        @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd")
        @JSONField(format = "yyyy-MM-dd HH:mm:ss")
        @ApiModelProperty(value = "报告日期")
        private LocalDate REPORT_DATE;

        @Field(type = FieldType.Text, analyzer = "ik_max_word", searchAnalyzer = "ik_smart")
        @ApiModelProperty(value = "经营评述")
        private String BUSINESS_REVIEW;

}
