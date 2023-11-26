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
 * 股票持仓计划对象 stock_position_plan
 *
 * @author lijingxiang
 * @date 2023-11-26
 */
@ApiModel(value = "ClassName", description = "股票持仓计划对象")
public class StockPositionPlan extends BaseEntity
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

    /** 触发价格 */
    @Excel(name = "触发价格")
    @ApiModelProperty(value = "触发价格", example = "1")
    private BigDecimal advicePrice;

    /** 网格持仓 */
    @Excel(name = "网格持仓")
    @ApiModelProperty(value = "网格持仓", example = "1")
    private BigDecimal griddingAmount;

    /** 网格持仓百分比 */
    @Excel(name = "网格持仓百分比")
    @ApiModelProperty(value = "网格持仓百分比", example = "1")
    private BigDecimal griddingPercent;

    /** 触发日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "触发日期", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "触发日期", example = "2020-10-10")
    private Date adviceDate;

    /** 定投持仓金额 */
    @Excel(name = "定投持仓金额")
    @ApiModelProperty(value = "定投持仓金额", example = "1")
    private BigDecimal adviceAmount;

    /** 定投持仓百分比 */
    @Excel(name = "定投持仓百分比")
    @ApiModelProperty(value = "定投持仓百分比", example = "1")
    private BigDecimal advicePercent;

    /** 交易类型 */
    @Excel(name = "交易类型")
    @ApiModelProperty(value = "交易类型", example = "1")
    private String tradeType;

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
    public void setAdvicePrice(BigDecimal advicePrice)
    {
        this.advicePrice = advicePrice;
    }

    public BigDecimal getAdvicePrice()
    {
        return advicePrice;
    }
    public void setGriddingAmount(BigDecimal griddingAmount)
    {
        this.griddingAmount = griddingAmount;
    }

    public BigDecimal getGriddingAmount()
    {
        return griddingAmount;
    }
    public void setGriddingPercent(BigDecimal griddingPercent)
    {
        this.griddingPercent = griddingPercent;
    }

    public BigDecimal getGriddingPercent()
    {
        return griddingPercent;
    }
    public void setAdviceDate(Date adviceDate)
    {
        this.adviceDate = adviceDate;
    }

    public Date getAdviceDate()
    {
        return adviceDate;
    }
    public void setAdviceAmount(BigDecimal adviceAmount)
    {
        this.adviceAmount = adviceAmount;
    }

    public BigDecimal getAdviceAmount()
    {
        return adviceAmount;
    }
    public void setAdvicePercent(BigDecimal advicePercent)
    {
        this.advicePercent = advicePercent;
    }

    public BigDecimal getAdvicePercent()
    {
        return advicePercent;
    }
    public void setTradeType(String tradeType)
    {
        this.tradeType = tradeType;
    }

    public String getTradeType()
    {
        return tradeType;
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
            .append("advicePrice", getAdvicePrice())
            .append("griddingAmount", getGriddingAmount())
            .append("griddingPercent", getGriddingPercent())
            .append("adviceDate", getAdviceDate())
            .append("adviceAmount", getAdviceAmount())
            .append("advicePercent", getAdvicePercent())
            .append("tradeType", getTradeType())
            .toString();
    }
}
