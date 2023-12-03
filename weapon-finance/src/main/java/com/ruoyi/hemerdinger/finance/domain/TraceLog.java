package com.ruoyi.hemerdinger.finance.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 追踪日志对象 trace_log
 *
 * @author lijingxiang
 * @date 2023-11-30
 */
@ApiModel(value = "ClassName", description = "追踪日志对象")
public class TraceLog extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 内容 */
    @Excel(name = "内容")
    @ApiModelProperty(value = "内容", example = "1")
    private String content;

    /** 股票编码 */
    @Excel(name = "股票编码")
    @ApiModelProperty(value = "股票编码", example = "1")
    private String code;

    /** 日志类型 */
    @Excel(name = "日志类型")
    @ApiModelProperty(value = "日志类型", example = "1")
    private String logType;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }
    public void setContent(String content)
    {
        this.content = content;
    }

    public String getContent()
    {
        return content;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setLogType(String logType)
    {
        this.logType = logType;
    }

    public String getLogType()
    {
        return logType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("content", getContent())
            .append("code", getCode())
            .append("logType", getLogType())
            .toString();
    }
}
