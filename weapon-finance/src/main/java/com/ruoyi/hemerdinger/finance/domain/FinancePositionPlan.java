package com.ruoyi.hemerdinger.finance.domain;

    import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 持仓计划对象 finance_position_plan
 *
 * @author lijing xiang
 * @date 2022-04-27
 */
@ApiModel(value = "ClassName", description = "持仓计划对象")
public class FinancePositionPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 追踪id */
    @Excel(name = "追踪id")
    @ApiModelProperty(value = "追踪id", example = "1")
    private Long traceId;

    /** 资产名称 */
    @Excel(name = "资产名称")
    @ApiModelProperty(value = "资产名称", example = "1")
    private String name;

    /** 实际持仓 */
    @Excel(name = "实际持仓")
    @ApiModelProperty(value = "实际持仓", example = "1")
    private BigDecimal realityAmount;

    /** 计划持仓 */
    @Excel(name = "计划持仓")
    @ApiModelProperty(value = "计划持仓", example = "1")
    private BigDecimal targetAmount;

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
    public void setTraceId(Long traceId)
    {
        this.traceId = traceId;
    }

    public Long getTraceId()
    {
        return traceId;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
    public void setRealityAmount(BigDecimal realityAmount)
    {
        this.realityAmount = realityAmount;
    }

    public BigDecimal getRealityAmount()
    {
        return realityAmount;
    }
    public void setTargetAmount(BigDecimal targetAmount)
    {
        this.targetAmount = targetAmount;
    }

    public BigDecimal getTargetAmount()
    {
        return targetAmount;
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
            .append("traceId", getTraceId())
            .append("name", getName())
            .append("realityAmount", getRealityAmount())
            .append("targetAmount", getTargetAmount())
            .toString();
    }
}
