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
 * @author lijingxiang
 * @date 2023-11-26
 */
@ApiModel(value = "ClassName", description = "持仓计划对象")
public class FinancePositionPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 资产名称 */
    @Excel(name = "资产名称")
    @ApiModelProperty(value = "资产名称", example = "1")
    private String name;

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
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
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
            .append("name", getName())
            .append("targetAmount", getTargetAmount())
            .toString();
    }
}
