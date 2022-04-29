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
 * @author lijing xiang
 * @date 2022-04-27
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

    /** 追踪价格 */
    @Excel(name = "追踪价格")
    @ApiModelProperty(value = "追踪价格", example = "1")
    private BigDecimal price;

    /** 追踪pb */
    @Excel(name = "追踪pb")
    @ApiModelProperty(value = "追踪pb", example = "1")
    private BigDecimal pb;

    /** 预计最低市净率 */
    @Excel(name = "预计最低市净率")
    @ApiModelProperty(value = "预计最低市净率", example = "1")
    private BigDecimal pbMin;

    /** 预计最高市净率 */
    @Excel(name = "预计最高市净率")
    @ApiModelProperty(value = "预计最高市净率", example = "1")
    private BigDecimal pbMax;

    /** 预计合理市净率 */
    @Excel(name = "预计合理市净率")
    @ApiModelProperty(value = "预计合理市净率", example = "1")
    private BigDecimal pbFit;

    /** 开始追踪时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始追踪时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "开始追踪时间", example = "2020-10-10")
    private Date startTime;

    /** 持有时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "持有时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "持有时间", example = "2020-10-10")
    private Date keepData;

    /** 开始持有金额 */
    @Excel(name = "开始持有金额")
    @ApiModelProperty(value = "开始持有金额", example = "1")
    private BigDecimal amount;

    /** 最小持有金额 */
    @Excel(name = "最小持有金额")
    @ApiModelProperty(value = "最小持有金额", example = "1")
    private BigDecimal amountMin;

    /** 最大持有金额 */
    @Excel(name = "最大持有金额")
    @ApiModelProperty(value = "最大持有金额", example = "1")
    private BigDecimal amountMax;

    /** 合理持有金额 */
    @Excel(name = "合理持有金额")
    @ApiModelProperty(value = "合理持有金额", example = "1")
    private BigDecimal amountFit;

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
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }

    public BigDecimal getPrice()
    {
        return price;
    }
    public void setPb(BigDecimal pb)
    {
        this.pb = pb;
    }

    public BigDecimal getPb()
    {
        return pb;
    }
    public void setPbMin(BigDecimal pbMin)
    {
        this.pbMin = pbMin;
    }

    public BigDecimal getPbMin()
    {
        return pbMin;
    }
    public void setPbMax(BigDecimal pbMax)
    {
        this.pbMax = pbMax;
    }

    public BigDecimal getPbMax()
    {
        return pbMax;
    }
    public void setPbFit(BigDecimal pbFit)
    {
        this.pbFit = pbFit;
    }

    public BigDecimal getPbFit()
    {
        return pbFit;
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
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
    public void setAmountMin(BigDecimal amountMin)
    {
        this.amountMin = amountMin;
    }

    public BigDecimal getAmountMin()
    {
        return amountMin;
    }
    public void setAmountMax(BigDecimal amountMax)
    {
        this.amountMax = amountMax;
    }

    public BigDecimal getAmountMax()
    {
        return amountMax;
    }
    public void setAmountFit(BigDecimal amountFit)
    {
        this.amountFit = amountFit;
    }

    public BigDecimal getAmountFit()
    {
        return amountFit;
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
            .append("price", getPrice())
            .append("pb", getPb())
            .append("pbMin", getPbMin())
            .append("pbMax", getPbMax())
            .append("pbFit", getPbFit())
            .append("startTime", getStartTime())
            .append("keepData", getKeepData())
            .append("amount", getAmount())
            .append("amountMin", getAmountMin())
            .append("amountMax", getAmountMax())
            .append("amountFit", getAmountFit())
            .toString();
    }
}
