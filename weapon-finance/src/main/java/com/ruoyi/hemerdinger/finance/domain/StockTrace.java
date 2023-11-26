package com.ruoyi.hemerdinger.finance.domain;

    import java.math.BigDecimal;
    import java.util.Date;
    import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 股票追踪对象 stock_trace
 *
 * @author lijingxiang
 * @date 2023-11-26
 */
@ApiModel(value = "ClassName", description = "股票追踪对象")
public class StockTrace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty(value = "名称", example = "1")
    private String name;

    /** 代码 */
    @Excel(name = "代码")
    @ApiModelProperty(value = "代码", example = "1")
    private String code;

    /** 成本价格 */
    @Excel(name = "成本价格")
    @ApiModelProperty(value = "成本价格", example = "1")
    private BigDecimal costPrice;

    /** 持有份额 */
    @Excel(name = "持有份额")
    @ApiModelProperty(value = "持有份额", example = "1")
    private BigDecimal quotient;

    /** 最小持有份额 */
    @Excel(name = "最小持有份额")
    @ApiModelProperty(value = "最小持有份额", example = "1")
    private BigDecimal quotientMin;

    /** 最大持有份额 */
    @Excel(name = "最大持有份额")
    @ApiModelProperty(value = "最大持有份额", example = "1")
    private BigDecimal quotientMax;

    /** 合理持有份额 */
    @Excel(name = "合理持有份额")
    @ApiModelProperty(value = "合理持有份额", example = "1")
    private BigDecimal quotientFit;

    /** 估值方式 */
    @Excel(name = "估值方式")
    @ApiModelProperty(value = "估值方式", example = "1")
    private String assessmentType;

    /** 成本估值指标 */
    @Excel(name = "成本估值指标")
    @ApiModelProperty(value = "成本估值指标", example = "1")
    private BigDecimal assessmen;

    /** 预计最低估值指标 */
    @Excel(name = "预计最低估值指标")
    @ApiModelProperty(value = "预计最低估值指标", example = "1")
    private BigDecimal assessmenMin;

    /** 预计最高估值指标 */
    @Excel(name = "预计最高估值指标")
    @ApiModelProperty(value = "预计最高估值指标", example = "1")
    private BigDecimal assessmenMax;

    /** 预计合理估值指标 */
    @Excel(name = "预计合理估值指标")
    @ApiModelProperty(value = "预计合理估值指标", example = "1")
    private BigDecimal assessmenFit;

    /** 安全边际, 合理指标与最低指标之间百分比计算买入点 */
    @Excel(name = "安全边际, 合理指标与最低指标之间百分比计算买入点")
    @ApiModelProperty(value = "安全边际, 合理指标与最低指标之间百分比计算买入点", example = "1")
    private String safeSpan;

    /** 开始持有时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始持有时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始持有时间", example = "2020-10-10")
    private Date startTime;

    /** 目标持有时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "目标持有时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "目标持有时间", example = "2020-10-10")
    private Date keepData;

    /** 时间弹性,超过定投目标范围 */
    @Excel(name = "时间弹性,超过定投目标范围")
    @ApiModelProperty(value = "时间弹性,超过定投目标范围", example = "1")
    private BigDecimal timeSpan;

    /** 计划id */
    @Excel(name = "计划id")
    @ApiModelProperty(value = "计划id", example = "1")
    private Long planId;

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
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setCode(String code)
    {
        this.code = code;
    }

    public String getCode()
    {
        return code;
    }
    public void setCostPrice(BigDecimal costPrice)
    {
        this.costPrice = costPrice;
    }

    public BigDecimal getCostPrice()
    {
        return costPrice;
    }
    public void setQuotient(BigDecimal quotient)
    {
        this.quotient = quotient;
    }

    public BigDecimal getQuotient()
    {
        return quotient;
    }
    public void setQuotientMin(BigDecimal quotientMin)
    {
        this.quotientMin = quotientMin;
    }

    public BigDecimal getQuotientMin()
    {
        return quotientMin;
    }
    public void setQuotientMax(BigDecimal quotientMax)
    {
        this.quotientMax = quotientMax;
    }

    public BigDecimal getQuotientMax()
    {
        return quotientMax;
    }
    public void setQuotientFit(BigDecimal quotientFit)
    {
        this.quotientFit = quotientFit;
    }

    public BigDecimal getQuotientFit()
    {
        return quotientFit;
    }
    public void setAssessmentType(String assessmentType)
    {
        this.assessmentType = assessmentType;
    }

    public String getAssessmentType()
    {
        return assessmentType;
    }
    public void setAssessmen(BigDecimal assessmen)
    {
        this.assessmen = assessmen;
    }

    public BigDecimal getAssessmen()
    {
        return assessmen;
    }
    public void setAssessmenMin(BigDecimal assessmenMin)
    {
        this.assessmenMin = assessmenMin;
    }

    public BigDecimal getAssessmenMin()
    {
        return assessmenMin;
    }
    public void setAssessmenMax(BigDecimal assessmenMax)
    {
        this.assessmenMax = assessmenMax;
    }

    public BigDecimal getAssessmenMax()
    {
        return assessmenMax;
    }
    public void setAssessmenFit(BigDecimal assessmenFit)
    {
        this.assessmenFit = assessmenFit;
    }

    public BigDecimal getAssessmenFit()
    {
        return assessmenFit;
    }
    public void setSafeSpan(String safeSpan)
    {
        this.safeSpan = safeSpan;
    }

    public String getSafeSpan()
    {
        return safeSpan;
    }
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }

    public Date getStartTime()
    {
        return startTime;
    }
    public void setKeepData(Date keepData)
    {
        this.keepData = keepData;
    }

    public Date getKeepData()
    {
        return keepData;
    }
    public void setTimeSpan(BigDecimal timeSpan)
    {
        this.timeSpan = timeSpan;
    }

    public BigDecimal getTimeSpan()
    {
        return timeSpan;
    }
    public void setPlanId(Long planId)
    {
        this.planId = planId;
    }

    public Long getPlanId()
    {
        return planId;
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
            .append("name", getName())
            .append("code", getCode())
            .append("costPrice", getCostPrice())
            .append("quotient", getQuotient())
            .append("quotientMin", getQuotientMin())
            .append("quotientMax", getQuotientMax())
            .append("quotientFit", getQuotientFit())
            .append("assessmentType", getAssessmentType())
            .append("assessmen", getAssessmen())
            .append("assessmenMin", getAssessmenMin())
            .append("assessmenMax", getAssessmenMax())
            .append("assessmenFit", getAssessmenFit())
            .append("safeSpan", getSafeSpan())
            .append("startTime", getStartTime())
            .append("keepData", getKeepData())
            .append("timeSpan", getTimeSpan())
            .append("planId", getPlanId())
            .toString();
    }
}
