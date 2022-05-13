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
 * 经济事件追踪对象 finance_event_trace
 *
 * @author lijingxiang
 * @date 2022-05-11
 */
@ApiModel(value = "ClassName", description = "经济事件追踪对象")
public class FinanceEventTrace extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private Long id;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 发生时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发生时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty(value = "发生时间", example = "2020-10-10")
    private Date occurredTime;

    /** 数值 */
    @Excel(name = "数值")
    @ApiModelProperty(value = "数值", example = "1")
    private BigDecimal ammount;

    /** 较上期变化率 */
    @Excel(name = "较上期变化率")
    @ApiModelProperty(value = "较上期变化率", example = "1")
    private BigDecimal changeRate;

    /** 事件类型 */
    @Excel(name = "事件类型")
    @ApiModelProperty(value = "事件类型", example = "1")
    private String eventType;

    /** 事件名称 */
    @Excel(name = "事件名称")
    @ApiModelProperty(value = "事件名称", example = "1")
    private String name;

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
    public void setOccurredTime(Date occurredTime)
    {
        this.occurredTime = occurredTime;
    }

    public Date getOccurredTime()
    {
        return occurredTime;
    }
    public void setAmmount(BigDecimal ammount)
    {
        this.ammount = ammount;
    }

    public BigDecimal getAmmount()
    {
        return ammount;
    }
    public void setChangeRate(BigDecimal changeRate)
    {
        this.changeRate = changeRate;
    }

    public BigDecimal getChangeRate()
    {
        return changeRate;
    }
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }

    public String getEventType()
    {
        return eventType;
    }
    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
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
            .append("occurredTime", getOccurredTime())
            .append("ammount", getAmmount())
            .append("changeRate", getChangeRate())
            .append("eventType", getEventType())
            .append("name", getName())
            .toString();
    }
}
